/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.dalo.GitUserEntityDALO;
import com.liferay.jethr0.git.dalo.GitUserToGitBranchesEntityRelationshipDALO;
import com.liferay.jethr0.git.user.GitUserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitUserEntityRepository
	extends BaseEntityRepository<GitUserEntity> {

	@Override
	public GitUserEntityDALO getEntityDALO() {
		return _gitUserEntityDALO;
	}

	public void relateGitUserToGitPull(
		GitUserEntity gitUserEntity, GitBranchEntity gitBranchEntity) {

		gitUserEntity.addGitBranchEntity(gitBranchEntity);

		gitBranchEntity.setGitUserEntity(gitUserEntity);
	}

	public void setGitBranchEntityRepository(
		GitBranchEntityRepository gitBranchEntityRepository) {

		_gitBranchEntityRepository = gitBranchEntityRepository;
	}

	@Override
	protected GitUserEntity updateRelationshipsFromDALO(
		GitUserEntity gitUserEntity) {

		return _updateGitUserToGitPullRelationshipsFromDALO(gitUserEntity);
	}

	@Override
	protected GitUserEntity updateRelationshipsToDALO(
		GitUserEntity gitUserEntity) {

		_gitUserToGitBranchesEntityRelationshipDALO.updateChildEntities(
			gitUserEntity);

		return gitUserEntity;
	}

	private GitUserEntity _updateGitUserToGitPullRelationshipsFromDALO(
		GitUserEntity parentGitUserEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentGitUserEntity, _gitUserToGitBranchesEntityRelationshipDALO,
			_gitBranchEntityRepository,
			(gitUserEntity, gitBranchEntity) -> relateGitUserToGitPull(
				gitUserEntity, gitBranchEntity),
			gitUserEntity -> gitUserEntity.getGitBranchEntities(),
			(gitUserEntity, gitBranchEntity) ->
				gitUserEntity.removeGitBranchEntity(gitBranchEntity));
	}

	private GitBranchEntityRepository _gitBranchEntityRepository;

	@Autowired
	private GitUserEntityDALO _gitUserEntityDALO;

	@Autowired
	private GitUserToGitBranchesEntityRelationshipDALO
		_gitUserToGitBranchesEntityRelationshipDALO;

}