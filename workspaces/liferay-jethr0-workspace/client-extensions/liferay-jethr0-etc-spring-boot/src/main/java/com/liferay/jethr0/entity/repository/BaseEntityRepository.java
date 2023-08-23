/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.entity.repository;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.entity.dalo.EntityDefinitionDALO;

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
	public T add(JSONObject jsonObject) {
		EntityDefinitionDALO<T> entityDefinitionDALO =
			getEntityDefinitionDALO();

		T entity = entityDefinitionDALO.create(jsonObject);

		addAll(Collections.singleton(entity));

		return entity;
	}

	@Override
	public T add(T entity) {
		addAll(Collections.singleton(entity));

		return entity;
	}

	@Override
	public Set<T> addAll(Set<T> entities) {
		if (entities == null) {
			return entities;
		}

		entities.removeAll(Collections.singleton(null));

		EntityDefinitionDALO<T> entityDefinitionDALO =
			getEntityDefinitionDALO();

		for (T entity : entities) {
			if (entity.getId() == 0) {
				entity = entityDefinitionDALO.create(entity);
			}

			_entitiesMap.put(entity.getId(), entity);
		}

		return entities;
	}

	@Override
	public Set<T> getAll() {
		return new HashSet<>(_entitiesMap.values());
	}

	@Override
	public T getById(long id) {
		return _entitiesMap.get(id);
	}

	@Override
	public void initialize() {
		EntityDefinitionDALO<T> entityDefinitionDALO =
			getEntityDefinitionDALO();

		addAll(entityDefinitionDALO.getAll());
	}

	public void initializeRelationships() {
	}

	@Override
	public void remove(Set<T> entities) {
		if (entities == null) {
			return;
		}

		entities.removeAll(Collections.singleton(null));

		EntityDefinitionDALO<T> entityDefinitionDALO =
			getEntityDefinitionDALO();

		for (T entity : entities) {
			_entitiesMap.remove(entity.getId());

			entityDefinitionDALO.delete(entity);
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

		EntityDefinitionDALO<T> entityDefinitionDALO =
			getEntityDefinitionDALO();

		entity = entityDefinitionDALO.update(entity);

		_entitiesMap.put(entity.getId(), entity);

		return entity;
	}

	private final Map<Long, T> _entitiesMap = new HashMap<>();

}