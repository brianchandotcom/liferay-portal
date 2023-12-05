/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.BaseEventHandlerFactory;
import com.liferay.jethr0.event.EventHandler;
import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.util.StringUtil;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitHubEventHandlerFactory extends BaseEventHandlerFactory {

	@Override
	public EventHandler newEventHandler(JSONObject messageJSONObject)
		throws IllegalArgumentException {

		EventHandlerContext eventHandlerContext = getEventHandlerContext();

		String action = messageJSONObject.optString("action");

		if (!StringUtil.isNullOrEmpty(action)) {
			if (action.equals("created")) {
				JSONObject commentJSONObject = messageJSONObject.optJSONObject(
					"comment");

				if (commentJSONObject != null) {
					String body = commentJSONObject.getString("body");

					if (body.startsWith("ci:help")) {
						return new HelpGitHubIssueEventHandler(
							eventHandlerContext, messageJSONObject);
					}
					else if (body.startsWith("ci:test")) {
						return new TestGitHubIssueEventHandler(
							eventHandlerContext, messageJSONObject);
					}

					throw new IllegalArgumentException(
						"Invalid \"body\" from comment JSON");
				}
			}
			else if (action.equals("opened")) {
				JSONObject pullRequestJSONObject =
					messageJSONObject.optJSONObject("pull_request");

				if (pullRequestJSONObject != null) {
					return new OpenGitHubPullRequestEventHandler(
						eventHandlerContext, messageJSONObject);
				}
			}

			throw new IllegalArgumentException(
				"Invalid \"action\" from message JSON");
		}

		JSONObject pusherJSONObject = messageJSONObject.optJSONObject("pusher");

		if (pusherJSONObject != null) {
			return new PusherGitHubEventHandler(
				eventHandlerContext, messageJSONObject);
		}

		throw new IllegalArgumentException("Invalid message JSON");
	}

}