package ui_modelo;

import java.util.List;

public class Menu {
	
	private String execute;
	private int level;
	private String icon;
	private String name;
	private List<String> child;
	
	public Menu(String execute, int level, String icon, String name, List<String> child) {
		this.execute = execute;
		this.level = level;
		this.icon = icon;
		this.name = name;
		this.child = child;
	}

	public String getExecute() {
		return execute;
	}

	public void setExecute(String execute) {
		this.execute = execute;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getChild() {
		return child;
	}

	public void setChild(List<String> child) {
		this.child = child;
	}

}
