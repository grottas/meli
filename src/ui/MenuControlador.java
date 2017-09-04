package ui;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
		
		List<Menu> menu = new ArrayList<Menu>();
		menu.add(new Menu("/inicio.zul", 0, "fa fa-list-ul", "Meli", null ));
		menu.add(new Menu("/", 0, "fa fa-sign-out", "Cerrar Sesión", null));
		
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
			
			Component component = Executions.createComponents("ui/menu_ul.zul", metismenu, arg);
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
		}
	}

	private void redireccionamiento(Li li) {
		if (li.getId().equals("/")) {
			sesion.cerrarSesion();
		}
		ZkUtils.redireccion(li.getId());
	}
	

}
