/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.node;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Set;

import org.apache.tomcat.util.codec.binary.Base64;

import org.json.JSONObject;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Michael Hashimoto
 */
public class BaseJenkinsNodeEntity
	extends BaseEntity implements JenkinsNodeEntity {

	@Override
	public boolean getGoodBattery() {
		return _goodBattery;
	}

	@Override
	public JenkinsCohortEntity getJenkinsCohortEntity() {
		JenkinsServerEntity jenkinsServerEntity = getJenkinsServerEntity();

		if (jenkinsServerEntity == null) {
			return null;
		}

		return jenkinsServerEntity.getJenkinsCohortEntity();
	}

	@Override
	public JenkinsServerEntity getJenkinsServerEntity() {
		return _jenkinsServerEntity;
	}

	@Override
	public long getJenkinsServerEntityId() {
		return _jenkinsServerId;
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
		).put(
			"primaryLabel", getPrimaryLabel()
		).put(
			"r_jenkinsServerToJenkinsNodes_c_jenkinsServerId",
			getJenkinsServerEntityId()
		);

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
	public String getPrimaryLabel() {
		return _primaryLabel;
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
	public boolean isCompatible(BuildEntity buildEntity) {
		if (!_hasCompatibleBattery(buildEntity) ||
			!_hasCompatibleCohort(buildEntity) ||
			!_hasCompatibleNodeCount(buildEntity) ||
			!_hasCompatibleNodeRAM(buildEntity) ||
			!_hasCompatibleNodeType(buildEntity)) {

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
	public void setJenkinsServerEntity(
		JenkinsServerEntity jenkinsServerEntity) {

		_jenkinsServerEntity = jenkinsServerEntity;

		if (jenkinsServerEntity != null) {
			_jenkinsServerId = jenkinsServerEntity.getId();
		}
		else {
			_jenkinsServerId = 0;
		}
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
	public void setPrimaryLabel(String primaryLabel) {
		_primaryLabel = primaryLabel;
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

	protected BaseJenkinsNodeEntity(JSONObject jsonObject) {
		super(jsonObject);

		_jenkinsServerId = jsonObject.optLong(
			"r_jenkinsServerToJenkinsNodes_c_jenkinsServerId");
		_goodBattery = jsonObject.getBoolean("goodBattery");
		_primaryLabel = jsonObject.getString("primaryLabel");
		_name = jsonObject.getString("name");
		_nodeCount = jsonObject.getInt("nodeCount");
		_nodeRAM = jsonObject.getInt("nodeRAM");
		_type = Type.get(jsonObject.getJSONObject("type"));
		_url = StringUtil.toURL(jsonObject.getString("url"));
	}

	private JSONObject _getComputerJSONObject() {
		JenkinsServerEntity jenkinsServerEntity = getJenkinsServerEntity();

		String basicAuthorization = StringUtil.combine(
			jenkinsServerEntity.getJenkinsUserName(), ":",
			jenkinsServerEntity.getJenkinsUserPassword());

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

	private boolean _hasCompatibleBattery(BuildEntity buildEntity) {
		if (!buildEntity.requiresGoodBattery() || getGoodBattery()) {
			return true;
		}

		return false;
	}

	private boolean _hasCompatibleCohort(BuildEntity buildEntity) {
		JobEntity jobEntity = buildEntity.getJobEntity();

		Set<JenkinsCohortEntity> jenkinsCohortEntities =
			jobEntity.getJenkinsCohortEntities();

		if (jenkinsCohortEntities.isEmpty() ||
			jenkinsCohortEntities.contains(getJenkinsCohortEntity())) {

			return true;
		}

		return false;
	}

	private boolean _hasCompatibleNodeCount(BuildEntity buildEntity) {
		if (getNodeCount() <= buildEntity.getMaxNodeCount()) {
			return true;
		}

		return false;
	}

	private boolean _hasCompatibleNodeRAM(BuildEntity buildEntity) {
		if (getNodeRAM() >= buildEntity.getMinNodeRAM()) {
			return true;
		}

		return false;
	}

	private boolean _hasCompatibleNodeType(BuildEntity buildEntity) {
		JenkinsNodeEntity.Type jenkinsNodeType =
			buildEntity.getJenkinsNodeType();

		if ((jenkinsNodeType == null) || (jenkinsNodeType == getType())) {
			return true;
		}

		return false;
	}

	private boolean _goodBattery;
	private boolean _idle;
	private JenkinsServerEntity _jenkinsServerEntity;
	private long _jenkinsServerId;
	private String _name;
	private int _nodeCount;
	private int _nodeRAM;
	private boolean _offline;
	private String _primaryLabel;
	private final Type _type;
	private URL _url;

}