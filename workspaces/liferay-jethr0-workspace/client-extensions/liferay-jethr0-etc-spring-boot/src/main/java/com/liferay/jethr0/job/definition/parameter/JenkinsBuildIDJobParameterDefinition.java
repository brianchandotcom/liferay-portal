/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

/**
 * @author Michael Hashimoto
 */
public class JenkinsBuildIDJobParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "jenkinsBuildID";
	}

	@Override
	public String getLabel() {
		return "Jenkins Build ID";
	}

	@Override
	public JobParameterDefinition.Type getType() {
		return JobParameterDefinition.Type.STRING;
	}

	@Override
	public String getValueDefault() {
		return null;
	}

	@Override
	public String getValueDescription() {
		return "e.g. 5017401_7441";
	}

	@Override
	public String getValueRegex() {
		return "\\d+_\\d+";
	}

}