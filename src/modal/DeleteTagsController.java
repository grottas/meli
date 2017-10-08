package modal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import utils.EventQueuesUtils;
import utils.Message;
import utils.ZkUtils;

public class DeleteTagsController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire private Window win;
	@Wire private Label idQuestion;
	
	public String textDeleteQuestions() {
		return Message.DeleteTag;
	}
	
	@Listen("onClick = #closeWin")
	public void closeWindow() {
		win.detach();
	}
	
	@Listen("onClick = #btnDeleteSuceess")
	public void delete() {
		String id = idQuestion.getValue();
				
		EventQueues.lookup(EventQueuesUtils.DeleteTag, EventQueues.DESKTOP, true)
	        .publish(new Event ( id ));
		closeWindow();
		ZkUtils.mensaje(Message.DeleteTagSuceess, 1, null);
	}

}
