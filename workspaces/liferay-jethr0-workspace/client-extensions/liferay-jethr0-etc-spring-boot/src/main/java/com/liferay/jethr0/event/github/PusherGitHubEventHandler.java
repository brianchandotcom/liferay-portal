/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.event.github.commit.GitHubCommit;
import com.liferay.jethr0.event.github.repository.GitHubRepository;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.commit.GitCommitEntity;
import com.liferay.jethr0.git.repository.GitBranchEntityRepository;
import com.liferay.jethr0.git.repository.GitCommitEntityRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.MergeCentralSubrepositoryJobEntity;
import com.liferay.jethr0.job.RepositoryArchiveJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.routine.UpstreamBranchRoutineEntity;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.JobUtil;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PusherGitHubEventHandler extends BaseGitHubEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		GitBranchEntity gitBranchEntity = _getGitBranchEntity();

		GitHubCommit headGitHubCommit = _getHeadGitHubCommit();

		if ((gitBranchEntity != null) && (headGitHubCommit != null)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringUtil.combine(
						"Pusher started for ", gitBranchEntity.getURL(), " at ",
						headGitHubCommit.getShortSHA()));
			}
		}

		_updateUpstreamGitBranchEntity();
		_updateUpstreamGitBranchMirror();

		_syncCentralSubrepository();

		if ((gitBranchEntity != null) && (headGitHubCommit != null)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringUtil.combine(
						"Pusher completed for ", gitBranchEntity.getURL(),
						" at ", headGitHubCommit.getShortSHA()));
			}
		}

		return null;
	}

	protected PusherGitHubEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private JobEntity _createMergeCentralSubrepositoryJobEntity()
		throws InvalidJSONException {

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

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

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Created a merge central subrepository job ",
					jobEntityRepository.getEntityDALO(), " at ",
					StringUtil.toString(new Date())));
		}

		return mergeCentralSubrepositoryJobEntity;
	}

	private GitBranchEntity _getGitBranchEntity() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		String refName = messageJSONObject.optString("ref");

		refName = refName.replaceAll(".*/([^/]+)", "$1");

		GitBranchEntityRepository gitBranchEntityRepository =
			Jethr0ContextUtil.getGitBranchEntityRepository();

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

		GitHubFactory gitHubFactory = Jethr0ContextUtil.getGitHubFactory();

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

		if ((gitBranchEntity == null) ||
			(gitBranchEntity.getType() != GitBranchEntity.Type.UPSTREAM)) {

			return false;
		}

		return true;
	}

	private void _syncCentralSubrepository() throws InvalidJSONException {
		if (!_isUpstreamGitBranchEntity()) {
			return;
		}

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

		BuildQueue buildQueue = Jethr0ContextUtil.getBuildQueue();
		GitCommitEntityRepository gitCommitEntityRepository =
			Jethr0ContextUtil.getGitCommitEntityRepository();
		BuildEntityRepository buildEntityRepository =
			Jethr0ContextUtil.getBuildEntityRepository();
		JenkinsQueue jenkinsQueue = Jethr0ContextUtil.getJenkinsQueue();
		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		GitCommitEntity latestGitCommitEntity =
			gitCommitEntityRepository.createGitCommitEntity(
				gitBranchEntity, headGitHubCommit.getSHA());

		for (RoutineEntity routineEntity :
				gitBranchEntity.getRoutineEntities()) {

			if (routineEntity.getType() != RoutineEntity.Type.UPSTREAM_BRANCH) {
				continue;
			}

			UpstreamBranchRoutineEntity upstreamBranchRoutineEntity =
				(UpstreamBranchRoutineEntity)routineEntity;

			GitCommitEntity previousGitCommitEntity =
				upstreamBranchRoutineEntity.getPreviousGitCommitEntity();

			if ((latestGitCommitEntity == null) ||
				((previousGitCommitEntity != null) &&
				 Objects.equals(
					 latestGitCommitEntity.getSHA(),
					 previousGitCommitEntity.getSHA()))) {

				continue;
			}

			upstreamBranchRoutineEntity.setPreviousGitCommitEntity(
				latestGitCommitEntity);

			JobEntity jobEntity = jobEntityRepository.create(
				routineEntity,
				JobUtil.getUpdateJobEntityName(routineEntity.getJobName()),
				routineEntity.getJobParameters(),
				routineEntity.getJobPriority(), null, JobEntity.State.QUEUED,
				routineEntity.getJobType());

			try {
				for (JSONObject initialBuildJSONObject :
						jobEntity.getInitialBuildJSONObjects()) {

					BuildEntity buildEntity = buildEntityRepository.create(
						jobEntity, initialBuildJSONObject);

					buildEntity.setJobEntity(jobEntity);

					jobEntity.addBuildEntity(buildEntity);
				}

				if (jobEntity.getState() == JobEntity.State.QUEUED) {
					buildQueue.addJobEntity(jobEntity);

					jenkinsQueue.invoke();
				}

				if ((gitBranchEntity != null) && (headGitHubCommit != null)) {
					if (_log.isInfoEnabled()) {
						_log.info(
							StringUtil.combine(
								"Pusher started job ", jobEntity.getId()));
					}
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		}

		GitBranchEntityRepository gitBranchEntityRepository =
			Jethr0ContextUtil.getGitBranchEntityRepository();

		gitBranchEntityRepository.update(gitBranchEntity);
	}

	private void _updateUpstreamGitBranchMirror() throws InvalidJSONException {
		if (!_isUpstreamGitBranchEntity() ||
			!_isGitHubRepositoryMirrorCandidate() ||
			!_isGitHubUserMirrorCandidate()) {

			return;
		}

		GitBranchEntity gitBranchEntity = _getGitBranchEntity();

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

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

	private static final Log _log = LogFactory.getLog(
		PusherGitHubEventHandler.class);

	private static final Pattern _pattern = Pattern.compile("7\\.\\d\\.x");

}