package access;

import java.util.Map;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import utils.Sesion;
import utils.ZkUtils;

public class UserLogIn implements Initiator {
	
	private Sesion sesion = new Sesion();

	@Override
	public void doInit(Page arg0, Map<String, Object> arg1) throws Exception {
		if(sesion.getUserMeli() == null) {	// Usuario NO Existe
			ZkUtils.redireccion("/");
		}
	}

}
