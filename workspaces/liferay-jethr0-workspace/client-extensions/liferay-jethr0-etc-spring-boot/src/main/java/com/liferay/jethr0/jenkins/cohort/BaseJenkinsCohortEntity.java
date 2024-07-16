/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.cohort;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJenkinsCohortEntity
	extends BaseEntity implements JenkinsCohortEntity {

	@Override
	public void addJenkinsServerEntities(
		Set<JenkinsServerEntity> jenkinsServerEntities) {

		addRelatedEntities(jenkinsServerEntities);
	}

	@Override
	public void addJenkinsServerEntity(
		JenkinsServerEntity jenkinsServerEntity) {

		addRelatedEntity(jenkinsServerEntity);
	}

	@Override
	public void addJobEntities(Set<JobEntity> jobEntities) {
		addRelatedEntities(jobEntities);
	}

	@Override
	public void addJobEntity(JobEntity jobEntity) {
		addRelatedEntity(jobEntity);
	}

	@Override
	public URL getEntityURL() {
		return StringUtil.toURL(
			StringUtil.combine(
				Jethr0ContextUtil.getLiferayPortalURL(), "/#/jenkins-cohorts/",
				getId()));
	}

	@Override
	public int getJenkinsServerCount() {
		return _jenkinsServerCount;
	}

	@Override
	public Set<JenkinsServerEntity> getJenkinsServerEntities() {
		return getRelatedEntities(JenkinsServerEntity.class);
	}

	@Override
	public Set<JobEntity> getJobEntities() {
		return getRelatedEntities(JobEntity.class);
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"jenkinsServerCount", getJenkinsServerCount()
		).put(
			"name", getName()
		);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void removeJenkinsServerEntities(
		Set<JenkinsServerEntity> jenkinsServerEntities) {

		removeRelatedEntities(jenkinsServerEntities);
	}

	@Override
	public void removeJenkinsServerEntity(
		JenkinsServerEntity jenkinsServerEntity) {

		removeRelatedEntity(jenkinsServerEntity);
	}

	@Override
	public void removeJobEntities(Set<JobEntity> jobEntities) {
		removeRelatedEntities(jobEntities);
	}

	@Override
	public void removeJobEntity(JobEntity jobEntity) {
		removeRelatedEntity(jobEntity);
	}

	@Override
	public void setJenkinsServerCount(int jenkinsServerCount) {
		_jenkinsServerCount = jenkinsServerCount;
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_jenkinsServerCount = jsonObject.getInt("jenkinsServerCount");
		_name = jsonObject.getString("name");
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void update() {
		Set<JenkinsServerEntity> jenkinsServerEntities =
			getJenkinsServerEntities();

		setJenkinsServerCount(jenkinsServerEntities.size());
	}

	protected BaseJenkinsCohortEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private int _jenkinsServerCount;
	private String _name;

}