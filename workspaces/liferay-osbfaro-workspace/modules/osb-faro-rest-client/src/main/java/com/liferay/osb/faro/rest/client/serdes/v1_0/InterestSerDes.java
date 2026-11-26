/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.client.serdes.v1_0;

import com.liferay.osb.faro.rest.client.dto.v1_0.Interest;
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
public class InterestSerDes {

	public static Interest toDTO(String json) {
		InterestJSONParser interestJSONParser = new InterestJSONParser();

		return interestJSONParser.parseToDTO(json);
	}

	public static Interest[] toDTOs(String json) {
		InterestJSONParser interestJSONParser = new InterestJSONParser();

		return interestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Interest interest) {
		if (interest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (interest.getDateRecorded() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateRecorded\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(interest.getDateRecorded()));

			sb.append("\"");
		}

		if (interest.getIndividualId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"individualId\": ");

			sb.append("\"");

			sb.append(_escape(interest.getIndividualId()));

			sb.append("\"");
		}

		if (interest.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(interest.getName()));

			sb.append("\"");
		}

		if (interest.getRelatedPagesCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"relatedPagesCount\": ");

			sb.append(interest.getRelatedPagesCount());
		}

		if (interest.getScore() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"score\": ");

			sb.append(interest.getScore());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		InterestJSONParser interestJSONParser = new InterestJSONParser();

		return interestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Interest interest) {
		if (interest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (interest.getDateRecorded() == null) {
			map.put("dateRecorded", null);
		}
		else {
			map.put(
				"dateRecorded",
				liferayToJSONDateFormat.format(interest.getDateRecorded()));
		}

		if (interest.getIndividualId() == null) {
			map.put("individualId", null);
		}
		else {
			map.put("individualId", String.valueOf(interest.getIndividualId()));
		}

		if (interest.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(interest.getName()));
		}

		if (interest.getRelatedPagesCount() == null) {
			map.put("relatedPagesCount", null);
		}
		else {
			map.put(
				"relatedPagesCount",
				String.valueOf(interest.getRelatedPagesCount()));
		}

		if (interest.getScore() == null) {
			map.put("score", null);
		}
		else {
			map.put("score", String.valueOf(interest.getScore()));
		}

		return map;
	}

	public static class InterestJSONParser extends BaseJSONParser<Interest> {

		@Override
		protected Interest createDTO() {
			return new Interest();
		}

		@Override
		protected Interest[] createDTOArray(int size) {
			return new Interest[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "dateRecorded")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "individualId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "relatedPagesCount")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "score")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			Interest interest, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateRecorded")) {
				if (jsonParserFieldValue != null) {
					interest.setDateRecorded(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "individualId")) {
				if (jsonParserFieldValue != null) {
					interest.setIndividualId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					interest.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "relatedPagesCount")) {
				if (jsonParserFieldValue != null) {
					interest.setRelatedPagesCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "score")) {
				if (jsonParserFieldValue != null) {
					interest.setScore(
						Double.valueOf((String)jsonParserFieldValue));
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
// LIFERAY-REST-BUILDER-HASH:2088467841