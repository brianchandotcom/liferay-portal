/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.testsuite;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.job.JobEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTestSuiteEntity extends BaseEntity implements TestSuiteEntity {

	@Override
	public void addJobEntities(Set<JobEntity> jobEntities) {
		_jobEntities.addAll(jobEntities);
	}

	@Override
	public void addJobEntity(JobEntity jobEntity) {
		addJobEntities(Collections.singleton(jobEntity));
	}

	@Override
	public Set<JobEntity> getJobEntities() {
		return _jobEntities;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("name", getName());

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void removeJobEntities(Set<JobEntity> jobEntities) {
		_jobEntities.removeAll(jobEntities);
	}

	@Override
	public void removeJobEntity(JobEntity jobEntity) {
		_jobEntities.remove(jobEntity);
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	protected BaseTestSuiteEntity(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.getString("name");
	}

	private final Set<JobEntity> _jobEntities = new HashSet<>();
	private String _name;

}