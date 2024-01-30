/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.status;

import com.liferay.jethr0.event.github.GitHubFactory;
import com.liferay.jethr0.event.github.client.GitHubClient;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitHubStatus {

	public GitHubStatus(GitHubFactory gitHubFactory, JSONObject jsonObject) {
		_gitHubFactory = gitHubFactory;
		_jsonObject = jsonObject;
	}

	public String getContext() {
		return _jsonObject.getString("context");
	}

	public String getDescription() {
		return _jsonObject.getString("description");
	}

	public GitHubClient getGitHubClient() {
		return _gitHubFactory.getGitHubClient();
	}

	public String getState() {
		return _jsonObject.getString("state");
	}

	private final GitHubFactory _gitHubFactory;
	private final JSONObject _jsonObject;

}