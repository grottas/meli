package controlador;

import java.util.concurrent.ExecutionException;

import modelo.SubRol;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import plugin.MeliUtils;

import utils.EventQueuesUtils;
import utils.Message;
import utils.Sesion;
import utils.ZkUtils;

import dao.Bd;

public class ControladorRoles extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Listbox listRoles;
	
	private Sesion sesion = new Sesion();
	private Bd bd = new Bd();

	@Override
	 public void doAfterCompose(Component comp) throws Exception, ExecutionException {
		super.doAfterCompose(comp);
		
		eventQueue();
		setListModel();
	}
	
	private void eventQueue() {
		EventQueues.lookup(EventQueuesUtils.DeleteSubRol, EventQueues.DESKTOP, true)
			.subscribe(new EventListener<Event>() {
			
			@Override
			public void onEvent(Event arg) throws Exception {
				System.out.println(EventQueuesUtils.DeleteUsuario);
				Listitem t = listRoles.getSelectedItem();
				listRoles.removeChild( t );
				bd.subRolDelete( (SubRol) t.getValue() );
			}
		});
		
		EventQueues.lookup(EventQueuesUtils.SubRolChange, EventQueues.DESKTOP, true)
			.subscribe(new EventListener<Event>() {
			
			@Override
			public void onEvent(Event arg) throws Exception {
				setListModel();
				ZkUtils.mensaje_short(arg.getName(), 1, listRoles);
			}
		});
		
	}
	
	@Listen("onClick = #btnNuevo")
	public void add() {
		SubRol u = new SubRol();
		ZkUtils.crearModal("modal/rol.zul", MeliUtils.argRol( "Crear",
																"Nueva Rol",
																u));
	}
	
	@Listen("onClick = #btnEditar")
	public void update() {
		if (listRoles.getSelectedIndex() == -1) {
			ZkUtils.mensaje_short(Message.NeedSelectRol, 2, listRoles);
		} else {			
			SubRol u = listRoles.getSelectedItem().getValue();
			ZkUtils.crearModal("modal/rol.zul", MeliUtils.argRol( "Editar",
																	"Editar Rol",
																	u));	
		}
	}
	
	@Listen("onClick = #btnEliminar")
	public void delete() {
		if (listRoles.getSelectedIndex() == -1) {
			ZkUtils.mensaje_short(Message.NeedSelectRol, 2, listRoles);
		} else {			
			SubRol u = listRoles.getSelectedItem().getValue();
			
			ZkUtils.crearModal("meli/delete.zul", MeliUtils.argDelete( u.getId(),
																		"Eliminar Rol",
																		"modal.DeleteRolController"));			
		}
	}
	
	private void setListModel() {
		String id_meli = sesion.sesion.getAttribute("id").toString();
		listRoles.setModel(new ListModelList<SubRol> (bd.subRolSelectByIdMeli(id_meli)));
	}
	
}
