/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.commit;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.routine.RoutineEntity;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface GitCommitEntity extends Entity {

	public void addJobEntities(Set<JobEntity> jobEntities);

	public void addJobEntity(JobEntity jobEntity);

	public void addRoutineEntities(Set<RoutineEntity> routineEntities);

	public void addRoutineEntity(RoutineEntity routineEntity);

	public GitBranchEntity getGitBranchEntity();

	public long getGitBranchEntityId();

	public Set<JobEntity> getJobEntities();

	public Set<RoutineEntity> getRoutineEntities();

	public String getSHA();

	public void removeJobEntities(Set<JobEntity> jobEntities);

	public void removeJobEntity(JobEntity jobEntity);

	public void removeRoutineEntities(Set<RoutineEntity> routineEntities);

	public void removeRoutineEntity(RoutineEntity routineEntity);

	public void setGitBranchEntity(GitBranchEntity gitBranchEntity);

	public void setSHA(String sha);

}