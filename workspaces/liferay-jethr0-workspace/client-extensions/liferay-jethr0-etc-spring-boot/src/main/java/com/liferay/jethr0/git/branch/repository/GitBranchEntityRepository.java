/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.branch.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.commit.GitHubCommit;
import com.liferay.jethr0.event.github.ref.GitHubRef;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.branch.UpstreamGitBranchEntity;
import com.liferay.jethr0.git.branch.dalo.GitBranchEntityDALO;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

	public GitBranchEntity createSenderGitBranchEntity(URL gitHubRefURL) {
		return _createGitBranchEntity(
			gitHubRefURL, GitBranchEntity.Type.SENDER);
	}

	public GitBranchEntity createUpstreamGitBranchEntity(URL gitHubRefURL) {
		return _createGitBranchEntity(
			gitHubRefURL, GitBranchEntity.Type.UPSTREAM);
	}

	public Set<GitBranchEntity> getAllByType(GitBranchEntity.Type... types) {
		Set<GitBranchEntity> gitBranchEntities = new HashSet<>();

		if ((types == null) || (types.length == 0)) {
			return gitBranchEntities;
		}

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
			if (Objects.equals(gitBranchEntity.getBranchURL(), url)) {
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

		for (String gitHubUpstreamBranchURL :
				_gitHubUpstreamBranchURLs.split(",")) {

			boolean gitBranchEntryExists = false;

			for (GitBranchEntity gitBranchEntity : gitBranchEntities) {
				if (Objects.equals(
						String.valueOf(gitBranchEntity.getBranchURL()),
						gitHubUpstreamBranchURL)) {

					gitBranchEntryExists = true;

					break;
				}
			}

			if (!gitBranchEntryExists) {
				gitBranchEntities.add(
					createUpstreamGitBranchEntity(
						StringUtil.toURL(gitHubUpstreamBranchURL)));
			}
		}

		addAll(gitBranchEntities);
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

	private GitBranchEntity _createGitBranchEntity(
		URL gitHubRefURL, GitBranchEntity.Type type) {

		GitBranchEntity gitBranchEntity = getByURL(gitHubRefURL);

		if (gitBranchEntity != null) {
			return gitBranchEntity;
		}

		GitHubRef gitHubRef = _gitHubClient.getGitHubRef(gitHubRefURL);

		GitHubCommit gitHubCommit = gitHubRef.getGitHubCommit();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"branchSHA", gitHubCommit.getSHA()
		).put(
			"branchURL", String.valueOf(gitHubRefURL)
		).put(
			"rebased", false
		).put(
			"type", type.getJSONObject()
		).put(
			"upstreamBranchSHA", gitHubCommit.getSHA()
		).put(
			"upstreamBranchURL", String.valueOf(gitHubRefURL)
		);

		return create(jsonObject);
	}

	private long _getSenderGitBranchArchiveAge() {
		return Long.valueOf(_senderGitBranchArchiveAgeInDays) * 1000 * 60 * 60 *
			24;
	}

	private static final Log _log = LogFactory.getLog(
		GitBranchEntityRepository.class);

	@Autowired
	private GitBranchEntityDALO _gitBranchEntityDALO;

	@Autowired
	private GitHubClient _gitHubClient;

	@Value("${liferay.jethr0.github.upstream.branch.urls}")
	private String _gitHubUpstreamBranchURLs;

	@Value("${JETHR0_SENDER_BRANCH_ARCHIVE_AGE_IN_DAYS:1}")
	private String _senderGitBranchArchiveAgeInDays;

}