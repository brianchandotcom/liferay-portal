/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.issue.GitHubIssue;

import java.io.IOException;

import java.util.*;

import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.user.GitHubUser;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class MergeGitHubCommentEventHandler
	extends BaseGitHubCommentEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		if (closeInvalidUpstreamGitHubBranchName()) {
			return null;
		}

		GitHubComment gitHubComment = getGitHubComment();

		String gitHubCommentBody = gitHubComment.getBody();

		if (gitHubCommentBody.startsWith("ci:merge:force")) {
			GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

			GitHubUser receiverGitHubUser =
				gitHubPullRequest.getReceiverGitHubUser();

			if (!Objects.equals(
					receiverGitHubUser.getName(), "brianchandotcom")) {

				String message = "Only Brian Chan can force a merge";

				gitHubPullRequest.comment(message + ".");
			}

			mergeSubrepo(pullRequest, true);
		}
		else {
			mergeSubrepo(pullRequest, false);
		}
	}

	protected MergeGitHubCommentEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	protected void mergeSubrepo(PullRequest pullRequest, boolean force) {
		String ownerUsername = pullRequest.getOwnerUsername();

		String branchName = pullRequest.getUpstreamRemoteGitBranchName();

		String subrepoCentralMergePullRequestRecipientName =
				getSubrepoCentralMergePullRequestRecipientName(branchName);

		if (!ownerUsername.equals(
				subrepoCentralMergePullRequestRecipientName)) {

			if (_log.isInfoEnabled()) {
				_log.info(
						"Skip merge subrepo because the user is not " +
								subrepoCentralMergePullRequestRecipientName);
			}
		}

		String repositoryName = pullRequest.getGitHubRemoteGitRepositoryName();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
				"branch", branchName
		).put(
				"command", "pull"
		).put(
				"pullRequestNumber", pullRequest.getNumber()
		).put(
				"repo", repositoryName
		);

		try {
			if (!pullRequest.isValidCIMergeFile()) {
				String message = JenkinsResultsParserUtil.combine(
						"Closing pull request because a subrepo merge request ",
						"must only contain a single change to a single ",
						"ci-merge file");

				if (_log.isInfoEnabled()) {
					_log.info(message);
				}

				pullRequest.addComment(message);

				pullRequest.close();

				return;
			}
		}
		catch (Exception exception) {
			String message = "Skip merge subrepo because of a GitHub error";

			if (_log.isInfoEnabled()) {
				_log.info(message, exception);
			}

			pullRequest.addComment(message + ".");

			return;
		}

		String sha = pullRequest.getCIMergeSHA();

		if (sha.equals("")) {
			String message =
					"Closing pull request because the ci-merge file modification " +
							"is missing or incorrectly formatted";

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			pullRequest.addComment(message);

			pullRequest.close();

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Merge subrepo SHA " + sha);
		}

		jsonObject.put("sha", sha);

		String subrepo = pullRequest.getCIMergeSubrepo();

		if (_log.isInfoEnabled()) {
			_log.info("Merge subrepo name " + subrepo);
		}

		jsonObject.put("subrepo", subrepo);

		String statusURL =
				"https://api.github.com/repos/" +
						subrepoCentralMergePullRequestRecipientName + "/" +
						repositoryName + "/commits/" + pullRequest.getSenderSHA() +
						"/status";

		JSONArray statusesJSONArray = null;

		for (int i = 0; i < 3; i++) {
			try {
				JSONObject statusJSONObject = new JSONObject(
						processURL(statusURL));

				statusesJSONArray = statusJSONObject.getJSONArray("statuses");

				break;
			}
			catch (Exception exception) {
				if (_log.isInfoEnabled()) {
					_log.info("Retrying " + statusURL, exception);
				}

				JenkinsResultsParserUtil.sleep(1000);
			}
		}

		if (statusesJSONArray == null) {
			String message = "Skip merge subrepo because of a GitHub error";

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			pullRequest.addComment(message);

			pullRequest.close();

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Merge subrepo force " + force);
		}

		if (!force) {
			Map<String, String> statuses = new HashMap<>();

			for (int i = 0; i < statusesJSONArray.length(); i++) {
				JSONObject statusJSONObject = statusesJSONArray.getJSONObject(
						i);

				String context = statusJSONObject.getString("context");
				String state = statusJSONObject.getString("state");

				statuses.put(context, state);
			}

			boolean validStatus = false;

			if (statuses.containsKey("liferay/ci:test:relevant") &&
					statuses.containsKey("liferay/ci:test:sf")) {

				String relevantStatus = statuses.get(
						"liferay/ci:test:relevant");
				String sfStatus = statuses.get("liferay/ci:test:sf");

				if (relevantStatus.equals("success") &&
						sfStatus.equals("success")) {

					validStatus = true;
				}
			}

			if (!validStatus) {
				String message =
						"Skip merge subrepo because tests have not passed";

				if (_log.isInfoEnabled()) {
					_log.info(message);
				}

				pullRequest.addComment(message);

				pullRequest.close();

				return;
			}
		}

		String gitHubWebSubrepoHostname = _jenkinsBuildProperties.getProperty(
				"github.webhook.pullrequest.web.subrepo.hostname");

		try {
			jsonObject.put("remove", "true");

			processURL(
					"http://" + gitHubWebSubrepoHostname +
							"/osb-github-web/subrepo",
					jsonObject.toString());
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(
						"Unable to remove key from subrepo processor queue",
						exception);
			}
		}

		jsonObject.remove("remove");

		try {
			String subrepoJSON = processURL(
					"http://" + gitHubWebSubrepoHostname +
							"/osb-github-web/subrepo",
					jsonObject.toString());

			JSONObject subrepoJSONObject = new JSONObject(subrepoJSON);

			int queueSize = subrepoJSONObject.getInt("queueSize");

			String message =
					"This subrepo merge request was added to the processor queue " +
							"at position " + queueSize;

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			pullRequest.addComment(message + ".");
		}
		catch (Exception exception) {
			exception.printStackTrace();

			String message = "Skip merge subrepo because of an internal error";

			if (_log.isInfoEnabled()) {
				_log.info(message, exception);
			}

			pullRequest.addComment(message + ".");
		}
	}

}