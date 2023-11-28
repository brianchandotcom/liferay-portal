/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.client;

import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.issue.GitHubIssue;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.ref.GitHubRef;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.util.BaseRetryable;
import com.liferay.jethr0.util.Retryable;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitHubClient {

	public GitHubComment createGitHubComment(
		GitHubIssue gitHubIssue, String body) {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("body", body);

		return new GitHubComment(
			new JSONObject(
				_requestPost(gitHubIssue.getCommentsURL(), requestJSONObject)));
	}

	public String getFileContent(
		GitBranchEntity gitBranchEntity, String filePath) {

		return _requestGet(
			StringUtil.toURL(
				StringUtil.combine(
					"https://raw.githubusercontent.com/",
					gitBranchEntity.getBranchUserName(), "/",
					gitBranchEntity.getRepositoryName(), "/",
					gitBranchEntity.getBranchName(), "/", filePath)));
	}

	public GitHubPullRequest getGitHubPullRequest(GitHubIssue gitHubIssue) {
		return new GitHubPullRequest(
			new JSONObject(_requestGet(gitHubIssue.getPullRequestAPIURL())));
	}

	public GitHubRef getGitHubRef(URL gitHubRefURL) {
		URL gitHubRefAPIURL = StringUtil.toURL(
			StringUtil.combine(
				"https://api.github.com/repos/",
				GitHubRef.getUserName(gitHubRefURL), "/",
				GitHubRef.getRepositoryName(gitHubRefURL), "/branches/",
				GitHubRef.getRefName(gitHubRefURL)));

		return new GitHubRef(
			gitHubRefURL, new JSONObject(_requestGet(gitHubRefAPIURL)));
	}

	private String _getAuthorization() {
		return StringUtil.combine("token ", _gitHubToken);
	}

	private String _requestGet(URL url) {
		String urlString = url.toString();

		String gitHubURL = urlString.replaceAll(
			"https://api\\.github\\.com", _gitHubProxyURL);

		Retryable<String> retryable = new BaseRetryable<String>() {

			@Override
			public String execute() {
				String response = WebClient.create(
					gitHubURL
				).get(
				).accept(
					MediaType.APPLICATION_JSON
				).header(
					"Authorization", _getAuthorization()
				).retrieve(
				).bodyToMono(
					String.class
				).block();

				if (response == null) {
					throw new RuntimeException("No response");
				}

				return response;
			}

			@Override
			protected String getRetryMessage(int retryCount) {
				return StringUtil.combine(
					"Unable to post to ", url, ". Retry attempt ", retryCount,
					" of ", maxRetries);
			}

		};

		return retryable.executeWithRetries();
	}

	private String _requestPost(URL url, JSONObject requestJSONObject) {
		String urlString = url.toString();

		String gitHubURL = urlString.replaceAll(
			"https://api\\.github\\.com", _gitHubProxyURL);

		Retryable<String> retryable = new BaseRetryable<String>() {

			@Override
			public String execute() {
				String response = WebClient.create(
					gitHubURL
				).post(
				).accept(
					MediaType.APPLICATION_JSON
				).contentType(
					MediaType.APPLICATION_JSON
				).header(
					"Authorization", _getAuthorization()
				).body(
					BodyInserters.fromValue(requestJSONObject.toString())
				).retrieve(
				).bodyToMono(
					String.class
				).block();

				if (response == null) {
					throw new RuntimeException("No response");
				}

				return response;
			}

			@Override
			protected String getRetryMessage(int retryCount) {
				return StringUtil.combine(
					"Unable to post to ", url, ". Retry attempt ", retryCount,
					" of ", maxRetries);
			}

		};

		return retryable.executeWithRetries();
	}

	@Value("${JETHR0_GITHUB_PROXY_URL:https://api.github.com}")
	private String _gitHubProxyURL;

	@Value("${JETHR0_GITHUB_TOKEN:github-token}")
	private String _gitHubToken;

}