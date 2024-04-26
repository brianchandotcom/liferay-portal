/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.git.dalo.GitPullRequestEntityDALO;
import com.liferay.jethr0.git.dalo.GitPullRequestToJobsEntityRelationshipDALO;
import com.liferay.jethr0.git.pullrequest.GitPullRequestEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.PullRequestJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitPullRequestEntityRepository
	extends BaseEntityRepository<GitPullRequestEntity> {

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

	@Autowired
	private GitPullRequestEntityDALO _gitPullRequestEntityDALO;

	@Autowired
	private GitPullRequestToJobsEntityRelationshipDALO
		_gitPullRequestToJobsEntityRelationshipDALO;

	private JobEntityRepository _jobEntityRepository;

}