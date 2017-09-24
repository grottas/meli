package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.SubRol;
import bean.Conexion;

public class SubRolDAO extends ClassConexionDAO {
	
	public SubRolDAO() {
		super();
	}
	
	public SubRol selectById(String id) {
		SubRol r = null;
		String tiraSQL = "SELECT * FROM sub_rol WHERE id="+ id +";";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id_meli = resultSet.getString("id_meli").trim();
				String descripcion = resultSet.getString("descripcion").trim();
				
				r = new SubRol(id, id_meli, descripcion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
    }
	
	public void update(SubRol r) {
		String Sql = "UPDATE sub_rol ";
		Sql = Sql.concat("SET descripcion = '" + r.getDescripcion().trim() + "' ");
		Sql = Sql.concat("WHERE id="+ r.getId() +";");

	    Conexion.ejecutar(Sql);
	 }
	
	public void insert(SubRol r) {
		String Sql = "INSERT INTO ";
		Sql = Sql.concat("sub_rol(id_meli, descripcion) ");
		Sql = Sql.concat("VALUES ("+ r.getId_meli() +", ");
		Sql = Sql.concat("'" + r.getDescripcion().trim() + "');"); 

	    Conexion.ejecutar(Sql);
	 }

}
