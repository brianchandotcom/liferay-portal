/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.event.github.ref.GitHubRef;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.dalo.GitUserEntityDALO;
import com.liferay.jethr0.git.dalo.GitUserToGitBranchesEntityRelationshipDALO;
import com.liferay.jethr0.git.dalo.ReceiverGitUserToGitPullRequestsEntityRelationshipDALO;
import com.liferay.jethr0.git.dalo.SenderGitUserToGitPullRequestsEntityRelationshipDALO;
import com.liferay.jethr0.git.pullrequest.GitPullRequestEntity;
import com.liferay.jethr0.git.user.GitUserEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Objects;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitUserEntityRepository
	extends BaseEntityRepository<GitUserEntity> {

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

	public GitUserEntity createGitUserEntity(GitHubUser gitHubUser) {
		URL url = gitHubUser.getHTMLURL();

		GitUserEntity gitUserEntity = getByURL(url);

		if (gitUserEntity != null) {
			return gitUserEntity;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("url", String.valueOf(url));

		return create(jsonObject);
	}

	public GitUserEntity createGitUserEntity(String userName) {
		URL url = StringUtil.toURL("https://github.com/" + userName);

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

	@Override
	public void initialize() {
		addAll(_gitUserEntityDALO.getAll());
	}

	public void relateGitUserToGitBranch(
		GitUserEntity gitUserEntity, GitBranchEntity gitBranchEntity) {

		gitUserEntity.addGitBranchEntity(gitBranchEntity);

		gitBranchEntity.setGitUserEntity(gitUserEntity);
	}

	public void relateReceiverGitUserToGitPull(
		GitUserEntity gitUserEntity,
		GitPullRequestEntity gitPullRequestEntity) {

		gitUserEntity.addGitPullRequestEntity(gitPullRequestEntity);

		gitPullRequestEntity.setReceiverGitUserEntity(gitUserEntity);
	}

	public void relateSenderGitUserToGitPull(
		GitUserEntity gitUserEntity,
		GitPullRequestEntity gitPullRequestEntity) {

		gitUserEntity.addGitPullRequestEntity(gitPullRequestEntity);

		gitPullRequestEntity.setSenderGitUserEntity(gitUserEntity);
	}

	public void setGitBranchEntityRepository(
		GitBranchEntityRepository gitBranchEntityRepository) {

		_gitBranchEntityRepository = gitBranchEntityRepository;
	}

	public void setGitPullRequestEntityRepository(
		GitPullRequestEntityRepository gitPullRequestEntityRepository) {

		_gitPullRequestEntityRepository = gitPullRequestEntityRepository;
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
		_receiverGitUserToGitPullRequestsEntityRelationshipDALO.
			updateChildEntities(gitUserEntity);
		_senderGitUserToGitPullRequestsEntityRelationshipDALO.
			updateChildEntities(gitUserEntity);

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
			_receiverGitUserToGitPullRequestsEntityRelationshipDALO,
			_gitPullRequestEntityRepository,
			(gitUserEntity, gitPullRequestEntity) ->
				relateReceiverGitUserToGitPull(
					gitUserEntity, gitPullRequestEntity),
			gitUserEntity -> gitUserEntity.getGitPullRequestEntities(),
			(gitUserEntity, gitPullRequestEntity) ->
				gitUserEntity.removeGitPullRequestEntity(gitPullRequestEntity));
	}

	private GitUserEntity _updateSenderGitUserToGitPullRelationshipsFromDALO(
		GitUserEntity parentGitUserEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentGitUserEntity,
			_senderGitUserToGitPullRequestsEntityRelationshipDALO,
			_gitPullRequestEntityRepository,
			(gitUserEntity, gitPullRequestEntity) ->
				relateSenderGitUserToGitPull(
					gitUserEntity, gitPullRequestEntity),
			gitUserEntity -> gitUserEntity.getGitPullRequestEntities(),
			(gitUserEntity, gitPullRequestEntity) ->
				gitUserEntity.removeGitPullRequestEntity(gitPullRequestEntity));
	}

	private GitBranchEntityRepository _gitBranchEntityRepository;
	private GitPullRequestEntityRepository _gitPullRequestEntityRepository;

	@Autowired
	private GitUserEntityDALO _gitUserEntityDALO;

	@Autowired
	private GitUserToGitBranchesEntityRelationshipDALO
		_gitUserToGitBranchesEntityRelationshipDALO;

	@Autowired
	private ReceiverGitUserToGitPullRequestsEntityRelationshipDALO
		_receiverGitUserToGitPullRequestsEntityRelationshipDALO;

	@Autowired
	private SenderGitUserToGitPullRequestsEntityRelationshipDALO
		_senderGitUserToGitPullRequestsEntityRelationshipDALO;

}