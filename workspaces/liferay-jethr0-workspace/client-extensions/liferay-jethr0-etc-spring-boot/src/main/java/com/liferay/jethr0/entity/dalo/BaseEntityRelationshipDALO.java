/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.entity.dalo;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.util.BaseRetryable;
import com.liferay.jethr0.util.Retryable;
import com.liferay.jethr0.util.StringUtil;

import java.util.Collections;
import java.util.HashSet;
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
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Michael Hashimoto
 */
@Configuration
public abstract class BaseEntityRelationshipDALO
	<T extends Entity, U extends Entity>
		extends BaseDALO implements EntityRelationshipDALO<T, U> {

	@Override
	public void create(T parentEntity, U childEntity) {
		_create(_getParentURLPath(), parentEntity.getId(), childEntity.getId());
	}

	@Override
	public void delete(T parentEntity, U childEntity) {
		_delete(_getParentURLPath(), parentEntity.getId(), childEntity.getId());
	}

	@Override
	public Set<U> getChildEntities(T parentEntity) {
		Set<U> children = new HashSet<>();

		EntityFactory<U> childEntityFactory = getChildEntityFactory();

		for (JSONObject jsonObject :
				_get(_getParentURLPath(), parentEntity.getId())) {

			children.add(childEntityFactory.newEntity(jsonObject));
		}

		return children;
	}

	@Override
	public Set<Long> getChildEntityIds(T parentEntity) {
		Set<Long> childEntityIds = new HashSet<>();

		for (U childEntity : getChildEntities(parentEntity)) {
			childEntityIds.add(childEntity.getId());
		}

		return childEntityIds;
	}

	@Override
	public Set<T> getParentEntities(U childEntity) {
		Set<T> parentEntities = new HashSet<>();

		EntityFactory<T> parentEntityFactory = getParentEntityFactory();

		for (JSONObject jsonObject :
				_get(_getChildURLPath(), childEntity.getId())) {

			parentEntities.add(parentEntityFactory.newEntity(jsonObject));
		}

		return parentEntities;
	}

	@Override
	public Set<Long> getParentEntityIds(U childEntity) {
		Set<Long> parentEntityIds = new HashSet<>();

		for (T parentEntity : getParentEntities(childEntity)) {
			parentEntityIds.add(parentEntity.getId());
		}

		return parentEntityIds;
	}

	@Override
	public void updateChildEntities(T parentEntity) {
		EntityFactory<U> childEntityFactory = getChildEntityFactory();

		Class<U> childEntityClass = childEntityFactory.getEntityClass();

		Set<U> childEntities = getChildEntities(parentEntity);

		for (Entity childEntity : parentEntity.getRelatedEntities()) {
			if (!childEntityClass.isInstance(childEntity)) {
				continue;
			}

			if (childEntities.contains(childEntity)) {
				childEntities.removeAll(Collections.singletonList(childEntity));

				continue;
			}

			create(parentEntity, childEntityClass.cast(childEntity));
		}

		for (U remoteChildEntity : childEntities) {
			delete(parentEntity, remoteChildEntity);
		}
	}

	@Override
	public void updateParentEntities(U childEntity) {
		EntityFactory<T> parentEntityFactory = getParentEntityFactory();

		Class<T> parentEntityClass = parentEntityFactory.getEntityClass();

		Set<T> parentEntities = getParentEntities(childEntity);

		for (Entity parentEntity : childEntity.getRelatedEntities()) {
			if (!parentEntityClass.isInstance(parentEntity)) {
				continue;
			}

			if (parentEntities.contains(parentEntity)) {
				parentEntities.removeAll(
					Collections.singletonList(parentEntity));

				continue;
			}

			create(parentEntityClass.cast(parentEntity), childEntity);
		}

		for (T remoteParentEntity : parentEntities) {
			delete(remoteParentEntity, childEntity);
		}
	}

	protected abstract String getObjectRelationshipName();

	private void _create(
		String objectDefinitionURLPath, long objectEntryId,
		long relatedObjectEntryId) {

		String objectRelationshipURL = StringUtil.combine(
			_liferayPortalURL, objectDefinitionURLPath, "/", objectEntryId, "/",
			getObjectRelationshipName(), "/", relatedObjectEntryId);

		Retryable<Void> retryable = new BaseRetryable<Void>() {

			@Override
			public Void execute() {
				String response;

				try {
					response = WebClient.create(
						objectRelationshipURL
					).put(
					).accept(
						MediaType.APPLICATION_JSON
					).contentType(
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

				new JSONObject(response);

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringUtil.combine(
							"Created relationship with ",
							objectRelationshipURL));
				}

				return null;
			}

			@Override
			protected String getRetryMessage(int retryCount) {
				return StringUtil.combine(
					"Unable to create relationship with ",
					objectRelationshipURL, ". Retry attempt ", retryCount,
					" of ", maxRetries);
			}

		};

		retryable.executeWithRetries();
	}

	private void _delete(
		String objectDefinitionURLPath, long objectEntryId,
		long relatedObjectEntryId) {

		String objectRelationshipURL = StringUtil.combine(
			_liferayPortalURL, objectDefinitionURLPath, "/", objectEntryId, "/",
			getObjectRelationshipName(), "/", relatedObjectEntryId);

		Retryable<Void> retryable = new BaseRetryable<Void>() {

			@Override
			public Void execute() {
				try {
					WebClient.create(
						objectRelationshipURL
					).delete(
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

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringUtil.combine(
							"Deleted relationship with ",
							objectRelationshipURL));
				}

				return null;
			}

			@Override
			protected String getRetryMessage(int retryCount) {
				return StringUtil.combine(
					"Unable to delete relationship with ",
					objectRelationshipURL, ". Retry attempt ", retryCount,
					" of ", maxRetries);
			}

		};

		retryable.executeWithRetries();
	}

	private Set<JSONObject> _get(
		String objectDefinitionURLPath, long objectEntryId) {

		String objectRelationshipURL = StringUtil.combine(
			_liferayPortalURL, objectDefinitionURLPath, "/", objectEntryId, "/",
			getObjectRelationshipName());

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
								objectRelationshipURL
							).get(
							).uri(
								uriBuilder -> uriBuilder.queryParam(
									"page", String.valueOf(finalCurrentPage)
								).build()
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

						JSONObject responseJSONObject = new JSONObject(
							response);

						Integer lastPage = responseJSONObject.getInt(
							"lastPage");

						JSONArray itemsJSONArray =
							responseJSONObject.getJSONArray("items");

						Set<JSONObject> jsonObjects = new HashSet<>();

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
							"Unable to retrieve object relationships. ",
							"Retry attempt ", retryCount, " of ", maxRetries);
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
					"Retrieved ", jsonObjects.size(), " objects"));
		}

		return jsonObjects;
	}

	private String _getChildURLPath() {
		EntityFactory<U> childEntityFactory = getChildEntityFactory();

		String entityPluralLabel = childEntityFactory.getEntityPluralLabel();

		entityPluralLabel = entityPluralLabel.replaceAll("\\s+", "");
		entityPluralLabel = StringUtil.toLowerCase(entityPluralLabel);

		return StringUtil.combine("/o/c/", entityPluralLabel);
	}

	private String _getParentURLPath() {
		EntityFactory<T> parentEntityFactory = getParentEntityFactory();

		String entityPluralLabel = parentEntityFactory.getEntityPluralLabel();

		entityPluralLabel = entityPluralLabel.replaceAll("\\s+", "");
		entityPluralLabel = StringUtil.toLowerCase(entityPluralLabel);

		return StringUtil.combine("/o/c/", entityPluralLabel);
	}

	private static final Log _log = LogFactory.getLog(
		BaseEntityRelationshipDALO.class);

	@Value("${liferay.portal.url}")
	private String _liferayPortalURL;

}