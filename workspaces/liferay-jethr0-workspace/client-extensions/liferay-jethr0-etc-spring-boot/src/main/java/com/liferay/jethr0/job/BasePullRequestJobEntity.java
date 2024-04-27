/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.git.pullrequest.GitPullRequestEntity;
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
	public GitPullRequestEntity getGitPullRequestEntity() {
		return _gitPullRequestEntity;
	}

	@Override
	public long getGitPullRequestEntityId() {
		return _gitPullRequestEntityId;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"r_gitPullRequestToJobs_c_gitPullRequestId",
			getGitPullRequestEntityId());

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
	public void setGitPullRequestEntity(
		GitPullRequestEntity gitPullRequestEntity) {

		_gitPullRequestEntity = gitPullRequestEntity;

		if (_gitPullRequestEntity == null) {
			_gitPullRequestEntityId = 0;
		}
		else {
			_gitPullRequestEntityId = _gitPullRequestEntity.getId();
		}
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_gitPullRequestEntityId = jsonObject.optLong(
			"r_gitPullRequestToJobs_c_gitPullRequestId");
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

	private GitPullRequestEntity _gitPullRequestEntity;
	private long _gitPullRequestEntityId;
	private long _number;
	private String _receiverUserName;
	private String _repositoryName;

}