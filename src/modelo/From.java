package modelo;

public class From {
	
	private String id;
	private int answered_questions;
	
	public From(String id, int answered_questions) {
		this.id = id;
		this.answered_questions = answered_questions;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getAnswered_questions() {
		return answered_questions;
	}
	public void setAnswered_questions(int answered_questions) {
		this.answered_questions = answered_questions;
	}

}
