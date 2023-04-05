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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTask implements Task {

	@Override
	public void addEnvironment(Environment environment) {
		addEnvironments(Arrays.asList(environment));
	}

	@Override
	public void addEnvironments(List<Environment> environments) {
		for (Environment environment : environments) {
			if (_environments.contains(environment)) {
				continue;
			}

			_environments.add(environment);
		}
	}

	@Override
	public void addTaskRun(TaskRun taskRun) {
		addTaskRuns(Arrays.asList(taskRun));
	}

	@Override
	public void addTaskRuns(List<TaskRun> taskRuns) {
		for (TaskRun taskRun : taskRuns) {
			if (_taskRuns.contains(taskRun)) {
				continue;
			}

			_taskRuns.add(taskRun);
		}
	}

	@Override
	public Build getBuild() {
		return _build;
	}

	@Override
	public List<Environment> getEnvironments() {
		return _environments;
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public JSONObject getJSONObject() {
		Build build = getBuild();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"id", getId()
		).put(
			"name", getName()
		).put(
			"r_buildToTasks_c_buildId", build.getId()
		);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Project getProject() {
		return _project;
	}

	@Override
	public List<TaskRun> getTaskRuns() {
		return null;
	}

	@Override
	public void removeEnvironment(Environment environment) {
		_environments.remove(environment);
	}

	@Override
	public void removeEnvironments(List<Environment> environments) {
		_environments.removeAll(environments);
	}

	@Override
	public void removeTaskRun(TaskRun taskRun) {
		_taskRuns.remove(taskRun);
	}

	@Override
	public void removeTaskRuns(List<TaskRun> taskRuns) {
		_taskRuns.removeAll(taskRuns);
	}

	@Override
	public void setBuild(Build build) {
		_build = build;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setProject(Project project) {
		_project = project;
	}

	@Override
	public String toString() {
		return String.valueOf(getJSONObject());
	}

	protected BaseTask(Build build, JSONObject jsonObject) {
		_build = build;

		_id = jsonObject.getLong("id");
		_name = jsonObject.getString("name");
	}

	protected BaseTask(Project project, JSONObject jsonObject) {
		_project = project;

		_id = jsonObject.getLong("id");
		_name = jsonObject.getString("name");
	}

	private Build _build;
	private final List<Environment> _environments = new ArrayList<>();
	private final long _id;
	private String _name;
	private Project _project;
	private final List<TaskRun> _taskRuns = new ArrayList<>();

}