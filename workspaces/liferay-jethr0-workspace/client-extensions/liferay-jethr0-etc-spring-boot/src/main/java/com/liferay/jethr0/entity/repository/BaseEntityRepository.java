/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.entity.repository;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.entity.dalo.EntityDALO;
import com.liferay.jethr0.entity.dalo.EntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseEntityRepository<T extends Entity>
	implements EntityRepository<T> {

	@Override
	public T add(JSONObject jsonObject) {
		EntityDALO<T> entityDALO = getEntityDALO();

		EntityFactory<T> entityFactory = entityDALO.getEntityFactory();

		T entity = entityFactory.newEntity(jsonObject);

		add(entity);

		return entity;
	}

	@Override
	public boolean contains(long id) {
		if (_entitiesMap.containsKey(id)) {
			return true;
		}

		return false;
	}

	@Override
	public T create(JSONObject jsonObject) {
		EntityDALO<T> entityDALO = getEntityDALO();

		T entity = entityDALO.create(jsonObject);

		add(entity);

		return entity;
	}

	@Override
	public Set<T> getAll() {
		return new HashSet<>(_entitiesMap.values());
	}

	@Override
	public T getById(long id) {
		if (_entitiesMap.containsKey(id)) {
			return _entitiesMap.get(id);
		}

		EntityDALO<T> entityDALO = getEntityDALO();

		T entity = entityDALO.get(id);

		add(entity);

		return entity;
	}

	@Override
	public void initialize() {
		EntityDALO<T> entityDALO = getEntityDALO();

		addAll(entityDALO.getAll());
	}

	@Override
	public void initializeRelationships() {
	}

	@Override
	public void remove(Set<T> entities) {
		if (entities == null) {
			return;
		}

		entities.removeAll(Collections.singleton(null));

		EntityDALO<T> entityDALO = getEntityDALO();

		for (T entity : entities) {
			_entitiesMap.remove(entity.getId());

			entityDALO.delete(entity);
		}
	}

	@Override
	public void remove(T entity) {
		remove(Collections.singleton(entity));
	}

	@Override
	public T update(T entity) {
		if (entity.getId() == 0) {
			throw new RuntimeException("Unable to update entity");
		}

		EntityDALO<T> entityDALO = getEntityDALO();

		entity = entityDALO.update(entity);

		entity = updateRelationshipsToDALO(entity);

		_entitiesMap.put(entity.getId(), entity);

		return entity;
	}

	protected T add(T entity) {
		Set<T> entities = new HashSet<>();

		entities.add(entity);

		addAll(entities);

		return entity;
	}

	protected Set<T> addAll(Set<T> entities) {
		if (entities == null) {
			return entities;
		}

		entities.removeAll(Collections.singleton(null));

		if (entities.isEmpty()) {
			return entities;
		}

		for (T entity : entities) {
			if (entity.getId() == 0) {
				throw new RuntimeException("Unable to add entity");
			}

			if (_entitiesMap.containsKey(entity.getId())) {
				continue;
			}

			_entitiesMap.put(entity.getId(), entity);

			updateRelationshipsFromDALO(entity);
		}

		return entities;
	}

	protected Map<Long, T> getEntitiesMap() {
		return _entitiesMap;
	}

	protected abstract EntityDALO<T> getEntityDALO();

	protected <U extends Entity> T updateParentToChildRelationshipsFromDALO(
		T parentEntity,
		EntityRelationshipDALO<T, U> parentToChildEntityRelationshipDALO,
		EntityRepository<U> childEntityRepository,
		BiConsumer<T, U> associateEntities,
		Function<T, Set<U>> getChildEntities,
		BiConsumer<T, U> removeChildEntities) {

		Set<U> daloChildEntities =
			parentToChildEntityRelationshipDALO.getChildEntities(parentEntity);

		Set<U> childEntities = getChildEntities.apply(parentEntity);

		for (U childEntity : childEntities) {
			if (!daloChildEntities.contains(childEntity)) {
				removeChildEntities.accept(parentEntity, childEntity);
			}
		}

		childEntities = getChildEntities.apply(parentEntity);

		for (U daloChildEntity : daloChildEntities) {
			if (!childEntities.contains(daloChildEntity)) {
				U childEntity = childEntityRepository.getById(
					daloChildEntity.getId());

				associateEntities.accept(parentEntity, childEntity);
			}
		}

		return parentEntity;
	}

	protected T updateRelationshipsFromDALO(T entity) {
		return entity;
	}

	protected T updateRelationshipsToDALO(T entity) {
		return entity;
	}

	private final Map<Long, T> _entitiesMap = new HashMap<>();

}