package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Tag;
import bean.Conexion;

public class TagDAO extends ClassConexionDAO {
	
	public TagDAO() {
		super();
	}
	
	public List<Tag> selectAll(String id_meli) {
		List<Tag> list = new ArrayList<Tag>();
		String tiraSQL = "SELECT * FROM tag WHERE id_meli = " + id_meli + ";";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id = resultSet.getString("id").trim();
				String nombre = resultSet.getString("nombre").trim();
				String descripcion = resultSet.getString("descripcion").trim();
				String texto = resultSet.getString("texto").trim();
				
				Tag t = new Tag(nombre, descripcion, texto, id, id_meli);
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
    }
	
	public Tag selectById(String id) {
		Tag t = null;
		String tiraSQL = "SELECT * FROM tag WHERE id="+ id +";";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id_meli = resultSet.getString("id_meli").trim();
				String nombre = resultSet.getString("nombre").trim();
				String descripcion = resultSet.getString("descripcion").trim();
				String texto = resultSet.getString("texto").trim();
				
				t = new Tag(nombre, descripcion, texto, id, id_meli);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
    }
	
	public Tag selectByIdMeliAndNombre(String id_meli, String nombre) {
		Tag t = null;
		String tiraSQL = "SELECT * FROM tag WHERE id_meli = "+ id_meli +" AND nombre = '" + nombre + "';";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id = resultSet.getString("id").trim();
				String descripcion = resultSet.getString("descripcion").trim();
				String texto = resultSet.getString("texto").trim();
				
				t = new Tag(nombre, descripcion, texto, id, id_meli);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
    }
	
	public void update(Tag t) {
		String Sql = "UPDATE tag ";
		Sql = Sql.concat("SET nombre = '" + t.getNombre().trim() + "', ");
		Sql = Sql.concat("descripcion = '" + t.getDescripcion().trim() + "', ");
		Sql = Sql.concat("texto = '" + t.getTexto().trim() + "', ");
		Sql = Sql.concat("id_meli = " + t.getId_meli() + " ");
		Sql = Sql.concat("WHERE id="+ t.getId() +";");

	    Conexion.ejecutar(Sql);
	 }
	
	public void insert(Tag t) {
		String Sql = "INSERT INTO ";
		Sql = Sql.concat("tag(id_meli, nombre, descripcion, texto) ");
		Sql = Sql.concat("VALUES (" + t.getId_meli() + ", ");
		Sql = Sql.concat("'" + t.getNombre().trim() +"', ");
		Sql = Sql.concat("'" + t.getDescripcion().trim() +"', ");
		Sql = Sql.concat("'" + t.getTexto().trim() + "');"); 

		Conexion.ejecutar(Sql);
	 }
	
	public void delete(Tag t) {
		String Sql = "DELETE FROM tag ";
		Sql = Sql.concat("WHERE id="+ t.getId() +";");

		Conexion.ejecutar(Sql);
	}

}
