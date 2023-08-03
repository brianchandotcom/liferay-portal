/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.task;

import com.liferay.jethr0.bui1d.Build;
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