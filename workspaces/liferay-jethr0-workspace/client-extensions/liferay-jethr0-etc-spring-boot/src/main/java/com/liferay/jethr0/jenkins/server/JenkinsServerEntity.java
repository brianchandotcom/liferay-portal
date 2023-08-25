/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.server;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;

import java.net.URL;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface JenkinsServerEntity extends Entity {

	public void addJenkinsNodeEnitities(
		Set<JenkinsNodeEntity> jenkinsNodeEntities);

	public void addJenkinsNodeEntity(JenkinsNodeEntity jenkinsNodeEntity);

	public JSONObject getComputerJSONObject();

	public JenkinsCohortEntity getJenkinsCohortEntity();

	public long getJenkinsCohortEntityId();

	public Set<JenkinsNodeEntity> getJenkinsNodeEntities();

	public String getJenkinsUserName();

	public String getJenkinsUserPassword();

	public String getName();

	public URL getURL();

	public void removeJenkinsNode(JenkinsNodeEntity jenkinsNodeEntity);

	public void removeJenkinsNodes(Set<JenkinsNodeEntity> jenkinsNodeEntities);

	public void setJenkinsCohortEntity(JenkinsCohortEntity jenkinsCohortEntity);

	public void setJenkinsUserName(String jenkinsUserName);

	public void setJenkinsUserPassword(String jenkinsUserPassword);

	public void setName(String name);

	public void setURL(URL url);

	public void update();

}