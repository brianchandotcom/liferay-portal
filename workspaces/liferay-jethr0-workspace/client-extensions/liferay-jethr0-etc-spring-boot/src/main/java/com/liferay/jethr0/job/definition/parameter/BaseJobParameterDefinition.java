/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJobParameterDefinition
	implements JobParameterDefinition {

	@Override
	public int compareTo(JobParameterDefinition jobParameterDefinition) {
		String label = getLabel();

		return label.compareTo(jobParameterDefinition.getLabel());
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"key", getKey()
		).put(
			"label", getLabel()
		).put(
			"type", getType()
		).put(
			"valueDefault", getValueDefault()
		).put(
			"valueDescription", getValueDescription()
		).put(
			"valueRegex", getValueRegex()
		);

		return jsonObject;
	}

}