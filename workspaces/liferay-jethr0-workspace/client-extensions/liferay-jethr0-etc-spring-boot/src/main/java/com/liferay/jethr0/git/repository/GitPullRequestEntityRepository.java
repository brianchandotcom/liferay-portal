/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.event.github.GitHubFactory;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.dalo.GitPullRequestEntityDALO;
import com.liferay.jethr0.git.dalo.GitPullRequestToJobsEntityRelationshipDALO;
import com.liferay.jethr0.git.pullrequest.GitPullRequestEntity;
import com.liferay.jethr0.git.user.GitUserEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.PullRequestJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitPullRequestEntityRepository
	extends BaseEntityRepository<GitPullRequestEntity> {

	public GitPullRequestEntity create(URL pullRequestURL) {
		GitPullRequestEntity gitPullRequestEntity = getByURL(pullRequestURL);

		if (gitPullRequestEntity != null) {
			return gitPullRequestEntity;
		}

		Matcher pullRequestURLMatcher = _pullRequestURLPattern.matcher(
			String.valueOf(pullRequestURL));

		if (!pullRequestURLMatcher.find()) {
			throw new RuntimeException("Invalid Pull Request URL");
		}

		URL pullRequestAPIURL = StringUtil.toURL(
			StringUtil.combine(
				"https://api.github.com/repos/",
				pullRequestURLMatcher.group("userName"), "/",
				pullRequestURLMatcher.group("repositoryName"), "/pulls/",
				pullRequestURLMatcher.group("number")));

		GitHubPullRequest gitHubPullRequest =
			_gitHubFactory.newGitHubPullRequest(
				new JSONObject(_gitHubClient.requestGet(pullRequestAPIURL)));

		if (gitHubPullRequest == null) {
			throw new RuntimeException("Invalid Pull Request URL");
		}

		GitBranchEntity baseGitBranchEntity =
			_gitBranchEntityRepository.createGitBranchEntity(
				gitHubPullRequest.getBaseGitHubRef());
		GitBranchEntity headGitBranchEntity =
			_gitBranchEntityRepository.createGitBranchEntity(
				gitHubPullRequest.getHeadGitHubRef());
		GitUserEntity receiverGitUserEntity =
			_gitUserEntityRepository.createGitUserEntity(
				gitHubPullRequest.getReceiverGitHubUser());
		GitUserEntity senderGitUserEntity =
			_gitUserEntityRepository.createGitUserEntity(
				gitHubPullRequest.getSenderGitHubUser());

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"r_baseGitBranchToGitPullRequests_c_gitBranchId",
			baseGitBranchEntity.getId()
		).put(
			"r_headGitBranchToGitPullRequests_c_gitBranchId",
			headGitBranchEntity.getId()
		).put(
			"r_receiverGitUserToGitPullRequests_c_gitUserId",
			receiverGitUserEntity.getId()
		).put(
			"r_senderGitUserToGitPullRequests_c_gitUserId",
			senderGitUserEntity.getId()
		).put(
			"url", pullRequestURL
		);

		return create(jsonObject);
	}

	public GitPullRequestEntity getByURL(URL pullRequestURL) {
		for (GitPullRequestEntity gitPullRequestEntity : getAll()) {
			if (Objects.equals(gitPullRequestEntity.getURL(), pullRequestURL)) {
				return gitPullRequestEntity;
			}
		}

		return null;
	}

	@Override
	public GitPullRequestEntityDALO getEntityDALO() {
		return _gitPullRequestEntityDALO;
	}

	public void relateGitPullToJob(
		GitPullRequestEntity gitPullRequestEntity, JobEntity jobEntity) {

		if (jobEntity instanceof PullRequestJobEntity) {
			PullRequestJobEntity pullRequestJobEntity =
				(PullRequestJobEntity)jobEntity;

			gitPullRequestEntity.addJobEntity(pullRequestJobEntity);

			pullRequestJobEntity.setGitPullRequestEntity(gitPullRequestEntity);
		}
	}

	public void setGitBranchEntityRepository(
		GitBranchEntityRepository gitBranchEntityRepository) {

		_gitBranchEntityRepository = gitBranchEntityRepository;
	}

	public void setGitUserEntityRepository(
		GitUserEntityRepository gitUserEntityRepository) {

		_gitUserEntityRepository = gitUserEntityRepository;
	}

	public void setJobEntityRepository(
		JobEntityRepository jobEntityRepository) {

		_jobEntityRepository = jobEntityRepository;
	}

	@Override
	protected GitPullRequestEntity updateRelationshipsFromDALO(
		GitPullRequestEntity gitPullRequestEntity) {

		return _updateGitCommitToJobRelationshipsFromDALO(gitPullRequestEntity);
	}

	@Override
	protected GitPullRequestEntity updateRelationshipsToDALO(
		GitPullRequestEntity gitPullRequestEntity) {

		_gitPullRequestToJobsEntityRelationshipDALO.updateChildEntities(
			gitPullRequestEntity);

		return gitPullRequestEntity;
	}

	private GitPullRequestEntity _updateGitCommitToJobRelationshipsFromDALO(
		GitPullRequestEntity parentGitPullRequestEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentGitPullRequestEntity,
			_gitPullRequestToJobsEntityRelationshipDALO, _jobEntityRepository,
			(gitPullRequestEntity, jobEntity) -> relateGitPullToJob(
				gitPullRequestEntity, jobEntity),
			gitPullRequestEntity -> gitPullRequestEntity.getJobEntities(),
			(gitPullRequestEntity, jobEntity) ->
				gitPullRequestEntity.removeJobEntity(jobEntity));
	}

	private static final Pattern _pullRequestURLPattern = Pattern.compile(
		"https://github.com/(?<userName>[^/]+)/(?<repositoryName>[^/]+)/pull/" +
			"(?<number>\\d+)");

	private GitBranchEntityRepository _gitBranchEntityRepository;

	@Autowired
	private GitHubClient _gitHubClient;

	@Autowired
	private GitHubFactory _gitHubFactory;

	@Autowired
	private GitPullRequestEntityDALO _gitPullRequestEntityDALO;

	@Autowired
	private GitPullRequestToJobsEntityRelationshipDALO
		_gitPullRequestToJobsEntityRelationshipDALO;

	private GitUserEntityRepository _gitUserEntityRepository;
	private JobEntityRepository _jobEntityRepository;

}