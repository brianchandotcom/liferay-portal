/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.entity.repository;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.entity.dalo.EntityDALO;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseEntityRepository<T extends Entity>
	implements EntityRepository<T> {

	@Override
	public T create(JSONObject jsonObject) {
		EntityDALO<T> entityDALO = getEntityDALO();

		return add(entityDALO.create(jsonObject));
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

		return add(entityDALO.get(id));
	}

	@Override
	public void initialize() {
		EntityDALO<T> entityDALO = getEntityDALO();

		addAll(entityDALO.getAll());
	}

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

		_entitiesMap.put(entity.getId(), entity);

		return entity;
	}

	protected T add(T entity) {
		addAll(Collections.singleton(entity));

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

			_entitiesMap.put(entity.getId(), entity);
		}

		return entities;
	}

	protected abstract EntityDALO<T> getEntityDALO();

	protected boolean hasEntity(long id) {
		if (_entitiesMap.containsKey(id)) {
			return true;
		}

		return false;
	}

	private final Map<Long, T> _entitiesMap = new HashMap<>();

}