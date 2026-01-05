/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.hubspot.service;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Keven Leone
 * @author Ricardo Mariz
 */
@Component
public class HubSpotService extends BaseService {

	public JSONObject postCompany(JSONObject propertiesJSONObject) {
		JSONObject companyJSONObject = search(
			"companies", "name", propertiesJSONObject.getString("name"));

		if (companyJSONObject != null) {
			return companyJSONObject;
		}

		JSONObject jsonObject = new JSONObject(
			post(
				_getAuthorization(),
				new JSONObject(
				).put(
					"properties",
					_getHubSpotPropertiesJSONObject(
						propertiesJSONObject, "companies")
				).toString(),
				UriComponentsBuilder.fromUriString(
					_hubSpotURL
				).path(
					"/crm/v3/objects/companies"
				).build(
				).toUri()));

		if (_log.isInfoEnabled()) {
			_log.info("HubSpot company: " + jsonObject);
		}

		return jsonObject;
	}

	public JSONObject postContact(JSONObject propertiesJSONObject) {
		JSONObject contactJSONObject = search(
			"contacts", "email", propertiesJSONObject.getString("email"));

		if (contactJSONObject != null) {
			return contactJSONObject;
		}

		JSONObject jsonObject = new JSONObject(
			post(
				_getAuthorization(),
				new JSONObject(
				).put(
					"properties",
					_getHubSpotPropertiesJSONObject(
						propertiesJSONObject, "contacts")
				).toString(),
				UriComponentsBuilder.fromUriString(
					_hubSpotURL
				).path(
					"/crm/v3/objects/contacts"
				).build(
				).toUri()));

		if (_log.isInfoEnabled()) {
			_log.info("HubSpot contact: " + jsonObject);
		}

		return jsonObject;
	}

	public JSONObject postLead(
		String contactId, JSONObject propertiesJSONObject) {

		JSONObject jsonObject = new JSONObject(
			post(
				_getAuthorization(),
				new JSONObject(
				).put(
					"associations",
					new JSONArray(
					).put(
						new JSONObject(
						).put(
							"to",
							new JSONObject(
							).put(
								"id", contactId
							)
						).put(
							"types",
							new JSONArray(
							).put(
								new JSONObject(
								).put(
									"associationCategory", "HUBSPOT_DEFINED"
								).put(
									"associationTypeId", _ASSOCIATION_TYPE_ID
								)
							)
						)
					)
				).put(
					"properties",
					_getHubSpotPropertiesJSONObject(
						propertiesJSONObject, "leads")
				).toString(),
				UriComponentsBuilder.fromUriString(
					_hubSpotURL
				).path(
					"/crm/v3/objects/leads"
				).build(
				).toUri()));

		if (_log.isInfoEnabled()) {
			_log.info("HubSpot lead: " + jsonObject);
		}

		return jsonObject;
	}

	@Cacheable("hubSpotSearch")
	public JSONObject search(
		String objectName, String propertyName, String value) {

		JSONObject jsonObject = new JSONObject(
			post(
				_getAuthorization(),
				new JSONObject(
				).put(
					"filterGroups",
					new JSONArray(
					).put(
						new JSONObject(
						).put(
							"filters",
							new JSONArray(
							).put(
								new JSONObject(
								).put(
									"operator", "EQ"
								).put(
									"propertyName", propertyName
								).put(
									"value", value
								)
							)
						)
					)
				).put(
					"limit", 1
				).toString(),
				UriComponentsBuilder.fromUriString(
					_hubSpotURL
				).path(
					"/crm/v3/objects/" + objectName + "/search"
				).build(
				).toUri()));

		JSONArray jsonArray = jsonObject.optJSONArray("results");

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Found ", jsonObject.optInt("total"), " results for \"",
					objectName, "\" equal to \"", value, "\""));
		}

		if (jsonArray.isEmpty()) {
			return null;
		}

		return jsonArray.getJSONObject(0);
	}

	private String _getAuthorization() {
		return "Bearer " + _hubSpotToken;
	}

	private JSONObject _getHubSpotPropertiesJSONObject(
		JSONObject jsonObject, String propertyName) {

		Map<String, JSONObject> map = _writeObject(
			_getPropertyJSONArray(propertyName));

		JSONObject propertiesJSONObject = new JSONObject();

		for (String key : jsonObject.keySet()) {
			Object value = jsonObject.get(key);

			String normalizedKey = _normalizeKey(key);

			if (map.containsKey(normalizedKey)) {
				JSONObject hubspotPropertyJSONObject = map.get(normalizedKey);

				Object propertyValue = value;

				JSONArray optionsJSONArray =
					hubspotPropertyJSONObject.optJSONArray("options");

				for (int i = 0; i < optionsJSONArray.length(); i++) {
					JSONObject optionJSONObject =
						optionsJSONArray.getJSONObject(i);

					if (Objects.equals(
							_normalizeKey(optionJSONObject.getString("value")),
							_normalizeKey(String.valueOf(value)))) {

						propertyValue = optionJSONObject.getString("value");

						break;
					}
				}

				propertiesJSONObject.put(
					hubspotPropertyJSONObject.getString("name"), propertyValue);
			}
		}

		return propertiesJSONObject;
	}

	@Cacheable("hubSpotProperty")
	private JSONArray _getPropertyJSONArray(String propertyName) {
		JSONObject jsonObject = new JSONObject(
			get(
				_getAuthorization(),
				UriComponentsBuilder.fromUriString(
					_hubSpotURL
				).path(
					"/crm/v3/properties/" + propertyName
				).build(
				).toUri()));

		return jsonObject.optJSONArray("results");
	}

	private String _normalizeKey(String key) {
		return key.toLowerCase(
		).replaceAll(
			"[^a-z0-9]", StringPool.BLANK
		);
	}

	private Map<String, JSONObject> _writeObject(JSONArray jsonArray) {
		Map<String, JSONObject> map = new HashMap<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject propertyJSONObject = jsonArray.getJSONObject(i);

			String propertyName = propertyJSONObject.getString("name");

			map.put(_normalizeKey(propertyName), propertyJSONObject);
		}

		return map;
	}

	private static final int _ASSOCIATION_TYPE_ID = 578;

	private static final Log _log = LogFactory.getLog(HubSpotService.class);

	@Value("${liferay.hubspot.token}")
	private String _hubSpotToken;

	@Value("${liferay.hubspot.url}")
	private String _hubSpotURL;

}