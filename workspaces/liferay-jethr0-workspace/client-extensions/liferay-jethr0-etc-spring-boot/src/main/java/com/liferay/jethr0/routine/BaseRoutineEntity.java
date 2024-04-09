/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseRoutineEntity
	extends BaseEntity implements RoutineEntity {

	@Override
	public String getCron() {
		return _cron;
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
			"cron", getCron()
		).put(
			"jobName", getJobName()
		).put(
			"jobParameters", String.valueOf(_getJobParametersJSONObject())
		).put(
			"jobPriority", getJobPriority()
		).put(
			"jobType", jobType.getJSONObject()
		).put(
			"name", getName()
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
	public void setCron(String cron) {
		_cron = cron;
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
			JSONObject jobParametersJSONObject = new JSONObject(jobParameters);

			for (String key : jobParametersJSONObject.keySet()) {
				_jobParameters.put(key, jobParametersJSONObject.getString(key));
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

	private JSONObject _getJobParametersJSONObject() {
		JSONObject jobParametersJSONObject = new JSONObject();

		if (_jobParameters.isEmpty()) {
			return jobParametersJSONObject;
		}

		Set<String> jobParameterNames = new TreeSet<>(_jobParameters.keySet());

		for (String jobParameterName : jobParameterNames) {
			jobParametersJSONObject.put(
				jobParameterName, _jobParameters.get(jobParameterName));
		}

		return jobParametersJSONObject;
	}

	private static final Log _log = LogFactory.getLog(BaseRoutineEntity.class);

	private String _cron;
	private String _jobName;
	private Map<String, String> _jobParameters;
	private int _jobPriority;
	private JobEntity.Type _jobType;
	private String _name;
	private Type _type;

}