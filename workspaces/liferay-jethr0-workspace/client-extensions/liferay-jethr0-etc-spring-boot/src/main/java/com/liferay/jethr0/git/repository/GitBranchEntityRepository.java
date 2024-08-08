/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.event.github.GitHubFactory;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.commit.GitHubCommit;
import com.liferay.jethr0.event.github.ref.GitHubRef;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.branch.UpstreamGitBranchEntity;
import com.liferay.jethr0.git.commit.GitCommitEntity;
import com.liferay.jethr0.git.dalo.BaseGitBranchToGitPullsEntityRelationshipDALO;
import com.liferay.jethr0.git.dalo.GitBranchEntityDALO;
import com.liferay.jethr0.git.dalo.GitBranchToGitCommitsEntityRelationshipDALO;
import com.liferay.jethr0.git.dalo.GitBranchToRoutinesEntityRelationshipDALO;
import com.liferay.jethr0.git.dalo.HeadGitBranchToGitPullRequestsEntityRelationshipDALO;
import com.liferay.jethr0.git.pullrequest.GitPullRequestEntity;
import com.liferay.jethr0.git.user.GitUserEntity;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.routine.repository.RoutineEntityRepository;
import com.liferay.jethr0.util.StringUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.net.URL;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitBranchEntityRepository
	extends BaseEntityRepository<GitBranchEntity> {

	public GitBranchEntity createGitBranchEntity(GitHubRef gitHubRef) {
		return _createGitBranchEntity(gitHubRef, null);
	}

	public GitBranchEntity createUpstreamGitBranchEntity(URL gitHubRefURL) {
		return _createGitBranchEntity(
			_gitHubFactory.newGitHubRef(gitHubRefURL),
			GitBranchEntity.Type.UPSTREAM);
	}

	public Set<GitBranchEntity> getAllByType(GitBranchEntity.Type... types) {
		if (ArrayUtil.isEmpty(types)) {
			return Collections.emptySet();
		}

		Set<GitBranchEntity> gitBranchEntities = new HashSet<>();

		for (GitBranchEntity gitBranchEntity : getAll()) {
			for (GitBranchEntity.Type type : types) {
				if (gitBranchEntity.getType() == type) {
					gitBranchEntities.add(gitBranchEntity);

					break;
				}
			}
		}

		return gitBranchEntities;
	}

	public GitBranchEntity getByURL(URL url) {
		for (GitBranchEntity gitBranchEntity : getAll()) {
			if (Objects.equals(gitBranchEntity.getURL(), url)) {
				return gitBranchEntity;
			}
		}

		return null;
	}

	@Override
	public GitBranchEntityDALO getEntityDALO() {
		return _gitBranchEntityDALO;
	}

	@Override
	public void initialize() {
		Set<GitBranchEntity> gitBranchEntities = _gitBranchEntityDALO.getByType(
			GitBranchEntity.Type.UPSTREAM);

		addAll(gitBranchEntities);

		for (GitBranchEntity gitBranchEntity : gitBranchEntities) {
			GitUserEntity gitUserEntity = gitBranchEntity.getGitUserEntity();

			if (gitUserEntity != null) {
				continue;
			}

			Matcher matcher = _gitHubBranchURLPattern.matcher(
				String.valueOf(gitBranchEntity.getURL()));

			if (!matcher.find()) {
				continue;
			}

			gitUserEntity = _gitUserEntityRepository.createGitUserEntity(
				matcher.group("userName"));

			gitBranchEntity.setGitUserEntity(gitUserEntity);

			gitUserEntity.addGitBranchEntity(gitBranchEntity);

			update(gitBranchEntity);
		}
	}

	public void relateBaseGitBranchToGitPull(
		GitBranchEntity gitBranchEntity,
		GitPullRequestEntity gitPullRequestEntity) {

		gitBranchEntity.addGitPullRequestEntity(gitPullRequestEntity);

		gitPullRequestEntity.setBaseGitBranchEntity(gitBranchEntity);
	}

	public void relateGitBranchToGitCommit(
		GitBranchEntity gitBranchEntity, GitCommitEntity gitCommitEntity) {

		gitBranchEntity.addGitCommitEntity(gitCommitEntity);

		gitCommitEntity.setGitBranchEntity(gitBranchEntity);
	}

	public void relateGitBranchToRoutine(
		GitBranchEntity gitBranchEntity, RoutineEntity routineEntity) {

		gitBranchEntity.addRoutineEntity(routineEntity);

		routineEntity.setGitBranchEntity(gitBranchEntity);
	}

	public void relateHeadGitBranchToGitPull(
		GitBranchEntity gitBranchEntity,
		GitPullRequestEntity gitPullRequestEntity) {

		gitBranchEntity.addGitPullRequestEntity(gitPullRequestEntity);

		gitPullRequestEntity.setHeadGitBranchEntity(gitBranchEntity);
	}

	@Scheduled(cron = "${liferay.jethr0.git.branch.archive.cron}")
	public void scheduledArchive() {
		Date keepDate = new Date(
			System.currentTimeMillis() - _getSenderGitBranchArchiveAge());

		Set<Long> gitBranchEntityIds = new HashSet<>();

		for (GitBranchEntity gitBranchEntity : getAll()) {
			if ((gitBranchEntity instanceof UpstreamGitBranchEntity) ||
				keepDate.before(gitBranchEntity.getModifiedDate())) {

				continue;
			}

			gitBranchEntityIds.add(gitBranchEntity.getId());
		}

		Map<Long, GitBranchEntity> entitiesMap = getEntitiesMap();

		long gitBranchCount = entitiesMap.size();

		for (Long gitBranchEntityId : gitBranchEntityIds) {
			entitiesMap.remove(gitBranchEntityId);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Archived ", gitBranchEntityIds.size(), " of ",
					gitBranchCount, " git branches"));
		}
	}

	public void setGitCommitEntityRepository(
		GitCommitEntityRepository gitCommitEntityRepository) {

		_gitCommitEntityRepository = gitCommitEntityRepository;
	}

	public void setGitPullRequestEntityRepository(
		GitPullRequestEntityRepository gitPullRequestEntityRepository) {

		_gitPullRequestEntityRepository = gitPullRequestEntityRepository;
	}

	public void setGitUserEntityRepository(
		GitUserEntityRepository gitUserEntityRepository) {

		_gitUserEntityRepository = gitUserEntityRepository;
	}

	public void setRoutineEntityRepository(
		RoutineEntityRepository routineEntityRepository) {

		_routineEntityRepository = routineEntityRepository;
	}

	@Override
	protected GitBranchEntity updateRelationshipsFromDALO(
		GitBranchEntity gitBranchEntity) {

		gitBranchEntity = _updateBaseGitBranchToGitPullRelationshipsFromDALO(
			gitBranchEntity);
		gitBranchEntity = _updateGitBranchToGitCommitRelationshipsFromDALO(
			gitBranchEntity);
		gitBranchEntity = _updateGitBranchToRoutineRelationshipsFromDALO(
			gitBranchEntity);

		return _updateHeadGitBranchToGitPullRelationshipsFromDALO(
			gitBranchEntity);
	}

	@Override
	protected GitBranchEntity updateRelationshipsToDALO(
		GitBranchEntity gitBranchEntity) {

		_baseGitBranchToGitPullsEntityRelationshipDALO.updateChildEntities(
			gitBranchEntity);
		_gitBranchToGitCommitsEntityRelationshipDALO.updateChildEntities(
			gitBranchEntity);
		_gitBranchToRoutinesEntityRelationshipDALO.updateChildEntities(
			gitBranchEntity);
		_headGitBranchToGitPullRequestsEntityRelationshipDALO.
			updateChildEntities(gitBranchEntity);

		return gitBranchEntity;
	}

	private GitBranchEntity _createGitBranchEntity(
		GitHubRef gitHubRef, GitBranchEntity.Type type) {

		URL gitHubRefURL = gitHubRef.getURL();

		GitBranchEntity gitBranchEntity = getByURL(gitHubRefURL);

		if (gitBranchEntity != null) {
			return gitBranchEntity;
		}

		GitUserEntity gitUserEntity =
			_gitUserEntityRepository.createGitUserEntity(gitHubRef);

		GitHubCommit gitHubCommit = gitHubRef.getGitHubCommit();

		if (type == null) {
			type = GitBranchEntity.Type.DEFAULT;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"latestSHA", gitHubCommit.getSHA()
		).put(
			"r_gitUserToGitBranches_c_gitUserId", gitUserEntity.getId()
		).put(
			"type", type.getJSONObject()
		).put(
			"url", String.valueOf(gitHubRefURL)
		);

		gitBranchEntity = create(jsonObject);

		gitBranchEntity.setGitUserEntity(gitUserEntity);

		gitUserEntity.addGitBranchEntity(gitBranchEntity);

		_gitCommitEntityRepository.createGitCommitEntity(
			gitBranchEntity, gitHubCommit.getSHA());

		return gitBranchEntity;
	}

	private long _getSenderGitBranchArchiveAge() {
		return Long.valueOf(_senderGitBranchArchiveAgeInDays) * 1000 * 60 * 60 *
			24;
	}

	private GitBranchEntity _updateBaseGitBranchToGitPullRelationshipsFromDALO(
		GitBranchEntity parentGitBranchEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentGitBranchEntity,
			_baseGitBranchToGitPullsEntityRelationshipDALO,
			_gitPullRequestEntityRepository,
			(gitBranchEntity, gitPullRequestEntity) ->
				relateBaseGitBranchToGitPull(
					gitBranchEntity, gitPullRequestEntity),
			gitBranchEntity -> gitBranchEntity.getGitPullRequestEntities(),
			(gitBranchEntity, gitCommitEntity) ->
				gitBranchEntity.removeGitPullRequestEntity(gitCommitEntity));
	}

	private GitBranchEntity _updateGitBranchToGitCommitRelationshipsFromDALO(
		GitBranchEntity parentGitBranchEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentGitBranchEntity, _gitBranchToGitCommitsEntityRelationshipDALO,
			_gitCommitEntityRepository,
			(gitBranchEntity, gitCommitEntity) -> relateGitBranchToGitCommit(
				gitBranchEntity, gitCommitEntity),
			gitBranchEntity -> gitBranchEntity.getGitCommitEntities(),
			(gitBranchEntity, gitCommitEntity) ->
				gitBranchEntity.removeGitCommitEntity(gitCommitEntity));
	}

	private GitBranchEntity _updateGitBranchToRoutineRelationshipsFromDALO(
		GitBranchEntity parentGitBranchEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentGitBranchEntity, _gitBranchToRoutinesEntityRelationshipDALO,
			_routineEntityRepository,
			(gitBranchEntity, routineEntity) -> relateGitBranchToRoutine(
				gitBranchEntity, routineEntity),
			gitBranchEntity -> gitBranchEntity.getRoutineEntities(),
			(gitBranchEntity, routineEntity) ->
				gitBranchEntity.removeRoutineEntity(routineEntity));
	}

	private GitBranchEntity _updateHeadGitBranchToGitPullRelationshipsFromDALO(
		GitBranchEntity parentGitBranchEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentGitBranchEntity,
			_headGitBranchToGitPullRequestsEntityRelationshipDALO,
			_gitPullRequestEntityRepository,
			(gitBranchEntity, gitPullRequestEntity) ->
				relateHeadGitBranchToGitPull(
					gitBranchEntity, gitPullRequestEntity),
			gitBranchEntity -> gitBranchEntity.getGitPullRequestEntities(),
			(gitBranchEntity, gitCommitEntity) ->
				gitBranchEntity.removeGitPullRequestEntity(gitCommitEntity));
	}

	private static final Log _log = LogFactory.getLog(
		GitBranchEntityRepository.class);

	private static final Pattern _gitHubBranchURLPattern = Pattern.compile(
		"https://github.com/(?<userName>[^/]+)/(?<repositoryName>[^/]+)");

	@Autowired
	private BaseGitBranchToGitPullsEntityRelationshipDALO
		_baseGitBranchToGitPullsEntityRelationshipDALO;

	@Autowired
	private GitBranchEntityDALO _gitBranchEntityDALO;

	@Autowired
	private GitBranchToGitCommitsEntityRelationshipDALO
		_gitBranchToGitCommitsEntityRelationshipDALO;

	@Autowired
	private GitBranchToRoutinesEntityRelationshipDALO
		_gitBranchToRoutinesEntityRelationshipDALO;

	private GitCommitEntityRepository _gitCommitEntityRepository;

	@Autowired
	private GitHubClient _gitHubClient;

	@Autowired
	private GitHubFactory _gitHubFactory;

	private GitPullRequestEntityRepository _gitPullRequestEntityRepository;
	private GitUserEntityRepository _gitUserEntityRepository;

	@Autowired
	private HeadGitBranchToGitPullRequestsEntityRelationshipDALO
		_headGitBranchToGitPullRequestsEntityRelationshipDALO;

	private RoutineEntityRepository _routineEntityRepository;

	@Value("${JETHR0_SENDER_BRANCH_ARCHIVE_AGE_IN_DAYS:1}")
	private String _senderGitBranchArchiveAgeInDays;

}