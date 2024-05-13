/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseRoutineEntity
	extends BaseEntity implements RoutineEntity {

	@Override
	public void addJobEntities(Set<JobEntity> jobEntities) {
		addRelatedEntities(jobEntities);
	}

	@Override
	public void addJobEntity(JobEntity jobEntity) {
		addRelatedEntity(jobEntity);
	}

	@Override
	public Boolean getEnabled() {
		return _enabled;
	}

	@Override
	public URL getEntityURL() {
		return StringUtil.toURL(
			StringUtil.combine(
				Jethr0ContextUtil.getLiferayPortalURL(), "/#/routines/",
				getId()));
	}

	@Override
	public GitBranchEntity getGitBranchEntity() {
		return _gitBranchEntity;
	}

	@Override
	public long getGitBranchEntityId() {
		return _gitBranchEntityId;
	}

	@Override
	public Set<JobEntity> getJobEntities() {
		return getRelatedEntities(JobEntity.class);
	}

	@Override
	public String getJobName() {
		return _jobName;
	}

	@Override
	public Map<String, String> getJobParameters() {
		return _jobParameters;
	}

	@Override
	public String getJobParameterValue(String name) {
		return _jobParameters.get(name);
	}

	@Override
	public int getJobPriority() {
		return _jobPriority;
	}

	@Override
	public JobEntity.Type getJobType() {
		return _jobType;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		JobEntity.Type jobType = getJobType();
		Type type = getType();

		jsonObject.put(
			"enabled", getEnabled()
		).put(
			"jobName", getJobName()
		).put(
			"jobParameters", String.valueOf(_getJobParametersJSONArray())
		).put(
			"jobPriority", getJobPriority()
		).put(
			"jobType", jobType.getJSONObject()
		).put(
			"name", getName()
		).put(
			"r_gitBranchToRoutines_c_gitBranchId", _gitBranchEntityId
		).put(
			"type", type.getJSONObject()
		);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Type getType() {
		return _type;
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
	public void setEnabled(Boolean enabled) {
		_enabled = enabled;
	}

	@Override
	public void setGitBranchEntity(GitBranchEntity gitBranchEntity) {
		_gitBranchEntity = gitBranchEntity;

		if (_gitBranchEntity != null) {
			_gitBranchEntityId = _gitBranchEntity.getId();
		}
		else {
			_gitBranchEntityId = 0;
		}
	}

	@Override
	public void setJobName(String jobName) {
		_jobName = jobName;
	}

	@Override
	public void setJobParameterValue(String name, String value) {
		_jobParameters.put(name, value);
	}

	@Override
	public void setJobPriority(int jobPriority) {
		_jobPriority = jobPriority;
	}

	@Override
	public void setJobType(JobEntity.Type jobType) {
		_jobType = jobType;
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_gitBranchEntityId = jsonObject.optLong(
			"r_gitBranchToRoutines_c_gitBranchId");
		_name = jsonObject.getString("name");
		_jobName = jsonObject.getString("jobName");
		_jobParameters = new HashMap<>();
		_jobPriority = jsonObject.optInt("jobPriority");
		_jobType = JobEntity.Type.get(jsonObject.get("jobType"));
		_type = Type.get(jsonObject.get("type"));

		String jobParameters = jsonObject.getString("jobParameters");

		if (StringUtil.isNullOrEmpty(jobParameters)) {
			return;
		}

		try {
			JSONArray jobParametersJSONArray = new JSONArray(jobParameters);

			for (int i = 0; i < jobParametersJSONArray.length(); i++) {
				JSONObject jobParameterJSONObject =
					jobParametersJSONArray.getJSONObject(i);

				if (!jobParameterJSONObject.has("key")) {
					continue;
				}

				_jobParameters.put(
					jobParameterJSONObject.getString("key"),
					jobParameterJSONObject.getString("value"));
			}
		}
		catch (JSONException jsonException) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsonException);
			}
		}
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setType(Type type) {
		_type = type;
	}

	protected BaseRoutineEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private JSONArray _getJobParametersJSONArray() {
		JSONArray jobParametersJSONArray = new JSONArray();

		if (_jobParameters.isEmpty()) {
			return jobParametersJSONArray;
		}

		Set<String> jobParameterNames = new TreeSet<>(_jobParameters.keySet());

		for (String jobParameterName : jobParameterNames) {
			JSONObject jobParameterJSONObject = new JSONObject();

			jobParameterJSONObject.put(
				"key", jobParameterName
			).put(
				"value", _jobParameters.get(jobParameterName)
			);

			jobParametersJSONArray.put(jobParameterJSONObject);
		}

		return jobParametersJSONArray;
	}

	private static final Log _log = LogFactory.getLog(BaseRoutineEntity.class);

	private Boolean _enabled;
	private GitBranchEntity _gitBranchEntity;
	private long _gitBranchEntityId;
	private String _jobName;
	private Map<String, String> _jobParameters;
	private int _jobPriority;
	private JobEntity.Type _jobType;
	private String _name;
	private Type _type;

}