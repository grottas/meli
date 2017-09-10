package controlador;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

import modelo.Producto;
import modelo.Question;
import modelo.User;
import modelo.UserCurrent;

import org.zkoss.zhtml.Button;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import plugin.Meli;
import plugin.MeliException;
import plugin.MeliUtils;
import utils.ParseJson;
import utils.Sesion;
import utils.ZkUtils;

import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;

import comparator.CompareQuestionsByDate;
import comparator.CompareQuestionsByItem;
import comparator.CompareQuestionsByReverseDate;
import comparator.CompareQuestionsByUser;

public class ControladorInicio extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Label code;
	@Wire private Listbox listQuestions;
	@Wire private Button sortUsuario;
	@Wire private Button sortFecha;
	@Wire private Button sortPublicacion;
	@Wire private Textbox filterUsuario;
	@Wire private Combobox filterPublicacion;
	@Wire private Datebox filterFecha;
	
	@Wire private Div filterUsuarioContent;	
	@Wire private Div filterPublicacionContent;
	@Wire private Div filterFechaContent;
	
	private static Meli m = new Meli(MeliUtils.APP_ID, MeliUtils.Secret_Key);
	private FluentStringsMap params = new FluentStringsMap();
	private Sesion sesion = new Sesion();
	
	private boolean validateSearchQuestions = true;
	private int totalQuestions = 0;
	private List<Question> questions = new ArrayList<Question>();
	private static Map<String, User> users = new HashMap<String, User>();
	private static Map<String, Producto> products = new HashMap<String, Producto>();
	
	private String tokenAux = "APP_USR-8051032385985753-090921-552b238e7acb570e24b79cc7ae726ef1__H_G__-268910416";
	private String idUsuarioAux = "268910416";
	
	@Override
	 public void doAfterCompose(Component comp) throws Exception, ExecutionException {
		super.doAfterCompose(comp);
		
		
		if (sesion.sesion.getAttribute("id") == null) {
			System.out.println("ACTIVO CON LA SESION");
			String cod = code.getValue().replaceFirst("code=", "");
			m.authorize(cod, MeliUtils.Auth_Redirect_Url);
			System.out.println("token: " + m.getAccessToken());
			
			params.clear();
			params.add("access_token", m.getAccessToken());
			Response response = m.get("/users/me?", params);
			if (response.getStatusCode() == 200) {
				UserCurrent usu = ParseJson.me(response.getResponseBody(), m.getAccessToken(),  m.getRefreshToken());
				sesion.logIn(usu);		
				
				params.add("access_token", sesion.sesion.getAttribute("accessToken").toString());
				params.add("seller_id", sesion.sesion.getAttribute("id").toString());
				params.add("status", "UNANSWERED");
				createListQuestions(0, 0);
				
			} else {
				ZkUtils.problemasInternet();
				return;
			}
		} else {
			params.add("access_token", sesion.sesion.getAttribute("accessToken").toString());
			params.add("seller_id", sesion.sesion.getAttribute("id").toString());
			
			
			// Estas dos lineas son solo para modo TEST.
//			params.add("access_token", tokenAux);
//			params.add("seller_id", idUsuarioAux);
					
//			params.add("seller_id", String.valueOf( sesion.sesion.getAttribute("id") ));
			params.add("status", "UNANSWERED");
			createListQuestions(0, 0);
		}
		System.out.println(sesion.sesion.getAttribute("accessToken"));
		System.out.println(sesion.sesion.getAttribute("id"));
	}

	private void createListQuestions(int offset, int i) throws MeliException, IOException, ExecutionException, ParseException {
		if (params.containsKey("offset")) {
			params.replaceWith("offset", String.valueOf(offset));
			
		} else {
			params.add("offset", String.valueOf(offset));
		}
		
		Response response = m.get("/questions/search?", params);	

		if (response.getStatusCode() == 200) {
			
			if (validateSearchQuestions) {
				validateSearchQuestions = !validateSearchQuestions;
				totalQuestions = ParseJson.totalQuestions(response.getResponseBody());				
			}
			
			if (totalQuestions <= MeliUtils.LimitRequest) {
				questions.addAll( ParseJson.questions(response.getResponseBody()) );
				searchFrom(0);
				
			} else {
				questions.addAll( ParseJson.questions(response.getResponseBody()) );
				totalQuestions -= MeliUtils.LimitRequest;
				i++;	
				createListQuestions(i * MeliUtils.LimitRequest, i);
			}
		} else {
			ZkUtils.problemasInternet();
			return;
		}	
	}
	
	@Listen("onClick = #reloadQuestions")
	public void reloadListQuestions() throws MeliException, IOException, ExecutionException, ParseException {
		validateSearchQuestions = true;
		questions.clear();
		createListQuestions(0, 0);
	}
	
	public void searchFrom(int index) throws MeliException, IOException, ExecutionException {
		Question q = questions.get(index);
		User user = null;
		
		if (users.containsKey(q.getFrom().getId())) {
			user = users.get(q.getFrom().getId());
			q.setSeller(user);
			questions.set(index, q);
			
			continueSearchFrom(index);
		} else {
			
			Response response = m.get("/users/" + q.getFrom().getId());		
			if (response.getStatusCode() == 200) {
				
				user = ParseJson.user(response.getResponseBody());
				users.put(q.getFrom().getId(), user);
				q.setSeller(user);
				questions.set(index, q);
				
				continueSearchFrom(index);
			}
		}
	}
	
	private void continueSearchFrom(int index) throws MeliException, IOException, ExecutionException {
		index++;
		if (index < questions.size())  {
			searchFrom(index);
		} else {
			searchItem(0);
		}
	}
	
	public void searchItem(int index) throws MeliException, IOException, ExecutionException {
		Question q = questions.get(index);
		Producto producto = null;
		
		if (products.containsKey(q.getItem_id())) {
			producto = products.get(q.getItem_id());
			q.setItem(producto);
			questions.set(index, q);
			
			continueSearchItem(index);
		} else {
			Response response = m.get("/items/" + q.getItem_id());		
			if (response.getStatusCode() == 200) {
				
				producto = ParseJson.item(response.getResponseBody());
				products.put(q.getItem_id(), producto);
				q.setItem(producto);
				questions.set(index, q);
				
				continueSearchItem(index);
			}
		}
	}
	
	private void continueSearchItem(int index) throws MeliException, IOException, ExecutionException {
		index++;
		if (index < questions.size())  {
			searchItem(index);
		} else {
			setComboPublicacion();
			setListModel();
		}
	}

	private void setComboPublicacion() {
		for (Map.Entry<String, Producto> p : products.entrySet()) {
			Comboitem combito = new Comboitem( p.getValue().getNombre() );
			filterPublicacion.appendChild(combito);			
		}
		ZkUtils.crearComponente(filterPublicacion, this);
	}

	@Listen("onClick = #sortUsuario; onClick = #sortFecha; onClick = #sortPublicacion")
	public void sort(Event e) {
		if (e.getTarget() instanceof Button) {
			Button button = (Button) e.getTarget();
			
			activeSortsButton(button);
						
			switch (button.getId()) {
				case "sortUsuario":
					visiblefilterContent(filterUsuarioContent);
					sortByUsuario();
					break;
				case "sortFecha":
					visiblefilterContent(filterFechaContent);
					if (button.getSclass().equals("btn btn-success")) {
						sortByDate();
					} else {
						sortByInverseDate();
					}
					break;
				case "sortPublicacion":
					visiblefilterContent(filterPublicacionContent);
					sortByPublicacion();
					break;
			}
		}
	}

	public void visiblefilterContent(Div visible) {
		filterPublicacionContent.setVisible(false);
		filterUsuarioContent.setVisible(false);
		filterFechaContent.setVisible(false);
		
		visible.setVisible(true);
	}

	private void activeSortsButton(Button button) {		
		if (button.getId().equals("sortFecha") && !(button.getSclass().equals("btn btn-white"))) {			
			if (button.getSclass().equals("btn btn-success")) {
				// info - order
				button.setSclass("btn btn-info");
			} else {
				// back status - reverse
				button.setSclass("btn btn-success");
			}
		} else {
			sortUsuario.setSclass("btn btn-white");
			sortFecha.setSclass("btn btn-white");
			sortPublicacion.setSclass("btn btn-white");
			
			filterPublicacion.setValue("");
			filterUsuario.setValue("");
			filterFecha.setValue(new Date());
			
			button.setSclass("btn btn-success");
		}
	}
	
	private void sortByInverseDate() {
		Collections.sort(questions, new CompareQuestionsByReverseDate());
		if (filterFecha.getValue() == null) {
			setListModel();
		} else {
			setListModelFilter( filterByDate() );
		}
	}

	private void sortByDate() {
		Collections.sort(questions, new CompareQuestionsByDate());

		if (filterFecha.getValue() == null) {
			setListModel();
		} else {
			setListModelFilter( filterByDate() );
		}
	}
	
	private void sortByPublicacion() {
		Collections.sort(questions, new CompareQuestionsByItem());
		if (filterPublicacion.getValue().trim().equals("")) {
			setListModel();
		} else {
			setListModelFilter( filterByPublicacion() );
		}
	}

	private void sortByUsuario() {
		Collections.sort(questions, new CompareQuestionsByUser());		
		if (filterUsuario.getValue().trim().equals("")) {
			setListModel();
		} else {
			setListModelFilter( filterByUsuario() );
		}
		
	}
	
	@Listen("onClick = #btnFilterUsuario; onClick = #btnFilterPublicacion; onOK = #filterFecha; onChange = #filterFecha")
	public void filter(Event e) {
		if (e.getTarget() instanceof Button) {
			Button button = (Button) e.getTarget();
						
			switch (button.getId()) {
				case "btnFilterUsuario":
					setListModelFilter( filterByUsuario() );
					break;
				case "btnFilterPublicacion":
					setListModelFilter( filterByPublicacion() );
					break;
			}
		} else if (e.getTarget() instanceof Datebox) {			
			setListModelFilter( filterByDate() );
		}
	}
	
	public ArrayList<Question> filterByDate() {		
		if (filterFecha.getValue() == null) {
			return (ArrayList<Question>) questions;
		} else {
			ArrayList<Question> matches = new ArrayList<Question>();
			Pattern p = Pattern.compile("(.*)" + ZkUtils.dateMacht.format(filterFecha.getValue() ) + "(.*)");
	
			for (Question q : questions) {
				if (p.matcher(ZkUtils.dateMacht.format(q.getDate_created())).matches()) {
					matches.add(q);
				}
			}
			  return matches;
		}
	}
	
	public ArrayList<Question> filterByPublicacion() {		
		ArrayList<Question> matches = new ArrayList<Question>();
		
		Pattern p = Pattern.compile("(.*)" + filterPublicacion.getValue().trim().toUpperCase() + "(.*)");

		for (Question q : questions) {
			if (p.matcher(q.getItem().getNombre().toUpperCase()).matches()) {
				matches.add(q);
			}
		}
		  return matches;
	}
	
	public ArrayList<Question> filterByUsuario() {
		ArrayList<Question> matches = new ArrayList<Question>();

		Pattern p = Pattern.compile("(.*)" + filterUsuario.getValue().trim().toUpperCase() + "(.*)");

		for (Question q : questions) {
			if (p.matcher(q.getSeller().getNickname().toUpperCase()).matches()) {
				matches.add(q);
			}
		}
		return matches;
	}
	
	private void setListModel() {
		listQuestions.setModel(new ListModelList<Question> (questions));
	}
	
	private void setListModelFilter(ArrayList<Question> questionsAux) {
		listQuestions.setModel(new ListModelList<Question> (questionsAux));
	}
	
	public String dateFormat(Date date) throws ParseException {
		return ZkUtils.dateToShow.format(date);
	}
	
	public void mostrarRespuesta() {
		System.out.println(listQuestions.getSelectedIndex());
	}
 
}
