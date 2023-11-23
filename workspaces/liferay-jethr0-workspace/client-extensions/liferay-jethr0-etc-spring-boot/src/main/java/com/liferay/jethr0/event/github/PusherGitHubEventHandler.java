/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.commit.GitHubCommit;
import com.liferay.jethr0.event.github.repository.GitHubRepository;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.branch.repository.GitBranchEntityRepository;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PusherGitHubEventHandler extends BaseGitHubEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		GitBranchEntity gitBranchEntity = _getGitBranchEntity();

		if (gitBranchEntity.getType() == GitBranchEntity.Type.UPSTREAM) {
			GitHubCommit headGitHubCommit = _getHeadGitHubCommit();

			gitBranchEntity.setBranchSHA(headGitHubCommit.getSHA());
			gitBranchEntity.setUpstreamBranchSHA(headGitHubCommit.getSHA());

			GitBranchEntityRepository gitBranchEntityRepository =
				getGitBranchEntityRepository();

			gitBranchEntityRepository.update(gitBranchEntity);
		}

		return gitBranchEntity.toString();
	}

	protected PusherGitHubEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	private GitBranchEntity _getGitBranchEntity() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		String refName = messageJSONObject.optString("ref");

		refName = refName.replaceAll(".*/([^/]+)", "$1");

		GitBranchEntityRepository gitBranchEntityRepository =
			getGitBranchEntityRepository();

		GitHubRepository gitHubRepository = getGitHubRepository();

		try {
			return gitBranchEntityRepository.getByURL(
				new URL(gitHubRepository.getHtmlURL() + "/tree/" + refName));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private GitHubCommit _getHeadGitHubCommit() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject headCommitJSONObject = messageJSONObject.optJSONObject(
			"head_commit");

		if (headCommitJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"head_commit\" from message JSON");
		}

		return new GitHubCommit(headCommitJSONObject);
	}

}