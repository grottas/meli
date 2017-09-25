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
import utils.ZkUtils;

import dao.Bd;

public class AdminUsuarioController extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Listbox listUsuarios;
	
	private Bd bd = new Bd();
	
	@Override
	 public void doAfterCompose(Component comp) throws Exception, ExecutionException {
		super.doAfterCompose(comp);
				
		eventQueue();
		setModelList();
	}
	
	private void eventQueue() {
		EventQueues.lookup(EventQueuesUtils.DeleteUsuario, EventQueues.DESKTOP, true)
			.subscribe(new EventListener<Event>() {
			
			@Override
			public void onEvent(Event arg) throws Exception {
				System.out.println(EventQueuesUtils.DeleteUsuario);
				Listitem t = listUsuarios.getSelectedItem();
				listUsuarios.removeChild( t );
				bd.delete( (UserMeli) t.getValue() );
			}
		});
		
		EventQueues.lookup(EventQueuesUtils.UsuarioChange, EventQueues.DESKTOP, true)
			.subscribe(new EventListener<Event>() {
			
			@Override
			public void onEvent(Event arg) throws Exception {
				setModelList();
				ZkUtils.mensaje_short(arg.getName(), 1, listUsuarios);
			}
		});
		
	}

	@Listen("onClick = #btnNuevo")
	public void addUsuario() {
		UserMeli u = new UserMeli();
		ZkUtils.crearModal("../modal/vendedor.zul", MeliUtils.argUsuario( "Crear",
																"Nueva Usuario",
																u));
	}
	
	@Listen("onClick = #btnEditar")
	public void updateUsuario() {
		if (listUsuarios.getSelectedIndex() == -1) {
			ZkUtils.mensaje_short(Message.NeedSelectUser, 2, listUsuarios);
		} else {			
			UserMeli u = listUsuarios.getSelectedItem().getValue();
			ZkUtils.crearModal("../modal/vendedor.zul", MeliUtils.argUsuario( "Editar",
																	"Editar Usuario",
																	u));	
		}
	}
	
	@Listen("onClick = #btnEliminar")
	public void deleteUsuario() {
		if (listUsuarios.getSelectedIndex() == -1) {
			ZkUtils.mensaje_short(Message.NeedSelectUser, 2, listUsuarios);
		} else {			
			UserMeli u = listUsuarios.getSelectedItem().getValue();
			
			ZkUtils.crearModal("../meli/delete.zul", MeliUtils.argDelete( u.getId(),
																		"Eliminar Usuario",
																		"modal.DeleteUsuarioController"));			
		}
	}
	
	private void setModelList() {
		listUsuarios.setModel(new ListModelList<UserMeli> (bd.userSelectVendedores()));
	}
	
	@Listen("onClick = #btnDetalle")
	public void seeMore() {
		if (listUsuarios.getSelectedIndex() == -1) {
			ZkUtils.mensaje_short(Message.NeedSelectUser, 2, listUsuarios);
		} else {
			UserMeli u = listUsuarios.getSelectedItem().getValue();
			
			ZkUtils.crearModal("../modal/vendedorDetalle.zul", MeliUtils.argUsuarioDetalle( u ));
		}
	}
	
}
