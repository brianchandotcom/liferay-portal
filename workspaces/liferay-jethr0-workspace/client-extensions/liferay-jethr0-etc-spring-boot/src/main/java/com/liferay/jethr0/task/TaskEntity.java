/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.task;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.environment.EnvironmentEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.task.run.TaskRunEntity;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface TaskEntity extends Entity {

	public void addEnvironmentEntities(
		Set<EnvironmentEntity> environmentEntities);

	public void addEnvironmentEntity(EnvironmentEntity environmentEntity);

	public void addTaskRunEntities(Set<TaskRunEntity> taskRunEntities);

	public void addTaskRunEntity(TaskRunEntity taskRunEntity);

	public BuildEntity getBuildEntity();

	public Set<EnvironmentEntity> getEnvironmentEntities();

	public JobEntity getJobEntity();

	public String getName();

	public Set<TaskRunEntity> getTaskRunEntities();

	public void removeEnvironmentEntities(
		Set<EnvironmentEntity> environmentEntities);

	public void removeEnvironmentEntity(EnvironmentEntity environmentEntity);

	public void removeTaskRunEntities(Set<TaskRunEntity> taskRunEntities);

	public void removeTaskRunEntity(TaskRunEntity taskRunEntity);

	public void setBuildEntity(BuildEntity buildEntity);

	public void setJobEntity(JobEntity jobEntity);

	public void setName(String name);

}