package utils;

import modelo.UserCurrent;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

public class Sesion {
	
	public Session sesion = Sessions.getCurrent();
	
	public void cerrarSesion() {
		sesion.invalidate();
	}
	
	public void logIn(UserCurrent usu) {
		sesion.setAttribute("id", usu.getId());
		sesion.setAttribute("nickname", usu.getNickname());
		sesion.setAttribute("first_name", usu.getFirst_name());
		sesion.setAttribute("last_name", usu.getLast_name());
		sesion.setAttribute("accessToken", usu.getAccessToken());
		sesion.setAttribute("refreshToken", usu.getRefreshToken());
	}
	
	public UserCurrent getCurrentUser() {
		UserCurrent usu = new UserCurrent(sesion.getAttribute("id").toString(),
											sesion.getAttribute("nickname").toString(),
											sesion.getAttribute("first_name").toString(),
											sesion.getAttribute("last_name").toString(),
											sesion.getAttribute("accessToken").toString(),
											sesion.getAttribute("refreshToken").toString());
    	return usu;
	}

}
