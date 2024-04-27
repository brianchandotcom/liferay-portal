/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.ref;

import com.liferay.jethr0.event.github.GitHubFactory;
import com.liferay.jethr0.event.github.client.GitHubClient;
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
			throw new RuntimeException("Invalid GitHub ref URL" + gitHubRefURL);
		}

		return matcher.group("refName");
	}

	public static String getRepositoryName(URL gitHubRefURL) {
		Matcher matcher = _gitHubRefURLPattern.matcher(gitHubRefURL.toString());

		if (!matcher.find()) {
			throw new RuntimeException("Invalid GitHub ref URL" + gitHubRefURL);
		}

		return matcher.group("repositoryName");
	}

	public static String getUserName(URL gitHubRefURL) {
		Matcher matcher = _gitHubRefURLPattern.matcher(gitHubRefURL.toString());

		if (!matcher.find()) {
			throw new RuntimeException("Invalid GitHub ref URL" + gitHubRefURL);
		}

		return matcher.group("userName");
	}

	public GitHubRef(
		GitHubFactory gitHubFactory, GitHubCommit gitHubCommit,
		URL gitHubRefURL, JSONObject jsonObject) {

		_gitHubFactory = gitHubFactory;
		_gitHubCommit = gitHubCommit;
		_gitHubRefURL = gitHubRefURL;
		_jsonObject = jsonObject;
	}

	public GitHubRef(
		GitHubFactory gitHubFactory, URL gitHubRefURL, JSONObject jsonObject) {

		_gitHubFactory = gitHubFactory;
		_gitHubRefURL = gitHubRefURL;
		_jsonObject = jsonObject;

		_gitHubCommit = _gitHubFactory.newGitHubCommit(
			jsonObject.getJSONObject("commit"));
	}

	public GitHubClient getGitHubClient() {
		return _gitHubFactory.getGitHubClient();
	}

	public GitHubCommit getGitHubCommit() {
		return _gitHubCommit;
	}

	public String getRefName() {
		if (_jsonObject.has("name")) {
			return _jsonObject.getString("name");
		}

		return _jsonObject.getString("ref");
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
	private final GitHubFactory _gitHubFactory;
	private final URL _gitHubRefURL;
	private final JSONObject _jsonObject;

}