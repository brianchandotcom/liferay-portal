/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine;

import com.liferay.jethr0.git.branch.GitBranchEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseUpstreamBranchCronRoutineEntity
	extends BaseCronRoutineEntity implements UpstreamBranchCronRoutineEntity {

	@Override
	public GitBranchEntity getGitBranchEntity() {
		List<GitBranchEntity> gitBranchEntities = new ArrayList<>(
			getRelatedEntities(GitBranchEntity.class));

		if (gitBranchEntities.isEmpty()) {
			return null;
		}

		return gitBranchEntities.get(0);
	}

	@Override
	public void setGitBranchEntity(GitBranchEntity gitBranchEntity) {
		Set<GitBranchEntity> gitBranchEntities = getRelatedEntities(
			GitBranchEntity.class);

		gitBranchEntities.clear();

		gitBranchEntities.add(gitBranchEntity);
	}

	protected BaseUpstreamBranchCronRoutineEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

}