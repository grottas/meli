package ui;

import modelo.Tag;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;
import org.zkoss.zul.Combobox;

import utils.ZkUtils;


public class ResponderControlador extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire private Window win;
	@Wire private Hbox answerRequests;
	@Wire private Combobox txtRespuesta;

	
	private void cargarTags() {
		System.out.println("TAGS");
		for (Tag tag : Tag.defaultTags()) {
			Comboitem combito = new Comboitem( tag.getNombre() + " " + tag.getDescripcion() );
			combito.setValue( tag.getTexto() );
			txtRespuesta.appendChild(combito);			
		}
		ZkUtils.crearComponente(txtRespuesta, this);
	}

	@Listen("onClick = #closeWin")
	public void closeWindow() {
		win.detach();
	}
	
	@Listen("onClick = #btnResponderQuestion")
	public void responder() {
		for (int i = 0; i < answerRequests.getChildren().size(); i++) {
			Div div = (Div) answerRequests.getChildren().get(i);
			Label id_questions = (Label) div.getChildren().get(1);
			System.out.println(id_questions.getValue());
		}
	}
	
	@Listen("onSelect = #txtRespuesta")
	public void selectTags() {
		Comboitem combito = txtRespuesta.getSelectedItem();
		System.out.println(combito.getValue().toString());
		
		String txt = txtRespuesta.getValue();
		txt = txt.replace(combito.getLabel(), "");
		txt = combito.getValue().toString();
		System.out.println(txt);
	}
	
	@Listen("onOK = #txtRespuesta")
	public void openTags() {
		ZkUtils.removerTodo(txtRespuesta);
		cargarTags();
		
		System.out.println(txtRespuesta.isOpen());
		if (txtRespuesta.isOpen()) {
			txtRespuesta.setOpen(false);
			txtRespuesta.setOpen(true);
		} else {
			txtRespuesta.setOpen(true);
		}
		
		System.out.println(txtRespuesta.getValue());
	}

}
