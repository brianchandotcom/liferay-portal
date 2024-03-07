/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.PortalPullRequestJobEntity;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
			createPortalPullRequestJobEntity(getTestSuite());

		portalPullRequestJobEntity.setGitHubGistID(_getGitHubGistID());

		invokeJobEntity(portalPullRequestJobEntity);

		return portalPullRequestJobEntity.toString();
	}

	protected PortalTestGitHubCommentEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);

		String testSuiteName = "default";

		try {
			testSuiteName = getTestSuite();
		}
		catch (InvalidJSONException | IOException exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		_testSuiteName = testSuiteName;
	}

	@Override
	protected JobEntity.Type getJobEntityType() {
		if (_testSuiteName.equals("sf")) {
			return JobEntity.Type.PORTAL_PULL_REQUEST_SF;
		}

		return JobEntity.Type.PORTAL_PULL_REQUEST;
	}

	@Override
	protected int getJobPriority() {
		if (_testSuiteName.equals("sf")) {
			return 3;
		}

		return 5;
	}

	private String _getGitHubGistID() throws InvalidJSONException {
		for (String testOption : getTestOptions()) {
			if (StringUtil.isNullOrEmpty(testOption)) {
				continue;
			}

			Matcher gitHubGistIDMatcher = _gitHubGistIDPattern.matcher(
				testOption);

			if (!gitHubGistIDMatcher.find()) {
				continue;
			}

			return gitHubGistIDMatcher.group("id");
		}

		return null;
	}

	private static final Log _log = LogFactory.getLog(
		PortalTestGitHubCommentEventHandler.class);

	private static final Pattern _gitHubGistIDPattern = Pattern.compile(
		"gist-(?<id>[a-f0-9]{7,40})");

	private final String _testSuiteName;

}