package plugin;

import java.util.ArrayList;
import java.util.HashMap;

import modelo.AnswerRequest;

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
	
}
