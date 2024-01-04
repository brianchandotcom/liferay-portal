/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

import com.liferay.jethr0.util.StringUtil;

/**
 * @author Michael Hashimoto
 */
public class PortalCherryPickSHAsJobParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "portalCherryPickSHAs";
	}

	@Override
	public String getLabel() {
		return "Portal Cherry Pick SHAs";
	}

	@Override
	public Type getType() {
		return Type.STRING;
	}

	@Override
	public String getValueDefault() {
		return null;
	}

	@Override
	public String getValueDescription() {
		return StringUtil.combine(
			"e.g. 617eb3201c2ad33025bfe6a3989ba90c268431ba,",
			"b2003beba572c86324f221650b696eaa431903f9,",
			"eec06d83124d6f6b47fac5fcca9464b9bb3fe877");
	}

	@Override
	public String getValueRegex() {
		return null;
	}

}