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

import com.liferay.jethr0.util.StringUtil;
import com.liferay.jethr0.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BaseRelationshipDALO {

	protected JSONObject create(
		String objectDefinitionURLPath, long objectEntryId,
		long relatedObjectEntryId) {

		String objectRelationshipURL = StringUtil.combine(
			_liferayPortalURL, objectDefinitionURLPath, "/", objectEntryId, "/",
			getObjectRelationshipName(), "/", relatedObjectEntryId);

		for (int i = 0; i <= _RETRY_COUNT; i++) {
			try {
				String response = WebClient.create(
					objectRelationshipURL
				).put(
				).accept(
					MediaType.APPLICATION_JSON
				).contentType(
					MediaType.APPLICATION_JSON
				).header(
					"Authorization",
					"Bearer " + _oAuth2AccessToken.getTokenValue()
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
							"Created relationship with ",
							objectRelationshipURL));
				}

				return responseJSONObject;
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Unable to create relationship with ",
							objectRelationshipURL, ". Retry in ",
							_RETRY_DELAY_DURATION, "ms: ",
							exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}

		return null;
	}

	protected void delete(
		String objectDefinitionURLPath, long objectEntryId,
		long relatedObjectEntryId) {

		String objectRelationshipURL = StringUtil.combine(
			_liferayPortalURL, objectDefinitionURLPath, "/", objectEntryId, "/",
			getObjectRelationshipName(), "/", relatedObjectEntryId);

		for (int i = 0; i <= _RETRY_COUNT; i++) {
			try {
				WebClient.create(
					objectRelationshipURL
				).delete(
				).accept(
					MediaType.APPLICATION_JSON
				).header(
					"Authorization",
					"Bearer " + _oAuth2AccessToken.getTokenValue()
				).retrieve(
				).bodyToMono(
					String.class
				).block();

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringUtil.combine(
							"Deleted relationship with ",
							objectRelationshipURL));
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Unable to delete relationship with ",
							objectRelationshipURL, ". Retry in ",
							_RETRY_DELAY_DURATION, "ms: ",
							exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}
	}

	protected String getObjectRelationshipName() {
		throw new UnsupportedOperationException();
	}

	protected List<JSONObject> retrieve(
		String objectDefinitionURLPath, long objectEntryId) {

		String objectRelationshipURL = StringUtil.combine(
			_liferayPortalURL, objectDefinitionURLPath, "/", objectEntryId, "/",
			getObjectRelationshipName());

		List<JSONObject> jsonObjects = new ArrayList<>();

		int currentPage = 1;
		int lastPage = -1;

		while (true) {
			int finalCurrentPage = currentPage;

			for (int i = 0; i <= _RETRY_COUNT; i++) {
				try {
					String response = WebClient.create(
						objectRelationshipURL
					).get(
					).uri(
						uriBuilder -> uriBuilder.queryParam(
							"page", String.valueOf(finalCurrentPage)
						).build()
					).accept(
						MediaType.APPLICATION_JSON
					).header(
						"Authorization",
						"Bearer " + _oAuth2AccessToken.getTokenValue()
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
								"Unable to retrieve objects. Retry in ",
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
					"Retrieved ", jsonObjects.size(), " objects"));
		}

		return jsonObjects;
	}

	private static final long _RETRY_COUNT = 3;

	private static final long _RETRY_DELAY_DURATION = 1000;

	private static final Log _log = LogFactory.getLog(
		BaseRelationshipDALO.class);

	@Value("${liferay.portal.url}")
	private String _liferayPortalURL;

	@Autowired
	private OAuth2AccessToken _oAuth2AccessToken;

}