package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Permiso;
import modelo.SubRol;
import modelo.SubRolPermiso;
import bean.Conexion;

public class SubRolPermisoDAO extends ClassConexionDAO {
	
	private SubRolDAO subRolDAO = new SubRolDAO();
	private PermisoDAO permisoDAO = new PermisoDAO();
	
	public SubRolPermisoDAO() {
		super();
	}
	
	public List<SubRolPermiso> selectBySubRolId(String sub_rol_id) {
		List<SubRolPermiso> list = new ArrayList<SubRolPermiso>();
		sub_rol_id = sub_rol_id.isEmpty() ? "0" : sub_rol_id;
		String tiraSQL = "SELECT * FROM sub_rol_permiso WHERE sub_rol_id="+ sub_rol_id +";";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				SubRol sub_rol = subRolDAO.selectById( resultSet.getString("sub_rol_id") );
				Permiso permiso = permisoDAO.selectById( resultSet.getString("permiso_id").trim() );
				
				SubRolPermiso p = new SubRolPermiso(id, sub_rol, permiso);
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
    }
	
	public SubRolPermiso selectBySubRolAndPermisionId(String sub_rol_id, String permiso_id) {
		SubRolPermiso p = null;
		String tiraSQL = "SELECT * FROM sub_rol_permiso WHERE sub_rol_id="+ sub_rol_id +" AND permiso_id ="+ permiso_id +";";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				SubRol sub_rol = subRolDAO.selectById( resultSet.getString("sub_rol_id") );
				Permiso permiso = permisoDAO.selectById( resultSet.getString("permiso_id").trim() );
				
				p = new SubRolPermiso(id, sub_rol, permiso);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
    }
	
	public boolean rolAccess(String sub_rol_id, String permiso_id) {
		return selectBySubRolAndPermisionId(sub_rol_id, permiso_id) != null;
	}
	
	public void update(SubRolPermiso p) {
		String Sql = "UPDATE sub_rol_permiso ";
		Sql = Sql.concat("SET sub_rol_id = " + p.getSubRol().getId() + ", ");
		Sql = Sql.concat("permiso_id = " + p.getPermiso().getId() +" ");
		Sql = Sql.concat("WHERE id="+ p.getId() +";");

	    Conexion.ejecutar(Sql);
	 }
	
	public void insert(SubRolPermiso p) {
		String Sql = "INSERT INTO ";
		Sql = Sql.concat("sub_rol_permiso(sub_rol_id, permiso_id) ");
		Sql = Sql.concat("VALUES ("+ p.getSubRol().getId() + ", ");
		Sql = Sql.concat(p.getPermiso().getId() +");"); 
System.out.println(Sql);
	    Conexion.ejecutar(Sql);
	 }
	
	public void delete(SubRolPermiso p) {
		String Sql = "DELETE FROM sub_rol_permiso ";
		Sql = Sql.concat("WHERE id="+ p.getId() +";");

		Conexion.ejecutar(Sql);
	}

}
