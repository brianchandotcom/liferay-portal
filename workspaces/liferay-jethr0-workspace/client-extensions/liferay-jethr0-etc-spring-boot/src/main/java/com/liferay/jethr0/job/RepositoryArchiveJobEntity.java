/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class RepositoryArchiveJobEntity extends BaseJobEntity {

	public String getRepositoryNames() {
		return getParameterValue("repositoryNames");
	}

	public void setRepositoryNames(String repositoryNames) {
		setParameterValue("repositoryNames", repositoryNames);
	}

	protected RepositoryArchiveJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		return HashMapBuilder.put(
			"BUILD_PRIORITY", String.valueOf(getPriority())
		).put(
			"REPOSITORY_NAMES", getRepositoryNames()
		).build();
	}

	@Override
	protected String getJenkinsJobName() {
		return "repository-archive";
	}

}