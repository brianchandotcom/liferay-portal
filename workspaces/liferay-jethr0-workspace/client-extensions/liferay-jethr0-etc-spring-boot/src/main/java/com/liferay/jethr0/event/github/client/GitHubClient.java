/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.client;

import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.comment.GitHubCommentFactory;
import com.liferay.jethr0.event.github.issue.GitHubIssue;
import com.liferay.jethr0.util.BaseRetryable;
import com.liferay.jethr0.util.Retryable;
import com.liferay.jethr0.util.StringUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URL;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitHubClient {

	public GitHubComment createGitHubComment(
		GitHubIssue gitHubIssue, String body) {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("body", body);

		return GitHubCommentFactory.newGitHubComment(
			_requestPost(gitHubIssue.getCommentsURL(), requestJSONObject));
	}

	private String _getAuthorization() {
		return StringUtil.combine("token ", _gitHubToken);
	}

	private JSONObject _requestPost(
		URL url, JSONObject requestJSONObject) {

		Retryable<JSONObject> retryable = new BaseRetryable<JSONObject>() {

			@Override
			public JSONObject execute() {
				String response = WebClient.create(
					String.valueOf(url)
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

				return new JSONObject(response);
			}

			@Override
			protected String getRetryMessage(int retryCount) {
				return StringUtil.combine(
					"Unable to post to ", url, ". Retry attempt ",
					retryCount, " of ", maxRetries);
			}

		};

		return retryable.executeWithRetries();
	}

	@Value("${JETHR0_GITHUB_TOKEN:github-token}")
	private String _gitHubToken;

}
