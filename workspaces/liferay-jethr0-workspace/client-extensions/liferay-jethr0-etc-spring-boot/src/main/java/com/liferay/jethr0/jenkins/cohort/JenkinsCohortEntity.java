/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.cohort;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
import com.liferay.jethr0.project.ProjectEntity;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface JenkinsCohortEntity extends Entity {

	public void addJenkinsServerEntities(
		Set<JenkinsServerEntity> jenkinsServerEntities);

	public void addJenkinsServerEntity(JenkinsServerEntity jenkinsServerEntity);

	public void addProjectEntities(Set<ProjectEntity> projectEntities);

	public void addProjectEntity(ProjectEntity projectEntity);

	public Set<JenkinsServerEntity> getJenkinsServerEntities();

	public String getName();

	public Set<ProjectEntity> getProjectEntities();

	public void removeJenkinsServerEntities(
		Set<JenkinsServerEntity> jenkinsServerEntities);

	public void removeJenkinsServerEntity(
		JenkinsServerEntity jenkinsServerEntity);

	public void removeProjectEntities(Set<ProjectEntity> projectEntities);

	public void removeProjectEntity(ProjectEntity projectEntity);

	public void setName(String name);

}