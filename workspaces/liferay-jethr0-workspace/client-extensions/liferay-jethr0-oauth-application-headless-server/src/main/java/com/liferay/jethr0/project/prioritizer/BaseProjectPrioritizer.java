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

package com.liferay.jethr0.project.prioritizer;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseProjectPrioritizer implements ProjectPrioritizer {

	@Override
	public long getID() {
		return _id;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"id", getID()
		).put(
			"name", getName()
		);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public String toString() {
		return String.valueOf(getJSONObject());
	}

	protected BaseProjectPrioritizer(JSONObject jsonObject) {
		_id = jsonObject.getLong("id");
		_name = jsonObject.getString("name");
	}

	private final long _id;
	private String _name;

}