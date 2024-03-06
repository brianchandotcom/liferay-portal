/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.job.PortalPullRequestJobEntity;

import java.io.IOException;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalTestGitHubCommentEventHandler
	extends BaseTestGitHubCommentEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		if (checkLiferayGitHubUser() ||
			closeInvalidUpstreamGitHubBranchName()) {

			return null;
		}

		PortalPullRequestJobEntity portalPullRequestJobEntity =
			createPortalPullRequestJobEntity(_getTestSuite());

		invokeJobEntity(portalPullRequestJobEntity);

		return portalPullRequestJobEntity.toString();
	}

	protected PortalTestGitHubCommentEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	private String _getTestSuite() throws InvalidJSONException, IOException {
		Set<String> availableTestSuites = getAvailableTestSuites();

		for (String testOption : getTestOptions()) {
			if (availableTestSuites.contains(testOption)) {
				return testOption;
			}
		}

		return "default";
	}

}