/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.cohort;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.jenkins.server.JenkinsServer;
import com.liferay.jethr0.project.Project;

import java.util.Set;

import com.liferay.jethr0.util.StringUtil;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJenkinsCohort
	extends BaseEntity implements JenkinsCohort {

	@Override
	public void addJenkinsServer(JenkinsServer jenkinsServer) {
		addRelatedEntity(jenkinsServer);
	}

	@Override
	public void addJenkinsServers(Set<JenkinsServer> jenkinsNodes) {
		addRelatedEntities(jenkinsNodes);
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
	public Set<JenkinsServer> getJenkinsServers() {
		return getRelatedEntities(JenkinsServer.class);
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
	public void removeJenkinsServer(JenkinsServer jenkinsServer) {
		removeRelatedEntity(jenkinsServer);
	}

	@Override
	public void removeJenkinsServers(Set<JenkinsServer> jenkinsServers) {
		removeRelatedEntities(jenkinsServers);
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

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("name", getName());

		return jsonObject;
	}

	protected BaseJenkinsCohort(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.optString("name");
	}

	private String _name;

}