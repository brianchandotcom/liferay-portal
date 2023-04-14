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

import org.json.JSONObject;

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
	public URL getURL() {
		return _url;
	}

	@Override
	public boolean isCompatible(Build build) {
		if (!_hasCompatibleBattery(build) || !_hasCompatibleNodeCount(build) ||
			!_hasCompatibleNodeRAM(build)) {

			return false;
		}

		return true;
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

	protected BaseJenkinsNode(JSONObject jsonObject) {
		super(jsonObject);

		_goodBattery = jsonObject.getBoolean("goodBattery");
		_name = jsonObject.getString("name");
		_nodeCount = jsonObject.getInt("nodeCount");
		_nodeRAM = jsonObject.getInt("nodeRAM");
		_url = StringUtil.toURL(jsonObject.getString("url"));
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

	private boolean _goodBattery;
	private JenkinsServer _jenkinsServer;
	private String _name;
	private int _nodeCount;
	private int _nodeRAM;
	private URL _url;

}