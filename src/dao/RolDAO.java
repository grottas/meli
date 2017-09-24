package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Rol;
import bean.Conexion;

public class RolDAO extends ClassConexionDAO {
	
	public RolDAO() {
		super();
	}
	
	public Rol selectById(String id) {
		Rol r = null;
		String tiraSQL = "SELECT * FROM rol WHERE id="+ id +";";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String descripcion = resultSet.getString("descripcion").trim();
				
				r = new Rol(id, descripcion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
    }
	
	public void update(Rol r) {
		String Sql = "UPDATE rol SET descripcion = '" + r.getDescripcion().trim() + 
	    	  "' WHERE id="+ r.getId() +";";

	    Conexion.ejecutar(Sql);
	 }
	
	public void insert(Rol r) {
		String Sql = "INSERT INTO rol(descripcion) VALUES ('" + r.getDescripcion().trim() + "');"; 

	    Conexion.ejecutar(Sql);
	 }

}
