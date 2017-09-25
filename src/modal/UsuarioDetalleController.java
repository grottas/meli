package modal;

import modelo.UserMeli;

import org.zkoss.zhtml.Button;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dao.Bd;

import utils.Message;
import utils.ZkUtils;

public class UsuarioDetalleController extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Window win;
	@Wire private Label idUser;
	@Wire private Textbox txtNombreML;
	@Wire private Textbox txtIdMeli;
	@Wire private Textbox txtEmail;
	@Wire private Listbox listUsuarios;
	@Wire private Button resetClave;
	
	private Bd bd = new Bd();
	
	@Listen("onClick = #closeWin")
	public void closeWindow() {
		win.detach();
	}
	
	@Listen("onCreate = #listUsuarios")
	public void loadUsers() {
		listUsuarios.setModel(new ListModelList<UserMeli> (bd.userSelectSubVendedores( txtIdMeli.getValue() )));
	}
	
	@Listen("onClick = #resetClave")
	public void resetPass() {
		UserMeli u = bd.userSelectById(idUser.getValue());
		String newPass = ZkUtils.md5( u.getNombre() );
		u.setClave( newPass );
		bd.userUpdate(u);
		ZkUtils.mensaje_short(Message.ResetPass, 1, resetClave);
	}
	
}
