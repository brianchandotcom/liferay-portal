/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

/**
 * @author Michael Hashimoto
 */
public class PortalReleaseVersionParameterDefinition
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
		return "e.g. 2023.q4.0, 7.4.13-u106";
	}

	@Override
	public String getValueRegex() {
		return "https?://.+";
	}

}