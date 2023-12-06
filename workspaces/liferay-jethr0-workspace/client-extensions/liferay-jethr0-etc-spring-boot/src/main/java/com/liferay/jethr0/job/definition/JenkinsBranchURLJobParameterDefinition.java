/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.util.StringUtil;

/**
 * @author Michael Hashimoto
 */
public class JenkinsBranchURLJobParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "jenkinsBranchURL";
	}

	@Override
	public String getLabel() {
		return "Jenkins Branch URL";
	}

	@Override
	public Type getType() {
		return Type.JENKINS_BRANCH_URL;
	}

	@Override
	public String getValueDefault() {
		return null;
	}

	@Override
	public String getValueDescription() {
		return StringUtil.combine(
			"e.g. https://github.com/[user]/liferay-jenkins-ee/",
			"tree/[branch]");
	}

	@Override
	public String getValueRegex() {
		return "https://github.com/[^/]+/liferay-jenkins-ee/tree/[^/]+";
	}

}