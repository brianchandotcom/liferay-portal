/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SFPortalPullRequestJobEntity
	extends BasePortalPullRequestJobEntity {

	public static List<ParameterDefinition> getParameterDefinitions() {
		return Arrays.asList(
			JENKINS_GITHUB_URL_PARAMETER_DEFINITION,
			PORTAL_PULL_REQUEST_PARAMETER_DEFINITION,
			TEST_SUITE_NAME_PARAMETER_DEFINITION);
	}

	@Override
	public URL getPortalPullRequestURL() {
		if (_portalPullRequestURL != null) {
			return _portalPullRequestURL;
		}

		_portalPullRequestURL = super.getPortalPullRequestURL();

		if (_portalPullRequestURL != null) {
			return _portalPullRequestURL;
		}

		for (BuildEntity initialBuildEntity : getInitialBuildEntities()) {
			String pullRequestURL = initialBuildEntity.getBuildParameterValue(
				"PULL_REQUEST_URL");

			if (!StringUtil.isNullOrEmpty(pullRequestURL)) {
				_portalPullRequestURL = StringUtil.toURL(pullRequestURL);

				return _portalPullRequestURL;
			}
		}

		return null;
	}

	@Override
	public String getTestSuiteName() {
		String testSuiteName = super.getTestSuiteName();

		if (StringUtil.isNullOrEmpty(testSuiteName)) {
			testSuiteName = "sf";

			setTestSuiteName(testSuiteName);
		}

		return testSuiteName;
	}

	protected SFPortalPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParamaters =
			super.getInitialBuildParameters();

		initialBuildParamaters.put(
			"PULL_REQUEST_URL", String.valueOf(getPortalPullRequestURL()));

		return initialBuildParamaters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "test-portal-source-format";
	}

	private URL _portalPullRequestURL;

}