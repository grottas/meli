package modelo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Answer {
	
	public static final Map<String, String> STATUS = createMap();

    private static Map<String, String> createMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("ACTIVE", "Activo");
        result.put("DISABLED", "Desabilitado");
        return Collections.unmodifiableMap(result);
    }
	
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
