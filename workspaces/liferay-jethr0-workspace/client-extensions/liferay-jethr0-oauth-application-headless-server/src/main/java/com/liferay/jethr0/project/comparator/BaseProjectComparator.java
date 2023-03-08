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

package com.liferay.jethr0.project.comparator;

import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseProjectComparator implements ProjectComparator {

	@Override
	public long getID() {
		return _id;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		ProjectComparator.Type type = getType();
		ProjectPrioritizer projectPrioritizer = getProjectPrioritizer();

		jsonObject.put(
			"id", getID()
		).put(
			"position", getPosition()
		).put(
			"r_projectPrioritizerToProjectComparators_c_projectPrioritizerId",
			projectPrioritizer.getID()
		).put(
			"type", type.getJSONObject()
		).put(
			"value", getValue()
		);

		return jsonObject;
	}

	@Override
	public int getPosition() {
		return _position;
	}

	@Override
	public ProjectPrioritizer getProjectPrioritizer() {
		return _projectPrioritizer;
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public String getValue() {
		return _value;
	}

	@Override
	public void setPosition(int position) {
		_position = position;
	}

	@Override
	public void setValue(String value) {
		_value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(getJSONObject());
	}

	protected BaseProjectComparator(
		ProjectPrioritizer projectPrioritizer, JSONObject jsonObject) {

		_projectPrioritizer = projectPrioritizer;

		_projectPrioritizer.addProjectComparator(this);

		_id = jsonObject.getLong("id");
		_position = jsonObject.getInt("position");
		_type = Type.get(jsonObject.getJSONObject("type"));
		_value = jsonObject.optString("value");
	}

	private final long _id;
	private int _position;
	private final ProjectPrioritizer _projectPrioritizer;
	private final Type _type;
	private String _value;

}