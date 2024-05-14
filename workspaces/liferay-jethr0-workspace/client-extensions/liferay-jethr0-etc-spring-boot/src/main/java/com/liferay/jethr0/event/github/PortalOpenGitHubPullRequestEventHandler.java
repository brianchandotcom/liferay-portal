/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.file.GitHubFile;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.Date;
import java.util.HashSet;
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
public class PortalOpenGitHubPullRequestEventHandler
	extends BaseOpenGitHubPullRequestEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		if (checkLiferayGitHubUser() ||
			closeInvalidUpstreamGitHubBranchName() ||
			_skipAutoTestReleaseBranch() || skipAutoTestSenderBlacklist()) {

			return null;
		}

		commentAutoCommentMessage();
		commentBroadcastMessage();

		if (checkForwardedPullRequest() ||
			_checkMergeSubrepositoryPullRequest()) {

			commentDefaultMessage();

			return null;
		}

		invokeJobEntities();

		return String.valueOf(getMessageJSONObject());
	}

	protected PortalOpenGitHubPullRequestEventHandler(
		JSONObject messageJSONObject) {

		super(messageJSONObject);
	}

	@Override
	protected Set<JobEntity> createJobEntities()
		throws InvalidJSONException, IOException {

		Set<JobEntity> jobEntities = new HashSet<>();

		for (String testOption : getTestOptions()) {
			jobEntities.add(createPortalPullRequestJobEntity(testOption));
		}

		return jobEntities;
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
					upstreamGitBranchEntity.getName(), "]"));

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

	private boolean _skipAutoTestReleaseBranch() throws InvalidJSONException {
		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		Matcher releaseBranchNameMatcher = _releaseBranchNamePattern.matcher(
			gitHubPullRequest.getHeadBranchName());

		if (!releaseBranchNameMatcher.matches()) {
			return false;
		}

		gitHubPullRequest.comment(
			StringUtil.combine(
				"To conserve resources, the PR Tester does not automatically ",
				"run for portal release branches."));

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Skipped release branch jobs for ",
					gitHubPullRequest.getHTMLURL(), " at ",
					StringUtil.toString(new Date())));
		}

		return true;
	}

	private static final Log _log = LogFactory.getLog(
		PortalOpenGitHubPullRequestEventHandler.class);

	private static final Pattern _branchSHAPattern = Pattern.compile(
		"\\+(?<branchSHA>[0-9a-f]{40})");
	private static final Pattern _releaseBranchNamePattern = Pattern.compile(
		"release-\\d{4}\\.q\\d");

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

		protected GitRepo(String gitRepoFileContent) {
			_gitRepoFileContent = gitRepoFileContent;
		}

		private static final Pattern _gitRepoRepositoryNamePattern =
			Pattern.compile("remote = .*/(?<repositoryName>[^\\.]*)\\.git");
		private static final Pattern _gitRepoSHAPattern = Pattern.compile(
			"commit = (?<repositorySHA>[0-9a-f]{40})");

		private final String _gitRepoFileContent;

	}

}