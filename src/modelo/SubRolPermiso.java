package modelo;

public class SubRolPermiso {
	
	private String id;
	private SubRol subRol;
	private Permiso permiso;
	
	public SubRolPermiso(String id, SubRol subRol, Permiso permiso) {
		super();
		this.id = id;
		this.subRol = subRol;
		this.permiso = permiso;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SubRol getSubRol() {
		return subRol;
	}
	public void setSubRol(SubRol subRol) {
		this.subRol = subRol;
	}
	public Permiso getPermiso() {
		return permiso;
	}
	public void setPermiso(Permiso permiso) {
		this.permiso = permiso;
	}

}
