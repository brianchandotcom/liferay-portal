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

package com.liferay.jethr0.entity.repository;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.entity.dalo.EntityDALO;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseEntityRepository<T extends Entity>
	implements EntityRepository<T> {

	@Override
	public T add(JSONObject jsonObject) {
		EntityDALO<T> entityDALO = getEntityDALO();

		T entity = entityDALO.create(jsonObject);

		addAll(Collections.singleton(entity));

		return entity;
	}

	@Override
	public T add(T entity) {
		addAll(Collections.singleton(entity));

		return entity;
	}

	@Override
	public void addAll(Set<T> entities) {
		if (entities == null) {
			return;
		}

		entities.removeAll(Collections.singleton(null));

		EntityDALO<T> entityDALO = getEntityDALO();

		Map<Long, T> entitiesMap = _getEntitiesMap();

		for (T entity : entities) {
			if (entity.getId() == 0) {
				entity = entityDALO.create(entity);
			}

			entitiesMap.put(entity.getId(), entity);
		}
	}

	@Override
	public Set<T> getAll() {
		Map<Long, T> entitiesMap = _getEntitiesMap();

		return new HashSet<>(entitiesMap.values());
	}

	@Override
	public T getById(long id) {
		Map<Long, T> entitiesMap = _getEntitiesMap();

		return entitiesMap.get(id);
	}

	@Override
	public void remove(Set<T> entities) {
		if (entities == null) {
			return;
		}

		entities.removeAll(Collections.singleton(null));

		EntityDALO<T> entityDALO = getEntityDALO();

		Map<Long, T> entitiesMap = _getEntitiesMap();

		for (T entity : entities) {
			entitiesMap.remove(entity.getId());

			entityDALO.delete(entity);
		}
	}

	@Override
	public void remove(T entity) {
		remove(Collections.singleton(entity));
	}

	@Override
	public T update(T entity) {
		EntityDALO<T> entityDALO = getEntityDALO();

		if (entity.getId() == 0) {
			entity = entityDALO.create(entity);

			add(entity);
		}

		updateEntityRelationshipsInDatabase(entity);

		return entityDALO.update(entity);
	}

	protected T updateEntityRelationshipsFromDatabase(T entity) {
		return entity;
	}

	protected T updateEntityRelationshipsInDatabase(T entity) {
		return entity;
	}

	private Map<Long, T> _getEntitiesMap() {
		synchronized (_log) {
			if (_entitiesMap != null) {
				return _entitiesMap;
			}

			_entitiesMap = new HashMap<>();

			EntityDALO<T> entityDALO = getEntityDALO();

			for (T entity : entityDALO.getAll()) {
				entity = updateEntityRelationshipsFromDatabase(entity);

				_entitiesMap.put(entity.getId(), entity);
			}

			return _entitiesMap;
		}
	}

	private static final Log _log = LogFactory.getLog(EntityRepository.class);

	private Map<Long, T> _entitiesMap;

}