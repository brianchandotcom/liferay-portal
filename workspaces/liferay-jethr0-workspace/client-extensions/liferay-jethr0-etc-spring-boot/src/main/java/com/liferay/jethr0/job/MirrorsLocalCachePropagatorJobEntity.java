/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.net.URL;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class MirrorsLocalCachePropagatorJobEntity extends BaseJobEntity {

	public URL getMirrorsURL() {
		return getParameterValueURL("mirrorsURL");
	}

	public void setMirrorsURL(URL mirrorsURL) {
		setParameterValueURL("mirrorsURL", mirrorsURL);
	}

	protected MirrorsLocalCachePropagatorJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"MIRRORS_URL", String.valueOf(getMirrorsURL()));

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "mirrors-local-cache-propagator";
	}

}