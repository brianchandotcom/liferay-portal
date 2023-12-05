/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.pullrequest;

import com.liferay.jethr0.event.github.commit.GitHubCommit;
import com.liferay.jethr0.event.github.repository.GitHubRepository;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitHubPullRequest {

	public GitHubPullRequest(JSONObject jsonObject) {
		_jsonObject = jsonObject;

		JSONObject baseJSONObject = jsonObject.getJSONObject("base");

		_baseBranchName = baseJSONObject.getString("ref");
		_baseGitHubCommit = new GitHubCommit(baseJSONObject);
		_baseGitHubRepository = new GitHubRepository(
			baseJSONObject.getJSONObject("repo"));

		JSONObject headJSONObject = jsonObject.getJSONObject("head");

		_headBranchName = headJSONObject.getString("ref");
		_headGitHubCommit = new GitHubCommit(headJSONObject);
		_headGitHubRepository = new GitHubRepository(
			headJSONObject.getJSONObject("repo"));

		_originGitHubUser = new GitHubUser(
			headJSONObject.getJSONObject("user"));

		_receiverGitHubUser = new GitHubUser(
			baseJSONObject.getJSONObject("user"));

		_senderGitHubUser = new GitHubUser(jsonObject.getJSONObject("user"));
	}

	public String getBaseBranchName() {
		return _baseBranchName;
	}

	public String getBaseBranchSHA() {
		return _baseGitHubCommit.getSHA();
	}

	public String getBaseRepositoryName() {
		return _baseGitHubRepository.getName();
	}

	public String getHeadBranchName() {
		return _headBranchName;
	}

	public String getHeadBranchSHA() {
		return _headGitHubCommit.getSHA();
	}

	public URL getHeadBranchURL() {
		return StringUtil.toURL(
			StringUtil.combine(
				_headGitHubRepository.getHTMLURL(), "/tree/",
				getHeadBranchName()));
	}

	public URL getHTMLURL() {
		return StringUtil.toURL(_jsonObject.getString("html_url"));
	}

	public GitHubUser getOriginGitHubUser() {
		return _originGitHubUser;
	}

	public GitHubUser getReceiverGitHubUser() {
		return _receiverGitHubUser;
	}

	public GitHubUser getSenderGitHubUser() {
		return _senderGitHubUser;
	}

	public URL getUpstreamBranchURL() {
		return StringUtil.toURL(
			StringUtil.combine(
				"https://github.com/liferay/", getBaseRepositoryName(),
				"/tree/", getBaseBranchName()));
	}

	private final String _baseBranchName;
	private final GitHubCommit _baseGitHubCommit;
	private final GitHubRepository _baseGitHubRepository;
	private final String _headBranchName;
	private final GitHubCommit _headGitHubCommit;
	private final GitHubRepository _headGitHubRepository;
	private final JSONObject _jsonObject;
	private final GitHubUser _originGitHubUser;
	private final GitHubUser _receiverGitHubUser;
	private final GitHubUser _senderGitHubUser;

}