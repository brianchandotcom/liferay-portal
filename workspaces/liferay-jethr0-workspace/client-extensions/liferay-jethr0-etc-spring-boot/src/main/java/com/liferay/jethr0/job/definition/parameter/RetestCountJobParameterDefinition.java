/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

import com.liferay.jethr0.util.StringUtil;

/**
 * @author Michael Hashimoto
 */
public class RetestCountJobParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "retestCount";
	}

	@Override
	public String getLabel() {
		return "Retest Count";
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
		return StringUtil.combine("Insert your retest count here");
	}

	@Override
	public String getValueRegex() {
		return "\\d+";
	}

}