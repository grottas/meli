package modelo;

public class Answer {
	
	// Text of the answer.
	private String text;
	
	// active: 		Answer is active.
	// disabled: 	The answer has been disabled.
	private String status;
	
	// Creation date.
	private String date_created;
	
	public Answer(String text, String status, String date_created) {
		this.text = text;
		this.status = status;
		this.date_created = date_created;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate_created() {
		return date_created;
	}

	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}

}
