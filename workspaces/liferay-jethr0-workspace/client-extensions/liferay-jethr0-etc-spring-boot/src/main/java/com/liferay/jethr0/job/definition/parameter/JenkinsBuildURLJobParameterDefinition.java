/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

import com.liferay.jethr0.util.StringUtil;

/**
 * @author Michael Hashimoto
 */
public class JenkinsBuildURLJobParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "jenkinsBuildURL";
	}

	@Override
	public String getLabel() {
		return "Jenkins Build URL";
	}

	@Override
	public JobParameterDefinition.Type getType() {
		return JobParameterDefinition.Type.URL;
	}

	@Override
	public String getValueDefault() {
		return null;
	}

	@Override
	public String getValueDescription() {
		return StringUtil.combine(
			"e.g. https://test-1-1.liferay.com/job",
			"/test-portal-acceptance-pullrequest(master)/1/");
	}

	@Override
	public String getValueRegex() {
		return "https?://test-\\d+-\\d+(.liferay.com)?/job/[^/]+/\\d+/?";
	}

}