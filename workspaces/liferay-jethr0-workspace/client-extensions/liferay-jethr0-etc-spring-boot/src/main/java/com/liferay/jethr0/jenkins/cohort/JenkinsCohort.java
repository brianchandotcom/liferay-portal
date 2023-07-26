/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.cohort;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.jenkins.server.JenkinsServer;
import com.liferay.jethr0.project.Project;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface JenkinsCohort extends Entity {

	public void addJenkinsServer(JenkinsServer jenkinsServer);

	public void addJenkinsServers(Set<JenkinsServer> jenkinsServers);

	public void addProject(Project project);

	public void addProjects(Set<Project> projects);

	public Set<JenkinsServer> getJenkinsServers();

	public String getName();

	public Set<Project> getProjects();

	public void removeJenkinsServer(JenkinsServer jenkinsServer);

	public void removeJenkinsServers(Set<JenkinsServer> jenkinsServers);

	public void removeProject(Project project);

	public void removeProjects(Set<Project> projects);

	public void setName(String name);

}