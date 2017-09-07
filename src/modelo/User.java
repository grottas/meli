package modelo;

public class User {
	
	private String id;
	private String nickname;
	private String points;
	private String permalink;
	
	public User(String id, String nickname, String points,
			String permalink) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.points = points;
		this.permalink = permalink;
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

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	
}
