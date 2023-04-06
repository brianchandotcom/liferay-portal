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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseEntityRepository<T extends Entity>
	implements EntityRepository<T> {

	@Override
	public void add(List<T> entities) {
		if (entities == null) {
			return;
		}

		entities.removeAll(Collections.singleton(null));

		EntityDALO<T> entityDALO = getEntityDALO();

		for (T entity : entities) {
			if (entity.getId() == 0) {
				entity = entityDALO.create(entity);
			}

			_entities.put(entity.getId(), entity);
		}
	}

	@Override
	public void add(T entity) {
		add(Collections.singletonList(entity));
	}

	@Override
	public List<T> get() {
		return new ArrayList<>(_entities.values());
	}

	@Override
	public T getById(long id) {
		return _entities.get(id);
	}

	@Override
	public void remove(List<T> entities) {
		if (entities == null) {
			return;
		}

		entities.removeAll(Collections.singleton(null));

		EntityDALO<T> entityDALO = getEntityDALO();

		for (T entity : entities) {
			_entities.remove(entity.getId());

			entityDALO.delete(entity);
		}
	}

	@Override
	public void remove(T entity) {
		remove(Collections.singletonList(entity));
	}

	@Override
	public T update(T entity) {
		EntityDALO<T> entityDALO = getEntityDALO();

		if (entity.getId() == 0) {
			entity = entityDALO.create(entity);

			add(entity);
		}

		return entityDALO.update(entity);
	}

	protected abstract EntityDALO<T> getEntityDALO();

	private final Map<Long, T> _entities = new HashMap<>();

}