package utils;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;

public final class ZkUtils {
	
	public static void redireccion(String url) {
		Executions.sendRedirect(url);
	}
	
	public static void mensaje(String mensaje, int tipo, Component com) {
		String t = (tipo == 1) ? "info" : 
				   (tipo == 2) ? "warning" : "error";
		Clients.showNotification(mensaje, t, com, null, 2000, true);
	}
	
	public static void alerta(String mensaje, String titulo) {
		Clients.alert(mensaje, titulo, null);
	}
	
	public static void campoRequerido(Component comp) {
		mensaje("Campo Obligatorio", 2, comp);
	}
	
	public static void crearComponente(Component component, Object object) {
		Selectors.wireComponents(component, object, false);
		Selectors.wireEventListeners(component, object);
	}
	
	public static void removerTodo(Component componente) {
		List<Component> componentes = componente.getChildren();
		for (int i = componentes.size() - 1; i >= 0; i--) {
			componente.removeChild(componentes.get(i));
		}
	}
	
}
