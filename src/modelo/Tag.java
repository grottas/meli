package modelo;

public class Tag {
	
	private String id;
	private String nombre;
	private String descripcion;
	private String texto;
	private String id_meli;
	
	public Tag(String nombre, String descripcion, String texto, String id, String id_meli) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.texto = texto;
		this.id_meli = id_meli;
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}

}
