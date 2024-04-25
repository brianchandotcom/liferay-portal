/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.git.pull.GitPullEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePullRequestJobEntity
	extends BaseJobEntity implements PullRequestJobEntity {

	@Override
	public GitPullEntity getGitPullEntity() {
		return _gitPullEntity;
	}

	@Override
	public long getGitPullEntityId() {
		return _gitPullEntityId;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("r_gitPullToJobs_c_gitPullId", getGitPullEntityId());

		return jsonObject;
	}

	@Override
	public long getNumber() {
		if (_number > 0) {
			return _number;
		}

		Matcher matcher = _pullRequestURLPattern.matcher(
			String.valueOf(getPullRequestURL()));

		if (matcher.find()) {
			_number = Long.valueOf(matcher.group("number"));

			return _number;
		}

		return -1;
	}

	@Override
	public String getOriginName() {
		return getParameterValue("originName");
	}

	@Override
	public URL getPullRequestURL() {
		return getParameterValueURL("pullRequestURL");
	}

	@Override
	public String getReceiverUserName() {
		if (!StringUtil.isNullOrEmpty(_receiverUserName)) {
			return _receiverUserName;
		}

		Matcher matcher = _pullRequestURLPattern.matcher(
			String.valueOf(getPullRequestURL()));

		if (matcher.find()) {
			_receiverUserName = matcher.group("receiverUserName");

			return _receiverUserName;
		}

		return null;
	}

	@Override
	public String getRepositoryName() {
		if (!StringUtil.isNullOrEmpty(_repositoryName)) {
			return _repositoryName;
		}

		Matcher matcher = _pullRequestURLPattern.matcher(
			String.valueOf(getPullRequestURL()));

		if (matcher.find()) {
			_repositoryName = matcher.group("repositoryName");

			return _repositoryName;
		}

		return null;
	}

	@Override
	public String getSenderBranchName() {
		return getParameterValue("senderBranchName");
	}

	@Override
	public String getSenderBranchSHA() {
		return getParameterValue("senderBranchSHA");
	}

	@Override
	public String getSenderUserName() {
		return getParameterValue("senderUserName");
	}

	@Override
	public String getTestSuiteName() {
		return getParameterValue("testSuiteName");
	}

	@Override
	public String getUpstreamBranchName() {
		return getParameterValue("upstreamBranchName");
	}

	@Override
	public String getUpstreamBranchSHA() {
		return getParameterValue("upstreamBranchSHA");
	}

	@Override
	public void setGitPullEntity(GitPullEntity gitPullEntity) {
		_gitPullEntity = gitPullEntity;

		if (_gitPullEntity == null) {
			_gitPullEntityId = 0;
		}
		else {
			_gitPullEntityId = _gitPullEntity.getId();
		}
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_gitPullEntityId = jsonObject.optLong("r_gitPullToJobs_c_gitPullId");
	}

	@Override
	public void setOriginName(String originName) {
		setParameterValue("originName", originName);
	}

	@Override
	public void setPullRequestURL(URL pullRequestURL) {
		setParameterValueURL("pullRequestURL", pullRequestURL);
	}

	@Override
	public void setSenderBranchName(String senderBranchName) {
		setParameterValue("senderBranchName", senderBranchName);
	}

	@Override
	public void setSenderBranchSHA(String senderBranchSHA) {
		setParameterValue("senderBranchSHA", senderBranchSHA);
	}

	@Override
	public void setSenderUserName(String senderUserName) {
		setParameterValue("senderUserName", senderUserName);
	}

	@Override
	public void setTestSuiteName(String testSuiteName) {
		setParameterValue("testSuiteName", testSuiteName);
	}

	@Override
	public void setUpstreamBranchName(String upstreamBranchName) {
		setParameterValue("upstreamBranchName", upstreamBranchName);
	}

	@Override
	public void setUpstreamBranchSHA(String upstreamBranchSHA) {
		setParameterValue("upstreamBranchSHA", upstreamBranchSHA);
	}

	protected BasePullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private static final Pattern _pullRequestURLPattern = Pattern.compile(
		StringUtil.combine(
			"https://github.com/(?<receiverUserName>[^/]+)/",
			"(?<repositoryName>[^/]+)/pull/(?<number>\\d+)"));

	private GitPullEntity _gitPullEntity;
	private long _gitPullEntityId;
	private long _number;
	private String _receiverUserName;
	private String _repositoryName;

}