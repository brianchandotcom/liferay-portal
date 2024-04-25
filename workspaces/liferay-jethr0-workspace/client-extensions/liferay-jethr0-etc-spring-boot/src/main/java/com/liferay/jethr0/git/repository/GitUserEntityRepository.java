/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.event.github.ref.GitHubRef;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.dalo.GitUserEntityDALO;
import com.liferay.jethr0.git.dalo.GitUserToGitBranchesEntityRelationshipDALO;
import com.liferay.jethr0.git.dalo.ReceiverGitUserToGitPullsEntityRelationshipDALO;
import com.liferay.jethr0.git.dalo.SenderGitUserToGitPullsEntityRelationshipDALO;
import com.liferay.jethr0.git.pull.GitPullEntity;
import com.liferay.jethr0.git.user.GitUserEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Objects;
import java.util.Set;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitUserEntityRepository
	extends BaseEntityRepository<GitUserEntity> {

	@Override
	public void initialize() {
		addAll(_gitUserEntityDALO.getAll());
	}

	public GitUserEntity createGitUserEntity(GitHubRef gitHubRef) {
		URL url = StringUtil.toURL(
			"https://github.com/" + gitHubRef.getUserName());

		GitUserEntity gitUserEntity = getByURL(url);

		if (gitUserEntity != null) {
			return gitUserEntity;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("url", String.valueOf(url));

		return create(jsonObject);
	}

	public GitUserEntity getByURL(URL url) {
		for (GitUserEntity gitUserEntity : getAll()) {
			if (Objects.equals(gitUserEntity.getURL(), url)) {
				return gitUserEntity;
			}
		}

		return null;
	}

	@Override
	public GitUserEntityDALO getEntityDALO() {
		return _gitUserEntityDALO;
	}

	public void relateGitUserToGitBranch(
		GitUserEntity gitUserEntity, GitBranchEntity gitBranchEntity) {

		gitUserEntity.addGitBranchEntity(gitBranchEntity);

		gitBranchEntity.setGitUserEntity(gitUserEntity);
	}

	public void relateReceiverGitUserToGitPull(
		GitUserEntity gitUserEntity, GitPullEntity gitPullEntity) {

		gitUserEntity.addGitPullEntity(gitPullEntity);

		gitPullEntity.setReceiverGitUserEntity(gitUserEntity);
	}

	public void relateSenderGitUserToGitPull(
		GitUserEntity gitUserEntity, GitPullEntity gitPullEntity) {

		gitUserEntity.addGitPullEntity(gitPullEntity);

		gitPullEntity.setSenderGitUserEntity(gitUserEntity);
	}

	public void setGitBranchEntityRepository(
		GitBranchEntityRepository gitBranchEntityRepository) {

		_gitBranchEntityRepository = gitBranchEntityRepository;
	}

	public void setGitPullEntityRepository(
		GitPullEntityRepository gitPullEntityRepository) {

		_gitPullEntityRepository = gitPullEntityRepository;
	}

	@Override
	protected GitUserEntity updateRelationshipsFromDALO(
		GitUserEntity gitUserEntity) {

		gitUserEntity = _updateGitUserToGitBranchRelationshipsFromDALO(
			gitUserEntity);
		gitUserEntity = _updateReceiverGitUserToGitPullRelationshipsFromDALO(
			gitUserEntity);

		return _updateSenderGitUserToGitPullRelationshipsFromDALO(
			gitUserEntity);
	}

	@Override
	protected GitUserEntity updateRelationshipsToDALO(
		GitUserEntity gitUserEntity) {

		_gitUserToGitBranchesEntityRelationshipDALO.updateChildEntities(
			gitUserEntity);
		_receiverGitUserToGitPullsEntityRelationshipDALO.updateChildEntities(
			gitUserEntity);
		_senderGitUserToGitPullsEntityRelationshipDALO.updateChildEntities(
			gitUserEntity);

		return gitUserEntity;
	}

	private GitUserEntity _updateGitUserToGitBranchRelationshipsFromDALO(
		GitUserEntity parentGitUserEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentGitUserEntity, _gitUserToGitBranchesEntityRelationshipDALO,
			_gitBranchEntityRepository,
			(gitUserEntity, gitBranchEntity) -> relateGitUserToGitBranch(
				gitUserEntity, gitBranchEntity),
			gitUserEntity -> gitUserEntity.getGitBranchEntities(),
			(gitUserEntity, gitBranchEntity) ->
				gitUserEntity.removeGitBranchEntity(gitBranchEntity));
	}

	private GitUserEntity _updateReceiverGitUserToGitPullRelationshipsFromDALO(
		GitUserEntity parentGitUserEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentGitUserEntity,
			_receiverGitUserToGitPullsEntityRelationshipDALO,
			_gitPullEntityRepository,
			(gitUserEntity, gitPullEntity) -> relateReceiverGitUserToGitPull(
				gitUserEntity, gitPullEntity),
			gitUserEntity -> gitUserEntity.getGitPullEntities(),
			(gitUserEntity, gitPullEntity) -> gitUserEntity.removeGitPullEntity(
				gitPullEntity));
	}

	private GitUserEntity _updateSenderGitUserToGitPullRelationshipsFromDALO(
		GitUserEntity parentGitUserEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentGitUserEntity, _senderGitUserToGitPullsEntityRelationshipDALO,
			_gitPullEntityRepository,
			(gitUserEntity, gitPullEntity) -> relateSenderGitUserToGitPull(
				gitUserEntity, gitPullEntity),
			gitUserEntity -> gitUserEntity.getGitPullEntities(),
			(gitUserEntity, gitPullEntity) -> gitUserEntity.removeGitPullEntity(
				gitPullEntity));
	}

	private GitBranchEntityRepository _gitBranchEntityRepository;
	private GitPullEntityRepository _gitPullEntityRepository;

	@Autowired
	private GitUserEntityDALO _gitUserEntityDALO;

	@Autowired
	private GitUserToGitBranchesEntityRelationshipDALO
		_gitUserToGitBranchesEntityRelationshipDALO;

	@Autowired
	private ReceiverGitUserToGitPullsEntityRelationshipDALO
		_receiverGitUserToGitPullsEntityRelationshipDALO;

	@Autowired
	private SenderGitUserToGitPullsEntityRelationshipDALO
		_senderGitUserToGitPullsEntityRelationshipDALO;

}