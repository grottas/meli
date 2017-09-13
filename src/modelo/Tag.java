package modelo;

import java.util.ArrayList;
import java.util.List;

public class Tag {
	
	private String nombre;
	private String descripcion;
	private String texto;
	
	public Tag(String nombre, String descripcion, String texto) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.texto = texto;
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
	
	public static List<Tag> defaultTags() {
		List<Tag> tags = new ArrayList<>();
		tags.add(new Tag("@stock", "Si hay", "Buenas amigos, si tenemos disponibilidad. Saludos!"));
		tags.add(new Tag("@minimo", "Precio min.", "Buenas amigo, lo minimo es lo publicado!"));
		tags.add(new Tag("@cambio", "Cambio producto", "Buenas amigo, gracias pero no acepto cambios."));

		return tags;
	}

}
