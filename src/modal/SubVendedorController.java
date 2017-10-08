package modal;

import modelo.Rol;
import modelo.SubRol;
import modelo.UserMeli;

import org.zkoss.zhtml.Button;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dao.Bd;

import utils.EventQueuesUtils;
import utils.InputUtils;
import utils.Message;
import utils.Sesion;
import utils.ZkUtils;

public class SubVendedorController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire private Window win;
	@Wire private Label tipo;
	@Wire private Label id;
	@Wire private Textbox txtNombre;
	@Wire private Textbox txtEmail;
	@Wire private Textbox txtClave;
	@Wire private Textbox txtReClave;
	@Wire private Combobox comboRol;
	@Wire private Button resetClave;
	
	private Sesion sesion = new Sesion();
	private Bd bd = new Bd();
	
	@Listen("onCreate = #win")
	public void onCreate() {
		String id_meli = sesion.sesion.getAttribute("id").toString();
		System.out.println(id_meli);
		for (SubRol s : bd.subRolSelectByIdMeli( id_meli )) {
			Comboitem combito = new Comboitem(s.getDescripcion());
			combito.setValue(s.getId());
			comboRol.appendChild(combito);
		}
		ZkUtils.crearComponente(comboRol, this);
		
		selectComboEdit(id_meli);
	}
	
	private void selectComboEdit(String id_meli) {
		if (!tipo.getValue().equals("Crear")) {
			UserMeli u = bd.userSelectById(id.getValue());
			int i = 0;
			
			for (SubRol s : bd.subRolSelectByIdMeli( id_meli )) {
				if (s.getId().equals( u.getSub_rol().getId() )) {
					comboRol.setSelectedIndex(i);
					break;
				}
				i++;
			}
		}
	}

	@Listen("onClick = #closeWin")
	public void closeWindow() {
		win.detach();
	}
	
	@Listen("onClick = #btnSuccess")
	public void btnSuccess() {
		if (txtNombre.getValue().isEmpty())
			ZkUtils.campoRequerido(txtNombre);
		else if (txtEmail.getValue().isEmpty())
			ZkUtils.campoRequerido(txtEmail);
		else if (InputUtils.validateEmail(txtEmail.getValue()))
			ZkUtils.mensaje(Message.EmailValidate, 2, txtEmail);
		else if (comboRol.getSelectedIndex() == -1)
			ZkUtils.campoRequerido(comboRol);
		else
			continueToSuccess();
	}

	private void continueToSuccess() {
		UserMeli u = bd.userSelectByEmail(txtEmail.getValue());
		String message = "";
		
		if (tipo.getValue().equals("Crear")) {
			
			if (u == null) {
				if (txtClave.getValue().isEmpty()) {
					ZkUtils.campoRequerido(txtClave);
					return;
				} else if (txtReClave.getValue().isEmpty()) {
					ZkUtils.campoRequerido(txtReClave);
					return;
				} else if (!txtClave.getValue().equals( txtReClave.getValue() )) {
					ZkUtils.mensaje_short("Las claves no coinciden", 2, txtClave);
					return;
				} else {
					message = newUsuario();
				}
			} else {
				ZkUtils.mensaje_short("Email en uso", 2, null);
				return;
			}
		} else {
			
			if (u != null) {				
				if (!u.getId().equals(id.getValue())) {
					ZkUtils.mensaje_short("Email en uso", 2, txtEmail);
					return;
				}
			}
			
			if (txtClave.getValue().isEmpty() && !txtReClave.getValue().isEmpty()) {
				ZkUtils.campoRequerido(txtClave);
				return;
			} else if (!txtClave.getValue().isEmpty() && txtReClave.getValue().isEmpty()) {
				ZkUtils.campoRequerido(txtReClave);
				return;
			} else if (txtClave.getValue().isEmpty() && txtReClave.getValue().isEmpty()) {
				message = updateUsuario(true);

			} else if (!txtClave.getValue().isEmpty() && !txtReClave.getValue().isEmpty()) {
				if (!txtClave.getValue().equals( txtReClave.getValue() )) {
					ZkUtils.mensaje_short("Las claves no coinciden", 2, txtClave);
					return;
				} else {
					message = updateUsuario(false);	
				}
			}					
		}
		EventQueues.lookup(EventQueuesUtils.SubUserChange, EventQueues.DESKTOP, true)
        	.publish(new Event ( message ));
		win.detach();
	}
	
	private String updateUsuario(boolean changePass) {
		UserMeli user = getUsuario();
		if (changePass) {
			UserMeli u = bd.userSelectById(id.getValue());
			user.setClave( u.getClave() );
		}
		bd.userUpdate( user );
		return "Usuario actualizada";
	}

	private String newUsuario() {
		bd.userInsert( getUsuario() );
		return "Usuario creado";	
	}
	
	@Listen("onClick = #resetClave")
	public void resetPass() {
		UserMeli u = bd.userSelectById(id.getValue());
		String newPass = ZkUtils.md5( u.getNombre() );
		u.setClave( newPass );
		bd.userUpdate(u);
		ZkUtils.mensaje_short(Message.ResetPass, 1, resetClave);
	}

	private UserMeli getUsuario() {
		Rol r = new Rol("3", "");
		SubRol s = new SubRol(comboRol.getSelectedItem().getValue().toString(), "", "");
		String id_meli = sesion.sesion.getAttribute("id").toString();

		return new UserMeli(id.getValue(), id_meli,
					txtNombre.getValue(), txtEmail.getValue(), 
					ZkUtils.md5( txtClave.getValue() ), r, s);
	}
	
}
