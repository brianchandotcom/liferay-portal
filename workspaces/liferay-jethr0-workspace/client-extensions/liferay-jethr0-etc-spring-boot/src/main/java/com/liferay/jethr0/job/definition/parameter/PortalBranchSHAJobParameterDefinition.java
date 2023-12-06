/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

/**
 * @author Michael Hashimoto
 */
public class PortalBranchSHAJobParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "portalBranchSHA";
	}

	@Override
	public String getLabel() {
		return "Portal Branch SHA";
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
		return "e.g. 617eb3201c2ad33025bfe6a3989ba90c268431ba";
	}

	@Override
	public String getValueRegex() {
		return "[a-f0-9]+";
	}

}