package controlador;

import java.util.concurrent.ExecutionException;

import modelo.UserMeli;

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

public class ControladorVendores extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Listbox listUsuarios;
	
	private Sesion sesion = new Sesion();
	private Bd bd = new Bd();

	@Override
	 public void doAfterCompose(Component comp) throws Exception, ExecutionException {
		super.doAfterCompose(comp);
		
		eventQueue();
		setListModel();
	}
	
	private void eventQueue() {
		EventQueues.lookup(EventQueuesUtils.DeleteSubUser, EventQueues.DESKTOP, true)
			.subscribe(new EventListener<Event>() {
			
			@Override
			public void onEvent(Event arg) throws Exception {
				System.out.println(EventQueuesUtils.DeleteUsuario);
				Listitem t = listUsuarios.getSelectedItem();
				listUsuarios.removeChild( t );
				bd.delete( (UserMeli) t.getValue() );
			}
		});
		
		EventQueues.lookup(EventQueuesUtils.SubUserChange, EventQueues.DESKTOP, true)
			.subscribe(new EventListener<Event>() {
			
			@Override
			public void onEvent(Event arg) throws Exception {
				setListModel();
				ZkUtils.mensaje_short(arg.getName(), 1, listUsuarios);
			}
		});
	}
	
	@Listen("onClick = #btnNuevo")
	public void addUsuario() {
		UserMeli u = new UserMeli();
		ZkUtils.crearModal("modal/subVendedor.zul", MeliUtils.argUsuario( "Crear",
																"Nueva Vendedor",
																u));
	}
	
	@Listen("onClick = #btnEditar")
	public void updateUsuario() {
		if (listUsuarios.getSelectedIndex() == -1) {
			ZkUtils.mensaje_short(Message.NeedSelectSubUser, 2, listUsuarios);
		} else {			
			UserMeli u = listUsuarios.getSelectedItem().getValue();
			ZkUtils.crearModal("modal/subVendedor.zul", MeliUtils.argUsuario( "Editar",
																	"Editar Vendedor",
																	u));	
		}
	}
	
	@Listen("onClick = #btnEliminar")
	public void deleteUsuario() {
		if (listUsuarios.getSelectedIndex() == -1) {
			ZkUtils.mensaje_short(Message.NeedSelectSubUser, 2, listUsuarios);
		} else {			
			UserMeli u = listUsuarios.getSelectedItem().getValue();
			
			ZkUtils.crearModal("meli/delete.zul", MeliUtils.argDelete( u.getId(),
																		"Eliminar Vendedor",
																		"modal.DeleteSubUsuarioController"));			
		}
	}
	
	
	private void setListModel() {
		String id_meli = sesion.sesion.getAttribute("id").toString();
		listUsuarios.setModel(new ListModelList<UserMeli> (bd.userSelectSubVendedores(id_meli)));
	}
	
}
