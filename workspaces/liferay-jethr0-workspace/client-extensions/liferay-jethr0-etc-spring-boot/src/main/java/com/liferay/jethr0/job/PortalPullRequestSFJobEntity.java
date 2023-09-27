/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.parameter.BuildParameterEntity;
import com.liferay.jethr0.util.StringUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.net.URL;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalPullRequestSFJobEntity extends BaseJobEntity {

	@Override
	public List<JSONObject> getInitialBuildJSONObjects() {
		return Collections.singletonList(_getInitialBuildJSONObject());
	}

	public URL getJenkinsGitHubURL() {
		if (_jenkinsGitHubURL != null) {
			return _jenkinsGitHubURL;
		}

		String jenkinsGitHubBranchName = _getJenkinsGitHubBranchName();
		String jenkinsGitHubBranchUserName = _getJenkinsGitHubBranchUserName();

		if (StringUtil.isNullOrEmpty(jenkinsGitHubBranchName) ||
			StringUtil.isNullOrEmpty(jenkinsGitHubBranchUserName)) {

			return null;
		}

		_jenkinsGitHubURL = StringUtil.toURL(
			StringUtil.combine(
				"https://github.com/", jenkinsGitHubBranchUserName,
				"/liferay-jenkins-ee/tree/", jenkinsGitHubBranchName));

		return _jenkinsGitHubURL;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"jenkinsGitHubURL", getJenkinsGitHubURL()
		).put(
			"pullRequestURL", getPullRequestURL()
		);

		return jsonObject;
	}

	public URL getPullRequestURL() {
		if (_pullRequestURL != null) {
			return _pullRequestURL;
		}

		for (BuildEntity initialBuildEntity : getInitialBuildEntities()) {
			BuildParameterEntity buildParameterEntity =
				initialBuildEntity.getBuildParameterEntity("PULL_REQUEST_URL");

			if (buildParameterEntity != null) {
				_pullRequestURL = StringUtil.toURL(
					buildParameterEntity.getValue());

				return _pullRequestURL;
			}
		}

		return null;
	}

	protected PortalPullRequestSFJobEntity(JSONObject jsonObject) {
		super(jsonObject);

		String pullRequestURLString = jsonObject.optString("pullRequestURL");

		if (!StringUtil.isNullOrEmpty(pullRequestURLString)) {
			_pullRequestURL = StringUtil.toURL(pullRequestURLString);
		}

		String jenkinsGitHubURLString = jsonObject.optString(
			"jenkinsGitHubURL");

		if (!StringUtil.isNullOrEmpty(jenkinsGitHubURLString)) {
			_jenkinsGitHubURL = StringUtil.toURL(jenkinsGitHubURLString);
		}
	}

	private String _getBuildParameterValue(String buildParameterName) {
		for (BuildEntity initialBuildEntity : getInitialBuildEntities()) {
			BuildParameterEntity buildParameterEntity =
				initialBuildEntity.getBuildParameterEntity(buildParameterName);

			if (buildParameterEntity == null) {
				continue;
			}

			String buildParameterValue = buildParameterEntity.getValue();

			if (StringUtil.isNullOrEmpty(buildParameterValue)) {
				continue;
			}

			return buildParameterEntity.getValue();
		}

		return null;
	}

	private JSONObject _getInitialBuildJSONObject() {
		JSONObject initialBuildJSONObject = new JSONObject();

		initialBuildJSONObject.put(
			"buildParameters", _getInitialBuildParametersJSONArray()
		).put(
			"initialBuild", true
		).put(
			"jenkinsJobName", _JENKINS_JOB_NAME
		).put(
			"name", _JENKINS_JOB_NAME
		).put(
			"state", BuildEntity.State.OPENED
		);

		return initialBuildJSONObject;
	}

	private Map<String, String> _getInitialBuildParameters() {
		return HashMapBuilder.put(
			"BUILD_PRIORITY", _BUILD_PRIORITY
		).put(
			"JENKINS_GITHUB_BRANCH_NAME",
			() -> {
				String jenkinsGitHubBranchName = _getJenkinsGitHubBranchName();

				if (!StringUtil.isNullOrEmpty(jenkinsGitHubBranchName)) {
					return jenkinsGitHubBranchName;
				}

				return null;
			}
		).put(
			"JENKINS_GITHUB_BRANCH_USERNAME",
			() -> {
				String jenkinsGitHubBranchUserName =
					_getJenkinsGitHubBranchUserName();

				if (!StringUtil.isNullOrEmpty(jenkinsGitHubBranchUserName)) {
					return jenkinsGitHubBranchUserName;
				}

				return null;
			}
		).put(
			"PULL_REQUEST_URL", String.valueOf(getPullRequestURL())
		).build();
	}

	private JSONArray _getInitialBuildParametersJSONArray() {
		JSONArray initialBuildParametersJSONArray = new JSONArray();

		Map<String, String> initialBuildParameters =
			_getInitialBuildParameters();

		for (Map.Entry<String, String> initialBuildParameter :
				initialBuildParameters.entrySet()) {

			JSONObject initialBuildParameterJSONObject = new JSONObject();

			initialBuildParameterJSONObject.put(
				"name", initialBuildParameter.getKey()
			).put(
				"value", initialBuildParameter.getValue()
			);

			initialBuildParametersJSONArray.put(
				initialBuildParameterJSONObject);
		}

		return initialBuildParametersJSONArray;
	}

	private String _getJenkinsGitHubBranchName() {
		if (_jenkinsGitHubBranchName != null) {
			return _jenkinsGitHubBranchName;
		}

		if (_jenkinsGitHubURL != null) {
			Matcher matcher = _jenkinsGitHubURLPattern.matcher(
				String.valueOf(_jenkinsGitHubURL));

			if (matcher.find()) {
				_jenkinsGitHubBranchName = matcher.group("branchName");
			}

			return _jenkinsGitHubBranchName;
		}

		_jenkinsGitHubBranchName = _getBuildParameterValue(
			"JENKINS_GITHUB_BRANCH_NAME");

		return _jenkinsGitHubBranchName;
	}

	private String _getJenkinsGitHubBranchUserName() {
		if (_jenkinsGitHubBranchUserName != null) {
			return _jenkinsGitHubBranchUserName;
		}

		if (_jenkinsGitHubURL != null) {
			Matcher matcher = _jenkinsGitHubURLPattern.matcher(
				String.valueOf(_jenkinsGitHubURL));

			if (matcher.find()) {
				_jenkinsGitHubBranchUserName = matcher.group("branchUserName");
			}

			return _jenkinsGitHubBranchUserName;
		}

		_jenkinsGitHubBranchUserName = _getBuildParameterValue(
			"JENKINS_GITHUB_BRANCH_USERNAME");

		return _jenkinsGitHubBranchUserName;
	}

	private static final String _BUILD_PRIORITY = "4";

	private static final String _JENKINS_JOB_NAME = "test-portal-source-format";

	private static final Pattern _jenkinsGitHubURLPattern = Pattern.compile(
		"https://github.com/(?<branchUserName>[^/]+)/liferay-jenkins-ee/tree/" +
			"(?<branchName>[^/]+)");

	private String _jenkinsGitHubBranchName;
	private String _jenkinsGitHubBranchUserName;
	private URL _jenkinsGitHubURL;
	private URL _pullRequestURL;

}