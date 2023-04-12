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

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.project.Project;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTestSuite extends BaseEntity implements TestSuite {

	@Override
	public void addProject(Project project) {
		addProjects(Collections.singleton(project));
	}

	@Override
	public void addProjects(Set<Project> projects) {
		_projects.addAll(projects);
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
	public Set<Project> getProjects() {
		return _projects;
	}

	@Override
	public void removeProject(Project project) {
		_projects.remove(project);
	}

	@Override
	public void removeProjects(Set<Project> projects) {
		_projects.removeAll(projects);
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	protected BaseTestSuite(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.getString("name");
	}

	private String _name;
	private final Set<Project> _projects = new HashSet<>();

}