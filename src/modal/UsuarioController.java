package modal;

import java.io.IOException;

import modelo.Rol;
import modelo.SitesMl;
import modelo.SubRol;
import modelo.UserMeli;

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

import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;

import plugin.Meli;
import plugin.MeliException;
import plugin.MeliUtils;

import utils.EventQueuesUtils;
import utils.InputUtils;
import utils.Message;
import utils.ParseJson;
import utils.ZkUtils;

import dao.Bd;

public class UsuarioController extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Window win;
	@Wire private Label tipo;
	@Wire private Label idUser;
	@Wire private Combobox comboSites;
	@Wire private Textbox txtNombreML;
	@Wire private Textbox txtIdMeli;
	@Wire private Textbox txtEmail;
	
	private static Meli m = new Meli(MeliUtils.APP_ID, MeliUtils.Secret_Key);
	private FluentStringsMap params = new FluentStringsMap();
	private Bd bd = new Bd();
	
	@Listen("onClick = #closeWin")
	public void closeWindow() {
		win.detach();
	}
	
	@Listen("onCreate = #comboSites")
	public void loadSites() {
		for (SitesMl s : bd.sitesSelectAll()) {
			Comboitem combito = new Comboitem(s.getName());
			combito.setValue(s.getId());
			comboSites.appendChild(combito);
		}
		ZkUtils.crearComponente(comboSites, this);
	}
	
	@Listen("onClick = #btnValidarNombreMl")
	public void validarNombreMl() throws MeliException, IOException {
		if (txtNombreML.getValue().isEmpty()) {
			ZkUtils.campoRequerido(txtNombreML);
		} else if (comboSites.getSelectedIndex() == -1) {
			ZkUtils.campoRequerido(comboSites);
		} else {
			String site = comboSites.getSelectedItem().getValue().toString();
			String nick = txtNombreML.getValue().trim();
			
			params.clear();
			params.add("nickname", nick);
			
			Response response = m.get("/sites/"+site+"/search?", params);
			if (response != null) {
				if (response.getStatusCode() == 200) {
					String idMeli = ParseJson.searchIdMeli(response.getResponseBody());
					System.out.println(idMeli);
					if (idMeli.equals("")) {
						ZkUtils.mensaje("Usuario no encontrado", 2, txtNombreML);
						txtIdMeli.setValue(null);		
					} else {
						txtIdMeli.setValue(idMeli);						
					}
					
				} else {
					ZkUtils.problemasInternet();
				}
			} else {
				ZkUtils.problemasInternet();
			}
		}
	}
	
	@Listen("onClick = #btnSuccess")
	public void btnSuccess() {
		if (txtNombreML.getValue().isEmpty())
			ZkUtils.campoRequerido(txtNombreML);
		else if (txtIdMeli.getValue().isEmpty())
			ZkUtils.campoRequerido(txtIdMeli);
		else if (txtEmail.getValue().isEmpty())
			ZkUtils.campoRequerido(txtEmail);
		else if (InputUtils.validateEmail(txtEmail.getValue()))
			ZkUtils.mensaje(Message.EmailValidate, 2, txtEmail);
		else
			continueSuccess();
	}

	private void continueSuccess() {
		if (validateIdMl()) {
			ZkUtils.mensaje("Nombre de usuario en uso.", 2, txtNombreML);
			return;
		}
		String message = "";
		if (tipo.getValue().equals("Crear")) {
			message = newUsuario();
		} else {
			message = updateUsuario();
		}
		EventQueues.lookup(EventQueuesUtils.UsuarioChange, EventQueues.DESKTOP, true)
        	.publish(new Event ( message ));
		win.detach();
	}
	
	private boolean validateIdMl() {
		boolean exist = bd.userCodeMeliIsUsed(txtIdMeli.getValue());
		
		if (exist) {
			// Is the same user that want to changed?
			UserMeli user = bd.userSelectByIdMeli(txtIdMeli.getValue());
			System.out.println(user.getId_meli()+" "+idUser.getValue());
			return user.getId_meli().equals(idUser.getValue()); 
		} else {
			return false;
		}
	}

	private String updateUsuario() {
		bd.userUpdate( getUsuario() );
		return "Usuario actualizada";
	}

	private String newUsuario() {
		bd.userInsert( getUsuario() );
		return "Usuario creado";
	}
	
	private UserMeli getUsuario() {
		Rol r = new Rol("2", "");
		SubRol s = new SubRol("0", "", "");
		return new UserMeli(idUser.getValue(), txtIdMeli.getValue(),
					txtNombreML.getValue(), txtEmail.getValue(), 
					ZkUtils.md5( txtNombreML.getValue() ), r, s);
	}

}
