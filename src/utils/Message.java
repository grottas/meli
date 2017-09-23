package utils;

public final class Message {

	public final static String ConexionProblems = "Actualmente existen problemas de conexión, por favor intente de nuevo más tarde";
	public final static String DeleteQuestion = "¿Desea eliminar la pregunta seleccionada? De confirmar, la misma no aparecerá en los listados de preguntas realizadas";
	public final static String EmptyListQuestions = "No hay preguntas para mostrar!";
	public final static String EmptyResponse = "Ingrese una respuesta.";
	public final static String ResponseSuceess = "La(s) pregunta(s) se respondieron exitosamente.";
	public final static String NeedSelectQuestions = "Seleccione una pregunta.";
	public final static String DeleteQuestionSuceess = "Pregunta eliminada exitosamente.";
	public final static String PlantillaInsert = "Plantilla guardada exitosamente.";
	public final static String PlantillaUpdate = "Plantilla actualizada exitosamente.";
	
	public final static String sendResponse(int n, int total) {
		return "Respondiendo " + n + " de " + total + " preguntas.";
	}
}