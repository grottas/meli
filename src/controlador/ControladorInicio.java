package controlador;

import java.io.IOException;
import java.util.List;

import modelo.Question;
import modelo.UserCurrent;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import plugin.Meli;
import plugin.MeliException;
import plugin.MeliUtils;
import utils.ParseJson;
import utils.Sesion;

import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;

public class ControladorInicio extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Label code;
	@Wire private Listbox listQuestions;
	
	private Meli m = new Meli(MeliUtils.APP_ID, MeliUtils.Secret_Key);
	private FluentStringsMap params = new FluentStringsMap();

	private Sesion sesion = new Sesion();
	
	@Override
	 public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		if (sesion.sesion.getAttribute("id") == null) {
			String cod = code.getValue().replaceFirst("code=", "");
			m.authorize(cod, MeliUtils.Auth_Redirect_Url);
			
			params.clear();
			params.add("access_token", m.getAccessToken());
			Response response = m.get("/users/me?", params);
			UserCurrent usu = ParseJson.me(response.getResponseBody(), m.getAccessToken(),  m.getRefreshToken());
			sesion.logIn(usu);			
		}
		createListQuestions();
	}

	private void createListQuestions() throws MeliException, IOException {
		params.add("seller_id", sesion.sesion.getAttribute("id").toString());
		Response response = m.get("/questions/search?", params);		
		List<Question> questions = ParseJson.questions(response.getResponseBody());
		
		if (questions.size() > 0) {
			listQuestions.setModel(new ListModelList<Question> (questions));
		}
	}

}
