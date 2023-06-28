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

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.entity.BaseEntity;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildParameter
	extends BaseEntity implements BuildParameter {

	@Override
	public Build getBuild() {
		return _build;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
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
	public void setBuild(Build build) {
		_build = build;
	}

	protected BaseBuildParameter(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.getString("name");
		_value = jsonObject.getString("value");
	}

	private Build _build;
	private final String _name;
	private final String _value;

}