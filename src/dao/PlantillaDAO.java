package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Plantilla;
import bean.Conexion;

public class PlantillaDAO extends ClassConexionDAO {
	
	public PlantillaDAO() {
		super();
	}
	
	public Plantilla selectById(String id) {
		Plantilla p = null;
		String tiraSQL = "SELECT * FROM plantilla WHERE id_user='"+ id +"';";
		ResultSet resultSet = Conexion.consultar(tiraSQL);		

		try {
			while (resultSet.next()) {
				String text = resultSet.getString("text").trim();
				
				p = new Plantilla(id, text);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
    }
	
	public boolean hasOne(String id) {
		return selectById(id) != null;
	}
	
	public void update(Plantilla p) {
		String Sql = "UPDATE plantilla SET text = '" + p.getText().trim() + 
	    	  "' WHERE id_user='"+ p.getId() +"';";

	    Conexion.ejecutar(Sql);
	 }
	
	public void insert(Plantilla p) {
		String Sql = "INSERT INTO plantilla VALUES ('" +
	    				p.getId() + "', '" + p.getText().trim() + "');"; 

	    Conexion.ejecutar(Sql);
	 }

}
