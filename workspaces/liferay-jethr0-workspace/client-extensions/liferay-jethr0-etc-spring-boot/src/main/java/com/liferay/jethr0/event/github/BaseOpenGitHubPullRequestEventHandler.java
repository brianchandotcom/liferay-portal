/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.file.GitHubFile;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			_skipCISenderBlacklistGitHubUser()) {

			return null;
		}

		_commentAutoCommentMessage();
		_commentBroadcastMessage();

		if (_checkForwardedPullRequest() ||
			_checkMergeSubrepositoryPullRequest()) {

			_commentDefaultMessage();

			return null;
		}

		_invokeJobEntities();

		return String.valueOf(getMessageJSONObject());
	}

	protected BaseOpenGitHubPullRequestEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	private boolean _checkForwardedPullRequest()
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

	private boolean _checkMergeSubrepositoryPullRequest()
		throws InvalidJSONException, IOException {

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest == null) {
			return false;
		}

		GitBranchEntity upstreamGitBranchEntity = getUpstreamGitBranchEntity();

		GitHubUser receiverGitHubUser =
			gitHubPullRequest.getReceiverGitHubUser();

		String subrepositoryMergeReceiverUserName =
			getJenkinsBranchBuildPropertyValue(
				StringUtil.combine(
					"subrepo.merge.receiver.name[",
					upstreamGitBranchEntity.getBranchName(), "]"));

		if (!gitHubPullRequest.isMergeSubrepositoryPullRequest() ||
			!Objects.equals(
				receiverGitHubUser.getName(),
				subrepositoryMergeReceiverUserName)) {

			return false;
		}

		GitRepo gitRepo = new GitRepo(
			upstreamGitBranchEntity.getFileContent(
				gitHubPullRequest.getGitRepoFilePath()));

		GitHubFile ciMergeGitHubFile = gitHubPullRequest.getCIMergeGitHubFile();

		Matcher matcher = _branchSHAPattern.matcher(
			ciMergeGitHubFile.getPatch());

		String ciMergeBranchSHA = "";

		if (matcher.find()) {
			ciMergeBranchSHA = matcher.group("branchSHA");
		}

		String compareURL = StringUtil.combine(
			"https://github.com/liferay/", gitRepo.getRepositoryName(),
			"/compare/", gitRepo.getRepositorySHA(), "..." + ciMergeBranchSHA);

		gitHubPullRequest.comment(
			StringUtil.combine(
				"Subrepo changes: ", compareURL, "\n\nci:test:sf and ",
				"ci:test:relevant must pass in order for auto-merge to ",
				"initiate."));

		invokeJobEntity(createPortalPullRequestJobEntity("relevant"));

		invokeJobEntity(createPortalPullRequestJobEntity("sf"));

		return true;
	}

	private void _commentAutoCommentMessage()
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

	private void _commentBroadcastMessage()
		throws InvalidJSONException, IOException {

		String broadcastMessage = getCIProperty(
			"pull.request.broadcast.message");

		if (StringUtil.isNullOrEmpty(broadcastMessage)) {
			return;
		}

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		gitHubPullRequest.comment(broadcastMessage);
	}

	private void _commentDefaultMessage() throws InvalidJSONException {
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

	private Set<JobEntity> _createJobEntities()
		throws InvalidJSONException, IOException {

		Set<JobEntity> jobEntities = new HashSet<>();

		for (String testSuite : _getTestSuites()) {
			jobEntities.add(createPortalPullRequestJobEntity(testSuite));
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

	private void _invokeJobEntities() throws InvalidJSONException, IOException {
		Set<JobEntity> jobEntities = _createJobEntities();

		for (JobEntity jobEntity : jobEntities) {
			invokeJobEntity(jobEntity);
		}
	}

	private boolean _skipCISenderBlacklistGitHubUser()
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

		if (!ciTestAutoSendersBlacklist.contains(senderGitHubUser.getName())) {
			return false;
		}

		return true;
	}

	private static final Pattern _branchSHAPattern = Pattern.compile(
		"\\+(?<branchSHA>[0-9a-f]{40})");
	private static final Pattern _ciTestAutoRecipientPattern = Pattern.compile(
		"(?<userName>[^\\]]+)\\[(?<testSuites>[^\\]]+)\\]");

	private static class GitRepo {

		public String getRepositoryName() {
			Matcher matcher = _gitRepoRepositoryNamePattern.matcher(
				_gitRepoFileContent);

			if (!matcher.find()) {
				return null;
			}

			return matcher.group("repositoryName");
		}

		public String getRepositorySHA() {
			Matcher matcher = _gitRepoSHAPattern.matcher(_gitRepoFileContent);

			if (!matcher.find()) {
				return null;
			}

			return matcher.group("repositorySHA");
		}

		private GitRepo(String gitRepoFileContent) {
			_gitRepoFileContent = gitRepoFileContent;
		}

		private static final Pattern _gitRepoRepositoryNamePattern =
			Pattern.compile("remote = .*/(?<repositoryName>[^\\.]*)\\.git");
		private static final Pattern _gitRepoSHAPattern = Pattern.compile(
			"commit = (?<repositorySHA>[0-9a-f]{40})");

		private final String _gitRepoFileContent;

	}

}