/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.branch.repository.GitBranchEntityRepository;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.routine.UpstreamBranchCronRoutineEntity;
import com.liferay.jethr0.routine.dalo.RoutineEntityDALO;
import com.liferay.jethr0.routine.dalo.RoutineToJobsEntityRelationshipDALO;
import com.liferay.jethr0.routine.dalo.RoutinesToGitBranchesEntityRelationshipDALO;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class RoutineEntityRepository
	extends BaseEntityRepository<RoutineEntity> {

	@Override
	public RoutineEntityDALO getEntityDALO() {
		return _routineEntityDALO;
	}

	@Override
	public void initialize() {
		addAll(_routineEntityDALO.getAll());
	}

	public void relateRoutineToGitBranch(
		RoutineEntity routineEntity, GitBranchEntity gitBranchEntity) {

		if (routineEntity instanceof UpstreamBranchCronRoutineEntity) {
			UpstreamBranchCronRoutineEntity upstreamBranchCronRoutineEntity =
				(UpstreamBranchCronRoutineEntity)routineEntity;

			upstreamBranchCronRoutineEntity.addGitBranchEntity(gitBranchEntity);

			gitBranchEntity.addRoutineEntity(routineEntity);
		}
	}

	public void relateRoutineToJob(
		RoutineEntity routineEntity, JobEntity jobEntity) {

		routineEntity.addJobEntity(jobEntity);

		jobEntity.setRoutineEntity(routineEntity);
	}

	public void setGitBranchEntityRepository(
		GitBranchEntityRepository gitBranchEntityRepository) {

		_gitBranchEntityRepository = gitBranchEntityRepository;
	}

	public void setJobEntityRepository(
		JobEntityRepository jobEntityRepository) {

		_jobEntityRepository = jobEntityRepository;
	}

	@Override
	protected RoutineEntity updateRelationshipsFromDALO(
		RoutineEntity routineEntity) {

		return _updateRoutineToJobRelationshipsFromDALO(routineEntity);
	}

	@Override
	protected RoutineEntity updateRelationshipsToDALO(
		RoutineEntity routineEntity) {

		_routineToJobsEntityRelationshipDALO.updateChildEntities(routineEntity);

		return routineEntity;
	}

	private RoutineEntity _updateRoutineToJobRelationshipsFromDALO(
		RoutineEntity parentRoutineEntity) {

		updateParentToChildRelationshipsFromDALO(
			parentRoutineEntity, _routinesToGitBranchesEntityRelationshipDALO,
			_gitBranchEntityRepository,
			(routineEntity, gitBranchEntity) -> relateRoutineToGitBranch(
				routineEntity, gitBranchEntity),
			routineEntity -> {
				if (routineEntity instanceof UpstreamBranchCronRoutineEntity) {
					UpstreamBranchCronRoutineEntity
						upstreamBranchCronRoutineEntity =
							(UpstreamBranchCronRoutineEntity)routineEntity;

					return upstreamBranchCronRoutineEntity.
						getGitBranchEntities();
				}

				return new HashSet<>();
			},
			(routineEntity, gitBranchEntity) -> {
				if (routineEntity instanceof UpstreamBranchCronRoutineEntity) {
					UpstreamBranchCronRoutineEntity
						upstreamBranchCronRoutineEntity =
							(UpstreamBranchCronRoutineEntity)routineEntity;

					upstreamBranchCronRoutineEntity.removeGitBranchEntity(
						gitBranchEntity);
				}
			});

		return updateParentToChildRelationshipsFromDALO(
			parentRoutineEntity, _routineToJobsEntityRelationshipDALO,
			_jobEntityRepository,
			(routineEntity, jobEntity) -> relateRoutineToJob(
				routineEntity, jobEntity),
			jobEntity -> jobEntity.getJobEntities(),
			(routineEntity, jobEntity) -> routineEntity.removeJobEntity(
				jobEntity));
	}

	private GitBranchEntityRepository _gitBranchEntityRepository;
	private JobEntityRepository _jobEntityRepository;

	@Autowired
	private RoutineEntityDALO _routineEntityDALO;

	@Autowired
	private RoutinesToGitBranchesEntityRelationshipDALO
		_routinesToGitBranchesEntityRelationshipDALO;

	@Autowired
	private RoutineToJobsEntityRelationshipDALO
		_routineToJobsEntityRelationshipDALO;

}