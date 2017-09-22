package utils;

public final class Message {

	public final static String ConexionProblems = "Actualmente existen problemas de conexión, por favor intente de nuevo más tarde";
	public final static String DeleteQuestion = "¿Desea eliminar la pregunta seleccionada? De confirmar, la misma no aparecerá en los listados de preguntas realizadas";
	public final static String EmptyListQuestions = "No hay preguntas para mostrar!";
	public final static String EmptyResponse = "Ingrese una respuesta.";
	
	public final static String sendResponse(int n, int total) {
		return "Respondiendo " + n + " de " + total + " preguntas.";
	}
}