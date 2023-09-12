/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.gitbranch.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.gitbranch.GitBranchEntity;
import com.liferay.jethr0.gitbranch.dalo.GitBranchEntityDALO;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.dalo.JobsToGitBranchesEntityRelationshipDALO;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitBranchEntityRepository
	extends BaseEntityRepository<GitBranchEntity> {

	public Set<GitBranchEntity> getAll(JobEntity jobEntity) {
		Set<GitBranchEntity> gitBranchEntities = new HashSet<>();

		Set<Long> gitBranchEntityIds =
			_jobsToGitBranchesEntityRelationshipDALO.getChildEntityIds(
				jobEntity);

		for (GitBranchEntity gitBranchEntity : getAll()) {
			if (!gitBranchEntityIds.contains(gitBranchEntity.getId())) {
				continue;
			}

			gitBranchEntity.addJobEntity(jobEntity);

			jobEntity.addGitBranchEntity(gitBranchEntity);

			gitBranchEntities.add(gitBranchEntity);
		}

		return gitBranchEntities;
	}

	@Override
	public GitBranchEntityDALO getEntityDALO() {
		return _gitBranchEntityDALO;
	}

	@Autowired
	private GitBranchEntityDALO _gitBranchEntityDALO;

	@Autowired
	private JobsToGitBranchesEntityRelationshipDALO
		_jobsToGitBranchesEntityRelationshipDALO;

}