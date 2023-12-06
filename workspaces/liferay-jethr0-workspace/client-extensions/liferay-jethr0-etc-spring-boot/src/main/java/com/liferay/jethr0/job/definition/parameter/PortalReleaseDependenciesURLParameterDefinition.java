/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

import com.liferay.jethr0.util.StringUtil;

/**
 * @author Michael Hashimoto
 */
public class PortalReleaseDependenciesURLParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "portalReleaseDependenciesURL";
	}

	@Override
	public String getLabel() {
		return "Portal Release Dependencies URL";
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
			"e.g. http://mirrors/files.liferay.com/private/ee/portal/7.0.10.17",
			"/liferay-dxp-digital-enterprise-dependencies-7.0.10.17-sp17.zip");
	}

	@Override
	public String getValueRegex() {
		return "https?://.+";
	}

}