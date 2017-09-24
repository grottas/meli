package controlador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import utils.ZkUtils;

public class ControladorIndex extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Label scheme;
	@Wire private Label nombrePuerto;
	@Wire private Label puerto;
	@Wire private Label nombreProyecto;
	
	@Listen("onClick = #meli")
	public void showModalLogIn() {
		ZkUtils.crearModal("modal/login.zul", null);
	}
	
}