package access;

import java.util.Map;

import modelo.UserMeli;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import plugin.Meli;
import plugin.MeliUtils;

import utils.Sesion;
import utils.ZkUtils;

public class Index implements Initiator {

	private Sesion sesion = new Sesion();
	
	@Override
	public void doInit(Page arg0, Map<String, Object> arg1) throws Exception {
		if(sesion.getUserMeli() != null) {	// Usuario Existe
			UserMeli u = sesion.getUserMeli();		
			
			if (u.getRol().getId().equals("1")) {
				ZkUtils.redireccion("/admin");
			} else {
				Meli m = new Meli(MeliUtils.APP_ID, MeliUtils.Secret_Key);
				
				String redirectUrl = m.getAuthUrl(MeliUtils.Auth_Redirect_Url, Meli.AuthUrls.MLV);
				ZkUtils.redireccion(redirectUrl);
			}
		}
	}

}
