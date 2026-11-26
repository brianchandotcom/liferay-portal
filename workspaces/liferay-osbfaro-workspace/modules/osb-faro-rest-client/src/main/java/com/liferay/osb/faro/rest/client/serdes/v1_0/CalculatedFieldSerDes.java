/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.client.serdes.v1_0;

import com.liferay.osb.faro.rest.client.dto.v1_0.CalculatedField;
import com.liferay.osb.faro.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Leslie Wong
 * @generated
 */
@Generated("")
public class CalculatedFieldSerDes {

	public static CalculatedField toDTO(String json) {
		CalculatedFieldJSONParser calculatedFieldJSONParser =
			new CalculatedFieldJSONParser();

		return calculatedFieldJSONParser.parseToDTO(json);
	}

	public static CalculatedField[] toDTOs(String json) {
		CalculatedFieldJSONParser calculatedFieldJSONParser =
			new CalculatedFieldJSONParser();

		return calculatedFieldJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CalculatedField calculatedField) {
		if (calculatedField == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (calculatedField.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					calculatedField.getDateModified()));

			sb.append("\"");
		}

		if (calculatedField.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(calculatedField.getName()));

			sb.append("\"");
		}

		if (calculatedField.getNamespace() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"namespace\": ");

			sb.append("\"");

			sb.append(_escape(calculatedField.getNamespace()));

			sb.append("\"");
		}

		if (calculatedField.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("\"");

			sb.append(_escape(calculatedField.getValue()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CalculatedFieldJSONParser calculatedFieldJSONParser =
			new CalculatedFieldJSONParser();

		return calculatedFieldJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(CalculatedField calculatedField) {
		if (calculatedField == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (calculatedField.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					calculatedField.getDateModified()));
		}

		if (calculatedField.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(calculatedField.getName()));
		}

		if (calculatedField.getNamespace() == null) {
			map.put("namespace", null);
		}
		else {
			map.put(
				"namespace", String.valueOf(calculatedField.getNamespace()));
		}

		if (calculatedField.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(calculatedField.getValue()));
		}

		return map;
	}

	public static class CalculatedFieldJSONParser
		extends BaseJSONParser<CalculatedField> {

		@Override
		protected CalculatedField createDTO() {
			return new CalculatedField();
		}

		@Override
		protected CalculatedField[] createDTOArray(int size) {
			return new CalculatedField[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "dateModified")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "namespace")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			CalculatedField calculatedField, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					calculatedField.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					calculatedField.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "namespace")) {
				if (jsonParserFieldValue != null) {
					calculatedField.setNamespace((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					calculatedField.setValue((String)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>)value;

			return _toJSON(collection.toArray());
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}
// LIFERAY-REST-BUILDER-HASH:839265714