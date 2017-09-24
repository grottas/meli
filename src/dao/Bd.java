package dao;

import java.util.ArrayList;
import java.util.List;

import modelo.Plantilla;
import modelo.Rol;
import modelo.Tag;
import modelo.UserMeli;

public class Bd {
	
	private PlantillaDAO plantillaDAO = new PlantillaDAO();
	private RolDAO rolDAO = new RolDAO();
	private UserMeliDAO userMeliDAO = new UserMeliDAO();
	private TagDAO tagDAO = new TagDAO();
	
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
	
	public Rol rolSelectById(String id) {
		return rolDAO.selectById(id);
	}
	
	public void rolUpdate(Rol r) {
		rolDAO.update(r);
	}

	public void rolInsert(Rol r) {
		rolDAO.insert(r);
	}
	
	public ArrayList<UserMeli> userSelectAll() {
		return userMeliDAO.selectAll();
	}
	
	public ArrayList<UserMeli> selectVendedores() {
		return userMeliDAO.selectVendedores();
	}
	
	public UserMeli userSelectByEmailAndClave(String email, String clave) {
		return userMeliDAO.selectByEmailAndClave(email, clave);
	}
	
	public UserMeli userSelectById(String id) {
		return userMeliDAO.selectById(id);
	}
	
	public void userUpdate(UserMeli u) {
		userMeliDAO.update(u);
	}
	
	public void userInsert(UserMeli u) {
		userMeliDAO.insert(u);
	}
	
	public void delete(UserMeli u) {
		userMeliDAO.delete(u);
	}
	
	public List<Tag> tagSelectAll() {
		return tagDAO.selectAll();
	}
	
	public Tag tagSelectById(String id) {
		return tagDAO.selectById(id);
	}
	
	public void tagUpdate(Tag t) {
		tagDAO.update(t);
	}
	
	public void tagInsert(Tag t) {
		tagDAO.insert(t);
	}
	
	public void tagDelete(Tag t) {
		tagDAO.delete(t);
	}
	
}
