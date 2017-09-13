package modelo;

public class AnswerRequest {
	
	private String id_question;
	private String user_name;
	
	public AnswerRequest(String id_question, String user_name) {
		this.id_question = id_question;
		this.user_name = user_name;
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
