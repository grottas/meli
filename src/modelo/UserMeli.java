package modelo;

public class UserMeli {

	private String id;
	private String id_meli;
	private String nombre;
	private String email;
	private String clave;
	private Rol rol;
	private SubRol sub_rol;
	
	public UserMeli(String id, String id_meli, String nombre, String email,
			String clave, Rol rol, SubRol sub_rol) {
		this.id = id;
		this.id_meli = id_meli;
		this.nombre = nombre;
		this.email = email;
		this.clave = clave;
		this.rol = rol;
		this.sub_rol = sub_rol;
	}
	public UserMeli() {
		
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public SubRol getSub_rol() {
		return sub_rol;
	}
	public void setSub_rol(SubRol sub_rol) {
		this.sub_rol = sub_rol;
	}

}
