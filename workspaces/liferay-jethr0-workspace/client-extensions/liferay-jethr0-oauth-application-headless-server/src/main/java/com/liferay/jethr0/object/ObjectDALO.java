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

package com.liferay.jethr0.object;

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
public class ObjectDALO {

	protected JSONObject create(JSONObject requestJSONObject) {
		for (int i = 0; i <= _RETRY_COUNT; i++) {
			try {
				String response = WebClient.create(
					_liferayPortalURL + _getObjectURLPath()
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
							"Created ", getObjectName(), " ",
							responseJSONObject.getLong("id")));
				}

				return responseJSONObject;
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Unable to create ", getObjectName(),
							"s. Retry in ", _RETRY_DELAY_DURATION, "ms: ",
							exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}

		return null;
	}

	protected void delete(long objectID) {
		if (objectID <= 0) {
			return;
		}

		for (int i = 0; i <= _RETRY_COUNT; i++) {
			try {
				WebClient.create(
					StringUtil.combine(
						_liferayPortalURL, _getObjectURLPath(), "/", objectID)
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
							"Deleted ", getObjectName(), " ", objectID));
				}

				break;
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Unable to delete ", getObjectName(), " ", objectID,
							". Retry in ", _RETRY_DELAY_DURATION, "ms: ",
							exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}
	}

	protected String getObjectName() {
		throw new UnsupportedOperationException();
	}

	protected List<JSONObject> retrieve() {
		List<JSONObject> jsonObjects = new ArrayList<>();

		int currentPage = 1;
		int lastPage = -1;

		while (true) {
			int finalCurrentPage = currentPage;

			for (int i = 0; i <= _RETRY_COUNT; i++) {
				try {
					String response = WebClient.create(
						_liferayPortalURL + _getObjectURLPath()
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
								"Unable to retrieve ", getObjectName(),
								"s. Retry in ", _RETRY_DELAY_DURATION, "ms: ",
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
					"Retrieved ", jsonObjects.size(), " ", getObjectName(),
					"s"));
		}

		return jsonObjects;
	}

	protected JSONObject update(JSONObject requestJSONObject) {
		long requestObjectID = requestJSONObject.getLong("id");

		for (int i = 0; i <= _RETRY_COUNT; i++) {
			try {
				String response = WebClient.create(
					StringUtil.combine(
						_liferayPortalURL, _getObjectURLPath(), "/",
						requestObjectID)
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

				long responseObjectID = responseJSONObject.getLong("id");

				if (!Objects.equals(responseObjectID, requestObjectID)) {
					throw new RuntimeException(
						StringUtil.combine(
							"Updated wrong ", getObjectName(), " ",
							responseObjectID));
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringUtil.combine(
							"Updated ", getObjectName(), " ", requestObjectID));
				}

				return responseJSONObject;
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Unable to update ", getObjectName(), " ",
							requestObjectID, ". Retry in ",
							_RETRY_DELAY_DURATION, "ms: ",
							exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}

		return null;
	}

	private String _getObjectURLPath() {
		String objectNamePath = getObjectName();

		objectNamePath = objectNamePath.replaceAll("\\s+", "");
		objectNamePath = StringUtil.toLowerCase(objectNamePath);

		return StringUtil.combine("/o/c/", objectNamePath, "s");
	}

	private static final long _RETRY_COUNT = 3;

	private static final long _RETRY_DELAY_DURATION = 1000;

	private static final Log _log = LogFactory.getLog(ObjectDALO.class);

	@Autowired
	private LiferayOAuthConfiguration _liferayOAuthConfiguration;

	@Value("${liferay.portal.url}")
	private String _liferayPortalURL;

}