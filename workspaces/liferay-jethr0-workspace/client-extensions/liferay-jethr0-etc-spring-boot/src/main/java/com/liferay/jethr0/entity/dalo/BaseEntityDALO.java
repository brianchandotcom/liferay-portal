/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.entity.dalo;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.util.BaseRetryable;
import com.liferay.jethr0.util.Retryable;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

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
	extends BaseDALO implements EntityDALO<T> {

	@Override
	public T create(JSONObject jsonObject) {
		long id = jsonObject.optLong("id");

		if (id != 0) {
			throw new RuntimeException("Entity already exists");
		}

		JSONObject responseJSONObject = _create(jsonObject);

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		T entity = newEntity(responseJSONObject);

		entity.setCreatedDate(
			_getDateFromJSON(responseJSONObject, "dateCreated"));
		entity.setId(responseJSONObject.getLong("id"));
		entity.setModifiedDate(
			_getDateFromJSON(responseJSONObject, "dateModified"));

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
		return newEntity(_get(id));
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

		entity.setCreatedDate(
			_getDateFromJSON(responseJSONObject, "dateCreated"));
		entity.setId(responseJSONObject.getLong("id"));
		entity.setModifiedDate(
			_getDateFromJSON(responseJSONObject, "dateModified"));

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
		Retryable<JSONObject> retryable = new BaseRetryable<JSONObject>() {

			@Override
			public JSONObject execute() {
				String response;

				try {
					response = WebClient.create(
						StringUtil.combine(
							_liferayPortalURL, _getEntityURLPath())
					).post(
					).accept(
						MediaType.APPLICATION_JSON
					).contentType(
						MediaType.APPLICATION_JSON
					).header(
						"Authorization", getAuthorization()
					).body(
						BodyInserters.fromValue(requestJSONObject.toString())
					).retrieve(
					).bodyToMono(
						String.class
					).block();
				}
				catch (Exception exception) {
					refresh();

					throw new RuntimeException(exception);
				}

				if (response == null) {
					throw new RuntimeException("No response");
				}

				JSONObject jsonObject = new JSONObject();

				for (String key : requestJSONObject.keySet()) {
					jsonObject.put(key, requestJSONObject.get(key));
				}

				JSONObject responseJSONObject = new JSONObject(response);

				for (String key : responseJSONObject.keySet()) {
					jsonObject.put(key, responseJSONObject.get(key));
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringUtil.combine(
							"Created ", _getEntityLabel(), " ",
							jsonObject.getLong("id")));
				}

				return jsonObject;
			}

			@Override
			protected String getRetryMessage(int retryCount) {
				return StringUtil.combine(
					"Unable to create ", _getEntityPluralLabel(),
					". Retry attempt ", retryCount, " of ", maxRetries);
			}

		};

		return retryable.executeWithRetries();
	}

	private void _delete(long objectEntryId) {
		if (objectEntryId <= 0) {
			return;
		}

		Retryable<Void> retryable = new BaseRetryable<Void>() {

			@Override
			public Void execute() {
				try {
					WebClient.create(
						StringUtil.combine(
							_liferayPortalURL, _getEntityURLPath(objectEntryId))
					).delete(
					).accept(
						MediaType.APPLICATION_JSON
					).header(
						"Authorization", getAuthorization()
					).retrieve(
					).bodyToMono(
						Void.class
					).block();
				}
				catch (Exception exception) {
					refresh();

					throw new RuntimeException(exception);
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringUtil.combine(
							"Deleted ", _getEntityLabel(), " ", objectEntryId));
				}

				return null;
			}

			@Override
			protected String getRetryMessage(int retryCount) {
				return StringUtil.combine(
					"Unable to delete ", _getEntityLabel(), " ", objectEntryId,
					". Retry attempt ", String.valueOf(retryCount), " of ",
					maxRetries);
			}

		};

		retryable.executeWithRetries();
	}

	private JSONObject _get(final long id) {
		Retryable<JSONObject> retryable = new BaseRetryable<JSONObject>() {

			@Override
			public JSONObject execute() {
				String response = null;

				try {
					response = WebClient.create(
						StringUtil.combine(
							_liferayPortalURL, _getEntityURLPath(), "/", id)
					).get(
					).accept(
						MediaType.APPLICATION_JSON
					).header(
						"Authorization", getAuthorization()
					).retrieve(
					).bodyToMono(
						String.class
					).block();
				}
				catch (Exception exception) {
					refresh();

					throw new RuntimeException(exception);
				}

				if (response == null) {
					throw new RuntimeException("No response");
				}

				return new JSONObject(response);
			}

			@Override
			protected String getRetryMessage(int retryCount) {
				return StringUtil.combine(
					"Unable to retrieve ", _getEntityPluralLabel(),
					". Retry attempt ", retryCount, " of ", maxRetries);
			}

		};

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringUtil.combine(
					"Retrieved ", _getEntityLabel(), " with ID " + id));
		}

		return retryable.executeWithRetries();
	}

	private Set<JSONObject> _get(String filter, String search) {
		Set<JSONObject> jsonObjects = new HashSet<>();

		int currentPage = 1;
		int lastPage = -1;

		while (true) {
			int finalCurrentPage = currentPage;

			Retryable<Pair<Integer, Set<JSONObject>>> retryable =
				new BaseRetryable<Pair<Integer, Set<JSONObject>>>() {

					@Override
					public Pair<Integer, Set<JSONObject>> execute() {
						String response;

						try {
							response = WebClient.create(
								StringUtil.combine(
									_liferayPortalURL, _getEntityURLPath())
							).get(
							).uri(
								uriBuilder -> {
									uriBuilder = uriBuilder.queryParam(
										"page",
										String.valueOf(finalCurrentPage));

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
								"Authorization", getAuthorization()
							).retrieve(
							).bodyToMono(
								String.class
							).block();
						}
						catch (Exception exception) {
							refresh();

							throw new RuntimeException(exception);
						}

						if (response == null) {
							throw new RuntimeException("No response");
						}

						Set<JSONObject> jsonObjects = new HashSet<>();

						JSONObject responseJSONObject = new JSONObject(
							response);

						Integer lastPage = responseJSONObject.getInt(
							"lastPage");

						JSONArray itemsJSONArray =
							responseJSONObject.getJSONArray("items");

						if (itemsJSONArray != null) {
							for (int i = 0; i < itemsJSONArray.length(); i++) {
								jsonObjects.add(
									itemsJSONArray.getJSONObject(i));
							}
						}

						return new ImmutablePair<>(lastPage, jsonObjects);
					}

					@Override
					protected String getRetryMessage(int retryCount) {
						return StringUtil.combine(
							"Unable to retrieve ", _getEntityPluralLabel(),
							". Retry attempt ", retryCount, " of ", maxRetries);
					}

				};

			Pair<Integer, Set<JSONObject>> pair =
				retryable.executeWithRetries();

			if (pair == null) {
				break;
			}

			lastPage = pair.getKey();

			jsonObjects.addAll(pair.getValue());

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

	private Date _getDateFromJSON(JSONObject jsonObject, String dateKey) {
		Retryable<Date> retryable = new BaseRetryable<Date>() {

			@Override
			public Date execute() {
				return StringUtil.toDate(jsonObject.optString(dateKey));
			}

		};

		return retryable.executeWithRetries();
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

		Retryable<JSONObject> retryable = new BaseRetryable<JSONObject>() {

			@Override
			public JSONObject execute() {
				String response;

				try {
					response = WebClient.create(
						StringUtil.combine(
							_liferayPortalURL,
							_getEntityURLPath(requestObjectEntryId))
					).put(
					).accept(
						MediaType.APPLICATION_JSON
					).contentType(
						MediaType.APPLICATION_JSON
					).header(
						"Authorization", getAuthorization()
					).body(
						BodyInserters.fromValue(requestJSONObject.toString())
					).retrieve(
					).bodyToMono(
						String.class
					).block();
				}
				catch (Exception exception) {
					refresh();

					throw new RuntimeException(exception);
				}

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

			@Override
			protected String getRetryMessage(int retryCount) {
				return StringUtil.combine(
					"Unable to update ", _getEntityLabel(), " ",
					requestObjectEntryId, ". Retry attempt ", retryCount,
					" of ", maxRetries);
			}

		};

		return retryable.executeWithRetries();
	}

	private static final Log _log = LogFactory.getLog(BaseDALO.class);

	@Value("${liferay.portal.url}")
	private String _liferayPortalURL;

}