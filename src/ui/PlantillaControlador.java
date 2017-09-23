package ui;

import modelo.Plantilla;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dao.Bd;

import utils.Message;
import utils.Sesion;
import utils.ZkUtils;

public class PlantillaControlador  extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire private Window win;
	@Wire private Textbox txtPlantilla;
	@Wire private Label contCaracteres;
	
	private Sesion sesion = new Sesion();
	private Bd bd = new Bd();
	
	private int limit = 500;
	private String idUsuarioAux = "268910416";
	
	@Listen("onClick = #closeWin")
	public void closeWindow() {
		win.detach();
	}
	
	@Listen("onCreate = #txtPlantilla")
	public void initPlantillaTextLimit() {
		setTextPlantilla();
	}
	
	@Listen("onChanging = #txtPlantilla")
	public void plantillaTextLimit(InputEvent event) {
		int characters = event.getValue().length();
		
		String txt = characters > limit ? event.getValue().substring(0, limit - 1) : event.getValue();
		txtPlantilla.setValue(txt);
		
		setTextPlantilla();
	}
	
	private void setTextPlantilla() {
		int characters = txtPlantilla.getValue().length();
		contCaracteres.setValue(characters + " / " + limit + " caracteres");
	}
	
	@Listen("onClick = #btnPlantillaSuceess")
	public void addOrUpdatePlantilla() {
		if (!txtPlantilla.getValue().isEmpty()) {

//			String id = sesion.sesion.getAttribute("id").toString();
			String id = idUsuarioAux;

			Plantilla p = new Plantilla(id, txtPlantilla.getValue());
			if (bd.plantillaHasOne(id)) {
				bd.plantillaUpdate(p);
				messageAndClose(Message.PlantillaUpdate);
			} else {
				bd.plantillaInsert(p);
				messageAndClose(Message.PlantillaInsert);
			}
		} else {
			ZkUtils.campoRequerido(txtPlantilla);
		}
	}
	
	private void messageAndClose(String message) {
		ZkUtils.mensaje(message, 1, win);
	}

}
