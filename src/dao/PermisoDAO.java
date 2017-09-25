package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Permiso;
import bean.Conexion;

public class PermisoDAO extends ClassConexionDAO {
	
	public PermisoDAO() {
		super();
	}
	
	public Permiso selectById(String id) {
		Permiso p = null;
		String tiraSQL = "SELECT * FROM permiso WHERE id="+ id +";";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String nombre = resultSet.getString("nombre").trim();
				
				p = new Permiso(id, nombre);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
    }
	
	public void update(Permiso p) {
		String Sql = "UPDATE permiso ";
		Sql = Sql.concat("SET nombre = '" + p.getNombre().trim() + "' ");
		Sql = Sql.concat("WHERE id="+ p.getId() +";");

	    Conexion.ejecutar(Sql);
	 }
	
	public void insert(Permiso p) {
		String Sql = "INSERT INTO ";
		Sql = Sql.concat("permiso(nombre) ");
		Sql = Sql.concat("VALUES ('"+ p.getNombre() + "');"); 

	    Conexion.ejecutar(Sql);
	 }

}
