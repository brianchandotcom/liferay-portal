/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.definition.parameter.JobParameterDefinition;

import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJobDefinition implements JobDefinition {

	@Override
	public int compareTo(JobDefinition jobDefinition) {
		String label = getLabel();

		return label.compareTo(jobDefinition.getLabel());
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"key", getKey()
		).put(
			"label", getLabel()
		).put(
			"parameterDefinitions", _getJobParameterDefinitionsJSONArray()
		);

		return jsonObject;
	}

	@Override
	public String getKey() {
		return _jobEntityType.getKey();
	}

	@Override
	public String getLabel() {
		return _jobEntityType.getName();
	}

	protected BaseJobDefinition(JobEntity.Type jobEntityType) {
		_jobEntityType = jobEntityType;
	}

	private JSONArray _getJobParameterDefinitionsJSONArray() {
		JSONArray jobParameterDefinitionsJSONArray = new JSONArray();

		for (JobParameterDefinition jobParameterDefinition :
				new TreeSet<>(getJobParameterDefinitions())) {

			jobParameterDefinitionsJSONArray.put(
				jobParameterDefinition.getJSONObject());
		}

		return jobParameterDefinitionsJSONArray;
	}

	private final JobEntity.Type _jobEntityType;

}