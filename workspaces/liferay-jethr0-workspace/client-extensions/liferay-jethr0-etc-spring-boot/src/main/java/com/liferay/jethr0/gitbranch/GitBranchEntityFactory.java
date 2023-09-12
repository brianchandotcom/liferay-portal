/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.gitbranch;

import com.liferay.jethr0.entity.factory.BaseEntityFactory;

import org.json.JSONObject;

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
		return new DefaultGitBranchEntity(jsonObject);
	}

	protected GitBranchEntityFactory() {
		super(GitBranchEntity.class);
	}

}