/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jethr0.dalo;

import com.liferay.jethr0.util.LiferayOAuthConfiguration;
import com.liferay.jethr0.util.StringUtil;
import com.liferay.jethr0.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BaseDALO {

	protected JSONObject create(JSONObject requestJSONObject) {
		for (int i = 0; i <= _RETRY_COUNT; i++) {
			try {
				String response = WebClient.create(
					_liferayPortalURL + _getObjectDefinitionURLPath()
				).post(
				).accept(
					MediaType.APPLICATION_JSON
				).contentType(
					MediaType.APPLICATION_JSON
				).header(
					"Authorization",
					_liferayOAuthConfiguration.getAuthorization()
				).body(
					BodyInserters.fromValue(requestJSONObject.toString())
				).retrieve(
				).bodyToMono(
					String.class
				).block();

				if (response == null) {
					throw new RuntimeException("No response");
				}

				JSONObject responseJSONObject = new JSONObject(response);

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringUtil.combine(
							"Created ", getObjectDefinitionName(), " ",
							responseJSONObject.getLong("id")));
				}

				return responseJSONObject;
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Unable to create ", getObjectDefinitionName(),
							"s. Retry in ", _RETRY_DELAY_DURATION, "ms: ",
							exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}

		return null;
	}

	protected void delete(long objectEntryID) {
		if (objectEntryID <= 0) {
			return;
		}

		for (int i = 0; i <= _RETRY_COUNT; i++) {
			try {
				WebClient.create(
					StringUtil.combine(
						_liferayPortalURL,
						_getObjectEntryURLPath(objectEntryID))
				).delete(
				).accept(
					MediaType.APPLICATION_JSON
				).header(
					"Authorization",
					_liferayOAuthConfiguration.getAuthorization()
				).retrieve(
				).bodyToMono(
					Void.class
				).block();

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringUtil.combine(
							"Deleted ", getObjectDefinitionName(), " ",
							objectEntryID));
				}

				break;
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Unable to delete ", getObjectDefinitionName(), " ",
							objectEntryID, ". Retry in ", _RETRY_DELAY_DURATION,
							"ms: ", exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}
	}

	protected String getObjectDefinitionName() {
		throw new UnsupportedOperationException();
	}

	protected List<JSONObject> retrieve() {
		return retrieve(_getObjectDefinitionURLPath());
	}

	protected List<JSONObject> retrieve(String objectURLPath) {
		List<JSONObject> jsonObjects = new ArrayList<>();

		int currentPage = 1;
		int lastPage = -1;

		while (true) {
			int finalCurrentPage = currentPage;

			for (int i = 0; i <= _RETRY_COUNT; i++) {
				try {
					String response = WebClient.create(
						_liferayPortalURL + objectURLPath
					).get(
					).uri(
						uriBuilder -> uriBuilder.queryParam(
							"page", String.valueOf(finalCurrentPage)
						).build()
					).accept(
						MediaType.APPLICATION_JSON
					).header(
						"Authorization",
						_liferayOAuthConfiguration.getAuthorization()
					).retrieve(
					).bodyToMono(
						String.class
					).block();

					if (response == null) {
						throw new RuntimeException("No response");
					}

					JSONObject responseJSONObject = new JSONObject(response);

					lastPage = responseJSONObject.getInt("lastPage");

					JSONArray itemsJSONArray = responseJSONObject.getJSONArray(
						"items");

					if (itemsJSONArray.isEmpty()) {
						break;
					}

					for (int j = 0; j < itemsJSONArray.length(); j++) {
						jsonObjects.add(itemsJSONArray.getJSONObject(j));
					}

					break;
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to retrieve ",
								getObjectDefinitionName(), "s. Retry in ",
								_RETRY_DELAY_DURATION, "ms: ",
								exception.getMessage()));
					}

					ThreadUtil.sleep(_RETRY_DELAY_DURATION);
				}
			}

			if ((currentPage >= lastPage) || (lastPage == -1)) {
				break;
			}

			currentPage++;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringUtil.combine(
					"Retrieved ", jsonObjects.size(), " ",
					getObjectDefinitionName(), "s"));
		}

		return jsonObjects;
	}

	protected JSONObject update(JSONObject requestJSONObject) {
		long requestObjectEntryID = requestJSONObject.getLong("id");

		for (int i = 0; i <= _RETRY_COUNT; i++) {
			try {
				String response = WebClient.create(
					StringUtil.combine(
						_liferayPortalURL,
						_getObjectEntryURLPath(requestObjectEntryID))
				).put(
				).accept(
					MediaType.APPLICATION_JSON
				).contentType(
					MediaType.APPLICATION_JSON
				).header(
					"Authorization",
					_liferayOAuthConfiguration.getAuthorization()
				).body(
					BodyInserters.fromValue(requestJSONObject.toString())
				).retrieve(
				).bodyToMono(
					String.class
				).block();

				if (response == null) {
					throw new RuntimeException("No response");
				}

				JSONObject responseJSONObject = new JSONObject(response);

				long responseObjectEntryID = responseJSONObject.getLong("id");

				if (!Objects.equals(
						responseObjectEntryID, requestObjectEntryID)) {

					throw new RuntimeException(
						StringUtil.combine(
							"Updated wrong ", getObjectDefinitionName(), " ",
							responseObjectEntryID));
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringUtil.combine(
							"Updated ", getObjectDefinitionName(), " ",
							requestObjectEntryID));
				}

				return responseJSONObject;
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Unable to update ", getObjectDefinitionName(), " ",
							requestObjectEntryID, ". Retry in ",
							_RETRY_DELAY_DURATION, "ms: ",
							exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}

		return null;
	}

	private String _getObjectDefinitionURLPath() {
		String objectDefinitionName = getObjectDefinitionName();

		objectDefinitionName = objectDefinitionName.replaceAll("\\s+", "");
		objectDefinitionName = StringUtil.toLowerCase(objectDefinitionName);

		return StringUtil.combine("/o/c/", objectDefinitionName, "s");
	}

	private String _getObjectEntryURLPath(long objectEntryID) {
		return StringUtil.combine(
			_getObjectDefinitionURLPath(), "/", objectEntryID);
	}

	private static final long _RETRY_COUNT = 3;

	private static final long _RETRY_DELAY_DURATION = 1000;

	private static final Log _log = LogFactory.getLog(BaseDALO.class);

	@Autowired
	private LiferayOAuthConfiguration _liferayOAuthConfiguration;

	@Value("${liferay.portal.url}")
	private String _liferayPortalURL;

}