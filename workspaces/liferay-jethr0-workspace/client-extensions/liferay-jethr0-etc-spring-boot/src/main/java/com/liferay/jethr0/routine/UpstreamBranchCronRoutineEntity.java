/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine;

import com.liferay.jethr0.git.branch.GitBranchEntity;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface UpstreamBranchCronRoutineEntity extends CronRoutineEntity {

	public void addGitBranchEntities(Set<GitBranchEntity> gitBranchEntities);

	public void addGitBranchEntity(GitBranchEntity gitBranchEntity);

	public Set<GitBranchEntity> getGitBranchEntities();

	public String getPreviousBranchSHA();

	public void removeGitBranchEntities(Set<GitBranchEntity> gitBranchEntities);

	public void removeGitBranchEntity(GitBranchEntity gitBranchEntity);

	public void setPreviousBranchSHA(String previousBranchSHA);

}