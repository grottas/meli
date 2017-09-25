package modal;

import java.util.ArrayList;
import java.util.List;

import modelo.Permiso;
import modelo.SubRol;
import modelo.SubRolPermiso;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dao.Bd;

import utils.EventQueuesUtils;
import utils.Sesion;
import utils.ZkUtils;

public class RolController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire private Window win;
	@Wire private Label tipo;
	@Wire private Label id;
	@Wire private Textbox txtNombre;
	@Wire private Checkbox check1;
	@Wire private Checkbox check2;
	@Wire private Checkbox check3;
	@Wire private Checkbox check4;
	@Wire private Checkbox check5;
	@Wire private Checkbox check6;
	
	private List<SubRolPermiso> permisos = new ArrayList<SubRolPermiso>();
	private List<Checkbox> permisosCurrent = new ArrayList<Checkbox>();
	private Sesion sesion = new Sesion();
	private Bd bd = new Bd();
	
	@Listen("onCreate = #closeWin")
	public void onCreate() {
		permisosCurrent.add(check1);
		permisosCurrent.add(check2);
		permisosCurrent.add(check3);
		permisosCurrent.add(check4);
		permisosCurrent.add(check5);
		permisosCurrent.add(check6);
		
		permisos = bd.permisoRolSelectBySubRolId( id.getValue() );
		
		activarCheck();
	}
	
	private void activarCheck() {
		for (SubRolPermiso permi : permisos) {
			System.out.println(permi.getPermiso().getId());
			switch (permi.getPermiso().getId()) {
			case "1": check1.setChecked(true); break;
			case "2": check2.setChecked(true); break;
			case "3": check3.setChecked(true); break;
			case "4": check4.setChecked(true); break;
			case "5": check5.setChecked(true); break;
			case "6": check6.setChecked(true); break;
			}
		}
	}

	@Listen("onClick = #closeWin")
	public void closeWindow() {
		win.detach();
	}
	
	public boolean permisoActive(String id) {
		System.out.println(permisos.isEmpty());
		for (SubRolPermiso p : permisos) {
			if (p.getId().equals(id))
				return true;
		}
		return false;
	}
	
	@Listen("onClick = #btnSuccess")
	public void btnSuccess() {
		if (txtNombre.getValue().isEmpty())
			ZkUtils.campoRequerido(txtNombre);
		if (validateCheck())
			ZkUtils.mensaje_short("Seleccione algún permiso", 2, null);
		else
			continueToSuccess();
	}
	
	private boolean validateCheck() {
		if (check1.isChecked()
				|| check2.isChecked()
				|| check3.isChecked()
				|| check4.isChecked()
				|| check5.isChecked()
				|| check6.isChecked())
			return false;
		return true;
	}
	

	private void continueToSuccess() {
		String c = "";
		if (tipo.getValue().equals("Crear")) {
			c = newRol();
		} else {
			c = updateRol();
		}
		EventQueues.lookup(EventQueuesUtils.SubRolChange, EventQueues.DESKTOP, true)
        	.publish(new Event ( c ));
		win.detach();
	}

	private String updateRol() {
		bd.subRolUpdate( getSubRol() );
		
		deleteAllPermiso();
		insertNewPermiso();
		
		return "Rol actualizado";
	}

	private void deleteAllPermiso() {
		for (SubRolPermiso permi : permisos) {
			bd.subRoleleteOnlyBridge( permi.getSubRol() );
		}
	}

	private String newRol() {
		bd.subRolInsert( getSubRol() );
		id.setValue( bd.subRolSelectByDescMeliID( getSubRol() ).getId() );
		insertNewPermiso();
		return "Rol creado";
	}
	
	private void insertNewPermiso() {
		SubRol sub = getSubRol();
		for (Checkbox check : permisosCurrent) {
			if (check.isChecked()) {
				SubRolPermiso permi = new SubRolPermiso("", sub, new Permiso(check.getValue().toString(), ""));
				bd.permisoRolInsert(permi);
			}
		}
	}
	
	private SubRol getSubRol() {
		String id_meli = sesion.sesion.getAttribute("id").toString();
		return new SubRol(id.getValue(), id_meli, txtNombre.getValue());
	}

}
