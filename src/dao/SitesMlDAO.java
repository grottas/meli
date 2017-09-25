package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.SitesMl;
import bean.Conexion;

public class SitesMlDAO extends ClassConexionDAO {
	
	public SitesMlDAO() {
		super();
	}
	
	public List<SitesMl> selectAll() {
		List<SitesMl> list = new ArrayList<SitesMl>();
		String tiraSQL = "SELECT * FROM sites;";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String id = resultSet.getString("id").trim();
				String name = resultSet.getString("name").trim();
				
				SitesMl s = new SitesMl(id, name);
				list.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
    }

}
