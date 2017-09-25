package utils;

public final class Message {

	public final static String InvalideIdMeliWithIdML = "Debe ingresar con su cuenta de Mercadolibre";
	public final static String Oops = "Ops ocurrio un error!";
	public final static String ConexionProblems = "Actualmente existen problemas de conexión, por favor intente de nuevo más tarde";
	public final static String DeleteQuestion = "¿Desea eliminar la pregunta seleccionada? De confirmar, la misma no aparecerá en los listados de preguntas realizadas";
	public final static String DeleteTag = "¿Desea eliminar la etiqueta seleccionada?";
	public final static String DeleteRol = "¿Desea eliminar el rol seleccionada?";
	public final static String DeleteUsuario = "¿Desea eliminar al usuario seleccionado?";
	public final static String EmptyListQuestions = "No hay preguntas para mostrar!";
	public final static String EmptyResponse = "Ingrese una respuesta.";
	public final static String ResponseSuceess = "La(s) pregunta(s) se respondieron exitosamente.";
	public final static String NeedSelectQuestions = "Seleccione una pregunta.";
	public final static String NeedSelectTags = "Seleccione una etiqueta.";
	public final static String NeedSelectUser = "Seleccione una usuario.";
	public final static String NeedSelectRol = "Seleccione un rol.";
	public final static String NeedSelectSubUser = "Seleccione un vendedor.";
	public final static String DeleteQuestionSuceess = "Pregunta eliminada exitosamente.";
	public final static String DeleteRolionSuceess = "Rol eliminada exitosamente.";
	public final static String DeleteUsuarioSuccess = "Usuario eliminada exitosamente.";
	public final static String DeleteSubUsuarioSuccess = "Vendedor eliminado exitosamente.";
	public final static String PlantillaInsert = "Plantilla guardada exitosamente.";
	public final static String PlantillaUpdate = "Plantilla actualizada exitosamente.";
	public final static String LogInFail = "Combinacion email / clave incorrecta.";
	public final static String EmailValidate = "Formato de email incorrecto.";
	public final static String ResetPass = "Clave reestablecida a nombre de usuario.";
	public final static String RolAccess = "No posee los permisos.";
	public final static String ConfSuccess = "Datos actualizados.";

	
	public final static String sendResponse(int n, int total) {
		return "Respondiendo " + n + " de " + total + " preguntas.";
	}
}