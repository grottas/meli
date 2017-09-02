package controlador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import plugin.Meli;
import plugin.MeliUtils;

public class ControladorIndex extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Label scheme;
	@Wire private Label nombrePuerto;
	@Wire private Label puerto;
	@Wire private Label nombreProyecto;
	
	@Listen("onClick = #meli")
	public void mostrarGridEstudiantes() {
		// Hacemos la 1ra conexion con MLV para que nos regresen el token
		Meli m = new Meli(MeliUtils.APP_ID, MeliUtils.Secret_Key);
		
		String redirectUrl = m.getAuthUrl(scheme.getValue()+"://"+
										  nombrePuerto.getValue()+":"+
										  puerto.getValue()+
										  nombreProyecto.getValue()+
										  "/inicio.zul", Meli.AuthUrls.MLV);
		Executions.sendRedirect(redirectUrl);

	}
	
}