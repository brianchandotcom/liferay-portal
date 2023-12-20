/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

/**
 * @author Michael Hashimoto
 */
public class ForwardForceJobParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "forwardForce";
	}

	@Override
	public String getLabel() {
		return "Forward Force";
	}

	@Override
	public JobParameterDefinition.Type getType() {
		return JobParameterDefinition.Type.STRING;
	}

	@Override
	public String getValueDefault() {
		return "false";
	}

	@Override
	public String getValueDescription() {
		return "Insert whether to force forward the pull request here";
	}

	@Override
	public String getValueRegex() {
		return "(false|true)";
	}

}