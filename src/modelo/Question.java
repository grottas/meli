package modelo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Question {
	
	public static final Map<String, String> STATUS = createMap();

    private static Map<String, String> createMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("UNANSWERED", "Sin Responder");
        result.put("ANSWERED", "Respondida");
        result.put("CLOSED_UNANSWERED", "Sin Responder, Artículo Cerrado");
        result.put("UNDER_REVIEW", "Bajo Revisión");
        return Collections.unmodifiableMap(result);
    }
	
	// Question Id.
	private String id;
	
	// Answer from the seller to the question.
	private Answer answer;
	
	// Creation date.
	private String date_created;
	
	// Item Id that the question belongs to.
	private String item_id;
	
	//Seller Id of the item.
	private String seller_id;
	
	//	unanswered: 		Question is not answered yet.
	//	answered:			Question was answered.
	//	closed_unanswered: 	The item is closed and the question was never answered.
	//	under_review: 		The item is under review and the question too.
	private String status;
	
	// Text of the question.
	private String text;
	
	private boolean deleted_from_listing;
	
	private boolean hold;
	
	private From from;
	
	public Question(String id, Answer answer, String date_created,
			String item_id, String seller_id, String status, String text,
			boolean deleted_from_listing, boolean hold, From from) {
		super();
		this.id = id;
		this.answer = answer;
		this.date_created = date_created;
		this.item_id = item_id;
		this.seller_id = seller_id;
		this.status = status;
		this.text = text;
		this.deleted_from_listing = deleted_from_listing;
		this.hold = hold;
		this.from = from;
	}

	public boolean isDeleted_from_listing() {
		return deleted_from_listing;
	}

	public void setDeleted_from_listing(boolean deleted_from_listing) {
		this.deleted_from_listing = deleted_from_listing;
	}

	public boolean isHold() {
		return hold;
	}

	public void setHold(boolean hold) {
		this.hold = hold;
	}

	public From getFrom() {
		return from;
	}

	public void setFrom(From from) {
		this.from = from;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public String getDate_created() {
		return date_created;
	}

	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
