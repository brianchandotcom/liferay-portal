/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.file.GitHubFile;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.status.GitHubStatus;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.MergePortalSubrepositoryJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import java.util.Collections;
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
public class MergeGitHubCommentEventHandler
	extends BaseGitHubCommentEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		if (closeInvalidUpstreamGitHubBranchName()) {
			return null;
		}

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		GitHubUser receiverGitHubUser =
			gitHubPullRequest.getReceiverGitHubUser();

		String subrepoMergeReceiverName = _getSubrepoReceiverName();

		if (!Objects.equals(
				receiverGitHubUser.getName(), subrepoMergeReceiverName)) {

			String message = StringUtil.combine(
				"Skip merge subrepo because the receiving user is not ",
				subrepoMergeReceiverName);

			gitHubPullRequest.comment(message + ".");

			gitHubPullRequest.close();

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			return null;
		}

		if (!_isValidCIMergeFile()) {
			String message = StringUtil.combine(
				"Closing pull request because a subrepo merge request must",
				" only contain a single change to a single ci-merge file");

			gitHubPullRequest.comment(message + ".");

			gitHubPullRequest.close();

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			return null;
		}

		if (StringUtil.isNullOrEmpty(_getCIMergeSHA())) {
			String message = StringUtil.combine(
				"Closing pull request because the ci-merge file ",
				"modification is missing or incorrectly formatted");

			gitHubPullRequest.comment(message + ".");

			gitHubPullRequest.close();

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			return null;
		}

		if (_forceMerge()) {
			GitHubComment gitHubComment = getGitHubComment();

			GitHubUser commenterGitHubUser =
				gitHubComment.getCommenterGitHubUser();

			Set<String> ciMergeForceUserNames = _getCIMergeForceUserNames();

			String commenterGitHubUserName = commenterGitHubUser.getName();

			if (!ciMergeForceUserNames.contains(commenterGitHubUserName)) {
				String message = "Only Brian Chan can force a merge";

				gitHubPullRequest.comment(message + ".");

				if (_log.isInfoEnabled()) {
					_log.info(message);
				}

				return null;
			}
		}

		if (!_hasRequiredPassingTests()) {
			String message = "Skip merge subrepo because tests have not passed";

			gitHubPullRequest.comment(message + ".");

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			return null;
		}

		JobEntity jobEntity = _createMergePortalSubrepositoryJobEntity();

		invokeJobEntity(jobEntity);

		return jobEntity.toString();
	}

	protected MergeGitHubCommentEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private JobEntity _createMergePortalSubrepositoryJobEntity()
		throws InvalidJSONException {

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		GitHubUser receiverGitHubUser =
			gitHubPullRequest.getReceiverGitHubUser();

		String jobName = StringUtil.combine(
			"ci:merge - " + receiverGitHubUser.getName(), "#",
			gitHubPullRequest.getNumber());

		JobEntity jobEntity = jobEntityRepository.create(
			null, jobName, null, 3, null, JobEntity.State.OPENED,
			JobEntity.Type.MERGE_PORTAL_SUBREPOSITORY);

		if (!(jobEntity instanceof MergePortalSubrepositoryJobEntity)) {
			throw new RuntimeException("Invalid job type");
		}

		MergePortalSubrepositoryJobEntity mergePortalSubrepositoryJobEntity =
			(MergePortalSubrepositoryJobEntity)jobEntity;

		mergePortalSubrepositoryJobEntity.setPortalPullRequestURL(
			gitHubPullRequest.getHTMLURL());
		mergePortalSubrepositoryJobEntity.setSubrepositoryBranchSHA(
			_getCIMergeSHA());
		mergePortalSubrepositoryJobEntity.setSubrepositoryBranchURL(
			_getSubrepositoryBranchURL());
		mergePortalSubrepositoryJobEntity.setSubrepositoryUpstreamBranchName(
			gitHubPullRequest.getBaseBranchName());

		jobEntityRepository.update(mergePortalSubrepositoryJobEntity);

		return mergePortalSubrepositoryJobEntity;
	}

	private boolean _forceMerge() throws InvalidJSONException {
		GitHubComment gitHubComment = getGitHubComment();

		String gitHubCommentBody = gitHubComment.getBody();

		if (gitHubCommentBody.startsWith("ci:merge:force")) {
			return true;
		}

		return false;
	}

	private Set<String> _getCIMergeForceUserNames() throws IOException {
		Set<String> ciMergeForceUserNames = new HashSet<>();

		String ciMergeForceUserNamesString = getJenkinsBranchBuildPropertyValue(
			"ci.merge.force.usernames");

		Collections.addAll(
			ciMergeForceUserNames, ciMergeForceUserNamesString.split(","));

		return ciMergeForceUserNames;
	}

	private GitHubFile _getCIMergeGitHubFile() throws InvalidJSONException {
		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		List<GitHubFile> gitHubFiles = gitHubPullRequest.getGitHubFiles();

		if (gitHubFiles.size() != 1) {
			return null;
		}

		GitHubFile gitHubFile = gitHubFiles.get(0);

		String gitHubFileName = gitHubFile.getName();

		if (!gitHubFileName.endsWith("/ci-merge")) {
			return null;
		}

		return gitHubFile;
	}

	private String _getCIMergeSHA() throws InvalidJSONException {
		GitHubFile ciMergeGitHubFile = _getCIMergeGitHubFile();

		if (ciMergeGitHubFile == null) {
			return null;
		}

		Matcher ciMergeSHAMatcher = _ciMergeSHAPattern.matcher(
			ciMergeGitHubFile.getPatch());

		if (!ciMergeSHAMatcher.find()) {
			return null;
		}

		return ciMergeSHAMatcher.group();
	}

	private String _getSubrepoReceiverName()
		throws InvalidJSONException, IOException {

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		String subrepoMergeReceiverName = getJenkinsBranchBuildPropertyValue(
			"subrepo.merge.receiver.name",
			gitHubPullRequest.getBaseBranchName());

		if (StringUtil.isNullOrEmpty(subrepoMergeReceiverName)) {
			return "liferay";
		}

		return subrepoMergeReceiverName;
	}

	private URL _getSubrepositoryBranchURL() throws InvalidJSONException {
		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		GitHubUser originGitHubUser = gitHubPullRequest.getOriginGitHubUser();

		return StringUtil.toURL(
			StringUtil.combine(
				"https://github.com/", originGitHubUser.getName(), "/",
				_getSubrepositoryName(), "/tree/",
				gitHubPullRequest.getBaseBranchName()));
	}

	private String _getSubrepositoryName() throws InvalidJSONException {
		if (_subrepositoryName != null) {
			return _subrepositoryName;
		}

		GitHubFile ciMergeGitHubFile = _getCIMergeGitHubFile();

		if (ciMergeGitHubFile == null) {
			return null;
		}

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		GitHubUser receiverGitHubUser =
			gitHubPullRequest.getReceiverGitHubUser();

		String ciMergeGitHubFileName = ciMergeGitHubFile.getName();

		String gitRepoFilePath = StringUtil.replace(
			ciMergeGitHubFileName, "/ci-merge", "/.gitrepo");

		URL gitRepoFileURL = StringUtil.toURL(
			StringUtil.combine(
				"https://raw.githubusercontent.com/",
				receiverGitHubUser.getName(), "/",
				gitHubPullRequest.getBaseRepositoryName(), "/",
				gitHubPullRequest.getBaseBranchName(), "/", gitRepoFilePath));

		GitHubClient gitHubClient = Jethr0ContextUtil.getGitHubClient();

		Matcher gitRepoMatcher = _gitRepoPattern.matcher(
			gitHubClient.requestGet(gitRepoFileURL));

		if (!gitRepoMatcher.find()) {
			return null;
		}

		_subrepositoryName = gitRepoMatcher.group("subrepositoryName");

		return _subrepositoryName;
	}

	private boolean _hasRequiredPassingTests() throws InvalidJSONException {
		if (_forceMerge()) {
			return true;
		}

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		Set<GitHubStatus> gitHubStatuses =
			gitHubPullRequest.getGitHubStatuses();

		GitHubStatus relevantGitHubStatus = null;
		GitHubStatus sfGitHubStatus = null;

		for (GitHubStatus gitHubStatus : gitHubStatuses) {
			if (Objects.equals(
					gitHubStatus.getContext(), "liferay/ci:test:relevant")) {

				relevantGitHubStatus = gitHubStatus;
			}

			if (Objects.equals(
					gitHubStatus.getContext(), "liferay/ci:test:sf")) {

				sfGitHubStatus = gitHubStatus;
			}
		}

		if ((relevantGitHubStatus == null) || (sfGitHubStatus == null) ||
			!Objects.equals(relevantGitHubStatus.getState(), "success") ||
			!Objects.equals(sfGitHubStatus.getState(), "success")) {

			return false;
		}

		return true;
	}

	private boolean _isValidCIMergeFile() throws InvalidJSONException {
		GitHubFile ciMergeGitHubFile = _getCIMergeGitHubFile();

		if (ciMergeGitHubFile == null) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactory.getLog(
		MergeGitHubCommentEventHandler.class);

	private static final Pattern _ciMergeSHAPattern = Pattern.compile(
		"\\+([0-9a-f]{40})");
	private static final Pattern _gitRepoPattern = Pattern.compile(
		"remote = git@github.com\\:[^/]+/(?<subrepositoryName>[^\\.]+)\\.git");

	private String _subrepositoryName;

}