package controlador;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import modelo.Producto;
import modelo.Question;
import modelo.User;
import modelo.UserCurrent;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import plugin.Meli;
import plugin.MeliException;
import plugin.MeliUtils;
import utils.ParseJson;
import utils.Sesion;
import utils.ZkUtils;

import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;

public class ControladorInicio extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Label code;
	@Wire private Listbox listQuestions;
	
	private static Meli m = new Meli(MeliUtils.APP_ID, MeliUtils.Secret_Key);
	private FluentStringsMap params = new FluentStringsMap();
	private Sesion sesion = new Sesion();
	private static Map<String, User> users = new HashMap<String, User>();
	private static Map<String, Producto> products = new HashMap<String, Producto>();
	
	private String tokenAux = "APP_USR-8051032385985753-090620-1183cb97ce8659af93e9b1f310176c17__A_E__-268910416";
	private String idUsuarioAux = "268910416";
	
	@Override
	 public void doAfterCompose(Component comp) throws Exception, ExecutionException {
		super.doAfterCompose(comp);
		
		/*
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
			} else {
				ZkUtils.problemasInternet();
				return;
			}
		}
		System.out.println(sesion.sesion.getAttribute("accessToken"));
		System.out.println(sesion.sesion.getAttribute("id"));
		 */
		createListQuestions();
	}

	private void createListQuestions() throws MeliException, IOException, ExecutionException {
		
		// Estas dos lineas son solo para modo TEST.
		params.add("access_token", tokenAux);
		params.add("seller_id", idUsuarioAux);
		
//		params.add("seller_id", String.valueOf( sesion.sesion.getAttribute("id") ));
		
		Response response = m.get("/questions/search?", params);	
		System.out.println(response.getResponseBody());
		if (response.getStatusCode() == 200) {
			List<Question> questions = ParseJson.questions(response.getResponseBody());
			
			if (questions.size() > 0) {
				listQuestions.setModel(new ListModelList<Question> (questions));
			}
		} else {
			ZkUtils.problemasInternet();
			return;
		}
	}
	
	public static String getBuscarClientePorId(String id, String retorno) throws MeliException, IOException, ExecutionException {
		User user = null;
		if (users.containsKey(id)) {
			user = users.get(id);
		} else {
			Response response = m.get("/users/" + id);		
			if (response.getStatusCode() == 200) {
				
				user = ParseJson.user(response.getResponseBody());
				users.put(id, user);
			} else {
				return null;
			}
		}		
		
		switch (retorno) {
			case "nombre": return user.getNickname();
			case "url": return user.getPermalink();
			case "puntos": return user.getPoints();
			default: return null;
		}
	}
	
	public static String getBuscarProductoPorId(String id, String retorno) throws MeliException, IOException {
		Producto producto = null;
		if (products.containsKey(id)) {
			producto = products.get(id);
		} else {
			Response response = null;
			try {
				response = m.get("/items/" + id);	
			} catch (Exception e) {
				ZkUtils.mensaje("Problemas con la conexion a meli", 2, null);
				return null;
			}	
			if (response.getStatusCode() == 200) {
				
				producto = ParseJson.item(response.getResponseBody());
				products.put(id, producto);
			} else {
				return null;
			}
		}		

		switch (retorno) {
			case "nombre": return producto.getNombre();
			case "imagen": return producto.getImagen();
			case "url": return producto.getPermalink();
			case "precio": return producto.getMoneda() + " " + ZkUtils.priceFormat( producto.getPrecio() );
			default: return null;
		}
	}
	
	public void mostrarRespuesta() {
		System.out.println(listQuestions.getSelectedIndex());
	}
 
}
