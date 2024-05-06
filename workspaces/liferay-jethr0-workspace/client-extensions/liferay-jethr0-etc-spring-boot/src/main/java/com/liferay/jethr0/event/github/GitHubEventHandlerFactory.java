/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.EventHandler;
import com.liferay.jethr0.event.EventHandlerFactory;
import com.liferay.jethr0.util.StringUtil;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitHubEventHandlerFactory implements EventHandlerFactory {

	@Override
	public EventHandler newEventHandler(JSONObject messageJSONObject)
		throws IllegalArgumentException {

		String action = messageJSONObject.optString("action");

		if (!StringUtil.isNullOrEmpty(action)) {
			if (action.equals("created")) {
				JSONObject commentJSONObject = messageJSONObject.optJSONObject(
					"comment");

				if (commentJSONObject != null) {
					String body = commentJSONObject.getString("body");

					if (body.startsWith("ci:close")) {
						return new CloseGitHubCommentEventHandler(
							messageJSONObject);
					}
					else if (body.startsWith("ci:forward")) {
						return new ForwardGitHubCommentEventHandler(
							messageJSONObject);
					}
					else if (body.startsWith("ci:help")) {
						return new HelpGitHubCommentEventHandler(
							messageJSONObject);
					}
					else if (body.startsWith("ci:merge")) {
						return new MergeGitHubCommentEventHandler(
							messageJSONObject);
					}
					else if (body.startsWith("ci:reevaluate")) {
						return new ReevaluateGitHubCommentEventHandler(
							messageJSONObject);
					}
					else if (body.startsWith("ci:reopen")) {
						return new ReopenGitHubCommentEventHandler(
							messageJSONObject);
					}
					else if (body.startsWith("ci:report")) {
						return new ReportGitHubCommentEventHandler(
							messageJSONObject);
					}
					else if (body.startsWith("ci:stop")) {
						return new StopGitHubCommentEventHandler(
							messageJSONObject);
					}
					else if (body.startsWith("ci:test")) {
						JSONObject repositoryJSONObject =
							messageJSONObject.getJSONObject("repository");

						String repositoryName = repositoryJSONObject.getString(
							"name");

						if (repositoryName.startsWith("com-liferay-")) {
							return new SubrepositoryTestGitHubCommentEventHandler(
								messageJSONObject);
						}
						else if (repositoryName.equals(
									"liferay-fix-pack-builder-ee")) {

							return new FixpackTestGitHubCommentEventHandler(
								messageJSONObject);
						}
						else if (repositoryName.equals("liferay-jenkins-ee")) {
							return new JenkinsTestGitHubCommentEventHandler(
								messageJSONObject);
						}
						else if (repositoryName.equals("liferay-plugins") ||
								 repositoryName.equals("liferay-plugins-ee")) {

							return new PluginsTestGitHubCommentEventHandler(
								messageJSONObject);
						}
						else if (repositoryName.equals("liferay-portal") ||
								 repositoryName.equals("liferay-portal-ee")) {

							return new PortalTestGitHubCommentEventHandler(
								messageJSONObject);
						}
						else if (repositoryName.equals(
									"liferay-qa-websites-ee")) {

							return new QAWebsitesTestGitHubCommentEventHandler(
								messageJSONObject);
						}

						throw new IllegalArgumentException(
							"Invalid repository " + repositoryName);
					}

					throw new IllegalArgumentException(
						"Invalid \"body\" from comment JSON");
				}
			}
			else if (action.equals("opened")) {
				JSONObject pullRequestJSONObject =
					messageJSONObject.optJSONObject("pull_request");

				if (pullRequestJSONObject != null) {
					JSONObject repositoryJSONObject =
						messageJSONObject.getJSONObject("repository");

					String repositoryName = repositoryJSONObject.getString(
						"name");

					if (repositoryName.startsWith("com-liferay")) {
						return new SubrepositoryOpenGitHubPullRequestEventHandler(
							messageJSONObject);
					}
					else if (repositoryName.equals(
								"liferay-fix-pack-builder-ee")) {

						return new FixpackOpenGitHubPullRequestEventHandler(
							messageJSONObject);
					}
					else if (repositoryName.equals("liferay-jenkins-ee")) {
						return new JenkinsOpenGitHubPullRequestEventHandler(
							messageJSONObject);
					}
					else if (repositoryName.equals("liferay-plugins") ||
							 repositoryName.equals("liferay-plugins-ee")) {

						return new PluginsOpenGitHubPullRequestEventHandler(
							messageJSONObject);
					}
					else if (repositoryName.equals("liferay-portal") ||
							 repositoryName.equals("liferay-portal-ee")) {

						return new PortalOpenGitHubPullRequestEventHandler(
							messageJSONObject);
					}
					else if (repositoryName.equals("liferay-qa-websites-ee")) {
						return new QAWebsitesOpenGitHubPullRequestEventHandler(
							messageJSONObject);
					}

					throw new IllegalArgumentException(
						"Invalid repository " + repositoryName);
				}
			}
			else if (action.equals("synchronize")) {
				JSONObject pullRequestJSONObject =
					messageJSONObject.optJSONObject("pull_request");

				if (pullRequestJSONObject != null) {
					return new SynchronizeGitHubPullRequestEventHandler(
						messageJSONObject);
				}
			}

			throw new IllegalArgumentException(
				"Invalid \"action\" from message JSON");
		}

		JSONObject pusherJSONObject = messageJSONObject.optJSONObject("pusher");

		if (pusherJSONObject != null) {
			return new PusherGitHubEventHandler(messageJSONObject);
		}

		throw new IllegalArgumentException("Invalid message JSON");
	}

}