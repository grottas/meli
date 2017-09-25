package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Rol;
import modelo.SubRol;
import modelo.UserMeli;
import bean.Conexion;

public class UserMeliDAO extends ClassConexionDAO {
	
	private RolDAO rolDAO = new RolDAO();
	private SubRolDAO subRolDAO = new SubRolDAO();
	
	public UserMeliDAO() {
		super();
	}
	
	public ArrayList<UserMeli> selectAll() {
		ArrayList<UserMeli> list = new ArrayList<UserMeli>();
		String tiraSQL = "SELECT * FROM usuario;";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String id_meli = resultSet.getString("id_meli");
				String nombre = resultSet.getString("nombre").trim();
				String email = resultSet.getString("email").trim();
				String clave = resultSet.getString("clave").trim();
				Rol rol = rolDAO.selectById( resultSet.getString("rol") );
				SubRol sub_rol = subRolDAO.selectById( resultSet.getString("sub_rol") );
				
				UserMeli u = new UserMeli(id, id_meli, nombre, email, clave, rol, sub_rol);
				list.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
    }
	
	public ArrayList<UserMeli> selectVendedores() {
		ArrayList<UserMeli> list = new ArrayList<UserMeli>();
		String tiraSQL = "SELECT * FROM usuario WHERE rol NOT IN (1) AND sub_rol = 0;";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String id_meli = resultSet.getString("id_meli");
				String nombre = resultSet.getString("nombre").trim();
				String email = resultSet.getString("email").trim();
				String clave = resultSet.getString("clave").trim();
				Rol rol = rolDAO.selectById( resultSet.getString("rol") );
				SubRol sub_rol = subRolDAO.selectById( resultSet.getString("sub_rol") );
				
				UserMeli u = new UserMeli(id, id_meli, nombre, email, clave, rol, sub_rol);
				list.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
    }
	
	public ArrayList<UserMeli> selectSubVendedores(String id_meli) {
		ArrayList<UserMeli> list = new ArrayList<UserMeli>();
		String tiraSQL = "SELECT * FROM usuario WHERE id_meli ="+ id_meli +" AND sub_rol NOT IN (0);";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String nombre = resultSet.getString("nombre").trim();
				String email = resultSet.getString("email").trim();
				String clave = resultSet.getString("clave").trim();
				Rol rol = rolDAO.selectById( resultSet.getString("rol") );
				SubRol sub_rol = subRolDAO.selectById( resultSet.getString("sub_rol") );
				
				UserMeli u = new UserMeli(id, id_meli, nombre, email, clave, rol, sub_rol);
				list.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
    }
	
	public UserMeli selectByEmailAndClave(String email, String clave) {
		UserMeli u = null;
		String tiraSQL = "SELECT * FROM usuario WHERE email='"+ email +"' AND clave='"+ clave+"';";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String id_meli = resultSet.getString("id_meli");
				String nombre = resultSet.getString("nombre").trim();
				Rol rol = rolDAO.selectById( resultSet.getString("rol") );
				SubRol sub_rol = subRolDAO.selectById( resultSet.getString("sub_rol") );
				
				u = new UserMeli(id, id_meli, nombre, email, clave, rol, sub_rol);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
    }
	
	public UserMeli selectById(String id) {
		UserMeli u = null;
		String tiraSQL = "SELECT * FROM usuario WHERE id="+ id +";";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id_meli = resultSet.getString("id_meli");
				String nombre = resultSet.getString("nombre").trim();
				String email = resultSet.getString("email").trim();
				String clave = resultSet.getString("clave").trim();
				Rol rol = rolDAO.selectById( resultSet.getString("rol") );
				SubRol sub_rol = subRolDAO.selectById( resultSet.getString("sub_rol") );
				
				u = new UserMeli(id, id_meli, nombre, email, clave, rol, sub_rol);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
    }
	
	public UserMeli selectByIdMeli(String id_meli) {
		UserMeli u = null;
		String tiraSQL = "SELECT * FROM usuario WHERE id_meli="+ id_meli +";";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String nombre = resultSet.getString("nombre").trim();
				String email = resultSet.getString("email").trim();
				String clave = resultSet.getString("clave").trim();
				Rol rol = rolDAO.selectById( resultSet.getString("rol") );
				SubRol sub_rol = subRolDAO.selectById( resultSet.getString("sub_rol") );
				
				u = new UserMeli(id, id_meli, nombre, email, clave, rol, sub_rol);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
    }
	
	public boolean codeMeliIsUsed(String id_meli) {
		return selectByIdMeli(id_meli) != null;
	}
	
	public void update(UserMeli u) {
		String rol = u.getRol() == null ? "0" : u.getRol().getId();
		String subRol = u.getSub_rol() == null ? "0" : u.getSub_rol().getId();
		
		String Sql = "UPDATE usuario ";
		Sql = Sql.concat("SET id_meli=" + u.getId_meli() + ",");
		Sql = Sql.concat("nombre='" + u.getNombre() + "',");
		Sql = Sql.concat("email='" + u.getEmail() + "',");
		Sql = Sql.concat("clave='" + u.getClave() + "',");
		Sql = Sql.concat("rol=" + rol + ",");
		Sql = Sql.concat("sub_rol=" + subRol + " ");
		Sql = Sql.concat("WHERE id="+ u.getId() +";");

		Conexion.ejecutar(Sql);
	 }
	
	public void insert(UserMeli u) {
		String rol = u.getRol() == null ? "0" : u.getRol().getId();
		String subRol = u.getSub_rol() == null ? "0" : u.getSub_rol().getId();
		
		String Sql = "INSERT INTO ";
		Sql = Sql.concat("usuario(id_meli, nombre, email, clave, rol, sub_rol) ");
		Sql = Sql.concat("VALUES ("+ u.getId_meli() +", ");
		Sql = Sql.concat("'"+ u.getNombre() +"', ");
		Sql = Sql.concat("'"+ u.getEmail() +"', ");
		Sql = Sql.concat("'"+ u.getClave() +"', ");
		Sql = Sql.concat(rol +", ");
		Sql = Sql.concat(subRol +");");

	    Conexion.ejecutar(Sql);
	 }
	
	public void delete(UserMeli u) {
		for (UserMeli users : selectSubVendedores(u.getId_meli())) {
			deleteUser(users);
		}
		deleteUser(u);
	}
	
	private void deleteUser(UserMeli u) {
		String Sql = "DELETE FROM usuario ";
		Sql = Sql.concat("WHERE id="+ u.getId() +";");

		Conexion.ejecutar(Sql);
	}

}
