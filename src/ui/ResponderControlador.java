package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import modelo.Plantilla;
import modelo.Tag;

import org.zkoss.zhtml.Button;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Combobox;

import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;

import dao.Bd;

import plugin.Meli;
import plugin.MeliException;
import plugin.MeliUtils;

import utils.EventQueuesUtils;
import utils.Message;
import utils.Sesion;
import utils.ZkUtils;


public class ResponderControlador extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire private Window win;
	@Wire private Hbox answerRequests;
	@Wire private Combobox comboRespuesta;
	@Wire private Textbox txtRespuesta;
	@Wire private Button closeWin;
	@Wire private Button btnResponderQuestion;
	@Wire private Label labelProgress;
	@Wire private Progressmeter progressmeter;
	@Wire private Checkbox loadPlantilla;
	
	private List<Comboitem> combitos = new ArrayList<Comboitem>();
	private static Meli m = new Meli(MeliUtils.APP_ID, MeliUtils.Secret_Key);
	private FluentStringsMap params = new FluentStringsMap();
	private Sesion sesion = new Sesion();
	private Bd bd = new Bd();
	
	private String tokenAux = "APP_USR-8051032385985753-092314-967900ea35260de7be221172aa3ace82__D_F__-268910416";
	private String idUsuarioAux = "268910416";
	
	private void cargarTags() {
		System.out.println("TAGS");
		combitos.clear();
		ZkUtils.removerTodo(comboRespuesta);
		for (Tag tag : bd.tagSelectAll()) {
			Comboitem combito = new Comboitem( tag.getNombre() + " " + tag.getDescripcion() );
			combito.setValue( tag.getTexto() );
			comboRespuesta.appendChild(combito);
			combitos.add(combito);
		}
		ZkUtils.crearComponente(comboRespuesta, this);
	}

	@Listen("onClick = #closeWin")
	public void closeWindow() {
		if (!closeWin.getSclass().contains("disabled"))
			win.detach();
	}
	
	@Listen("onClick = #btnResponderQuestion")
	public void responder() throws MeliException, InterruptedException {
		if (!btnResponderQuestion.getSclass().contains("disabled"))
			if (!txtRespuesta.getValue().isEmpty()) {
				// Active progress
				progressmeter.setVisible(true);
				// Bloquear botones
				btnResponderQuestion.setSclass(btnResponderQuestion.getSclass() + " disabled");
				closeWin.setSclass(closeWin.getSclass() + " disabled");
				
				params.clear(); 
				params.add("access_token", sesion.sesion.getAttribute("accessToken").toString());
//				params.add("access_token", tokenAux);
				sendMessage(0);
			} else {
				ZkUtils.campoRequerido(txtRespuesta);
			}
	}
	
	private void sendMessage(int i) throws MeliException, InterruptedException {
		labelProgress.setValue( Message.sendResponse(i + 1, answerRequests.getChildren().size()) );
		progressmeter.setValue( (int) (((double) (i + 1) / answerRequests.getChildren().size()) * 100) );
		
		Div div = (Div) answerRequests.getChildren().get(i);
		Label id_questions = (Label) div.getChildren().get(1);
		
		String json = "{ question_id: " + id_questions.getValue() + ",text: \"" + txtRespuesta.getValue() + "\"}";
		
		Response response = m.post("/answers?", params, json);
		
		if (response != null) {
			if (response.getStatusCode() == 200) {
				deleteItemAtList(id_questions.getValue());
				continueSendMessage(i);
			} else {
				ZkUtils.problemasInternet();
			}
		} else {
			ZkUtils.problemasInternet();
		}
	}

	private void continueSendMessage(int i) throws MeliException, InterruptedException {
		i++;
		if (i < answerRequests.getChildren().size())  {
			sendMessage(i);
		} else {
			ZkUtils.mensaje(Message.ResponseSuceess, 1, win);
			win.detach();
		}
	}
	
	private void deleteItemAtList(String index) {
		if (answerRequests.getChildren().size() == 1) {
			EventQueues.lookup(EventQueuesUtils.SimpleMessage, EventQueues.DESKTOP, true)
        	.publish(new Event ("simple change"));
		} else {
			EventQueues.lookup(EventQueuesUtils.MultipleMessage, EventQueues.DESKTOP, true)
        	.publish(new Event (index));
		}
	}
	
	@Listen("onSelect = #comboRespuesta")
	public void selectTags() {
		Comboitem combito = comboRespuesta.getSelectedItem();
		
		String respOld = txtRespuesta.getValue();
		String tag = respOld.substring(respOld.lastIndexOf("@"));
		int tagPosition = respOld.lastIndexOf("@");
		tag = tag.split(" ")[0];
		String resp = respOld.replace( tag , "");
		txtRespuesta.setValue( resp.substring(0, tagPosition) + combito.getValue() + resp.substring(tagPosition));
		
		comboRespuesta.setValue("");
	}
	
	private boolean validarArroba(String value) {
		if (value.length() > 0) {
//			String arroba = value.substring(value.length() - 1);
//			return arroba.equals("@");
			return value.indexOf("@") != -1;
		}
		return false;
	}
	
	private void autoCompletado(String value) {
		if (value.lastIndexOf("@") != -1) {
			ZkUtils.removerTodo(comboRespuesta);
			
//			String v = value.substring( value.lastIndexOf("@") );
			
			String v = value.substring(value.lastIndexOf("@"));
			v = v.split(" ")[0];
			
			Pattern p = Pattern.compile("(.*)" + v + "(.*)");
			for (Comboitem combito : combitos) {
				if (p.matcher( combito.getLabel() ).matches()) {
					comboRespuesta.appendChild(combito);
				}
			}
			ZkUtils.crearComponente(comboRespuesta, this);
		}
	}
	
	@Listen("onChanging = #txtRespuesta")
	public void openTags(InputEvent event) {		
		if (validarArroba(event.getValue())) {
			// Load Tags
			cargarTags();
			
			// Open Tags
			if (comboRespuesta.isOpen()) {
				comboRespuesta.setOpen(false);
			}
			comboRespuesta.setOpen(true);			
		}
		// Autocomplete Tags
		autoCompletado(event.getValue());
	}
	
	public boolean showPlantilla() {
//		String id = idUsuarioAux;
		String id = sesion.sesion.getAttribute("id").toString();
		return bd.plantillaHasOne(id);
	}
	
	@Listen("onCheck = #loadPlantilla")
	public void checkCargarPlantilla() {
//		String id = idUsuarioAux;
		String id = sesion.sesion.getAttribute("id").toString();
		
		Plantilla p = bd.plantillaSelectById(id);
		String txt = "";
		
		if (loadPlantilla.isChecked()) {
			txt = txtRespuesta.getValue() + p.getText();
		} else {
			txt = txtRespuesta.getValue().trim().replace(p.getText(), "");
		}
		txtRespuesta.setValue(txt);
	}
}
