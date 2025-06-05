/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.entity.dalo;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.util.StringUtil;
import com.liferay.petra.function.RetryableUnsafeSupplier;
import com.liferay.petra.function.UnsafeSupplier;

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

import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

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
		return getAll(null, null, null);
	}

	@Override
	public Set<T> getAllAfterCreatedDate(Date createdDate) {
		return getAll(
			"dateCreated gt " + StringUtil.toString(createdDate), null, null);
	}

	@Override
	public Set<T> getAllAfterModifiedDate(Date modifiedDate) {
		return getAll(
			"dateModified gt " + StringUtil.toString(modifiedDate), null, null);
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

	protected Set<T> getAll(String filterString, String search, String sort) {
		Set<T> entities = new HashSet<>();

		for (JSONObject jsonObject : _get(filterString, search, sort)) {
			T entity = newEntity(jsonObject);

			entities.add(entity);
		}

		return entities;
	}

	protected T newEntity(JSONObject jsonObject) {
		EntityFactory<T> entityFactory = getEntityFactory();

		return entityFactory.newEntity(jsonObject);
	}

	private JSONObject _create(JSONObject requestJSONObject) {
		UnsafeSupplier<JSONObject, RuntimeException> unsafeSupplier =
			new RetryableUnsafeSupplier<>(
				(exception, maxRetries, retryCount) -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to create ", _getEntityPluralLabel(),
								". Retry attempt ", retryCount, " of ",
								maxRetries, " ", requestJSONObject));
					}
				},
				() -> {
					String responseJSON;

					try {
						responseJSON = post(
							getAuthorization(), requestJSONObject.toString(),
							UriComponentsBuilder.fromPath(
								_getEntityURLPath()
							).build(
							).toUri());
					}
					catch (Exception exception) {
						refresh();

						throw new RuntimeException(exception);
					}

					if (responseJSON == null) {
						throw new RuntimeException("No response JSON");
					}

					JSONObject jsonObject = new JSONObject();

					for (String key : requestJSONObject.keySet()) {
						jsonObject.put(key, requestJSONObject.get(key));
					}

					JSONObject responseJSONObject = new JSONObject(
						responseJSON);

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
				});

		return unsafeSupplier.get();
	}

	private void _delete(long objectEntryId) {
		if (objectEntryId <= 0) {
			return;
		}

		UnsafeSupplier<Void, RuntimeException> unsafeSupplier =
			new RetryableUnsafeSupplier<>(
				(exception, maxRetries, retryCount) -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to delete ", _getEntityLabel(), " ",
								objectEntryId, ". Retry attempt ",
								String.valueOf(retryCount), " of ",
								maxRetries));
					}
				},
				() -> {
					try {
						delete(
							getAuthorization(), "",
							UriComponentsBuilder.fromPath(
								_getEntityURLPath(objectEntryId)
							).build(
							).toUri());
					}
					catch (Exception exception) {
						refresh();

						throw new RuntimeException(exception);
					}

					if (_log.isDebugEnabled()) {
						_log.debug(
							StringUtil.combine(
								"Deleted ", _getEntityLabel(), " ",
								objectEntryId));
					}

					return null;
				});

		unsafeSupplier.get();
	}

	private JSONObject _get(long id) {
		UnsafeSupplier<JSONObject, RuntimeException> unsafeSupplier =
			new RetryableUnsafeSupplier<>(
				(exception, maxRetries, retryCount) -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to retrieve ", _getEntityPluralLabel(),
								". Retry attempt ", retryCount, " of ",
								maxRetries));
					}
				},
				() -> {
					String responseJSON = null;

					try {
						responseJSON = get(
							getAuthorization(),
							UriComponentsBuilder.fromPath(
								_getEntityURLPath() + "/" + id
							).build(
							).toUri());
					}
					catch (Exception exception) {
						refresh();

						throw new RuntimeException(exception);
					}

					if (responseJSON == null) {
						throw new RuntimeException("No response JSON");
					}

					return new JSONObject(responseJSON);
				});

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringUtil.combine(
					"Retrieved ", _getEntityLabel(), " with ID " + id));
		}

		return unsafeSupplier.get();
	}

	private Set<JSONObject> _get(
		String filterString, String search, String sort) {

		Set<JSONObject> jsonObjects = new HashSet<>();

		int currentPage = 1;
		int lastPage = -1;

		while (true) {
			int finalCurrentPage = currentPage;

			UnsafeSupplier<Pair<Integer, Set<JSONObject>>, RuntimeException>
				unsafeSupplier = new RetryableUnsafeSupplier<>(
					(exception, maxRetries, retryCount) -> {
						if (_log.isWarnEnabled()) {
							_log.warn(
								StringUtil.combine(
									"Unable to retrieve ",
									_getEntityPluralLabel(), ". Retry attempt ",
									retryCount, " of ", maxRetries));
						}
					},
					() -> {
						String responseJSON;

						try {
							UriComponentsBuilder uriComponentsBuilder =
								UriComponentsBuilder.fromPath(
									_getEntityURLPath()
								).queryParam(
									"page", String.valueOf(finalCurrentPage)
								);

							if (filterString != null) {
								uriComponentsBuilder.queryParam(
									"filter", filterString);
							}

							if (search != null) {
								uriComponentsBuilder.queryParam(
									"search", search);
							}

							if (sort != null) {
								uriComponentsBuilder.queryParam("sort", sort);
							}

							responseJSON = get(
								getAuthorization(),
								uriComponentsBuilder.build(
								).toUri());
						}
						catch (Exception exception) {
							refresh();

							throw new RuntimeException(exception);
						}

						if (responseJSON == null) {
							throw new RuntimeException("No response JSON");
						}

						Set<JSONObject> localJsonObjects = new HashSet<>();

						JSONObject responseJSONObject = new JSONObject(
							responseJSON);

						Integer localLastPage = responseJSONObject.getInt(
							"lastPage");

						JSONArray itemsJSONArray =
							responseJSONObject.getJSONArray("items");

						if (itemsJSONArray != null) {
							for (int i = 0; i < itemsJSONArray.length(); i++) {
								localJsonObjects.add(
									itemsJSONArray.getJSONObject(i));
							}
						}

						return new ImmutablePair<>(
							localLastPage, localJsonObjects);
					});

			Pair<Integer, Set<JSONObject>> pair = unsafeSupplier.get();

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
		return StringUtil.toDate(jsonObject.optString(dateKey));
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

		UnsafeSupplier<JSONObject, RuntimeException> unsafeSupplier =
			new RetryableUnsafeSupplier<>(
				(exception, maxRetries, retryCount) -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to update ", _getEntityLabel(), " ",
								requestObjectEntryId, ". Retry attempt ",
								retryCount, " of ", maxRetries));
					}
				},
				() -> {
					String responseJSON;

					try {
						responseJSON = put(
							getAuthorization(), requestJSONObject.toString(),
							UriComponentsBuilder.fromPath(
								_getEntityURLPath(requestObjectEntryId)
							).build(
							).toUri());
					}
					catch (Exception exception) {
						refresh();

						throw new RuntimeException(exception);
					}

					if (responseJSON == null) {
						throw new RuntimeException("No response JSON");
					}

					JSONObject responseJSONObject = new JSONObject(
						responseJSON);

					long responseObjectEntryId = responseJSONObject.getLong(
						"id");

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
				});

		return unsafeSupplier.get();
	}

	private static final Log _log = LogFactory.getLog(BaseDALO.class);

}