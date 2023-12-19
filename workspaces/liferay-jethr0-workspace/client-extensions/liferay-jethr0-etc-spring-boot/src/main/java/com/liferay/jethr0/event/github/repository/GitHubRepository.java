/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.repository;

import com.liferay.jethr0.event.github.GitHubFactory;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitHubRepository {

	public GitHubRepository(
		GitHubFactory gitHubFactory, JSONObject jsonObject) {

		_gitHubFactory = gitHubFactory;
		_jsonObject = jsonObject;

		_gitHubUser = _gitHubFactory.newGitHubUser(
			jsonObject.getJSONObject("owner"));
	}

	public GitHubClient getGitHubClient() {
		return _gitHubFactory.getGitHubClient();
	}

	public GitHubUser getGitHubUser() {
		return _gitHubUser;
	}

	public URL getHTMLURL() {
		return StringUtil.toURL(_jsonObject.getString("html_url"));
	}

	public String getName() {
		return _jsonObject.getString("name");
	}

	private final GitHubFactory _gitHubFactory;
	private final GitHubUser _gitHubUser;
	private final JSONObject _jsonObject;

}