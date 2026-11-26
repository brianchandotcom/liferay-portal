/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.client.serdes.v1_0;

import com.liferay.osb.faro.rest.client.dto.v1_0.DataSource;
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
public class DataSourceSerDes {

	public static DataSource toDTO(String json) {
		DataSourceJSONParser dataSourceJSONParser = new DataSourceJSONParser();

		return dataSourceJSONParser.parseToDTO(json);
	}

	public static DataSource[] toDTOs(String json) {
		DataSourceJSONParser dataSourceJSONParser = new DataSourceJSONParser();

		return dataSourceJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataSource dataSource) {
		if (dataSource == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (dataSource.getAccountsCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountsCount\": ");

			sb.append(dataSource.getAccountsCount());
		}

		if (dataSource.getCampaignsCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"campaignsCount\": ");

			sb.append(dataSource.getCampaignsCount());
		}

		if (dataSource.getChannelIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelIds\": ");

			sb.append("[");

			for (int i = 0; i < dataSource.getChannelIds().length; i++) {
				sb.append(_toJSON(dataSource.getChannelIds()[i]));

				if ((i + 1) < dataSource.getChannelIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataSource.getContactsSynced() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contactsSynced\": ");

			sb.append(dataSource.getContactsSynced());
		}

		if (dataSource.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(dataSource.getCreateDate()));

			sb.append("\"");
		}

		if (dataSource.getEventsCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"eventsCount\": ");

			sb.append(dataSource.getEventsCount());
		}

		if (dataSource.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(dataSource.getId()));

			sb.append("\"");
		}

		if (dataSource.getModifiedDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(dataSource.getModifiedDate()));

			sb.append("\"");
		}

		if (dataSource.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(dataSource.getName()));

			sb.append("\"");
		}

		if (dataSource.getProviderType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"providerType\": ");

			sb.append("\"");

			sb.append(_escape(dataSource.getProviderType()));

			sb.append("\"");
		}

		if (dataSource.getSitesCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sitesCount\": ");

			sb.append(dataSource.getSitesCount());
		}

		if (dataSource.getSitesSynced() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sitesSynced\": ");

			sb.append(dataSource.getSitesSynced());
		}

		if (dataSource.getState() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"state\": ");

			sb.append("\"");

			sb.append(_escape(dataSource.getState()));

			sb.append("\"");
		}

		if (dataSource.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(_escape(dataSource.getStatus()));

			sb.append("\"");
		}

		if (dataSource.getUsersCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"usersCount\": ");

			sb.append(dataSource.getUsersCount());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataSourceJSONParser dataSourceJSONParser = new DataSourceJSONParser();

		return dataSourceJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DataSource dataSource) {
		if (dataSource == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (dataSource.getAccountsCount() == null) {
			map.put("accountsCount", null);
		}
		else {
			map.put(
				"accountsCount", String.valueOf(dataSource.getAccountsCount()));
		}

		if (dataSource.getCampaignsCount() == null) {
			map.put("campaignsCount", null);
		}
		else {
			map.put(
				"campaignsCount",
				String.valueOf(dataSource.getCampaignsCount()));
		}

		if (dataSource.getChannelIds() == null) {
			map.put("channelIds", null);
		}
		else {
			map.put("channelIds", String.valueOf(dataSource.getChannelIds()));
		}

		if (dataSource.getContactsSynced() == null) {
			map.put("contactsSynced", null);
		}
		else {
			map.put(
				"contactsSynced",
				String.valueOf(dataSource.getContactsSynced()));
		}

		if (dataSource.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(dataSource.getCreateDate()));
		}

		if (dataSource.getEventsCount() == null) {
			map.put("eventsCount", null);
		}
		else {
			map.put("eventsCount", String.valueOf(dataSource.getEventsCount()));
		}

		if (dataSource.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(dataSource.getId()));
		}

		if (dataSource.getModifiedDate() == null) {
			map.put("modifiedDate", null);
		}
		else {
			map.put(
				"modifiedDate",
				liferayToJSONDateFormat.format(dataSource.getModifiedDate()));
		}

		if (dataSource.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(dataSource.getName()));
		}

		if (dataSource.getProviderType() == null) {
			map.put("providerType", null);
		}
		else {
			map.put(
				"providerType", String.valueOf(dataSource.getProviderType()));
		}

		if (dataSource.getSitesCount() == null) {
			map.put("sitesCount", null);
		}
		else {
			map.put("sitesCount", String.valueOf(dataSource.getSitesCount()));
		}

		if (dataSource.getSitesSynced() == null) {
			map.put("sitesSynced", null);
		}
		else {
			map.put("sitesSynced", String.valueOf(dataSource.getSitesSynced()));
		}

		if (dataSource.getState() == null) {
			map.put("state", null);
		}
		else {
			map.put("state", String.valueOf(dataSource.getState()));
		}

		if (dataSource.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(dataSource.getStatus()));
		}

		if (dataSource.getUsersCount() == null) {
			map.put("usersCount", null);
		}
		else {
			map.put("usersCount", String.valueOf(dataSource.getUsersCount()));
		}

		return map;
	}

	public static class DataSourceJSONParser
		extends BaseJSONParser<DataSource> {

		@Override
		protected DataSource createDTO() {
			return new DataSource();
		}

		@Override
		protected DataSource[] createDTOArray(int size) {
			return new DataSource[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "accountsCount")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "campaignsCount")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "channelIds")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "contactsSynced")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "eventsCount")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "modifiedDate")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "providerType")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "sitesCount")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "sitesSynced")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "state")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "usersCount")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			DataSource dataSource, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "accountsCount")) {
				if (jsonParserFieldValue != null) {
					dataSource.setAccountsCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "campaignsCount")) {
				if (jsonParserFieldValue != null) {
					dataSource.setCampaignsCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "channelIds")) {
				if (jsonParserFieldValue != null) {
					dataSource.setChannelIds(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contactsSynced")) {
				if (jsonParserFieldValue != null) {
					dataSource.setContactsSynced((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					dataSource.setCreateDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "eventsCount")) {
				if (jsonParserFieldValue != null) {
					dataSource.setEventsCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					dataSource.setId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modifiedDate")) {
				if (jsonParserFieldValue != null) {
					dataSource.setModifiedDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					dataSource.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "providerType")) {
				if (jsonParserFieldValue != null) {
					dataSource.setProviderType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sitesCount")) {
				if (jsonParserFieldValue != null) {
					dataSource.setSitesCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sitesSynced")) {
				if (jsonParserFieldValue != null) {
					dataSource.setSitesSynced((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "state")) {
				if (jsonParserFieldValue != null) {
					dataSource.setState((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					dataSource.setStatus((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "usersCount")) {
				if (jsonParserFieldValue != null) {
					dataSource.setUsersCount(
						Long.valueOf((String)jsonParserFieldValue));
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
// LIFERAY-REST-BUILDER-HASH:411900151