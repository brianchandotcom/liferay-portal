/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.SubrepositoryPullRequestJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SubrepositoryOpenGitHubPullRequestEventHandler
	extends BaseOpenGitHubPullRequestEventHandler {

	protected SubrepositoryOpenGitHubPullRequestEventHandler(
		JSONObject messageJSONObject) {

		super(messageJSONObject);
	}

	@Override
	protected Set<JobEntity> createJobEntities()
		throws InvalidJSONException, IOException {

		Set<JobEntity> jobEntities = new HashSet<>();

		for (String testSuite : _getTestSuites()) {
			jobEntities.add(
				_createSubrepositoryPullRequestJobEntity(testSuite));
		}

		return jobEntities;
	}

	private JobEntity _createSubrepositoryPullRequestJobEntity(String testSuite)
		throws InvalidJSONException, IOException {

		JobEntity jobEntity = createPullRequestJobEntity(
			JobEntity.Type.SUBREPOSITORY_PULL_REQUEST, 5, testSuite);

		if (!(jobEntity instanceof SubrepositoryPullRequestJobEntity)) {
			return jobEntity;
		}

		String portalBranchName = _getPortalBranchName();

		if (StringUtil.isNullOrEmpty(portalBranchName)) {
			return jobEntity;
		}

		SubrepositoryPullRequestJobEntity subrepositoryPullRequestJobEntity =
			(SubrepositoryPullRequestJobEntity)jobEntity;

		subrepositoryPullRequestJobEntity.setPortalUpstreamBranchName(
			portalBranchName);

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		jobEntityRepository.update(subrepositoryPullRequestJobEntity);

		return subrepositoryPullRequestJobEntity;
	}

	private String _getPortalBranchName()
		throws InvalidJSONException, IOException {

		for (String testOption : getTestOptions()) {
			Matcher matcher = _portalBranchNamePattern.matcher(testOption);

			if (matcher.matches()) {
				return matcher.group();
			}
		}

		return null;
	}

	private Set<String> _getTestSuites()
		throws InvalidJSONException, IOException {

		Set<String> testSuites = new HashSet<>();

		for (String testOption : getTestOptions()) {
			Matcher matcher = _portalBranchNamePattern.matcher(testOption);

			if (matcher.matches()) {
				continue;
			}

			testSuites.add(testOption);
		}

		return testSuites;
	}

	private static final Pattern _portalBranchNamePattern = Pattern.compile(
		"(7\\.[0-4]\\.x|master|release-\\d{4}\\.q\\d+)");

}