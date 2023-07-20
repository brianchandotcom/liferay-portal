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

import com.liferay.jethr0.util.Retryable;
import com.liferay.jethr0.util.StringUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
			"dateModified", StringUtil.toString(getModifiedDate())
		).put(
			"id", getId()
		);

		return jsonObject;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public Set<Entity> getRelatedEntities() {
		Set<Entity> relatedEntities = new HashSet<>();

		for (Set<Entity> entities : _relatedEntitiesMap.values()) {
			relatedEntities.addAll(entities);
		}

		return relatedEntities;
	}

	@Override
	public int hashCode() {
		String string = StringUtil.combine(getClass(), getId());

		return string.hashCode();
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
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return String.valueOf(getJSONObject());
	}

	protected BaseEntity(JSONObject jsonObject) {
		_createdDate = _getDateFromJSON(jsonObject, "dateCreated");
		_id = jsonObject.optLong("id");
		_modifiedDate = _getDateFromJSON(jsonObject, "dateModified");
	}

	protected void addRelatedEntities(Collection<? extends Entity> entities) {
		for (Entity entity : entities) {
			addRelatedEntity(entity);
		}
	}

	protected void addRelatedEntity(Entity entity) {
		Set<Entity> relatedEntities = _getRelatedEntities(
			_getEntityClass(entity.getClass()));

		relatedEntities.add(entity);
	}

	protected <T extends Entity> Set<T> getRelatedEntities(Class<T> clazz) {
		Set<T> relatedEntities = new HashSet<>();

		for (Entity relatedEntity : _getRelatedEntities(clazz)) {
			relatedEntities.add(clazz.cast(relatedEntity));
		}

		return relatedEntities;
	}

	protected void removeRelatedEntities(Set<? extends Entity> entities) {
		for (Entity entity : entities) {
			removeRelatedEntity(entity);
		}
	}

	protected void removeRelatedEntity(Entity entity) {
		Set<Entity> relatedEntities = _getRelatedEntities(
			_getEntityClass(entity.getClass()));

		relatedEntities.removeAll(Arrays.asList(entity));
	}

	private Date _getDateFromJSON(JSONObject jsonObject, String dateKey) {
		Retryable<Date> retryable = new Retryable<Date>() {

			@Override
			public Date execute() {
				return StringUtil.toDate(jsonObject.optString(dateKey));
			}

		};

		return retryable.executeWithRetries();
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

	private Set<Entity> _getRelatedEntities(Class<?> clazz) {
		Class<? extends Entity> entityClass = _getEntityClass(clazz);

		Set<Entity> relatedEntities = _relatedEntitiesMap.get(entityClass);

		if (relatedEntities == null) {
			relatedEntities = new HashSet<>();

			_relatedEntitiesMap.put(entityClass, relatedEntities);
		}

		return relatedEntities;
	}

	private Date _createdDate;
	private long _id;
	private Date _modifiedDate;
	private final Map<Class<? extends Entity>, Set<Entity>>
		_relatedEntitiesMap = new HashMap<>();

}