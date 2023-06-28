/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jethr0.task;

import com.liferay.jethr0.build.Build;
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