/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

/**
 * @author Michael Hashimoto
 */
public class PortalBuildProfileJobParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "portalBuildProfile";
	}

	@Override
	public String getLabel() {
		return "Portal Build Profile";
	}

	@Override
	public Type getType() {
		return Type.STRING;
	}

	@Override
	public String getValueDefault() {
		return "dxp";
	}

	@Override
	public String getValueDescription() {
		return "Insert your Build Profile here (i.e. 'dxp' or 'portal')";
	}

	@Override
	public String getValueRegex() {
		return "(dxp|portal)";
	}

}