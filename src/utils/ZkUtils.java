package utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

public final class ZkUtils {
	
	private static DecimalFormat df = new DecimalFormat("###,###,###,###.00");
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	public static SimpleDateFormat dateMacht = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateToShow = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	public static void redireccion(String url) {
		Executions.sendRedirect(url);
	}
	
	public static String getPathRequest() {
		return Executions.getCurrent().getDesktop().getRequestPath();
	}
	
	public static String getMeliCode() {
		String cod = Executions.getCurrent().getDesktop().getQueryString();
		return cod.replaceFirst("code=", "");
	}
	
	public static void mensaje(String mensaje, int tipo, Component com) {
		String t = (tipo == 1) ? "info" : 
				   (tipo == 2) ? "warning" : "error";
		Clients.showNotification(mensaje, t, com, null, 2000, true);
	}
	
	public static void mensaje_short(String mensaje, int tipo, Component com) {
		String t = (tipo == 1) ? "info" : 
				   (tipo == 2) ? "warning" : "error";
		Clients.showNotification(mensaje, t, com, null, 1000, true);
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
	
	public static void crearModal(String zul, Map<?, ?> arg) {
		Window window = (Window) Executions.createComponents (zul ,null, arg);
		window.doModal();
	}
	
	public static void removerTodo(Component componente) {
		List<Component> componentes = componente.getChildren();
		for (int i = componentes.size() - 1; i >= 0; i--) {
			componente.removeChild(componentes.get(i));
		}
	}
	
	public static void problemasInternet() {
		mensaje(Message.ConexionProblems, 3, null);
	}
	
	public static String dateFormat(String date) {
		return date.substring(8, 10) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4) + " " + date.substring(11, date.indexOf("."));
	}
	
	public static String priceFormat(double numero) {
        return df.format(numero);
    }
	
}
