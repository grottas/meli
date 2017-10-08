package modal;

import modelo.UserMeli;

import org.zkoss.zhtml.Button;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import plugin.Meli;
import plugin.MeliUtils;

import dao.Bd;

import utils.InputUtils;
import utils.Message;
import utils.Sesion;
import utils.ZkUtils;

public class LoginControlador extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Window win;
	@Wire private Textbox txtUsuario;
	@Wire private Textbox txtClave;
	@Wire private Button btnIngresar;
	
	private Sesion sesion = new Sesion();
	private Bd bd = new Bd();
	
	@Listen("onOK = #txtUsuario")
	public void okUsuario() {	
		if (txtUsuario.getValue().isEmpty())
			ZkUtils.campoRequerido(txtUsuario);
		else if (InputUtils.validateEmail(txtUsuario.getValue()))
			ZkUtils.mensaje(Message.EmailValidate, 2, txtUsuario);
		else
			txtClave.setFocus(true);
	}
	
	@Listen("onClick = #btnIngresar; onOK = #txtClave")
	public void ingresar() {
		if (txtUsuario.getValue().isEmpty())
			ZkUtils.campoRequerido(txtUsuario);
		else if (InputUtils.validateEmail(txtUsuario.getValue()))
			ZkUtils.mensaje(Message.EmailValidate, 2, txtUsuario);
		else if (txtClave.getValue().isEmpty())
			ZkUtils.campoRequerido(txtClave);
		else 
			validarLogin();
	}

	private void validarLogin() {
		if (!btnIngresar.getSclass().contains("disabled")) {
			String email = txtUsuario.getValue();
			String clave = ZkUtils.md5( txtClave.getValue() );
			
			if (email == null) {
				ZkUtils.oops();
			} else {
				UserMeli user = bd.userSelectByEmailAndClave(email, clave);
				 
				if (user != null) {
					if (user.getRol().getId().equals("2")) {
						ZkUtils.mensaje("Sus credenciales no es de vendedor", 2, null);
					} else {
						sesion.saveUserMeli(user);
						
						// Vendedores
						loginWithMl();
					}
					
				} else {
					ZkUtils.mensaje(Message.LogInFail, 1, null);
				}
			}
		}
	}

	private void loginWithMl() {
		// Bloquear botones
		btnIngresar.setSclass(btnIngresar.getSclass() + " disabled");
		
		Meli m = new Meli(MeliUtils.APP_ID, MeliUtils.Secret_Key);
		
		String redirectUrl = m.getAuthUrl(MeliUtils.Auth_Redirect_Url, Meli.AuthUrls.MLV);
		ZkUtils.redireccion(redirectUrl);
	}
	
}
