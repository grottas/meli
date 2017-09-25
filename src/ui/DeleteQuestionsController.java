package ui;

import java.io.IOException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import plugin.Meli;
import plugin.MeliException;
import plugin.MeliUtils;

import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;

import utils.EventQueuesUtils;
import utils.Message;
import utils.Sesion;
import utils.ZkUtils;

public class DeleteQuestionsController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire private Window win;
	@Wire private Label idQuestion;

	private static Meli m = new Meli(MeliUtils.APP_ID, MeliUtils.Secret_Key);
	private FluentStringsMap params = new FluentStringsMap();
	private Sesion sesion = new Sesion();
		
	public String textDeleteQuestions() {
		return Message.DeleteQuestion;
	}
	
	@Listen("onClick = #closeWin")
	public void closeWindow() {
		win.detach();
	}
	
	@Listen("onClick = #btnDeleteSuceess")
	public void delete() throws MeliException, IOException {
		params.clear(); 
		params.add("access_token", sesion.sesion.getAttribute("accessToken").toString());
//		params.add("access_token", tokenAux);
		
		String id = idQuestion.getValue();
		
		Response response = m.delete("/questions/" + id + "?", params);
		
		if (response != null) {
			System.out.println(response.getStatusCode() + " " + response.getResponseBody());
			if (response.getStatusCode() == 200) {
				EventQueues.lookup(EventQueuesUtils.MultipleMessage, EventQueues.DESKTOP, true)
	        		.publish(new Event ( id ));
				closeWindow();
				ZkUtils.mensaje(Message.DeleteQuestionSuceess, 1, null);
			} else {
				ZkUtils.problemasInternet();
			}
		} else {
			ZkUtils.problemasInternet();
		}
	}

}
