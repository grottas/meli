package modal;

import modelo.Tag;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dao.Bd;

import utils.EventQueuesUtils;
import utils.Sesion;
import utils.ZkUtils;

public class TagController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire private Window win;
	@Wire private Label tipo;
	@Wire private Label idTag;
	@Wire private Textbox txtEtiqueta;
	@Wire private Textbox txtDescripcion;
	@Wire private Textbox txtTexto;
	
	private Sesion sesion = new Sesion();
	private Bd bd = new Bd();
	
	@Listen("onClick = #closeWin")
	public void closeWindow() {
		win.detach();
	}
	
	@Listen("onClick = #btnSuccess")
	public void btnSuccess() {
		if (txtEtiqueta.getValue().isEmpty())
			ZkUtils.campoRequerido(txtEtiqueta);
		else if (txtDescripcion.getValue().isEmpty())
			ZkUtils.campoRequerido(txtDescripcion);
		else if (txtTexto.getValue().isEmpty())
			ZkUtils.campoRequerido(txtTexto);
		else
			continueToSuccees();
	}

	private void continueToSuccees() {
		if (!validarArroba()) {
			ZkUtils.mensaje("Debe contener un prefijo @", 2, txtEtiqueta);
			return;
		}
		
		if (tipo.getValue().equals("Crear")) {			
			Tag t = bd.tagSelectByIdMeliAndNombre(sesion.sesion.getAttribute("id").toString(), txtEtiqueta.getValue().toLowerCase());
			if (t == null) {
				newTag();				
			} else {
				ZkUtils.mensaje_short("Etiqueta duplicada", 2, null);
				return;
			}
		} else {
			updateTag();
		}
		EventQueues.lookup(EventQueuesUtils.TagChange, EventQueues.DESKTOP, true)
        	.publish(new Event ( "" ));
		win.detach();
	}
	
	private boolean validarArroba() {
		String arroba = txtEtiqueta.getValue();
		return arroba.indexOf("@") == 0 && arroba.indexOf("@") == arroba.lastIndexOf("@");
	}

	private void updateTag() {
		bd.tagUpdate( getTag() );
		ZkUtils.mensaje_short("Etiqueta actualizada", 1, null);
	}

	private void newTag() {
		bd.tagInsert( getTag() );
		ZkUtils.mensaje_short("Etiqueta creada", 1, null);
	}
	
	private Tag getTag() {
		return new Tag(txtEtiqueta.getValue().toLowerCase(), txtDescripcion.getValue(), 
						txtTexto.getText(), idTag.getValue(), 
						sesion.sesion.getAttribute("id").toString());
	}

}
