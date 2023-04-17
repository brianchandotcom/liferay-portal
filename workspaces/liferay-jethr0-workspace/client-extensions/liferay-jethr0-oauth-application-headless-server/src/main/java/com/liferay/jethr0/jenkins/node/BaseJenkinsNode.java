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

package com.liferay.jethr0.jenkins.node;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.jenkins.server.JenkinsServer;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.apache.tomcat.util.codec.binary.Base64;

import org.json.JSONObject;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Michael Hashimoto
 */
public class BaseJenkinsNode extends BaseEntity implements JenkinsNode {

	@Override
	public boolean getGoodBattery() {
		return _goodBattery;
	}

	@Override
	public JenkinsServer getJenkinsServer() {
		return _jenkinsServer;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"goodBattery", getGoodBattery()
		).put(
			"name", getName()
		).put(
			"nodeCount", getNodeCount()
		).put(
			"nodeRAM", getNodeRAM()
		);

		JenkinsServer jenkinsServer = getJenkinsServer();

		if (jenkinsServer != null) {
			jsonObject.put(
				"r_jenkinsServerToJenkinsNodes_c_jenkinsServerId",
				jenkinsServer.getId());
		}

		Type type = getType();

		jsonObject.put(
			"type", type.getJSONObject()
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
	public int getNodeCount() {
		return _nodeCount;
	}

	@Override
	public int getNodeRAM() {
		return _nodeRAM;
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public URL getURL() {
		return _url;
	}

	@Override
	public boolean isAvailable() {
		if (!isOffline() && isIdle()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isCompatible(Build build) {
		if (!_hasCompatibleBattery(build) || !_hasCompatibleNodeCount(build) ||
			!_hasCompatibleNodeRAM(build) || !_hasCompatibleNodeType(build)) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isIdle() {
		return _idle;
	}

	@Override
	public boolean isOffline() {
		return _offline;
	}

	@Override
	public void setGoodBattery(boolean goodBattery) {
		_goodBattery = goodBattery;
	}

	@Override
	public void setJenkinsServer(JenkinsServer jenkinsServer) {
		_jenkinsServer = jenkinsServer;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setNodeCount(int nodeCount) {
		_nodeCount = nodeCount;
	}

	@Override
	public void setNodeRAM(int nodeRAM) {
		_nodeRAM = nodeRAM;
	}

	@Override
	public void setURL(URL url) {
		_url = url;
	}

	@Override
	public void update() {
		update(_getComputerJSONObject());
	}

	@Override
	public void update(JSONObject computerJSONObject) {
		_idle = computerJSONObject.getBoolean("idle");
		_offline = computerJSONObject.getBoolean("offline");
	}

	protected BaseJenkinsNode(JSONObject jsonObject) {
		super(jsonObject);

		_goodBattery = jsonObject.getBoolean("goodBattery");
		_name = jsonObject.getString("name");
		_nodeCount = jsonObject.getInt("nodeCount");
		_nodeRAM = jsonObject.getInt("nodeRAM");
		_type = Type.get(jsonObject.getJSONObject("type"));
		_url = StringUtil.toURL(jsonObject.getString("url"));
	}

	private JSONObject _getComputerJSONObject() {
		JenkinsServer jenkinsServer = getJenkinsServer();

		String basicAuthorization = StringUtil.combine(
			jenkinsServer.getJenkinsUserName(), ":",
			jenkinsServer.getJenkinsUserPassword());

		String response = WebClient.create(
			StringUtil.combine(getURL(), "/api/json")
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

	private boolean _hasCompatibleBattery(Build build) {
		if (!build.requiresGoodBattery() || getGoodBattery()) {
			return true;
		}

		return false;
	}

	private boolean _hasCompatibleNodeCount(Build build) {
		if (getNodeCount() <= build.getMaxNodeCount()) {
			return true;
		}

		return false;
	}

	private boolean _hasCompatibleNodeRAM(Build build) {
		if (getNodeRAM() >= build.getMinNodeRAM()) {
			return true;
		}

		return false;
	}

	private boolean _hasCompatibleNodeType(Build build) {
		JenkinsNode.Type jenkinsNodeType = build.getNodeType();

		if ((jenkinsNodeType == null) || (jenkinsNodeType == getType())) {
			return true;
		}

		return false;
	}

	private boolean _goodBattery;
	private boolean _idle;
	private JenkinsServer _jenkinsServer;
	private String _name;
	private int _nodeCount;
	private int _nodeRAM;
	private boolean _offline;
	private final Type _type;
	private URL _url;

}