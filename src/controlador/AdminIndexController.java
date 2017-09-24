package controlador;

import java.util.concurrent.ExecutionException;

import modelo.Tag;

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

public class AdminIndexController extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L; 
	@Wire private Listbox listTags;
	
	private Bd bd = new Bd();
	
	@Override
	 public void doAfterCompose(Component comp) throws Exception, ExecutionException {
		super.doAfterCompose(comp);
				
		eventQueue();
		setModelList();
	}
	
	private void eventQueue() {
		EventQueues.lookup(EventQueuesUtils.DeleteTag, EventQueues.DESKTOP, true)
			.subscribe(new EventListener<Event>() {
			
			@Override
			public void onEvent(Event arg) throws Exception {
				System.out.println(EventQueuesUtils.DeleteTag);
				Listitem t = listTags.getSelectedItem();
				listTags.removeChild( t );
				bd.tagDelete( (Tag) t.getValue() );
			}
		});
		
		EventQueues.lookup(EventQueuesUtils.TagChange, EventQueues.DESKTOP, true)
			.subscribe(new EventListener<Event>() {
			
			@Override
			public void onEvent(Event arg) throws Exception {
				setModelList();
			}
		});
		
	}

	@Listen("onClick = #btnNuevo")
	public void addTag() {
		ZkUtils.crearModal("../modal/tag.zul", MeliUtils.argTag( "Crear",
																"Nueva Etiqueta",
																"", "", "", ""));
	}
	
	@Listen("onClick = #btnEditar")
	public void updateTag() {
		if (listTags.getSelectedIndex() == -1) {
			ZkUtils.mensaje_short(Message.NeedSelectTags, 2, listTags);
		} else {			
			Tag t = listTags.getSelectedItem().getValue();
			ZkUtils.crearModal("../modal/tag.zul", MeliUtils.argTag( "Editar",
																	"Editar Etiqueta",
																	t.getId(),
																	t.getNombre(),
																	t.getDescripcion(),
																	t.getTexto()));	
		}
	}
	
	@Listen("onClick = #btnEliminar")
	public void deleteTag() {
		if (listTags.getSelectedIndex() == -1) {
			ZkUtils.mensaje_short(Message.NeedSelectTags, 2, listTags);
		} else {			
			Tag t = listTags.getSelectedItem().getValue();
			
			ZkUtils.crearModal("../meli/delete.zul", MeliUtils.argDelete( t.getId(),
																		"Eliminar Etiqueta",
																		"modal.DeleteTagsController"));			
		}
	}
	
	private void setModelList() {
		listTags.setModel(new ListModelList<Tag> (bd.tagSelectAll()));
	}
}
