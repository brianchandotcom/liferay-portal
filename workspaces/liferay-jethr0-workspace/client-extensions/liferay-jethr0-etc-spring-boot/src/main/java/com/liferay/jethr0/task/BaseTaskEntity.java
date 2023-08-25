/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.task;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.task.run.TaskRunEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTaskEntity extends BaseEntity implements TaskEntity {

	@Override
	public void addEnvironment(Environment environment) {
		addEnvironments(Collections.singleton(environment));
	}

	@Override
	public void addEnvironments(Set<Environment> environments) {
		_environments.addAll(environments);
	}

	@Override
	public void addTaskRunEntities(Set<TaskRunEntity> taskRunEntities) {
		for (TaskRunEntity taskRunEntity : taskRunEntities) {
			if (_taskRunEntities.contains(taskRunEntity)) {
				continue;
			}

			_taskRunEntities.add(taskRunEntity);
		}
	}

	@Override
	public void addTaskRunEntity(TaskRunEntity taskRunEntity) {
		addTaskRunEntities(Collections.singleton(taskRunEntity));
	}

	@Override
	public BuildEntity getBuildEntity() {
		return _buildEntity;
	}

	@Override
	public Set<Environment> getEnvironments() {
		return _environments;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		BuildEntity buildEntity = getBuildEntity();

		jsonObject.put(
			"name", getName()
		).put(
			"r_buildToTasks_c_buildId", buildEntity.getId()
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
	public Set<TaskRunEntity> getTaskRunEntities() {
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
	public void removeTaskRunEntities(Set<TaskRunEntity> taskRunEntities) {
		_taskRunEntities.removeAll(taskRunEntities);
	}

	@Override
	public void removeTaskRunEntity(TaskRunEntity taskRunEntity) {
		_taskRunEntities.remove(taskRunEntity);
	}

	@Override
	public void setBuildEntity(BuildEntity buildEntity) {
		_buildEntity = buildEntity;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setProject(Project project) {
		_project = project;
	}

	protected BaseTaskEntity(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.getString("name");
	}

	private BuildEntity _buildEntity;
	private final Set<Environment> _environments = new HashSet<>();
	private String _name;
	private Project _project;
	private final Set<TaskRunEntity> _taskRunEntities = new HashSet<>();

}