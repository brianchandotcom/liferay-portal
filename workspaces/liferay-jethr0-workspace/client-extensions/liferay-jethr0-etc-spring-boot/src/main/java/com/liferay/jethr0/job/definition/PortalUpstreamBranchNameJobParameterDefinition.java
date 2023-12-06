/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

/**
 * @author Michael Hashimoto
 */
public class PortalUpstreamBranchNameJobParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "portalUpstreamBranchName";
	}

	@Override
	public String getLabel() {
		return "Portal Upstream Branch Name";
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
		return "Insert your Portal Upstream Branch Name here";
	}

	@Override
	public String getValueRegex() {
		return null;
	}

}