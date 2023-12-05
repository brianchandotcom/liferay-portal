/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.bui1d.BuildEntity;
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
public abstract class BasePortalPullRequestJobEntity
	extends BaseJobEntity implements PortalPullRequestJobEntity {

	@Override
	public List<JSONObject> getInitialBuildJSONObjects() {
		return Collections.singletonList(_getInitialBuildJSONObject());
	}

	@Override
	public URL getJenkinsGitHubURL() {
		String jenkinsGitHubURL = getParameterValue("jenkinsGitHubURL");

		if (StringUtil.isNullOrEmpty(jenkinsGitHubURL)) {
			return null;
		}

		return StringUtil.toURL(jenkinsGitHubURL);
	}

	@Override
	public String getOriginName() {
		return _originName;
	}

	@Override
	public URL getPortalPullRequestURL() {
		String portalPullRequestURL = getParameterValue("portalPullRequestURL");

		if (StringUtil.isNullOrEmpty(portalPullRequestURL)) {
			return null;
		}

		return StringUtil.toURL(portalPullRequestURL);
	}

	@Override
	public String getSenderBranchName() {
		return _senderBranchName;
	}

	@Override
	public String getSenderBranchSHA() {
		return _senderBranchSHA;
	}

	public String getSenderUserName() {
		return _senderUserName;
	}

	@Override
	public String getTestSuiteName() {
		return getParameterValue("testSuiteName");
	}

	@Override
	public String getUpstreamBranchName() {
		return _upstreamBranchName;
	}

	@Override
	public String getUpstreamBranchSHA() {
		return _upstreamBranchSHA;
	}

	@Override
	public void setJenkinsGitHubURL(URL jenkinsGitHubURL) {
		setParameterValue("jenkinsGitHubURL", String.valueOf(jenkinsGitHubURL));
	}

	@Override
	public void setOriginName(String originName) {
		_originName = originName;
	}

	@Override
	public void setPortalPullRequestURL(URL portalPullRequestURL) {
		setParameterValue(
			"portalPullRequestURL", String.valueOf(portalPullRequestURL));
	}

	@Override
	public void setSenderBranchName(String senderBranchName) {
		_senderBranchName = senderBranchName;
	}

	public void setSenderBranchSHA(String senderBranchSHA) {
		_senderBranchSHA = senderBranchSHA;
	}

	public void setSenderUserName(String senderUserName) {
		_senderUserName = senderUserName;
	}

	@Override
	public void setTestSuiteName(String testSuiteName) {
		setParameterValue("testSuiteName", testSuiteName);
	}

	public void setUpstreamBranchName(String upstreamBranchName) {
		_upstreamBranchName = upstreamBranchName;
	}

	public void setUpstreamBranchSHA(String upstreamBranchSHA) {
		_upstreamBranchSHA = upstreamBranchSHA;
	}

	protected BasePortalPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	protected String getBuildParameterValue(String buildParameterName) {
		for (BuildEntity initialBuildEntity : getInitialBuildEntities()) {
			String buildParameterValue =
				initialBuildEntity.getBuildParameterValue(buildParameterName);

			if (StringUtil.isNullOrEmpty(buildParameterValue)) {
				continue;
			}

			return buildParameterValue;
		}

		return null;
	}

	protected Map<String, String> getInitialBuildParameters() {
		return HashMapBuilder.put(
			"BUILD_PRIORITY", _BUILD_PRIORITY
		).put(
			"JENKINS_GITHUB_BRANCH_NAME",
			() -> {
				String jenkinsGitHubBranchName = getJenkinsGitHubBranchName();

				if (!StringUtil.isNullOrEmpty(jenkinsGitHubBranchName)) {
					return jenkinsGitHubBranchName;
				}

				return null;
			}
		).put(
			"JENKINS_GITHUB_BRANCH_USERNAME",
			() -> {
				String jenkinsGitHubBranchUserName =
					getJenkinsGitHubBranchUserName();

				if (!StringUtil.isNullOrEmpty(jenkinsGitHubBranchUserName)) {
					return jenkinsGitHubBranchUserName;
				}

				return null;
			}
		).build();
	}

	protected String getJenkinsGitHubBranchName() {
		if (_jenkinsGitHubBranchName != null) {
			return _jenkinsGitHubBranchName;
		}

		URL jenkinsGitHubURL = getJenkinsGitHubURL();

		if (jenkinsGitHubURL != null) {
			Matcher matcher = _jenkinsGitHubURLPattern.matcher(
				String.valueOf(jenkinsGitHubURL));

			if (matcher.find()) {
				_jenkinsGitHubBranchName = matcher.group("branchName");
			}

			return _jenkinsGitHubBranchName;
		}

		_jenkinsGitHubBranchName = getBuildParameterValue(
			"JENKINS_GITHUB_BRANCH_NAME");

		return _jenkinsGitHubBranchName;
	}

	protected String getJenkinsGitHubBranchUserName() {
		if (_jenkinsGitHubBranchUserName != null) {
			return _jenkinsGitHubBranchUserName;
		}

		URL jenkinsGitHubURL = getJenkinsGitHubURL();

		if (jenkinsGitHubURL != null) {
			Matcher matcher = _jenkinsGitHubURLPattern.matcher(
				String.valueOf(jenkinsGitHubURL));

			if (matcher.find()) {
				_jenkinsGitHubBranchUserName = matcher.group("branchUserName");
			}

			return _jenkinsGitHubBranchUserName;
		}

		_jenkinsGitHubBranchUserName = getBuildParameterValue(
			"JENKINS_GITHUB_BRANCH_USERNAME");

		return _jenkinsGitHubBranchUserName;
	}

	protected abstract String getJenkinsJobName();

	private JSONObject _getInitialBuildJSONObject() {
		JSONObject initialBuildJSONObject = new JSONObject();

		initialBuildJSONObject.put(
			"initialBuild", true
		).put(
			"jenkinsJobName", getJenkinsJobName()
		).put(
			"name", "top-level"
		).put(
			"parameters", String.valueOf(_getInitialBuildParametersJSONArray())
		).put(
			"state", BuildEntity.State.OPENED
		);

		return initialBuildJSONObject;
	}

	private JSONArray _getInitialBuildParametersJSONArray() {
		JSONArray initialBuildParametersJSONArray = new JSONArray();

		Map<String, String> initialBuildParameters =
			getInitialBuildParameters();

		for (Map.Entry<String, String> initialBuildParameter :
				initialBuildParameters.entrySet()) {

			String initialBuildParameterValue =
				initialBuildParameter.getValue();

			if (StringUtil.isNullOrEmpty(initialBuildParameterValue)) {
				continue;
			}

			JSONObject initialBuildParameterJSONObject = new JSONObject();

			initialBuildParameterJSONObject.put(
				"name", initialBuildParameter.getKey()
			).put(
				"value", initialBuildParameterValue
			);

			initialBuildParametersJSONArray.put(
				initialBuildParameterJSONObject);
		}

		return initialBuildParametersJSONArray;
	}

	private static final String _BUILD_PRIORITY = "4";

	private static final Pattern _jenkinsGitHubURLPattern = Pattern.compile(
		"https://github.com/(?<branchUserName>[^/]+)/liferay-jenkins-ee/tree/" +
			"(?<branchName>[^/]+)");

	private String _jenkinsGitHubBranchName;
	private String _jenkinsGitHubBranchUserName;
	private String _originName;
	private String _senderBranchName;
	private String _senderBranchSHA;
	private String _senderUserName;
	private String _upstreamBranchName;
	private String _upstreamBranchSHA;

}