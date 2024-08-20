/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseOpenGitHubPullRequestEventHandler
	extends BaseGitHubPullRequestEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		if (checkLiferayGitHubUser() ||
			closeInvalidUpstreamGitHubBranchName() ||
			skipAutoTestSenderBlacklist()) {

			return null;
		}

		commentAutoCommentMessage();
		commentBroadcastMessage();

		if (checkForwardedPullRequest()) {
			commentDefaultMessage();

			return null;
		}

		invokeJobEntities();

		return String.valueOf(getMessageJSONObject());
	}

	protected BaseOpenGitHubPullRequestEventHandler(
		JSONObject messageJSONObject) {

		super(messageJSONObject);
	}

	protected boolean checkForwardedPullRequest()
		throws InvalidJSONException, IOException {

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest == null) {
			return false;
		}

		String body = gitHubPullRequest.getBody();
		GitHubUser receiverGitHubUser =
			gitHubPullRequest.getReceiverGitHubUser();
		GitHubUser senderGitHubUser = gitHubPullRequest.getSenderGitHubUser();

		if (body.startsWith("Forwarded from:") &&
			Objects.equals(
				receiverGitHubUser.getName(),
				getJenkinsBranchBuildPropertyValue(
					"ci.forward.default.receiver.username")) &&
			Objects.equals(
				senderGitHubUser.getName(),
				getJenkinsBranchBuildPropertyValue("github.ci.username"))) {

			gitHubPullRequest.comment(
				StringUtil.combine(
					"To conserve resources, the PR Tester does not ",
					"automatically run for forwarded pull requests."));

			return true;
		}

		return false;
	}

	protected void commentAutoCommentMessage()
		throws InvalidJSONException, IOException {

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		GitHubUser receiverGitHubUser =
			gitHubPullRequest.getReceiverGitHubUser();

		String autoCommentMessage = getCIProperty(
			StringUtil.combine(
				"ci.pull.request.auto.comment[", receiverGitHubUser.getName(),
				"]"));

		if (StringUtil.isNullOrEmpty(autoCommentMessage)) {
			return;
		}

		gitHubPullRequest.comment(
			StringUtil.combine(
				"The following guidelines have been set by the owner of this ",
				"repository:\n- &nbsp;&nbsp;&nbsp;&nbsp;", autoCommentMessage,
				"\n"));
	}

	protected void commentBroadcastMessage()
		throws InvalidJSONException, IOException {

		String broadcastMessage = getCIProperty(
			"pull.request.broadcast.message");

		if (StringUtil.isNullOrEmpty(broadcastMessage)) {
			return;
		}

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		gitHubPullRequest.comment(broadcastMessage);
	}

	protected void commentDefaultMessage() throws InvalidJSONException {
		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		GitHubUser senderGitHubUser = gitHubPullRequest.getSenderGitHubUser();

		if (!senderGitHubUser.isLiferayUser()) {
			return;
		}

		GitHubUser receiverGitHubUser =
			gitHubPullRequest.getReceiverGitHubUser();

		String receiverGitHubUserName = receiverGitHubUser.getName();

		String baseRepositoryName = gitHubPullRequest.getBaseRepositoryName();

		if (receiverGitHubUserName.equals("liferay") &&
			!baseRepositoryName.startsWith("com-liferay") &&
			!baseRepositoryName.equals("liferay-portal-ee")) {

			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("To conserve resources, the PR Tester does not ");
		sb.append("automatically run for every pull.\n\nIf your code changes ");
		sb.append("were already tested in another pull, reference that pull ");
		sb.append("in this pull so the test results can be analyzed.\n\nIf ");
		sb.append("your pull was never tested, comment &quot;ci:test&quot; ");
		sb.append("to run the PR Tester for this pull.");

		gitHubPullRequest.comment(sb.toString());
	}

	protected abstract Set<JobEntity> createJobEntities()
		throws InvalidJSONException, IOException;

	protected Set<String> getTestOptions()
		throws InvalidJSONException, IOException {

		Set<String> testOptions = new HashSet<>();

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

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		GitHubUser receiverGitHubUser =
			gitHubPullRequest.getReceiverGitHubUser();

		for (String ciTestAutoRecipient : ciTestAutoRecipients) {
			Matcher matcher = _ciTestAutoRecipientPattern.matcher(
				ciTestAutoRecipient);

			if (!matcher.find() ||
				!Objects.equals(
					matcher.group("userName"), receiverGitHubUser.getName())) {

				continue;
			}

			String testOptionsString = matcher.group("testOptions");

			Collections.addAll(testOptions, testOptionsString.split(","));
		}

		return testOptions;
	}

	protected void invokeJobEntities()
		throws InvalidJSONException, IOException {

		Set<JobEntity> jobEntities = createJobEntities();

		for (JobEntity jobEntity : jobEntities) {
			invokeJobEntity(jobEntity);
		}

		if (_log.isInfoEnabled()) {
			GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

			_log.info(
				StringUtil.combine(
					"Invoked ", jobEntities.size(), " jobs for ",
					gitHubPullRequest.getHTMLURL(), " at ",
					StringUtil.toString(new Date())));
		}
	}

	protected boolean skipAutoTestSenderBlacklist()
		throws InvalidJSONException, IOException {

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest == null) {
			return false;
		}

		List<String> ciTestAutoSendersBlacklist = new ArrayList<>();

		String ciTestAutoSendersBlacklistString =
			getSenderBranchCIPropertyValue("ci.test.auto.senders.blacklist");

		if (!StringUtil.isNullOrEmpty(ciTestAutoSendersBlacklistString)) {
			Collections.addAll(
				ciTestAutoSendersBlacklist,
				ciTestAutoSendersBlacklistString.split(","));
		}

		ciTestAutoSendersBlacklistString = getUpstreamBranchCIPropertyValue(
			"ci.test.auto.senders.blacklist");

		if (!StringUtil.isNullOrEmpty(ciTestAutoSendersBlacklistString)) {
			Collections.addAll(
				ciTestAutoSendersBlacklist,
				ciTestAutoSendersBlacklistString.split(","));
		}

		GitHubUser senderGitHubUser = gitHubPullRequest.getSenderGitHubUser();

		String senderGitHubUserName = senderGitHubUser.getName();

		if (!ciTestAutoSendersBlacklist.contains(senderGitHubUserName)) {
			return false;
		}

		gitHubPullRequest.comment(
			StringUtil.combine(
				"To conserve resources, the PR Tester does not run for the ",
				"sending user \"", senderGitHubUserName, "\"."));

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Skipped sender blacklist jobs for ",
					gitHubPullRequest.getHTMLURL(), " at ",
					StringUtil.toString(new Date())));
		}

		return true;
	}

	private static final Log _log = LogFactory.getLog(
		BaseOpenGitHubPullRequestEventHandler.class);

	private static final Pattern _ciTestAutoRecipientPattern = Pattern.compile(
		"(?<userName>[^\\]]+)\\[(?<testOptions>[^\\]]+)\\]");

}