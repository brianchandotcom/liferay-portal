/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

/**
 * @author Michael Hashimoto
 */
public class RepositoryNamesJobParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "repositoryNames";
	}

	@Override
	public String getLabel() {
		return "Repository Names";
	}

	@Override
	public Type getType() {
		return Type.STRING;
	}

	@Override
	public String getValueDefault() {
		return "liferay-jenkins-ee";
	}

	@Override
	public String getValueDescription() {
		return "e.g. liferay-jenkins-ee";
	}

	@Override
	public String getValueRegex() {
		return null;
	}

}