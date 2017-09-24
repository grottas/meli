package modelo;

public class SubRol {
	
	private String id;
	private String id_meli;
	private String descripcion;
	
	public SubRol(String id, String id_meli, String descripcion) {
		super();
		this.id = id;
		this.id_meli = id_meli;
		this.descripcion = descripcion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId_meli() {
		return id_meli;
	}
	public void setId_meli(String id_meli) {
		this.id_meli = id_meli;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
