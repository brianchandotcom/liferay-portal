/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.server;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.event.jenkins.client.JenkinsClient;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJenkinsServerEntity
	extends BaseEntity implements JenkinsServerEntity {

	@Override
	public void addJenkinsNodeEnitities(
		Set<JenkinsNodeEntity> jenkinsNodeEntities) {

		addRelatedEntities(jenkinsNodeEntities);
	}

	@Override
	public void addJenkinsNodeEntity(JenkinsNodeEntity jenkinsNodeEntity) {
		addRelatedEntity(jenkinsNodeEntity);
	}

	@Override
	public JSONObject getComputerJSONObject() {
		try {
			return new JSONObject(
				_jenkinsClient.requestGet(
					StringUtil.toURL(
						StringUtil.combine(getURL(), "/computer/api/json"))));
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(exception);
			}

			return null;
		}
	}

	@Override
	public URL getEntityURL() {
		return StringUtil.toURL(
			StringUtil.combine(
				Jethr0ContextUtil.getLiferayPortalURL(), "/#/jenkins-servers/",
				getId()));
	}

	@Override
	public JenkinsCohortEntity getJenkinsCohortEntity() {
		return _jenkinsCohortEntity;
	}

	@Override
	public long getJenkinsCohortEntityId() {
		return _jenkinsCohortEntityId;
	}

	@Override
	public int getJenkinsNodeCount() {
		return _jenkinsNodeCount;
	}

	@Override
	public Set<JenkinsNodeEntity> getJenkinsNodeEntities() {
		return getRelatedEntities(JenkinsNodeEntity.class);
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"jenkinsNodeCount", getJenkinsNodeCount()
		).put(
			"name", getName()
		).put(
			"r_jenkinsCohortToJenkinsServers_c_jenkinsCohortId",
			getJenkinsCohortEntityId()
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
	public void removeJenkinsNode(JenkinsNodeEntity jenkinsNodeEntity) {
		removeRelatedEntity(jenkinsNodeEntity);
	}

	@Override
	public void removeJenkinsNodes(Set<JenkinsNodeEntity> jenkinsNodeEntities) {
		removeRelatedEntities(jenkinsNodeEntities);
	}

	@Override
	public void setJenkinsCohortEntity(
		JenkinsCohortEntity jenkinsCohortEntity) {

		_jenkinsCohortEntity = jenkinsCohortEntity;

		if (jenkinsCohortEntity != null) {
			_jenkinsCohortEntityId = jenkinsCohortEntity.getId();
		}
		else {
			_jenkinsCohortEntityId = 0;
		}
	}

	@Override
	public void setJenkinsNodeCount(int jenkinsNodeCount) {
		_jenkinsNodeCount = jenkinsNodeCount;
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_jenkinsCohortEntityId = jsonObject.optLong(
			"r_jenkinsCohortToJenkinsServers_c_jenkinsCohortId");
		_jenkinsNodeCount = jsonObject.optInt("jenkinsNodeCount");
		_name = jsonObject.optString("name");
		_url = StringUtil.toURL(jsonObject.getString("url"));
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

		if (jsonObject == null) {
			return;
		}

		JSONArray computerJSONArray = jsonObject.optJSONArray("computer");

		if (computerJSONArray == null) {
			return;
		}

		Map<String, JenkinsNodeEntity> jenkinsNodeMap = new HashMap<>();

		for (JenkinsNodeEntity jenkinsNodeEntity : getJenkinsNodeEntities()) {
			jenkinsNodeMap.put(jenkinsNodeEntity.getName(), jenkinsNodeEntity);
		}

		for (int i = 0; i < computerJSONArray.length(); i++) {
			JSONObject computerJSONObject = computerJSONArray.getJSONObject(i);

			JenkinsNodeEntity jenkinsNodeEntity = jenkinsNodeMap.get(
				computerJSONObject.getString("displayName"));

			if (jenkinsNodeEntity == null) {
				continue;
			}

			jenkinsNodeEntity.update(computerJSONObject);
		}

		Set<JenkinsNodeEntity> jenkinsNodeEntities = getJenkinsNodeEntities();

		setJenkinsNodeCount(jenkinsNodeEntities.size());
	}

	protected BaseJenkinsServerEntity(
		JenkinsClient jenkinsClient, JSONObject jsonObject) {

		super(jsonObject);

		_jenkinsClient = jenkinsClient;
	}

	private static final Log _log = LogFactory.getLog(
		BaseJenkinsServerEntity.class);

	private final JenkinsClient _jenkinsClient;
	private JenkinsCohortEntity _jenkinsCohortEntity;
	private long _jenkinsCohortEntityId;
	private int _jenkinsNodeCount;
	private String _name;
	private URL _url;

}