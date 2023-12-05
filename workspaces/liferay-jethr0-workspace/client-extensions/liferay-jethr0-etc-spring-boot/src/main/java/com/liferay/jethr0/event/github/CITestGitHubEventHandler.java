/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.job.JobEntity;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CITestGitHubEventHandler extends BaseGitHubIssueEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		if (closeInvalidUpstreamGitHubBranchName()) {
			return null;
		}

		JobEntity jobEntity = createJobEntity(_getTestSuite());

		invokeJobEntity(jobEntity);

		return jobEntity.toString();
	}

	protected CITestGitHubEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	private List<String> _getTestOptions() throws InvalidJSONException {
		List<String> testOptions = new ArrayList<>();

		GitHubComment gitHubComment = getGitHubComment();

		Matcher matcher = _pattern.matcher(gitHubComment.getBody());

		if (!matcher.find()) {
			return testOptions;
		}

		String testOptionsString = matcher.group("testOptions");

		Collections.addAll(testOptions, testOptionsString.split(","));

		return testOptions;
	}

	private String _getTestSuite() throws InvalidJSONException, IOException {
		Set<String> availableTestSuites = getAvailableTestSuites();

		for (String testOption : _getTestOptions()) {
			if (availableTestSuites.contains(testOption)) {
				return testOption;
			}
		}

		return "default";
	}

	private static final Pattern _pattern = Pattern.compile(
		"ci:test(\\:(?<testOptions>[^\\s]+))?");

}