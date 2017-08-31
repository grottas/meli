package ui;

import org.zkoss.zhtml.Li;
import org.zkoss.zhtml.Ul;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;

public class MenuControlador extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L;
	private Li liOld = null;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		// Aca se cargara el menu dinamicamente
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
	

}
