/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.entity.dalo;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.util.StringUtil;
import com.liferay.petra.function.RetryableUnsafeSupplier;
import com.liferay.petra.function.UnsafeSupplier;

import java.net.URI;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;

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
			objectDefinitionURLPath, "/", objectEntryId, "/",
			getObjectRelationshipName(), "/", relatedObjectEntryId);

		UnsafeSupplier<Void, RuntimeException> unsafeSupplier =
			new RetryableUnsafeSupplier<>(
				(exception, maxRetries, retryCount) -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to create relationship with ",
								objectRelationshipURL, ". Retry attempt ",
								retryCount, " of ", maxRetries));
					}
				},
				() -> {
					String responseJSON;

					try {
						responseJSON = put(
							getAuthorization(), "",
							URI.create(objectDefinitionURLPath));
					}
					catch (Exception exception) {
						refresh();

						throw new RuntimeException(exception);
					}

					if (responseJSON == null) {
						throw new RuntimeException("No response JSON");
					}

					new JSONObject(responseJSON);

					if (_log.isDebugEnabled()) {
						_log.debug(
							StringUtil.combine(
								"Created relationship with ",
								objectRelationshipURL));
					}

					return null;
				});

		unsafeSupplier.get();
	}

	private void _delete(
		String objectDefinitionURLPath, long objectEntryId,
		long relatedObjectEntryId) {

		String objectRelationshipURL = StringUtil.combine(
			objectDefinitionURLPath, "/", objectEntryId, "/",
			getObjectRelationshipName(), "/", relatedObjectEntryId);

		UnsafeSupplier<Void, RuntimeException> unsafeSupplier =
			new RetryableUnsafeSupplier<>(
				(exception, maxRetries, retryCount) -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to delete relationship with ",
								objectRelationshipURL, ". Retry attempt ",
								retryCount, " of ", maxRetries));
					}
				},
				() -> {
					try {
						delete(
							getAuthorization(), "",
							URI.create(objectDefinitionURLPath));
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
				});

		unsafeSupplier.get();
	}

	private Set<JSONObject> _get(
		String objectDefinitionURLPath, long objectEntryId) {

		Set<JSONObject> jsonObjects = new HashSet<>();

		int currentPage = 1;

		while (true) {
			int finalCurrentPage = currentPage;

			UnsafeSupplier<Pair<Integer, Set<JSONObject>>, RuntimeException>
				unsafeSupplier = new RetryableUnsafeSupplier<>(
					(exception, maxRetries, retryCount) -> {
						if (_log.isWarnEnabled()) {
							_log.warn(
								StringUtil.combine(
									"Unable to retrieve object relationships. ",
									"Retry attempt ", retryCount, " of ",
									maxRetries));
						}
					},
					() -> {
						String responseJSON = null;

						try {
							responseJSON = get(
								getAuthorization(),
								_defaultUriBuilderFactory.builder(
								).path(
									StringUtil.combine(
										objectDefinitionURLPath, "/",
										objectEntryId, "/",
										getObjectRelationshipName())
								).queryParam(
									"page", String.valueOf(finalCurrentPage)
								).build());
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

						JSONArray itemsJSONArray =
							responseJSONObject.getJSONArray("items");

						return new ImmutablePair<>(
							responseJSONObject.getInt("lastPage"),
							new HashSet(itemsJSONArray.toList()));
					});

			Pair<Integer, Set<JSONObject>> pair = unsafeSupplier.get();

			if (pair == null) {
				break;
			}

			int lastPage = pair.getKey();

			jsonObjects.addAll(pair.getValue());

			if ((currentPage >= lastPage) || (lastPage == -1)) {
				break;
			}

			currentPage++;
		}

		if (_log.isDebugEnabled()) {
			EntityFactory<U> childEntityFactory = getChildEntityFactory();

			int entityCount = jsonObjects.size();

			String entityLabel = childEntityFactory.getEntityPluralLabel();

			if (entityCount == 0) {
				entityLabel = childEntityFactory.getEntityLabel();
			}

			_log.debug(
				StringUtil.combine(
					"Retrieved ", entityCount, " ", entityLabel));
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

	private final DefaultUriBuilderFactory _defaultUriBuilderFactory =
		new DefaultUriBuilderFactory();

}