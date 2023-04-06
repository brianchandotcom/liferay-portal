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

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.project.comparator.ProjectComparator;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseProjectPrioritizer
	extends BaseEntity implements ProjectPrioritizer {

	@Override
	public void addProjectComparator(ProjectComparator projectComparator) {
		if (_projectComparators.contains(projectComparator)) {
			return;
		}

		_projectComparators.add(projectComparator);
	}

	@Override
	public void addProjectComparators(
		List<ProjectComparator> projectComparators) {

		for (ProjectComparator projectComparator : projectComparators) {
			addProjectComparator(projectComparator);
		}
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("name", getName());

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public List<ProjectComparator> getProjectComparators() {
		return _projectComparators;
	}

	@Override
	public void removeProjectComparator(ProjectComparator projectComparator) {
		_projectComparators.remove(projectComparator);
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
		super(jsonObject);

		_name = jsonObject.getString("name");
	}

	private String _name;
	private final List<ProjectComparator> _projectComparators =
		new ArrayList<>();

}