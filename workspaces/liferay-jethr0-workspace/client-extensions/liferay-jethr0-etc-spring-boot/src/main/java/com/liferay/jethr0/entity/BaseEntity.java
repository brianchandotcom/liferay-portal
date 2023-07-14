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
import com.liferay.jethr0.util.ThreadUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	public String toString() {
		return String.valueOf(getJSONObject());
	}

	protected BaseEntity(JSONObject jsonObject) {
		Date createdDate = null;

		for (int i = 0; i < _RETRY_COUNT; i++) {
			String createDateString = jsonObject.optString("dateCreated");

			try {
				createdDate = StringUtil.toDate(createDateString);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Unable to get create date from ", createDateString,
							". Retry in ", _RETRY_DELAY_DURATION, "ms: ",
							exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}

		if (createdDate == null) {
			throw new RuntimeException(
				"Unable to get create date from " + jsonObject);
		}

		_createdDate = createdDate;

		_id = jsonObject.optLong("id");
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

	private static final long _RETRY_COUNT = 3;

	private static final long _RETRY_DELAY_DURATION = 1000;

	private static final Log _log = LogFactory.getLog(BaseEntity.class);

	private Date _createdDate;
	private long _id;
	private final Map<Class<? extends Entity>, Set<Entity>>
		_relatedEntitiesMap = new HashMap<>();

}