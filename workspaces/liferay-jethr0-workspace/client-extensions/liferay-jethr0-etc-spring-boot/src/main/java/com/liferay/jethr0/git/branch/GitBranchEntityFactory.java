/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.branch;

import com.liferay.jethr0.entity.factory.BaseEntityFactory;
import com.liferay.jethr0.event.github.client.GitHubClient;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitBranchEntityFactory extends BaseEntityFactory<GitBranchEntity> {

	@Override
	public String getEntityPluralLabel() {
		return "Git Branches";
	}

	@Override
	public GitBranchEntity newEntity(JSONObject jsonObject) {
		GitBranchEntity gitBranchEntity = null;

		GitBranchEntity.Type type = GitBranchEntity.Type.get(
			jsonObject.getJSONObject("type"));

		if (type == GitBranchEntity.Type.DEFAULT) {
			gitBranchEntity = new DefaultGitBranchEntity(jsonObject);
		}
		else if (type == GitBranchEntity.Type.UPSTREAM) {
			gitBranchEntity = new UpstreamGitBranchEntity(jsonObject);
		}
		else {
			throw new RuntimeException("Unsupported " + type);
		}

		gitBranchEntity.setGitHubClient(_gitHubClient);

		return gitBranchEntity;
	}

	protected GitBranchEntityFactory() {
		super(GitBranchEntity.class);
	}

	@Autowired
	private GitHubClient _gitHubClient;

}