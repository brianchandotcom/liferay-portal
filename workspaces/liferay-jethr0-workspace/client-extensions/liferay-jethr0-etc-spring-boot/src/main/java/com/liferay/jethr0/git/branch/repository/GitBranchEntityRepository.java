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
import com.liferay.jethr0.git.branch.dalo.GitBranchEntityDALO;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Objects;
import java.util.Set;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitBranchEntityRepository
	extends BaseEntityRepository<GitBranchEntity> {

	public GitBranchEntity createUpstreamBranch(URL gitHubRefURL) {
		GitHubRef gitHubRef = _gitHubClient.getGitHubRef(gitHubRefURL);

		GitHubCommit gitHubCommit = gitHubRef.getGitHubCommit();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"branchName", gitHubRef.getRefName()
		).put(
			"branchSHA", gitHubCommit.getSHA()
		).put(
			"rebased", false
		).put(
			"repositoryName", gitHubRef.getRepositoryName()
		).put(
			"type", GitBranchEntity.Type.UPSTREAM.getJSONObject()
		).put(
			"upstreamBranchName", gitHubRef.getRefName()
		).put(
			"upstreamBranchSHA", gitHubCommit.getSHA()
		).put(
			"url", String.valueOf(gitHubRefURL)
		);

		return create(jsonObject);
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

		for (String gitHubUpstreamBranchURL :
				_gitHubUpstreamBranchURLs.split(",")) {

			boolean gitBranchEntryExists = false;

			for (GitBranchEntity gitBranchEntity : gitBranchEntities) {
				URL gitBranchURL = gitBranchEntity.getURL();

				if (Objects.equals(
						gitBranchURL.toString(), gitHubUpstreamBranchURL)) {

					gitBranchEntryExists = true;

					break;
				}
			}

			if (!gitBranchEntryExists) {
				gitBranchEntities.add(
					createUpstreamBranch(
						StringUtil.toURL(gitHubUpstreamBranchURL)));
			}
		}

		addAll(gitBranchEntities);
	}

	@Autowired
	private GitBranchEntityDALO _gitBranchEntityDALO;

	@Autowired
	private GitHubClient _gitHubClient;

	@Value("${liferay.jethr0.github.upstream.branch.urls}")
	private String _gitHubUpstreamBranchURLs;

}