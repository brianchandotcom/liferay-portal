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

package com.liferay.jethr0.entity;

import com.liferay.jethr0.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseEntity implements Entity {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if ((object == null) || (getClass() != object.getClass())) {
			return false;
		}

		if (object instanceof Entity) {
			Entity entity = (Entity)object;

			if (Objects.equals(getId(), entity.getId())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Date getCreatedDate() {
		return _createdDate;
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"dateCreated", StringUtil.toString(getCreatedDate())
		).put(
			"id", getId()
		);

		return jsonObject;
	}

	@Override
	public List<Entity> getRelatedEntities() {
		List<Entity> relatedEntities = new ArrayList<>();

		for (List<Entity> entities : _relatedEntitiesMap.values()) {
			relatedEntities.addAll(entities);
		}

		return relatedEntities;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		_createdDate = createdDate;
	}

	@Override
	public void setId(long id) {
		_id = id;
	}

	@Override
	public String toString() {
		return String.valueOf(getJSONObject());
	}

	protected BaseEntity(JSONObject jsonObject) {
		_createdDate = StringUtil.toDate(jsonObject.optString("dateCreated"));
		_id = jsonObject.optLong("id");
	}

	protected void addRelatedEntities(List<? extends Entity> entities) {
		for (Entity entity : entities) {
			addRelatedEntity(entity);
		}
	}

	protected void addRelatedEntity(Entity entity) {
		List<Entity> relatedEntities = _getRelatedEntities(
			_getEntityClass(entity.getClass()));

		if (!relatedEntities.contains(entity)) {
			relatedEntities.add(entity);
		}
	}

	protected <T extends Entity> List<T> getRelatedEntities(Class<T> clazz) {
		List<T> relatedEntities = new ArrayList<>();

		for (Entity relatedEntity : _getRelatedEntities(clazz)) {
			relatedEntities.add(clazz.cast(relatedEntity));
		}

		return relatedEntities;
	}

	protected void removeRelatedEntities(List<? extends Entity> entities) {
		for (Entity entity : entities) {
			removeRelatedEntity(entity);
		}
	}

	protected void removeRelatedEntity(Entity entity) {
		List<Entity> relatedEntities = _getRelatedEntities(
			_getEntityClass(entity.getClass()));

		relatedEntities.removeAll(Arrays.asList(entity));
	}

	private Class<? extends Entity> _getEntityClass(Class<?> entityClass) {
		if (entityClass == null) {
			return null;
		}

		for (Class<?> interfaceClass : entityClass.getInterfaces()) {
			if (interfaceClass == Entity.class) {
				return (Class<? extends Entity>)entityClass;
			}

			Class<?> interfaceEntityClass = _getEntityClass(interfaceClass);

			if (interfaceEntityClass == null) {
				continue;
			}

			return (Class<? extends Entity>)interfaceEntityClass;
		}

		return _getEntityClass(entityClass.getSuperclass());
	}

	private List<Entity> _getRelatedEntities(Class<?> clazz) {
		Class<? extends Entity> entityClass = _getEntityClass(clazz);

		List<Entity> relatedEntities = _relatedEntitiesMap.get(entityClass);

		if (relatedEntities == null) {
			relatedEntities = new ArrayList<>();

			_relatedEntitiesMap.put(entityClass, relatedEntities);
		}

		return relatedEntities;
	}

	private Date _createdDate;
	private long _id;
	private final Map<Class<? extends Entity>, List<Entity>>
		_relatedEntitiesMap = new HashMap<>();

}