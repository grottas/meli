package access;

import java.util.Map;

import modelo.UserMeli;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import utils.Sesion;
import utils.ZkUtils;

public class OnlyAdmin implements Initiator {

	private Sesion sesion = new Sesion();
	
	@Override
	public void doInit(Page arg0, Map<String, Object> arg1) throws Exception {
		if(sesion.getUserMeli() != null) {
			UserMeli u = sesion.getUserMeli();		
			
			if (!u.getRol().getId().equals("1")) {
				ZkUtils.redireccion("/");
			}
		}
	}

}
