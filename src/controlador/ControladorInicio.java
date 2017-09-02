package controlador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import plugin.Meli;
import plugin.MeliUtils;

import com.ning.http.client.FluentStringsMap;

public class ControladorInicio extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Label code;
	
	// Bd ?
	private String accessToken;
	
	private FluentStringsMap params = new FluentStringsMap();
	private Meli m = new Meli(MeliUtils.APP_ID, MeliUtils.Secret_Key);
	
	@Override
	 public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		String cod = code.getValue().replaceFirst("code=", "");
		m.authorize(cod, MeliUtils.Auth_Redirect_Url);
		
		System.out.println(m.getAccessToken());
		if (m.getAccessToken() != null) {
			accessToken = m.getAccessToken();
		}
	}

}
