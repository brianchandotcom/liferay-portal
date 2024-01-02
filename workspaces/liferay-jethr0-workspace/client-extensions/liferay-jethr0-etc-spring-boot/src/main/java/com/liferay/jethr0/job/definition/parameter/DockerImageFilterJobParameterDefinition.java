/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

/**
 * @author Michael Hashimoto
 */
public class DockerImageFilterJobParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "dockerImageFilter";
	}

	@Override
	public String getLabel() {
		return "Docker Image Filter";
	}

	@Override
	public Type getType() {
		return Type.STRING;
	}

	@Override
	public String getValueDefault() {
		return "snapshot";
	}

	@Override
	public String getValueDescription() {
		return "Insert your Docker image filter here";
	}

	@Override
	public String getValueRegex() {
		return null;
	}

}