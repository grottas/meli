package utils;

import modelo.UserCurrent;
import modelo.UserMeli;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

public class Sesion {
	
	public Session sesion = Sessions.getCurrent();
	
	public void cerrarSesion() {
		sesion.invalidate();
	}
	
	public void test() {
		sesion.setAttribute("id", "268910416");
		sesion.setAttribute("accessToken", "APP_USR-8051032385985753-092416-8c0ed7e1d49ffc3611f8c31fc9f02ae8__F_D__-268910416");
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
	
	public void saveUserMeli(UserMeli u) {
		sesion.setAttribute("userMeli", u);
	}
	
	public UserMeli getUserMeli() {
		return (UserMeli) sesion.getAttribute("userMeli");
	}

}
