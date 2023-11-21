/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.ref;

import com.liferay.jethr0.event.github.commit.GitHubCommit;

import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitHubRef {

	public static String getRefName(URL gitHubRefURL) {
		Matcher matcher = _gitHubRefURLPattern.matcher(gitHubRefURL.toString());

		if (!matcher.find()) {
			throw new RuntimeException("Invalid GitHub Ref URL" + gitHubRefURL);
		}

		return matcher.group("refName");
	}

	public static String getRepositoryName(URL gitHubRefURL) {
		Matcher matcher = _gitHubRefURLPattern.matcher(gitHubRefURL.toString());

		if (!matcher.find()) {
			throw new RuntimeException("Invalid GitHub Ref URL" + gitHubRefURL);
		}

		return matcher.group("repositoryName");
	}

	public static String getUserName(URL gitHubRefURL) {
		Matcher matcher = _gitHubRefURLPattern.matcher(gitHubRefURL.toString());

		if (!matcher.find()) {
			throw new RuntimeException("Invalid GitHub Ref URL" + gitHubRefURL);
		}

		return matcher.group("userName");
	}

	public GitHubRef(URL gitHubRefURL, JSONObject jsonObject) {
		_gitHubRefURL = gitHubRefURL;
		_jsonObject = jsonObject;

		_gitHubCommit = new GitHubCommit(jsonObject.getJSONObject("commit"));
	}

	public GitHubCommit getGitHubCommit() {
		return _gitHubCommit;
	}

	public String getRefName() {
		return _jsonObject.getString("name");
	}

	public String getRepositoryName() {
		return getRepositoryName(_gitHubRefURL);
	}

	public URL getURL() {
		return _gitHubRefURL;
	}

	public String getUserName() {
		return getUserName(_gitHubRefURL);
	}

	private static final Pattern _gitHubRefURLPattern = Pattern.compile(
		"https://github.com/(?<userName>[^/]+)/(?<repositoryName>[^/]+)/" +
			"(commits|tree)/(?<refName>[^/]+)");

	private final GitHubCommit _gitHubCommit;
	private final URL _gitHubRefURL;
	private final JSONObject _jsonObject;

}