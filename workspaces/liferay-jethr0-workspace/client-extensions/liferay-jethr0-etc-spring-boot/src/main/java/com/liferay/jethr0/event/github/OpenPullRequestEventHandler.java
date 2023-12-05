/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class OpenPullRequestEventHandler
	extends BaseGitHubPullRequestEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		if (closeInvalidUpstreamGitHubBranchName()) {
			return null;
		}

		Set<JobEntity> jobEntities = _createJobEntities();

		for (JobEntity jobEntity : jobEntities) {
			invokeJobEntity(jobEntity);
		}

		return jobEntities.toString();
	}

	protected OpenPullRequestEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	private Set<JobEntity> _createJobEntities()
		throws InvalidJSONException, IOException {

		Set<JobEntity> jobEntities = new HashSet<>();

		for (String testSuite : _getTestSuites()) {
			jobEntities.add(createJobEntity(testSuite));
		}

		return jobEntities;
	}

	private Set<String> _getTestSuites()
		throws InvalidJSONException, IOException {

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		Set<String> ciTestAutoRecipients = new HashSet<>();

		String upstreamCITestAutoRecipients = getUpstreamBranchCIPropertyValue(
			"ci.test.auto.recipients");

		if (!StringUtil.isNullOrEmpty(upstreamCITestAutoRecipients)) {
			Collections.addAll(
				ciTestAutoRecipients, upstreamCITestAutoRecipients.split(","));
		}

		String senderCITestAutoRecipients = getSenderBranchCIPropertyValue(
			"ci.test.auto.recipients");

		if (!StringUtil.isNullOrEmpty(senderCITestAutoRecipients)) {
			Collections.addAll(
				ciTestAutoRecipients, senderCITestAutoRecipients.split(","));
		}

		GitHubUser receiverGitHubUser =
			gitHubPullRequest.getReceiverGitHubUser();

		Set<String> testSuites = new HashSet<>();

		for (String ciTestAutoRecipient : ciTestAutoRecipients) {
			Matcher matcher = _ciTestAutoRecipientPattern.matcher(
				ciTestAutoRecipient);

			if (!matcher.find() ||
				!Objects.equals(
					matcher.group("userName"), receiverGitHubUser.getName())) {

				continue;
			}

			String testSuitesString = matcher.group("testSuites");

			Collections.addAll(testSuites, testSuitesString.split(","));
		}

		return testSuites;
	}

	private static final Pattern _ciTestAutoRecipientPattern = Pattern.compile(
		"(?<userName>[^\\]]+)\\[(?<testSuites>[^\\]]+)\\]");

}