package modelo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Producto {
	
	public static final Map<String, String> CURRENCY = createMap();

    private static Map<String, String> createMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("VEF", "Bs.");
        return Collections.unmodifiableMap(result);
    }
	
	private String nombre;
	private String imagen;
	private String condicion;
	private double precio;
	private String moneda;
	private String id_moneda;
	private int vendido;
	private int disponible;
	private String permalink;

	public  Producto(String nombre, String imagen, String condicion, 
			double precio, String moneda, String id_moneda, int vendido, int disponible,
			String permalink) {
		this.nombre = nombre;
		this.imagen = imagen;
		this.condicion = condicion;
		this.precio = precio;
		this.moneda = moneda;
		this.id_moneda = id_moneda;
		this.vendido = vendido;
		this.disponible = disponible;
		this.permalink = permalink;
	}
	
	public Producto(){}
	
	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getVendido() {
		return vendido;
	}

	public void setVendido(int vendido) {
		this.vendido = vendido;
	}

	public int getDisponible() {
		return disponible;
	}

	public void setDisponible(int disponible) {
		this.disponible = disponible;
	}

	public String getId_moneda() {
		return id_moneda;
	}

	public void setId_moneda(String id_moneda) {
		this.id_moneda = id_moneda;
	}
	
	// Por defecto Mercadolibre manda imagenes CHICAS
	// Chica:			I.jpg
	// Normal:			Y.jpg
	// Grande:			O.jpg
	
	public static String imgNormal(String cadena) {		
		return cadena.replace("I.jpg", "Y.jpg");
	}												
	public static String imgExtraGrande(String cadena) {	
		return cadena.replace("I.jpg", "F.jpg");	
	}	
	
}