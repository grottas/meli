package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.SubRol;
import bean.Conexion;

public class SubRolDAO extends ClassConexionDAO {
	
	public SubRolDAO() {
		super();
	}
	
	public List<SubRol> selectByIdMeli(String id_meli) {
		List<SubRol> list = new ArrayList<SubRol>();
		String tiraSQL = "SELECT * FROM sub_rol WHERE id_meli="+ id_meli +";";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id = resultSet.getString("id").trim();
				String descripcion = resultSet.getString("descripcion").trim();
				
				SubRol r = new SubRol(id, id_meli, descripcion);
				list.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
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
	
	public SubRol selectByDescMeliID(SubRol r) {
		String tiraSQL = "SELECT * FROM sub_rol WHERE id_meli="+ r.getId_meli() +" AND descripcion ='"+ r.getDescripcion() +"';";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id = resultSet.getString("id").trim();
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
	
	public void delete(SubRol r) {
		String Sql = "DELETE FROM sub_rol ";
		Sql = Sql.concat("WHERE id="+ r.getId() +";");
		Conexion.ejecutar(Sql);
		
		Sql = "DELETE FROM sub_rol_permiso ";
		Sql = Sql.concat("WHERE sub_rol_id="+ r.getId() +";");
		Conexion.ejecutar(Sql);
		
		Sql = "DELETE FROM usuario ";
		Sql = Sql.concat("WHERE sub_rol="+ r.getId() +";");
		Conexion.ejecutar(Sql);
	}
	
	public void deleteOnlyBridge(SubRol r) {
		String Sql = "DELETE FROM sub_rol_permiso ";
		Sql = Sql.concat("WHERE sub_rol_id="+ r.getId() +";");
		Conexion.ejecutar(Sql);
	}

}
