package plugin;

import java.util.ArrayList;
import java.util.HashMap;

import modelo.AnswerRequest;
import modelo.SubRol;
import modelo.UserMeli;

public final class MeliUtils {

	public final static String APP_ID = "8051032385985753";
	public final static String Secret_Key = "Xng378w5qAUwMO9G5S4ksECgPkQl1oYy";
	public final static String Auth_Redirect_Url = "http://localhost:8080/meli/inicio.zul";
	public final static int LimitRequest = 50;
	
	public static HashMap<String, Object> arg(ArrayList<AnswerRequest> answerRequests) {
		HashMap<String, Object> arg  = new  HashMap<String, Object>();		
		arg.put("answerRequests", answerRequests);
		return arg;
	}
	
	public static HashMap<String, Object> argDelete(String answerRequests, String title, String controller) {
		HashMap<String, Object> arg  = new  HashMap<String, Object>();		
		arg.put("id", answerRequests);
		arg.put("titulo", title);
		arg.put("controller", controller);
		return arg;
	}
	
	public static HashMap<String, Object> argPlantilla(String answerRequests) {
		HashMap<String, Object> arg  = new  HashMap<String, Object>();		
		arg.put("texto", answerRequests);
		return arg;
	}
	
	public static HashMap<String, Object> argTag(String btnSuceess, String title, String id,
												String nombre, String descripcion, String texto) {
		HashMap<String, Object> arg  = new  HashMap<String, Object>();		
		arg.put("btnSuccess", btnSuceess);
		arg.put("title", title);
		arg.put("id", id);
		arg.put("nombde", nombre);
		arg.put("descripcion", descripcion);
		arg.put("texto", texto);
		return arg;
	}
	
	public static HashMap<String, Object> argUsuario(String btnSuceess, String title, UserMeli u) {
		HashMap<String, Object> arg  = new  HashMap<String, Object>();		
		arg.put("btnSuccess", btnSuceess);
		arg.put("title", title);
		arg.put("id", u.getId());
		arg.put("nombre", u.getNombre());
		arg.put("idMeli", u.getId_meli());
		arg.put("email", u.getEmail());
		return arg;
	}
	
	public static HashMap<String, Object> argUsuarioDetalle(UserMeli u) {
		HashMap<String, Object> arg  = new  HashMap<String, Object>();		
		arg.put("user", u);
		return arg;
	}
	
	public static HashMap<String, Object> argRol(String btnSuccess, String title, SubRol r) {
		HashMap<String, Object> arg  = new  HashMap<String, Object>();		
		arg.put("rol", r);
		arg.put("title", title);
		arg.put("btnSuccess", btnSuccess);
		return arg;
	}
	
}
