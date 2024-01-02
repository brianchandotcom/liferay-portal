/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

/**
 * @author Michael Hashimoto
 */
public class CleanUpCommandJobParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "cleanUpCommand";
	}

	@Override
	public String getLabel() {
		return "Clean Up Command";
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
		return "Insert your clean up command here";
	}

	@Override
	public String getValueRegex() {
		return null;
	}

}