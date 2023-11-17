/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.issue.GitHubIssue;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CITestGitHubEventHandler extends BaseGitHubEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		JobEntityRepository jobEntityRepository = getJobEntityRepository();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"name", "Portal Pull Request SF"
		).put(
			"priority", 5
		).put(
			"state", "queued"
		).put(
			"type", "portalPullRequestSF"
		);

		GitHubIssue gitHubIssue = getGitHubIssue();

		if (gitHubIssue != null) {
			jsonObject.put(
				"portalPullRequestURL",
				String.valueOf(gitHubIssue.getHtmlURL()));
		}

		JobEntity jobEntity = jobEntityRepository.create(jsonObject);

		BuildEntityRepository buildEntityRepository = getBuildRepository();

		for (JSONObject initialBuildJSONObject :
				jobEntity.getInitialBuildJSONObjects()) {

			buildEntityRepository.create(jobEntity, initialBuildJSONObject);
		}

		BuildQueue buildQueue = getBuildQueue();

		buildQueue.addJobEntity(jobEntity);

		JenkinsQueue jenkinsQueue = getJenkinsQueue();

		jenkinsQueue.invoke();

		return jobEntity.toString();
	}

	protected CITestGitHubEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

}