package modelo;

public class UserCurrent {
	
	private String id;
	private String nickname;
	private String first_name;
	private String last_name;
	private String accessToken;
	private String refreshToken;
	
	public UserCurrent(String id, String nickname, String first_name,
			String last_name, String accessToken, String refreshToken) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.first_name = first_name;
		this.last_name = last_name;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
}
