package modal;

import modelo.UserMeli;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dao.Bd;

import utils.InputUtils;
import utils.Message;
import utils.Sesion;
import utils.ZkUtils;

public class ConfigurarCuentaController extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Window win;
	@Wire private Textbox txtEmail;
	@Wire private Textbox txtClave;
	@Wire private Textbox txtReClave;
	
	private Sesion sesion = new Sesion();
	private Bd bd = new Bd();
	
	@Listen("onCreate =#win")
	public void create() {		
		UserMeli u = sesion.getUserMeli();
		if (u == null) {
			u = bd.userSelectByEmail(sesion.sesion.getAttribute("email").toString());
			txtEmail.setDisabled(true);
		}
		
		txtEmail.setValue( u.getEmail() );
	}
	
	@Listen("onClick = #closeWin")
	public void closeWindow() {
		win.detach();
	}
	
	@Listen("onClick = #btnSuccess")
	public void btnSuccess() {
		if (txtEmail.getValue().isEmpty())
			ZkUtils.campoRequerido(txtEmail);
		else if (InputUtils.validateEmail(txtEmail.getValue()))
			ZkUtils.mensaje(Message.EmailValidate, 2, txtEmail);
		else if (txtClave.getValue().isEmpty() && txtReClave.getValue().isEmpty())
			continueSuccess(false);
		else if (!txtClave.getValue().isEmpty() && txtReClave.getValue().isEmpty())
			ZkUtils.campoRequerido(txtReClave);
		else if (txtClave.getValue().isEmpty() && !txtReClave.getValue().isEmpty())
			ZkUtils.campoRequerido(txtClave);
		else if (!txtClave.getValue().isEmpty() && !txtReClave.getValue().isEmpty())
			if (txtClave.getValue().equals(txtReClave.getValue()))
				continueSuccess(true);
			else 
				ZkUtils.mensaje_short("Las claves no coinciden", 2, txtReClave);
		else
			continueSuccess(false);
	}

	private void continueSuccess(boolean cambioClave) {
		UserMeli currentUser = sesion.getUserMeli();
		UserMeli userAux = bd.userSelectByEmail(txtEmail.getValue().trim());
		UserMeli u = bd.userSelectByEmail(sesion.sesion.getAttribute("email").toString());

		if (userAux == null) {
			u.setEmail( txtEmail.getValue() );
		} else {
			String id = u.getId();
			if (currentUser != null) {
				id = currentUser.getId();
				u = currentUser;
			} 
			System.out.println("IDS: " + userAux.getId() + " vs " + id);

			if (userAux.getId().equals( id )) {
				// Is the same user
				u.setEmail( txtEmail.getValue() );				
			} else {
				ZkUtils.mensaje_short("Email en uso", 2, txtEmail);
				return;
			}
			
		}
		
		if (cambioClave)
			u.setClave( ZkUtils.md5( txtClave.getValue() ) );
		
		bd.userUpdate(u);
		
		ZkUtils.mensaje(Message.ConfSuccess, 1, null);
		closeWindow();
	}

}
