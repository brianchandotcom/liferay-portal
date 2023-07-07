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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
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
		implements EntityRelationshipDALO<T, U> {

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
					_liferayOAuth2AccessTokenConfiguration.getAuthorization()
				).retrieve(
				).bodyToMono(
					String.class
				).block();

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

				return;
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
	}

	private void _delete(
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
					_liferayOAuth2AccessTokenConfiguration.getAuthorization()
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

	private String _getChildURLPath() {
		EntityFactory<U> childEntityFactory = getChildEntityFactory();

		return StringUtil.combine(
			"/o/c/",
			StringUtil.toLowerCase(childEntityFactory.getEntityPluralLabel()));
	}

	private String _getParentURLPath() {
		EntityFactory<T> parentEntityFactory = getParentEntityFactory();

		return StringUtil.combine(
			"/o/c/",
			StringUtil.toLowerCase(parentEntityFactory.getEntityPluralLabel()));
	}

	private static final long _RETRY_COUNT = 3;

	private static final long _RETRY_DELAY_DURATION = 1000;

	private static final Log _log = LogFactory.getLog(
		BaseEntityRelationshipDALO.class);

	@Autowired
	private LiferayOAuth2AccessTokenConfiguration
		_liferayOAuth2AccessTokenConfiguration;

	@Value("${liferay.portal.url}")
	private String _liferayPortalURL;

}