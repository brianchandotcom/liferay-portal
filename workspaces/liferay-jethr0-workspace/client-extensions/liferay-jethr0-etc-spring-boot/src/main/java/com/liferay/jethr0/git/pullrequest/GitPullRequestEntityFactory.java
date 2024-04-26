/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.pullrequest;

import com.liferay.jethr0.entity.factory.BaseEntityFactory;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitPullRequestEntityFactory
	extends BaseEntityFactory<GitPullRequestEntity> {

	@Override
	public GitPullRequestEntity newEntity(JSONObject jsonObject) {
		return new DefaultGitPullRequestEntity(jsonObject);
	}

	protected GitPullRequestEntityFactory() {
		super(GitPullRequestEntity.class);
	}

}