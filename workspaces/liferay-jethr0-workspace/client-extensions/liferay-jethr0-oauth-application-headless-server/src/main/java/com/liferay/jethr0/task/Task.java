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
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.task.run.TaskRun;

import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface Task {

	public void addEnvironment(Environment environment);

	public void addEnvironments(List<Environment> environments);

	public void addTaskRun(TaskRun taskRun);

	public void addTaskRuns(List<TaskRun> taskRuns);

	public Build getBuild();

	public List<Environment> getEnvironments();

	public long getId();

	public JSONObject getJSONObject();

	public String getName();

	public Project getProject();

	public List<TaskRun> getTaskRuns();

	public void removeEnvironment(Environment environment);

	public void removeEnvironments(List<Environment> environments);

	public void removeTaskRun(TaskRun taskRun);

	public void removeTaskRuns(List<TaskRun> taskRuns);

	public void setBuild(Build build);

	public void setName(String name);

	public void setProject(Project project);

}