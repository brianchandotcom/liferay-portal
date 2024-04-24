/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.commit.GitHubCommit;
import com.liferay.jethr0.event.github.repository.GitHubRepository;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.repository.GitBranchEntityRepository;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.MergeCentralSubrepositoryJobEntity;
import com.liferay.jethr0.job.RepositoryArchiveJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PusherGitHubEventHandler extends BaseGitHubEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		_updateUpstreamGitBranchEntity();
		_updateUpstreamGitBranchMirror();

		_syncCentralSubrepository();

		return null;
	}

	protected PusherGitHubEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	private JobEntity _createMergeCentralSubrepositoryJobEntity()
		throws InvalidJSONException {

		JobEntityRepository jobEntityRepository = getJobEntityRepository();

		GitBranchEntity gitBranchEntity = _getGitBranchEntity();

		String jobName = StringUtil.combine(
			"Merge Central Subrepository (",
			gitBranchEntity.getRepositoryName(), "/", gitBranchEntity.getName(),
			"[", gitBranchEntity.getShortLatestSHA(), "])");

		JobEntity jobEntity = jobEntityRepository.create(
			null, jobName, null, 3, null, JobEntity.State.OPENED,
			JobEntity.Type.MERGE_CENTRAL_SUBREPOSITORY);

		if (!(jobEntity instanceof MergeCentralSubrepositoryJobEntity)) {
			throw new RuntimeException("Invalid job type");
		}

		MergeCentralSubrepositoryJobEntity mergeCentralSubrepositoryJobEntity =
			(MergeCentralSubrepositoryJobEntity)jobEntity;

		mergeCentralSubrepositoryJobEntity.setPortalUpstreamBranchName(
			gitBranchEntity.getName());

		jobEntityRepository.update(mergeCentralSubrepositoryJobEntity);

		return mergeCentralSubrepositoryJobEntity;
	}

	private GitBranchEntity _getGitBranchEntity() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		String refName = messageJSONObject.optString("ref");

		refName = refName.replaceAll(".*/([^/]+)", "$1");

		GitBranchEntityRepository gitBranchEntityRepository =
			getGitBranchEntityRepository();

		GitHubRepository gitHubRepository = getGitHubRepository();

		try {
			return gitBranchEntityRepository.getByURL(
				new URL(gitHubRepository.getHTMLURL() + "/tree/" + refName));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private GitHubCommit _getHeadGitHubCommit() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject headCommitJSONObject = messageJSONObject.optJSONObject(
			"head_commit");

		if (headCommitJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"head_commit\" from message JSON");
		}

		GitHubFactory gitHubFactory = getGitHubFactory();

		return gitHubFactory.newGitHubCommit(headCommitJSONObject);
	}

	private boolean _isGitHubRepositoryMirrorCandidate()
		throws InvalidJSONException {

		GitHubRepository gitHubRepository = getGitHubRepository();

		String gitHubRepositoryName = gitHubRepository.getName();

		if (!gitHubRepositoryName.equals("liferay-jenkins-ee")) {
			return false;
		}

		return true;
	}

	private boolean _isGitHubUserMirrorCandidate() throws InvalidJSONException {
		GitHubRepository gitHubRepository = getGitHubRepository();

		GitHubUser gitHubUser = gitHubRepository.getGitHubUser();

		String gitHubUserName = gitHubUser.getName();

		if (!gitHubUserName.equals("liferay")) {
			return false;
		}

		return true;
	}

	private boolean _isUpstreamGitBranchEntity() throws InvalidJSONException {
		GitBranchEntity gitBranchEntity = _getGitBranchEntity();

		if (gitBranchEntity.getType() != GitBranchEntity.Type.UPSTREAM) {
			return false;
		}

		return true;
	}

	private void _syncCentralSubrepository() throws InvalidJSONException {
		GitBranchEntity gitBranchEntity = _getGitBranchEntity();

		Matcher matcher = _pattern.matcher(gitBranchEntity.getName());

		if (!matcher.matches()) {
			return;
		}

		GitHubRepository gitHubRepository = getGitHubRepository();

		String gitHubRepositoryName = gitHubRepository.getName();

		if (!gitHubRepositoryName.startsWith("com-liferay-")) {
			return;
		}

		GitHubUser gitHubUser = gitHubRepository.getGitHubUser();

		if (!Objects.equals(gitHubUser.getName(), "liferay")) {
			return;
		}

		_createMergeCentralSubrepositoryJobEntity();
	}

	private void _updateUpstreamGitBranchEntity() throws InvalidJSONException {
		if (!_isUpstreamGitBranchEntity()) {
			return;
		}

		GitBranchEntity gitBranchEntity = _getGitBranchEntity();

		GitHubCommit headGitHubCommit = _getHeadGitHubCommit();

		gitBranchEntity.setLatestSHA(headGitHubCommit.getSHA());

		GitBranchEntityRepository gitBranchEntityRepository =
			getGitBranchEntityRepository();

		gitBranchEntityRepository.update(gitBranchEntity);
	}

	private void _updateUpstreamGitBranchMirror() throws InvalidJSONException {
		if (!_isUpstreamGitBranchEntity() ||
			!_isGitHubRepositoryMirrorCandidate() ||
			!_isGitHubUserMirrorCandidate()) {

			return;
		}

		GitBranchEntity gitBranchEntity = _getGitBranchEntity();

		JobEntityRepository jobEntityRepository = getJobEntityRepository();

		JobEntity jobEntity = jobEntityRepository.create(
			null,
			StringUtil.combine(
				"Repository Archive (", gitBranchEntity.getRepositoryName(),
				"/", gitBranchEntity.getName(), ")"),
			null, 1, new Date(), JobEntity.State.OPENED,
			JobEntity.Type.REPOSITORY_ARCHIVE);

		if (!(jobEntity instanceof RepositoryArchiveJobEntity)) {
			return;
		}

		RepositoryArchiveJobEntity repositoryArchiveJobEntity =
			(RepositoryArchiveJobEntity)jobEntity;

		repositoryArchiveJobEntity.setRepositoryNames(
			gitBranchEntity.getRepositoryName());

		invokeJobEntity(repositoryArchiveJobEntity);
	}

	private static final Pattern _pattern = Pattern.compile("7\\.\\d\\.x");

}