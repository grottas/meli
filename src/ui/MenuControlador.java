package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import modelo.UserMeli;

import org.zkoss.zhtml.Li;
import org.zkoss.zhtml.Ul;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;

import ui_modelo.Menu;
import utils.Sesion;
import utils.ZkUtils;

public class MenuControlador extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L;
	private @Wire("#metismenu") Ul metismenu;
	
	private Li liOld = null;
	public Sesion sesion = new Sesion();
		
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	
		UserMeli u = sesion.getUserMeli();		
		List<Menu> menu = new ArrayList<Menu>();
		
		// Admin
		if (u.getRol().getId().equals("1")) {
			menu.add(new Menu("admin/index.zul", 0, "fa fa-at", "Etiqueta", null));
			menu.add(new Menu("admin/usuario.zul", 0, "fa fa-users", "Usuario", null));
			
		// Vendedor
		} else {
			menu.add(new Menu("", 1, "fa fa-list-ul", "Gestión de Preguntas", Arrays.asList("Preguntas por Gestionar", "Preguntas Gestionadas") ));
		
			if (u.getSub_rol() == null) {
				menu.add(new Menu("", 1, "fa fa-users", "Vendedores", Arrays.asList("Vendedor", "Rol") ));
			}			
		}
		menu.add(new Menu("modalConfiguracion", 0, "fa fa-cog", "Configurar Cuenta", null));
		menu.add(new Menu("", 0, "fa fa-sign-out", "Cerrar Sesión", null));
		createmenu(menu);
	}
	
	private void createmenu(List<Menu> menu) {
		for (Menu m : menu) {
			HashMap<String, Object> arg  = new  HashMap<String, Object>();
			
			arg.put("execution", m.getExecute());
			arg.put("level", m.getLevel() == 0 ? "first-label" : "second-label");
			arg.put("icon", m.getIcon());
			arg.put("name", m.getName());
			arg.put("child", m.getChild());
			
			Component component = null;
			try {
				component = Executions.createComponents("ui/menu_ul.zul", metismenu, arg);
			} catch (Exception e) {
				component = Executions.createComponents("../ui/menu_ul.zul", metismenu, arg);
			}
		
			ZkUtils.crearComponente(component, this);
		}
	}

	@Listen("onClick = #metismenu Li")
	public void li(Event e) {
		if (e.getTarget() instanceof Li) {
			Li li = (Li) e.getTarget();
			if (li.getSclass() == null) {
				return;
	      	}

			setLi(li);
			if (liOld != null && liOld != li && liOld.getSclass().contains("active")) {
				setLi(liOld);
			}
			liOld = li;
		}
	}
	
	public void setLi(Li li) {
		if (li.getSclass().equals("first-label active")) {
			li.setSclass("first-label");
		} else if (li.getSclass().equals("first-label")) {
			li.setSclass("first-label active");
			redireccionamiento(li);
		} else if (li.getSclass().equals("second-label active")) {
			li.setSclass("second-label");
			Ul ul = (Ul) li.getChildren().get(3);
			ul.setSclass("nav nav-second-level collapse");
		} else if (li.getSclass().equals("second-label")) {
			li.setSclass("second-label active");
			Ul ul = (Ul) li.getChildren().get(3);
			ul.setSclass("nav nav-second-level collapse in");
		} else if (li.getSclass().equals("default")) {
			redireccionamiento(li);
		}
	}

	private void redireccionamiento(Li li) {
		if (li.getId().equals("")) {
			sesion.cerrarSesion();
		}
		
		if (li.getId().equals("modalConfiguracion")) {
			showModal();
			return;
		}
		
		ZkUtils.redireccion("/" + li.getId());
	}
	
	private void showModal() {
		try {
			ZkUtils.crearModal("modal/configurarCuenta.zul", null);
		} catch (Exception e) {
			ZkUtils.crearModal("../modal/configurarCuenta.zul", null);
		}
	}

	public String setSecondLevelMenuId(String name) {
		switch (name) {
			case "Preguntas por Gestionar": return "inicio.zul";
			case "Preguntas Gestionadas": return "preguntas_gestionadas.zul";
			case "Vendedor": return "vendedores.zul";
			case "Rol": return "roles.zul";
			default: return "";
		}
	}
	
}
