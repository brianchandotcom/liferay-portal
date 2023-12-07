/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

import com.liferay.jethr0.util.StringUtil;

/**
 * @author Michael Hashimoto
 */
public class PortalFixpackReleaseURLJobParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "portalFixpackReleaseURL";
	}

	@Override
	public String getLabel() {
		return "Portal Fixpack Release URL";
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
			"e.g. http://mirrors/files.liferay.com/private/ee/fix-packs/7.3.10",
			"/dxp/liferay-fix-pack-dxp-1-7310.zip");
	}

	@Override
	public String getValueRegex() {
		return "https?://.+";
	}

}