/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.git.commit.GitCommitEntity;
import com.liferay.jethr0.git.commit.GitCommitEntityFactory;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitCommitEntityDALO extends BaseEntityDALO<GitCommitEntity> {

	public GitCommitEntity getBySHA(String sha) {
		if ((sha == null) || !sha.matches("[0-9a-f]{5,40}")) {
			return null;
		}

		String filter = "sha eq '" + sha + "'";

		for (GitCommitEntity gitCommitEntity : getAll(filter, null, null)) {
			if (Objects.equals(gitCommitEntity.getSHA(), sha)) {
				return gitCommitEntity;
			}
		}

		return null;
	}

	@Override
	public EntityFactory<GitCommitEntity> getEntityFactory() {
		return _gitCommitEntityFactory;
	}

	@Autowired
	private GitCommitEntityFactory _gitCommitEntityFactory;

}