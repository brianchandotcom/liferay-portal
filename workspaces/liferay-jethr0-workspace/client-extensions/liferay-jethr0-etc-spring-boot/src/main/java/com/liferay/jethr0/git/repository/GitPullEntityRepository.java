/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.git.dalo.GitPullEntityDALO;
import com.liferay.jethr0.git.dalo.GitPullToJobsEntityRelationshipDALO;
import com.liferay.jethr0.git.pull.GitPullEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.PullRequestJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitPullEntityRepository
	extends BaseEntityRepository<GitPullEntity> {

	@Override
	public GitPullEntityDALO getEntityDALO() {
		return _gitPullEntityDALO;
	}

	public void relateGitPullToJob(
		GitPullEntity gitPullEntity, JobEntity jobEntity) {

		if (jobEntity instanceof PullRequestJobEntity) {
			PullRequestJobEntity pullRequestJobEntity =
				(PullRequestJobEntity)jobEntity;

			gitPullEntity.addJobEntity(pullRequestJobEntity);

			pullRequestJobEntity.setGitPullEntity(gitPullEntity);
		}
	}

	public void setJobEntityRepository(
		JobEntityRepository jobEntityRepository) {

		_jobEntityRepository = jobEntityRepository;
	}

	@Override
	protected GitPullEntity updateRelationshipsFromDALO(
		GitPullEntity gitPullEntity) {

		return _updateGitCommitToJobRelationshipsFromDALO(gitPullEntity);
	}

	@Override
	protected GitPullEntity updateRelationshipsToDALO(
		GitPullEntity gitPullEntity) {

		_gitPullToJobsEntityRelationshipDALO.updateChildEntities(gitPullEntity);

		return gitPullEntity;
	}

	private GitPullEntity _updateGitCommitToJobRelationshipsFromDALO(
		GitPullEntity parentGitPullEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentGitPullEntity, _gitPullToJobsEntityRelationshipDALO,
			_jobEntityRepository,
			(gitPullEntity, jobEntity) -> relateGitPullToJob(
				gitPullEntity, jobEntity),
			gitPullEntity -> gitPullEntity.getJobEntities(),
			(gitPullEntity, jobEntity) -> gitPullEntity.removeJobEntity(
				jobEntity));
	}

	@Autowired
	private GitPullEntityDALO _gitPullEntityDALO;

	@Autowired
	private GitPullToJobsEntityRelationshipDALO
		_gitPullToJobsEntityRelationshipDALO;

	private JobEntityRepository _jobEntityRepository;

}