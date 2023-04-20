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
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.task.run.TaskRun;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTask extends BaseEntity implements Task {

	@Override
	public void addEnvironment(Environment environment) {
		addEnvironments(Collections.singleton(environment));
	}

	@Override
	public void addEnvironments(Set<Environment> environments) {
		_environments.addAll(environments);
	}

	@Override
	public void addTaskRun(TaskRun taskRun) {
		addTaskRuns(Collections.singleton(taskRun));
	}

	@Override
	public void addTaskRuns(Set<TaskRun> taskRuns) {
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
	public Set<Environment> getEnvironments() {
		return _environments;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		Build build = getBuild();

		jsonObject.put(
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
	public Set<TaskRun> getTaskRuns() {
		return null;
	}

	@Override
	public void removeEnvironment(Environment environment) {
		_environments.remove(environment);
	}

	@Override
	public void removeEnvironments(Set<Environment> environments) {
		_environments.removeAll(environments);
	}

	@Override
	public void removeTaskRun(TaskRun taskRun) {
		_taskRuns.remove(taskRun);
	}

	@Override
	public void removeTaskRuns(Set<TaskRun> taskRuns) {
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

	protected BaseTask(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.getString("name");
	}

	private Build _build;
	private final Set<Environment> _environments = new HashSet<>();
	private String _name;
	private Project _project;
	private final Set<TaskRun> _taskRuns = new HashSet<>();

}