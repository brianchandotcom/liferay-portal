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

package com.liferay.jethr0.entity.dalo;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenConfiguration;
import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.util.StringUtil;
import com.liferay.jethr0.util.ThreadUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
public abstract class BaseEntityDALO<T extends Entity>
	implements EntityDALO<T> {

	@Override
	public T create(JSONObject jsonObject) {
		JSONObject responseJSONObject = _create(jsonObject);

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		T entity = newEntity(responseJSONObject);

		entity.setCreatedDate(
			StringUtil.toDate(responseJSONObject.getString("dateCreated")));
		entity.setId(responseJSONObject.getLong("id"));

		return entity;
	}

	@Override
	public T create(T entity) {
		JSONObject responseJSONObject = _create(entity.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		entity.setCreatedDate(
			StringUtil.toDate(responseJSONObject.getString("dateCreated")));
		entity.setId(responseJSONObject.getLong("id"));

		return entity;
	}

	@Override
	public void delete(T entity) {
		if (entity == null) {
			return;
		}

		_delete(entity.getId());
	}

	@Override
	public T get(long id) {
		for (T entity : getAll()) {
			if (!Objects.equals(entity.getId(), id)) {
				continue;
			}

			return entity;
		}

		return null;
	}

	@Override
	public Set<T> getAll() {
		return getAll(null, null);
	}

	@Override
	public T update(T entity) {
		JSONObject responseJSONObject = _update(entity.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return entity;
	}

	protected Set<T> getAll(String filter, String search) {
		Set<T> entities = new HashSet<>();

		for (JSONObject jsonObject : _get(filter, search)) {
			T entity = newEntity(jsonObject);

			entities.add(entity);
		}

		return entities;
	}

	protected abstract EntityFactory<T> getEntityFactory();

	protected T newEntity(JSONObject jsonObject) {
		EntityFactory<T> entityFactory = getEntityFactory();

		return entityFactory.newEntity(jsonObject);
	}

	private JSONObject _create(JSONObject requestJSONObject) {
		for (int i = 0; i <= _RETRY_COUNT; i++) {
			try {
				String response = WebClient.create(
					StringUtil.combine(_liferayPortalURL, _getEntityURLPath())
				).post(
				).accept(
					MediaType.APPLICATION_JSON
				).contentType(
					MediaType.APPLICATION_JSON
				).header(
					"Authorization",
					_liferayOAuth2AccessTokenConfiguration.getAuthorization()
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
							"Created ", _getEntityLabel(), " ",
							responseJSONObject.getLong("id")));
				}

				return responseJSONObject;
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Unable to create ", _getEntityPluralLabel(),
							". Retry in ", _RETRY_DELAY_DURATION, "ms: ",
							exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}

		return null;
	}

	private void _delete(long objectEntryId) {
		if (objectEntryId <= 0) {
			return;
		}

		for (int i = 0; i <= _RETRY_COUNT; i++) {
			try {
				WebClient.create(
					StringUtil.combine(
						_liferayPortalURL, _getEntityURLPath(objectEntryId))
				).delete(
				).accept(
					MediaType.APPLICATION_JSON
				).header(
					"Authorization",
					_liferayOAuth2AccessTokenConfiguration.getAuthorization()
				).retrieve(
				).bodyToMono(
					Void.class
				).block();

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringUtil.combine(
							"Deleted ", _getEntityLabel(), " ", objectEntryId));
				}

				break;
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Unable to delete ", _getEntityLabel(), " ",
							objectEntryId, ". Retry in ", _RETRY_DELAY_DURATION,
							"ms: ", exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}
	}

	private Set<JSONObject> _get(String filter, String search) {
		Set<JSONObject> jsonObjects = new HashSet<>();

		int currentPage = 1;
		int lastPage = -1;

		while (true) {
			int finalCurrentPage = currentPage;

			for (int i = 0; i <= _RETRY_COUNT; i++) {
				try {
					String response = WebClient.create(
						StringUtil.combine(
							_liferayPortalURL, _getEntityURLPath())
					).get(
					).uri(
						uriBuilder -> {
							uriBuilder = uriBuilder.queryParam(
								"page", String.valueOf(finalCurrentPage));

							if (filter != null) {
								uriBuilder.queryParam("filter", filter);
							}

							if (search != null) {
								uriBuilder.queryParam("search", search);
							}

							return uriBuilder.build();
						}
					).accept(
						MediaType.APPLICATION_JSON
					).header(
						"Authorization",
						_liferayOAuth2AccessTokenConfiguration.
							getAuthorization()
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
								"Unable to retrieve ", _getEntityPluralLabel(),
								". Retry in ", _RETRY_DELAY_DURATION, "ms: ",
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
					_getEntityPluralLabel()));
		}

		return jsonObjects;
	}

	private String _getEntityLabel() {
		EntityFactory<T> entityFactory = getEntityFactory();

		return entityFactory.getEntityLabel();
	}

	private String _getEntityPluralLabel() {
		EntityFactory<T> entityFactory = getEntityFactory();

		return entityFactory.getEntityPluralLabel();
	}

	private String _getEntityURLPath() {
		String entityPluralLabel = _getEntityPluralLabel();

		entityPluralLabel = entityPluralLabel.replaceAll("\\s+", "");
		entityPluralLabel = StringUtil.toLowerCase(entityPluralLabel);

		return StringUtil.combine("/o/c/", entityPluralLabel);
	}

	private String _getEntityURLPath(long objectEntryId) {
		return StringUtil.combine(_getEntityURLPath(), "/", objectEntryId);
	}

	private JSONObject _update(JSONObject requestJSONObject) {
		long requestObjectEntryId = requestJSONObject.getLong("id");

		for (int i = 0; i <= _RETRY_COUNT; i++) {
			try {
				String response = WebClient.create(
					StringUtil.combine(
						_liferayPortalURL,
						_getEntityURLPath(requestObjectEntryId))
				).put(
				).accept(
					MediaType.APPLICATION_JSON
				).contentType(
					MediaType.APPLICATION_JSON
				).header(
					"Authorization",
					_liferayOAuth2AccessTokenConfiguration.getAuthorization()
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

				long responseObjectEntryId = responseJSONObject.getLong("id");

				if (!Objects.equals(
						responseObjectEntryId, requestObjectEntryId)) {

					throw new RuntimeException(
						StringUtil.combine(
							"Updated wrong ", _getEntityLabel(), " ",
							responseObjectEntryId));
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringUtil.combine(
							"Updated ", _getEntityLabel(), " ",
							requestObjectEntryId));
				}

				return responseJSONObject;
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Unable to update ", _getEntityLabel(), " ",
							requestObjectEntryId, ". Retry in ",
							_RETRY_DELAY_DURATION, "ms: ",
							exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}

		return null;
	}

	private static final long _RETRY_COUNT = 3;

	private static final long _RETRY_DELAY_DURATION = 1000;

	private static final Log _log = LogFactory.getLog(BaseEntityDALO.class);

	@Autowired
	private LiferayOAuth2AccessTokenConfiguration
		_liferayOAuth2AccessTokenConfiguration;

	@Value("${liferay.portal.url}")
	private String _liferayPortalURL;

}