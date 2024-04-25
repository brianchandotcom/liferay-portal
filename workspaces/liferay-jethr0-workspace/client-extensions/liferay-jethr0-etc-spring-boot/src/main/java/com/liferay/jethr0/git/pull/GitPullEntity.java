/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.pull;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.job.JobEntity;

import java.net.URL;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface GitPullEntity extends Entity {

	public void addJobEntities(Set<JobEntity> jobEntities);

	public void addJobEntity(JobEntity jobEntity);

	public GitBranchEntity getBaseGitBranchEntity();

	public long getBaseGitBranchEntityId();

	public GitBranchEntity getHeadGitBranchEntity();

	public long getHeadGitBranchEntityId();

	public Set<JobEntity> getJobEntities();

	public URL getURL();

	public void removeJobEntities(Set<JobEntity> jobEntities);

	public void removeJobEntity(JobEntity jobEntity);

	public void setBaseGitBranchEntity(GitBranchEntity baseGitBranchEntity);

	public void setHeadGitBranchEntity(GitBranchEntity headGitBranchEntity);

	public void setURL(URL url);

}