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

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.jenkins.node.JenkinsNode;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJenkinsServer
	extends BaseEntity implements JenkinsServer {

	@Override
	public void addJenkinsNode(JenkinsNode jenkinsNode) {
		addRelatedEntity(jenkinsNode);

		jenkinsNode.setJenkinsServer(this);
	}

	@Override
	public void addJenkinsNodes(Set<JenkinsNode> jenkinsNodes) {
		for (JenkinsNode jenkinsNode : jenkinsNodes) {
			addJenkinsNode(jenkinsNode);
		}
	}

	@Override
	public Set<JenkinsNode> getJenkinsNodes() {
		return getRelatedEntities(JenkinsNode.class);
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"name", getName()
		).put(
			"url", getURL()
		);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public URL getURL() {
		return _url;
	}

	@Override
	public void removeJenkinsNode(JenkinsNode jenkinsNode) {
		removeRelatedEntity(jenkinsNode);
	}

	@Override
	public void removeJenkinsNodes(Set<JenkinsNode> jenkinsNodes) {
		removeRelatedEntities(jenkinsNodes);
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setURL(URL url) {
		_url = url;
	}

	protected BaseJenkinsServer(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.getString("name");
		_url = StringUtil.toURL(jsonObject.getString("url"));
	}

	private String _name;
	private URL _url;

}