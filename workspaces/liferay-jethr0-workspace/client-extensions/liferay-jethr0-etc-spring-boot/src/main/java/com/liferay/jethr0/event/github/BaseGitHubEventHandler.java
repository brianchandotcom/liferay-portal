/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.event.BaseEventHandler;
import com.liferay.jethr0.event.github.repository.GitHubRepository;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.repository.GitBranchEntityRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.PropertiesUtil;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitHubEventHandler extends BaseEventHandler {

	protected BaseGitHubEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	protected GitHubRepository getGitHubRepository()
		throws InvalidJSONException {

		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject repositoryJSONObject = messageJSONObject.optJSONObject(
			"repository");

		if (repositoryJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"repository\" from message JSON");
		}

		GitHubFactory gitHubFactory = Jethr0ContextUtil.getGitHubFactory();

		return gitHubFactory.newGitHubRepository(repositoryJSONObject);
	}

	protected String getJenkinsBranchBuildPropertyValue(
			String propertyName, String... propertyOpts)
		throws IOException {

		GitBranchEntity gitBranchEntity = getJenkinsGitBranchEntity();

		if (gitBranchEntity == null) {
			return null;
		}

		Properties properties = PropertiesUtil.combine(
			gitBranchEntity.getProperties("build.properties"),
			gitBranchEntity.getProperties("commands/build.properties"));

		if (properties == null) {
			return null;
		}

		return PropertiesUtil.getPropertyValue(
			properties, propertyName, propertyOpts);
	}

	protected GitBranchEntity getJenkinsGitBranchEntity() {
		if (_jenkinsGitBranchEntity != null) {
			return _jenkinsGitBranchEntity;
		}

		GitBranchEntityRepository gitBranchEntityRepository =
			Jethr0ContextUtil.getGitBranchEntityRepository();

		_jenkinsGitBranchEntity = gitBranchEntityRepository.getByURL(
			_JENKINS_GITHUB_URL);

		return _jenkinsGitBranchEntity;
	}

	protected void invokeJobEntity(JobEntity jobEntity) {
		if (jobEntity == null) {
			return;
		}

		BuildEntityRepository buildEntityRepository =
			Jethr0ContextUtil.getBuildEntityRepository();

		for (JSONObject initialBuildJSONObject :
				jobEntity.getInitialBuildJSONObjects()) {

			buildEntityRepository.create(jobEntity, initialBuildJSONObject);
		}

		BuildQueue buildQueue = Jethr0ContextUtil.getBuildQueue();

		buildQueue.addJobEntity(jobEntity);

		JenkinsQueue jenkinsQueue = Jethr0ContextUtil.getJenkinsQueue();

		jenkinsQueue.invoke();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Invoked job ", jobEntity.getEntityURL(), " at ",
					StringUtil.toString(new Date())));
		}
	}

	private static final URL _JENKINS_GITHUB_URL = StringUtil.toURL(
		"https://github.com/liferay/liferay-jenkins-ee");

	private static final Log _log = LogFactory.getLog(
		BaseGitHubEventHandler.class);

	private GitBranchEntity _jenkinsGitBranchEntity;

}