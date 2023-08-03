/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.task;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.task.run.TaskRun;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface Task extends Entity {

	public void addEnvironment(Environment environment);

	public void addEnvironments(Set<Environment> environments);

	public void addTaskRun(TaskRun taskRun);

	public void addTaskRuns(Set<TaskRun> taskRuns);

	public Build getBuild();

	public Set<Environment> getEnvironments();

	public String getName();

	public Project getProject();

	public Set<TaskRun> getTaskRuns();

	public void removeEnvironment(Environment environment);

	public void removeEnvironments(Set<Environment> environments);

	public void removeTaskRun(TaskRun taskRun);

	public void removeTaskRuns(Set<TaskRun> taskRuns);

	public void setBuild(Build build);

	public void setName(String name);

	public void setProject(Project project);

}