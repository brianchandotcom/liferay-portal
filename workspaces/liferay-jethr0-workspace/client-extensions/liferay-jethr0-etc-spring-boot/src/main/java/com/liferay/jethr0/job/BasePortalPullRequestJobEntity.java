/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

	private String _originName;
	private String _senderBranchName;
	private String _senderBranchSHA;
	private String _senderUserName;
	private String _upstreamBranchName;
	private String _upstreamBranchSHA;

}