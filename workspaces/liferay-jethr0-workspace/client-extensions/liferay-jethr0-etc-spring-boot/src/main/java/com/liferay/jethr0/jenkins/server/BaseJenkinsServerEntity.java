/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.server;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
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
	public JenkinsCohortEntity getJenkinsCohortEntity() {
		return _jenkinsCohortEntity;
	}

	public long getJenkinsCohortEntityId() {
		return _jenkinsCohortEntityId;
	}

	@Override
	public Set<JenkinsNodeEntity> getJenkinsNodeEntities() {
		return getRelatedEntities(JenkinsNodeEntity.class);
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
	}

	protected BaseJenkinsServerEntity(JSONObject jsonObject) {
		super(jsonObject);

		_jenkinsCohortEntityId = jsonObject.optLong(
			"r_jenkinsCohortToJenkinsServers_c_jenkinsCohortId");
		_jenkinsUserName = jsonObject.getString("jenkinsUserName");
		_jenkinsUserPassword = jsonObject.getString("jenkinsUserPassword");
		_name = jsonObject.optString("name");
		_url = StringUtil.toURL(jsonObject.getString("url"));
	}

	private JenkinsCohortEntity _jenkinsCohortEntity;
	private long _jenkinsCohortEntityId;
	private String _jenkinsUserName;
	private String _jenkinsUserPassword;
	private String _name;
	private URL _url;

}