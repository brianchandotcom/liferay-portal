/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.testsuite;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.job.ProjectEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTestSuiteEntity extends BaseEntity implements TestSuiteEntity {

	@Override
	public void addProjectEntities(Set<ProjectEntity> projectEntities) {
		_projectEntities.addAll(projectEntities);
	}

	@Override
	public void addProjectEntity(ProjectEntity projectEntity) {
		addProjectEntities(Collections.singleton(projectEntity));
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
	public Set<ProjectEntity> getProjectEntities() {
		return _projectEntities;
	}

	@Override
	public void removeProjectEntities(Set<ProjectEntity> projectEntities) {
		_projectEntities.removeAll(projectEntities);
	}

	@Override
	public void removeProjectEntity(ProjectEntity projectEntity) {
		_projectEntities.remove(projectEntity);
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	protected BaseTestSuiteEntity(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.getString("name");
	}

	private String _name;
	private final Set<ProjectEntity> _projectEntities = new HashSet<>();

}