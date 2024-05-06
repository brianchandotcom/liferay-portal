/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.commit;

import com.liferay.jethr0.event.github.GitHubFactory;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitHubCommit {

	public GitHubCommit(GitHubFactory gitHubFactory, JSONObject jsonObject) {
		_gitHubFactory = gitHubFactory;
		_jsonObject = jsonObject;
	}

	public GitHubClient getGitHubClient() {
		return _gitHubFactory.getGitHubClient();
	}

	public List<GitHubCommit> getParentGitHubCommits() {
		List<GitHubCommit> parentGitHubCommits = new ArrayList<>();

		JSONArray parentsJSONArray = _jsonObject.getJSONArray("parents");

		for (int i = 0; i < parentsJSONArray.length(); i++) {
			parentGitHubCommits.add(
				_gitHubFactory.newGitHubCommit(
					parentsJSONArray.getJSONObject(i)));
		}

		return parentGitHubCommits;
	}

	public String getSHA() {
		String sha = _jsonObject.optString("sha");

		if (StringUtil.isNullOrEmpty(sha)) {
			sha = _jsonObject.optString("id");
		}

		return sha;
	}

	public String getShortSHA() {
		String sha = getSHA();

		if (StringUtil.isNullOrEmpty(sha)) {
			return null;
		}

		return sha.substring(0, 7);
	}

	private final GitHubFactory _gitHubFactory;
	private final JSONObject _jsonObject;

}