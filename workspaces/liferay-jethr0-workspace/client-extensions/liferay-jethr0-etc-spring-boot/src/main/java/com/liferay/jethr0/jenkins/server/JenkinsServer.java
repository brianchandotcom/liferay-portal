/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.server;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohort;
import com.liferay.jethr0.jenkins.node.JenkinsNode;

import java.net.URL;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface JenkinsServer extends Entity {

	public void addJenkinsNode(JenkinsNode jenkinsNode);

	public void addJenkinsNodes(Set<JenkinsNode> jenkinsNodes);

	public JSONObject getComputerJSONObject();

	public JenkinsCohort getJenkinsCohort();

	public Set<JenkinsNode> getJenkinsNodes();

	public String getJenkinsUserName();

	public String getJenkinsUserPassword();

	public String getName();

	public URL getURL();

	public void removeJenkinsNode(JenkinsNode jenkinsNode);

	public void removeJenkinsNodes(Set<JenkinsNode> jenkinsNodes);

	public void setJenkinsCohort(JenkinsCohort jenkinsCohort);

	public void setJenkinsUserName(String jenkinsUserName);

	public void setJenkinsUserPassword(String jenkinsUserPassword);

	public void setName(String name);

	public void setURL(URL url);

	public void update();

}