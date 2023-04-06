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

import java.util.Date;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseEntity implements Entity {

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

	private Date _createdDate;
	private long _id;

}