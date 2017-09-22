package modelo;

public class AnswerRequest {
	
	private int index;
	private String id_question;
	private String user_name;
	
	public AnswerRequest(int index, String id_question, String user_name) {
		this.index = index;
		this.id_question = id_question;
		this.user_name = user_name;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getId_question() {
		return id_question;
	}

	public void setId_question(String id_question) {
		this.id_question = id_question;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

}
