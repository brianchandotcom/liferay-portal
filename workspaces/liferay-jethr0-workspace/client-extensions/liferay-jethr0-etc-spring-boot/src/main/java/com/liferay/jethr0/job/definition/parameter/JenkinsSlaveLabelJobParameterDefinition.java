/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

/**
 * @author Michael Hashimoto
 */
public class JenkinsSlaveLabelJobParameterDefinition
	extends BaseJobParameterDefinition {

	public JenkinsSlaveLabelJobParameterDefinition(String valueDefault) {
		_valueDefault = valueDefault;
	}

	@Override
	public String getKey() {
		return "jenkinsSlaveLabel";
	}

	@Override
	public String getLabel() {
		return "Jenkins Slave Label";
	}

	@Override
	public Type getType() {
		return Type.STRING;
	}

	@Override
	public String getValueDefault() {
		return _valueDefault;
	}

	@Override
	public String getValueDescription() {
		return "e.g. !master";
	}

	@Override
	public String getValueRegex() {
		return null;
	}

	private final String _valueDefault;

}