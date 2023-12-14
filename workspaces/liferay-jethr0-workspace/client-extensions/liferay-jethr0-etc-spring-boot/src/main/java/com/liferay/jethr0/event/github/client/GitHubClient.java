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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

	public void closeGitHubIssue(GitHubIssue gitHubIssue) {
		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("state", "closed");

		requestPatch(gitHubIssue.getPullRequestAPIURL(), requestJSONObject);
	}

	public void closeGitHubPullRequest(GitHubPullRequest gitHubPullRequest) {
		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("state", "closed");

		requestPatch(gitHubPullRequest.getAPIURL(), requestJSONObject);
	}

	public GitHubComment createGitHubComment(
		GitHubIssue gitHubIssue, String body) {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("body", body);

		return new GitHubComment(
			new JSONObject(
				requestPost(gitHubIssue.getCommentsURL(), requestJSONObject)));
	}

	public GitHubComment createGitHubComment(
		GitHubPullRequest gitHubPullRequest, String body) {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("body", body);

		return new GitHubComment(
			new JSONObject(
				requestPost(
					gitHubPullRequest.getCommentsURL(), requestJSONObject)));
	}

	public String getFileContent(
		GitBranchEntity gitBranchEntity, String filePath) {

		return requestGet(
			StringUtil.toURL(
				StringUtil.combine(
					"https://raw.githubusercontent.com/",
					gitBranchEntity.getBranchUserName(), "/",
					gitBranchEntity.getRepositoryName(), "/",
					gitBranchEntity.getBranchName(), "/", filePath)));
	}

	public GitHubRef getGitHubRef(URL gitHubRefURL) {
		URL gitHubRefAPIURL = StringUtil.toURL(
			StringUtil.combine(
				"https://api.github.com/repos/",
				GitHubRef.getUserName(gitHubRefURL), "/",
				GitHubRef.getRepositoryName(gitHubRefURL), "/branches/",
				GitHubRef.getRefName(gitHubRefURL)));

		return new GitHubRef(
			gitHubRefURL, new JSONObject(requestGet(gitHubRefAPIURL)));
	}

	public String requestGet(URL url) {
		String urlString = url.toString();

		if (urlString.startsWith("https://raw.githubusercontent.com")) {
			try {
				StringBuilder sb = new StringBuilder();

				String line = null;

				URLConnection urlConnection = url.openConnection();

				urlConnection.setRequestProperty(
					"Accept", MediaType.APPLICATION_JSON_VALUE);
				urlConnection.setRequestProperty(
					"Authorization", _getAuthorization());

				InputStream inputStream = urlConnection.getInputStream();

				BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));

				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line);
					sb.append("\n");
				}

				try {
					return sb.toString();
				}
				finally {
					bufferedReader.close();
					inputStream.close();
				}
			}
			catch (IOException ioException) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioException);
				}
			}
		}

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

	public String requestPatch(URL url, JSONObject requestJSONObject) {
		String urlString = url.toString();

		String gitHubURL = urlString.replaceAll(
			"https://api\\.github\\.com", _gitHubProxyURL);

		Retryable<String> retryable = new BaseRetryable<String>() {

			@Override
			public String execute() {
				String response = WebClient.create(
					gitHubURL
				).patch(
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

	public String requestPost(URL url, JSONObject requestJSONObject) {
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

	private String _getAuthorization() {
		return StringUtil.combine("token ", _gitHubToken);
	}

	private static final Log _log = LogFactory.getLog(GitHubClient.class);

	@Value("${JETHR0_GITHUB_PROXY_URL:https://api.github.com}")
	private String _gitHubProxyURL;

	@Value("${JETHR0_GITHUB_TOKEN:github-token}")
	private String _gitHubToken;

}