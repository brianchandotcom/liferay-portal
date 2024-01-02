/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PublishPortalDockerImageJobEntity extends BaseJobEntity {

	public String getDockerImageFilter() {
		return getParameterValue("dockerImageFilter");
	}

	public void setDockerImageFilter(String dockerImageFilter) {
		setParameterValue("dockerImageFilter", dockerImageFilter);
	}

	protected PublishPortalDockerImageJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"LIFERAY_DOCKER_IMAGE_FILTER", getDockerImageFilter());

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "publish-portal-docker-image";
	}

}