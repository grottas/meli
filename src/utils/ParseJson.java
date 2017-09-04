package utils;

import java.util.ArrayList;
import java.util.List;

import modelo.Answer;
import modelo.Question;
import modelo.UserCurrent;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class ParseJson {
	
	public static List<Question> questions(String json) {
		List<Question> list = new ArrayList<>();
		JsonObject obj = stringToJsonObject(json);
		JsonArray questions = obj.getAsJsonArray("questions");
		for (JsonElement question : questions) {
			Answer answer = null;
			if (buscarJson(question, "answer") != null) {
				answer = new Answer(buscarJson(question, "text"), 
						buscarJson(question, "status"), 
						buscarJson(question, "date_created"));
			}
			list.add(new Question(buscarJson(question, "id"), 
					answer, 
					buscarJson(question, "date_created"), 
					buscarJson(question, "item_id"), 
					buscarJson(question, "seller_id"), 
					buscarJson(question, "status"), 
					buscarJson(question, "text")));
		}
		return list;
	}
	
	public static UserCurrent me(String json, String accessToken, String refreshToken) {
		JsonElement obj = stringToJsonElement(json);
		
		return new UserCurrent(buscarJson(obj, "id"),
								buscarJson(obj, "nickname"),
								buscarJson(obj, "first_name"),
								buscarJson(obj, "last_name"),
								accessToken,
								refreshToken);
	}
	
	public static JsonElement stringToJsonElement(String cadena) {
		JsonParser jp = new JsonParser (); 
		return jp.parse(cadena);
	}
	public static JsonObject stringToJsonObject(String cadena) {
		JsonParser jp = new JsonParser (); 
		JsonElement je = jp.parse(cadena);
		return JsonElementToJsonObject(je);
	}
	public static JsonObject JsonElementToJsonObject(JsonElement je) {
		return je.getAsJsonObject();
	}
	public static String buscarJson(JsonElement je, String buscar) {
		JsonObject jo = JsonElementToJsonObject(je);
		return validarJsonObjToString(jo.get(buscar));
	}
	public static String validarJsonObjToString(JsonElement jsonElement) {
		String a = jsonElement.toString();
		return a.replaceAll("\"", "");
	}
	public static String buscarJson(JsonElement je, String array, String buscar) {
		JsonObject jo = JsonElementToJsonObject(je);
		return buscarJson(jo.get(array), buscar);
	}
 
}
