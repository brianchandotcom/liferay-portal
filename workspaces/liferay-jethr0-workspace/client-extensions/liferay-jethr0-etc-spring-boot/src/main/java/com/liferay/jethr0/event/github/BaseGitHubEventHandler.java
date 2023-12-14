/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.BaseEventHandler;
import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.repository.GitHubRepository;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.branch.repository.GitBranchEntityRepository;
import com.liferay.jethr0.util.PropertiesUtil;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import java.util.Properties;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitHubEventHandler extends BaseEventHandler {

	protected BaseGitHubEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
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

		GitHubFactory gitHubFactory = getGitHubFactory();

		return gitHubFactory.newGitHubRepository(repositoryJSONObject);
	}

	protected String getJenkinsBranchBuildPropertyValue(String propertyName)
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

		return PropertiesUtil.getPropertyValue(properties, propertyName);
	}

	protected GitBranchEntity getJenkinsGitBranchEntity() {
		if (_jenkinsGitBranchEntity != null) {
			return _jenkinsGitBranchEntity;
		}

		GitBranchEntityRepository gitBranchEntityRepository =
			getGitBranchEntityRepository();

		_jenkinsGitBranchEntity = gitBranchEntityRepository.getByURL(
			_JENKINS_GITHUB_URL);

		return _jenkinsGitBranchEntity;
	}

	private static final URL _JENKINS_GITHUB_URL = StringUtil.toURL(
		"https://github.com/liferay/liferay-jenkins-ee");

	private GitBranchEntity _jenkinsGitBranchEntity;

}