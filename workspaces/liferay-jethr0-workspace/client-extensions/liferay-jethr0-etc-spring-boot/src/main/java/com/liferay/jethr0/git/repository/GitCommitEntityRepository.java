/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.commit.GitCommitEntity;
import com.liferay.jethr0.git.dalo.GitCommitEntityDALO;
import com.liferay.jethr0.git.dalo.GitCommitToJobsEntityRelationshipDALO;
import com.liferay.jethr0.git.dalo.PreviousGitCommitToRoutinesEntityRelationshipDALO;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.routine.UpstreamBranchRoutineEntity;
import com.liferay.jethr0.routine.repository.RoutineEntityRepository;

import java.util.Objects;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitCommitEntityRepository
	extends BaseEntityRepository<GitCommitEntity> {

	public GitCommitEntity createGitCommitEntity(
		GitBranchEntity gitBranchEntity, String sha) {

		GitCommitEntity gitCommitEntity = getBySHA(sha);

		if (gitCommitEntity != null) {
			gitBranchEntity.addGitCommitEntity(gitCommitEntity);

			gitCommitEntity.setGitBranchEntity(gitBranchEntity);

			return gitCommitEntity;
		}

		JSONObject jsonObject = new JSONObject();

		if (gitBranchEntity != null) {
			jsonObject.put(
				"r_gitBranchToGitCommits_c_gitBranchId",
				gitBranchEntity.getId());
		}

		jsonObject.put("sha", sha);

		gitCommitEntity = create(jsonObject);

		if (gitBranchEntity != null) {
			gitBranchEntity.addGitCommitEntity(gitCommitEntity);
		}

		gitCommitEntity.setGitBranchEntity(gitBranchEntity);

		return gitCommitEntity;
	}

	public GitCommitEntity getBySHA(String sha) {
		for (GitCommitEntity gitCommitEntity : getAll()) {
			if (Objects.equals(gitCommitEntity.getSHA(), sha)) {
				return gitCommitEntity;
			}
		}

		return add(_gitCommitEntityDALO.getBySHA(sha));
	}

	@Override
	public GitCommitEntityDALO getEntityDALO() {
		return _gitCommitEntityDALO;
	}

	public void relateGitCommitToJob(
		GitCommitEntity gitCommitEntity, JobEntity jobEntity) {

		gitCommitEntity.addJobEntity(jobEntity);

		jobEntity.setGitCommitEntity(gitCommitEntity);
	}

	public void relateGitCommitToRoutine(
		GitCommitEntity gitCommitEntity, RoutineEntity routineEntity) {

		if (routineEntity instanceof UpstreamBranchRoutineEntity) {
			UpstreamBranchRoutineEntity upstreamBranchRoutineEntity =
				(UpstreamBranchRoutineEntity)routineEntity;

			gitCommitEntity.addRoutineEntity(upstreamBranchRoutineEntity);

			upstreamBranchRoutineEntity.setPreviousGitCommitEntity(
				gitCommitEntity);
		}
	}

	public void setJobEntityRepository(
		JobEntityRepository jobEntityRepository) {

		_jobEntityRepository = jobEntityRepository;
	}

	public void setRoutineEntityRepository(
		RoutineEntityRepository routineEntityRepository) {

		_routineEntityRepository = routineEntityRepository;
	}

	@Override
	protected GitCommitEntity updateRelationshipsFromDALO(
		GitCommitEntity gitCommitEntity) {

		gitCommitEntity = _updateGitCommitToJobRelationshipsFromDALO(
			gitCommitEntity);

		return _updateGitCommitToRoutineRelationshipsFromDALO(gitCommitEntity);
	}

	@Override
	protected GitCommitEntity updateRelationshipsToDALO(
		GitCommitEntity gitCommitEntity) {

		_gitCommitToJobsEntityRelationshipDALO.updateChildEntities(
			gitCommitEntity);
		_previousGitCommitToRoutinesEntityRelationshipDALO.updateChildEntities(
			gitCommitEntity);

		return gitCommitEntity;
	}

	private GitCommitEntity _updateGitCommitToJobRelationshipsFromDALO(
		GitCommitEntity parentGitCommitEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentGitCommitEntity, _gitCommitToJobsEntityRelationshipDALO,
			_jobEntityRepository,
			(gitCommitEntity, jobEntity) -> relateGitCommitToJob(
				gitCommitEntity, jobEntity),
			gitCommitEntity -> gitCommitEntity.getJobEntities(),
			(gitCommitEntity, jobEntity) -> gitCommitEntity.removeJobEntity(
				jobEntity));
	}

	private GitCommitEntity _updateGitCommitToRoutineRelationshipsFromDALO(
		GitCommitEntity parentGitCommitEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentGitCommitEntity,
			_previousGitCommitToRoutinesEntityRelationshipDALO,
			_routineEntityRepository,
			(gitCommitEntity, routineEntity) -> relateGitCommitToRoutine(
				gitCommitEntity, routineEntity),
			gitCommitEntity -> gitCommitEntity.getRoutineEntities(),
			(gitCommitEntity, routineEntity) ->
				gitCommitEntity.removeRoutineEntity(routineEntity));
	}

	@Autowired
	private GitCommitEntityDALO _gitCommitEntityDALO;

	@Autowired
	private GitCommitToJobsEntityRelationshipDALO
		_gitCommitToJobsEntityRelationshipDALO;

	private JobEntityRepository _jobEntityRepository;

	@Autowired
	private PreviousGitCommitToRoutinesEntityRelationshipDALO
		_previousGitCommitToRoutinesEntityRelationshipDALO;

	private RoutineEntityRepository _routineEntityRepository;

}