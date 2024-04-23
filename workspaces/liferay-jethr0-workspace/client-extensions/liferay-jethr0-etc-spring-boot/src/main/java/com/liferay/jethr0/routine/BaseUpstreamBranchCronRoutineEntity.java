/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine;

import com.liferay.jethr0.git.branch.GitBranchEntity;

import java.util.Collections;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseUpstreamBranchCronRoutineEntity
	extends BaseCronRoutineEntity implements UpstreamBranchCronRoutineEntity {

	@Override
	public void addGitBranchEntities(Set<GitBranchEntity> gitBranchEntities) {
		addRelatedEntities(gitBranchEntities);
	}

	@Override
	public void addGitBranchEntity(GitBranchEntity gitBranchEntity) {
		addRelatedEntities(Collections.singleton(gitBranchEntity));
	}

	@Override
	public Set<GitBranchEntity> getGitBranchEntities() {
		return getRelatedEntities(GitBranchEntity.class);
	}

	@Override
	public void removeGitBranchEntities(
		Set<GitBranchEntity> gitBranchEntities) {

		removeRelatedEntities(gitBranchEntities);
	}

	@Override
	public void removeGitBranchEntity(GitBranchEntity gitBranchEntity) {
		removeRelatedEntities(Collections.singleton(gitBranchEntity));
	}

	protected BaseUpstreamBranchCronRoutineEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

}