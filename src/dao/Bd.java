package dao;

import java.util.ArrayList;
import java.util.List;

import modelo.Permiso;
import modelo.Plantilla;
import modelo.Rol;
import modelo.SitesMl;
import modelo.SubRol;
import modelo.SubRolPermiso;
import modelo.Tag;
import modelo.UserMeli;

public class Bd {
	
	private PlantillaDAO plantillaDAO = new PlantillaDAO();
	private RolDAO rolDAO = new RolDAO();
	private UserMeliDAO userMeliDAO = new UserMeliDAO();
	private TagDAO tagDAO = new TagDAO();
	private SitesMlDAO sitesMlDAO = new SitesMlDAO();
	private PermisoDAO permisoDAO = new PermisoDAO();
	private SubRolPermisoDAO subRolPermisoDAO = new SubRolPermisoDAO();
	private SubRolDAO subRolDAO = new SubRolDAO();
	
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
	
	public ArrayList<UserMeli> userSelectVendedores() {
		return userMeliDAO.selectVendedores();
	}
	
	public ArrayList<UserMeli> userSelectSubVendedores(String id_meli) {
		return userMeliDAO.selectSubVendedores(id_meli);
	}
	
	public UserMeli userSelectByEmailAndClave(String email, String clave) {
		return userMeliDAO.selectByEmailAndClave(email, clave);
	}
	
	public UserMeli userSelectById(String id) {
		return userMeliDAO.selectById(id);
	}
	
	public UserMeli userSelectByIdMeli(String id_meli) {
		return userMeliDAO.selectByIdMeli(id_meli);
	}
	
	public boolean userCodeMeliIsUsed(String id_meli) {
		return userMeliDAO.codeMeliIsUsed(id_meli);
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
	
	public List<SitesMl> sitesSelectAll() {
		return sitesMlDAO.selectAll();
	}
	
	public Permiso permisoSelectById(String id) {
		return permisoDAO.selectById(id);
	}
	
	public void permisoUpdate(Permiso p) {
		permisoDAO.update(p);
	}
	
	public void permisoInsert(Permiso p) {
		permisoDAO.insert(p);
	}
	
	public List<SubRolPermiso> permisoRolSelectBySubRolId(String sub_rol_id) {
		return subRolPermisoDAO.selectBySubRolId(sub_rol_id);
	}
	
	public SubRolPermiso permisoSelectBySubRolAndPermisionId(String sub_rol_id, String permiso_id) {
		return subRolPermisoDAO.selectBySubRolAndPermisionId(sub_rol_id, permiso_id);
	}
	
	public boolean permisoRolAccess(String sub_rol_id, String permiso_id) {
		return subRolPermisoDAO.rolAccess(sub_rol_id, permiso_id);
	}
	
	public void permisoRolUpdate(SubRolPermiso p) {
		subRolPermisoDAO.update(p);
	}
	
	public void permisoRolInsert(SubRolPermiso p) {
		subRolPermisoDAO.insert(p);
	}
	
	public void permisoRolDelete(SubRolPermiso p) {
		subRolPermisoDAO.delete(p);
	}
	
	public List<SubRol> subRolSelectByIdMeli(String id_meli) {
		return subRolDAO.selectByIdMeli(id_meli);
	}
	
	public SubRol subRolSelectById(String id) {
		return subRolDAO.selectById(id);
	}
	
	public SubRol subRolSelectByDescMeliID(SubRol r) {
		return subRolDAO.selectByDescMeliID(r);
	}
	
	public void subRolUpdate(SubRol r) {
		subRolDAO.update(r);
	}
	
	public void subRolInsert(SubRol r) {
		subRolDAO.insert(r);
	}
	
	public void subRolDelete(SubRol r) {
		subRolDAO.delete(r);
	}
	
	public void subRoleleteOnlyBridge(SubRol r) {
		subRolDAO.deleteOnlyBridge(r);
	}
	
}
