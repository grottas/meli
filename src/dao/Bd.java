package dao;

import modelo.Plantilla;

public class Bd {
	
	private PlantillaDAO plantillaDAO = new PlantillaDAO();
	
	public Plantilla plantillaSelectById(String id) {
		return plantillaDAO.selectById(id);
	}
	
	public void plantillaUpdate(Plantilla p) {
		plantillaDAO.update(p);
	}

	public void plantillaInsert(Plantilla p) {
		plantillaDAO.insert(p);
	}
	
	public boolean plantillaHasOne(String id) {
		return plantillaDAO.hasOne(id);
	}
	
}
