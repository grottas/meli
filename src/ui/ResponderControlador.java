package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import modelo.Tag;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Combobox;

import utils.ZkUtils;


public class ResponderControlador extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire private Window win;
	@Wire private Hbox answerRequests;
	@Wire private Combobox comboRespuesta;
	@Wire private Textbox txtRespuesta;
	
	private List<Comboitem> combitos = new ArrayList<Comboitem>();

	private void cargarTags() {
		System.out.println("TAGS");
		combitos.clear();
		ZkUtils.removerTodo(comboRespuesta);
		for (Tag tag : Tag.defaultTags()) {
			Comboitem combito = new Comboitem( tag.getNombre() + " " + tag.getDescripcion() );
			combito.setValue( tag.getTexto() );
			comboRespuesta.appendChild(combito);
			combitos.add(combito);
		}
		ZkUtils.crearComponente(comboRespuesta, this);
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
	
	@Listen("onSelect = #comboRespuesta")
	public void selectTags() {
		Comboitem combito = comboRespuesta.getSelectedItem();
		
		String respOld = txtRespuesta.getValue();
		String resp = respOld.replace( respOld.substring( respOld.lastIndexOf("@") ) , "");
		txtRespuesta.setValue( resp + combito.getValue() );
		
		comboRespuesta.setValue("");
	}
	
	private boolean validarArroba(String value) {
		if (value.length() > 0) {
			String arroba = value.substring(value.length() - 1);
			return arroba.equals("@");
		}
		return false;
	}
	
	private void autoCompletado(String value) {
		if (value.lastIndexOf("@") != -1) {
			ZkUtils.removerTodo(comboRespuesta);
			
			String v = value.substring( value.lastIndexOf("@") );
			Pattern p = Pattern.compile("(.*)" + v + "(.*)");
			for (Comboitem combito : combitos) {
				if (p.matcher( combito.getLabel() ).matches()) {
					comboRespuesta.appendChild(combito);
				}
			}
			ZkUtils.crearComponente(comboRespuesta, this);
		}
	}
	
	@Listen("onChanging = #txtRespuesta")
	public void openTags(InputEvent event) {		
		if (validarArroba(event.getValue())) {
			// Load Tags
			cargarTags();
			
			// Open Tags
			if (comboRespuesta.isOpen()) {
				comboRespuesta.setOpen(false);
			}
			comboRespuesta.setOpen(true);			
		}
		// Autocomplete Tags
		autoCompletado(event.getValue());
		
	}
}
