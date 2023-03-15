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

package com.liferay.jethr0.testsuite;

import com.liferay.jethr0.project.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTestSuite implements TestSuite {

	@Override
	public void addProject(Project project) {
		addProjects(Arrays.asList(project));
	}

	@Override
	public void addProjects(List<Project> projects) {
		for (Project project : projects) {
			if (_projects.contains(project)) {
				continue;
			}

			_projects.add(project);
		}
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
		);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public List<Project> getProjects() {
		return _projects;
	}

	@Override
	public void removeProject(Project project) {
		_projects.remove(project);
	}

	@Override
	public void removeProjects(List<Project> projects) {
		_projects.removeAll(projects);
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public String toString() {
		return String.valueOf(getJSONObject());
	}

	protected BaseTestSuite(JSONObject jsonObject) {
		_id = jsonObject.getLong("id");
		_name = jsonObject.getString("name");
	}

	private final long _id;
	private String _name;
	private final List<Project> _projects = new ArrayList<>();

}