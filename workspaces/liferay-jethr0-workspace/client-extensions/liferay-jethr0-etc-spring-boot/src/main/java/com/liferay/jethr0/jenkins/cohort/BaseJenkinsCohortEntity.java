/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.cohort;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
import com.liferay.jethr0.project.Project;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJenkinsCohortEntity
	extends BaseEntity implements JenkinsCohortEntity {

	@Override
	public void addJenkinsServerEntities(
		Set<JenkinsServerEntity> jenkinsServerEntities) {

		addRelatedEntities(jenkinsServerEntities);
	}

	@Override
	public void addJenkinsServerEntity(
		JenkinsServerEntity jenkinsServerEntity) {

		addRelatedEntity(jenkinsServerEntity);
	}

	@Override
	public void addProject(Project project) {
		addRelatedEntity(project);
	}

	@Override
	public void addProjects(Set<Project> projects) {
		for (Project project : projects) {
			addProject(project);
		}
	}

	@Override
	public Set<JenkinsServerEntity> getJenkinsServerEntities() {
		return getRelatedEntities(JenkinsServerEntity.class);
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
		return getRelatedEntities(Project.class);
	}

	@Override
	public void removeJenkinsServerEntities(
		Set<JenkinsServerEntity> jenkinsServerEntities) {

		removeRelatedEntities(jenkinsServerEntities);
	}

	@Override
	public void removeJenkinsServerEntity(
		JenkinsServerEntity jenkinsServerEntity) {

		removeRelatedEntity(jenkinsServerEntity);
	}

	@Override
	public void removeProject(Project project) {
		removeRelatedEntity(project);
	}

	@Override
	public void removeProjects(Set<Project> projects) {
		removeRelatedEntities(projects);
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	protected BaseJenkinsCohortEntity(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.optString("name");
	}

	private String _name;

}