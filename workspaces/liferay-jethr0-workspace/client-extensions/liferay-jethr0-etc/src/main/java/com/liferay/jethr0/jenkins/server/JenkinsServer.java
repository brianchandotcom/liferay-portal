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

package com.liferay.jethr0.jenkins.server;

import com.liferay.jethr0.entity.Entity;
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

	public Set<JenkinsNode> getJenkinsNodes();

	public String getJenkinsUserName();

	public String getJenkinsUserPassword();

	public String getName();

	public URL getURL();

	public void removeJenkinsNode(JenkinsNode jenkinsNode);

	public void removeJenkinsNodes(Set<JenkinsNode> jenkinsNodes);

	public void setJenkinsUserName(String jenkinsUserName);

	public void setJenkinsUserPassword(String jenkinsUserPassword);

	public void setName(String name);

	public void setURL(URL url);

	public void update();

}