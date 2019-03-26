package com.liferay.talend.processor;

import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.Set;

public class OpenApiPrinter {

	public static void print(JsonObject openApiJSONObject) {
		if (openApiJSONObject == null) {
			System.out.println(
				"ERROR: Unable to print json object that is null");
		}

		JsonObject infoJSONObject = openApiJSONObject.getJsonObject("info");

		if (infoJSONObject == null) {
			printKeys(openApiJSONObject);

			System.out.println(
				"ERROR: Unable to continue without info node");

			return;
		}

		printValue("version", infoJSONObject);
		printValue("title", infoJSONObject);
		printValue("description", infoJSONObject);

		JsonObject pathsJSONObject = openApiJSONObject.getJsonObject("paths");

		Set<String> paths = pathsJSONObject.keySet();

		for (String path : paths) {
			JsonValue pathJSONValue = pathsJSONObject.get(path);

			System.out.println("Analyzing path: " + path);
			System.out.println("Value: " + pathJSONValue.toString());
		}
	}

	public static void printKeys(JsonObject jsonObject) {
		Set<String> keys = jsonObject.keySet();

		for (String key : keys) {
			System.out.println("Key --> " + key);
		}
	}

	public static void printVersion(JsonObject openApiJSONObject) {
		if (openApiJSONObject == null) {
			System.out.println("Unable to print json object that is null");
		}

		printValue("version", openApiJSONObject.getJsonObject("info"));
	}

	public static void printTitle(JsonObject openApiJSONObject) {
		if (openApiJSONObject == null) {
			System.out.println("Unable to print json object that is null");
		}

		printValue("title", openApiJSONObject.getJsonObject("info"));
	}

	public static void printDescription(JsonObject openApiJSONObject) {
		if (openApiJSONObject == null) {
			System.out.println("Unable to print json object that is null");
		}

		printValue("description", openApiJSONObject.getJsonObject("info"));
	}

	public static void printValue(String key, JsonObject jsonObject) {
		System.out.println(
			"Open Api Spec " + key + ":" + jsonObject.getString(key));
	}

}
