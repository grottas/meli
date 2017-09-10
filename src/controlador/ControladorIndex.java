package controlador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import plugin.Meli;
import plugin.MeliUtils;
import utils.ZkUtils;

public class ControladorIndex extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Label scheme;
	@Wire private Label nombrePuerto;
	@Wire private Label puerto;
	@Wire private Label nombreProyecto;
	
	@Listen("onClick = #meli")
	public void mostrarGridEstudiantes() {
		Meli m = new Meli(MeliUtils.APP_ID, MeliUtils.Secret_Key);
		
		String redirectUrl = m.getAuthUrl(MeliUtils.Auth_Redirect_Url, Meli.AuthUrls.MLV);
		ZkUtils.redireccion(redirectUrl);
	}
	
}