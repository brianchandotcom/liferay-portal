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

package com.liferay.jethr0.build.parameter;

import org.json.JSONObject;

import com.liferay.jethr0.build.Build;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildParameter implements BuildParameter {

	@Override
	public Build getBuild() {
		return _build;
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"id", getId()
		).put(
			"name", getName()
		).put(
			"value", getValue()
		).put(
			"value", getValue()
		);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return String.valueOf(getJSONObject());
	}

	protected BaseBuildParameter(Build build, JSONObject jsonObject) {
		_build = build;

		_name = jsonObject.getString("name");
		_id = jsonObject.getLong("id");
		_value = jsonObject.getString("value");
	}

	private final Build _build;
	private final long _id;
	private final String _name;
	private final String _value;

}