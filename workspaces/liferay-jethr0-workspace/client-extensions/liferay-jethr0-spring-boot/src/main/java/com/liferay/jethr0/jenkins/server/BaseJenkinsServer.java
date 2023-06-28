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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.tomcat.util.codec.binary.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

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
	public JSONObject getComputerJSONObject() {
		String basicAuthorization = StringUtil.combine(
			getJenkinsUserName(), ":", getJenkinsUserPassword());

		String response = WebClient.create(
			StringUtil.combine(getURL(), "/computer/api/json")
		).get(
		).accept(
			MediaType.APPLICATION_JSON
		).header(
			"Authorization",
			"Basic " + Base64.encodeBase64String(basicAuthorization.getBytes())
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		return new JSONObject(response);
	}

	@Override
	public Set<JenkinsNode> getJenkinsNodes() {
		return getRelatedEntities(JenkinsNode.class);
	}

	@Override
	public String getJenkinsUserName() {
		return _jenkinsUserName;
	}

	@Override
	public String getJenkinsUserPassword() {
		return _jenkinsUserPassword;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"jenkinsUserName", getJenkinsUserName()
		).put(
			"jenkinsUserPassword", getJenkinsUserPassword()
		).put(
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
	public void setJenkinsUserName(String jenkinsUserName) {
		_jenkinsUserName = jenkinsUserName;
	}

	@Override
	public void setJenkinsUserPassword(String jenkinsUserPassword) {
		_jenkinsUserPassword = jenkinsUserPassword;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setURL(URL url) {
		_url = url;
	}

	@Override
	public void update() {
		JSONObject jsonObject = getComputerJSONObject();

		JSONArray computerJSONArray = jsonObject.getJSONArray("computer");

		Map<String, JenkinsNode> jenkinsNodeMap = new HashMap<>();

		for (JenkinsNode jenkinsNode : getJenkinsNodes()) {
			jenkinsNodeMap.put(jenkinsNode.getName(), jenkinsNode);
		}

		for (int i = 0; i < computerJSONArray.length(); i++) {
			JSONObject computerJSONObject = computerJSONArray.getJSONObject(i);

			JenkinsNode jenkinsNode = jenkinsNodeMap.get(
				computerJSONObject.getString("displayName"));

			if (jenkinsNode == null) {
				continue;
			}

			jenkinsNode.update(computerJSONObject);
		}
	}

	protected BaseJenkinsServer(JSONObject jsonObject) {
		super(jsonObject);

		_jenkinsUserName = jsonObject.getString("jenkinsUserName");
		_jenkinsUserPassword = jsonObject.getString("jenkinsUserPassword");
		_name = jsonObject.optString("name");
		_url = StringUtil.toURL(jsonObject.getString("url"));
	}

	private String _jenkinsUserName;
	private String _jenkinsUserPassword;
	private String _name;
	private URL _url;

}