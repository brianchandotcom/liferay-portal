/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.task.repository;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.dalo.BuildToTasksEntityRelationshipDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.dalo.ProjectToTasksEntityRelationshipDALO;
import com.liferay.jethr0.task.TaskEntity;
import com.liferay.jethr0.task.dalo.TaskEntityDALO;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TaskEntityRepository extends BaseEntityRepository<TaskEntity> {

	public Set<TaskEntity> getAll(Build build) {
		Set<TaskEntity> buildTaskEntities = new HashSet<>();

		Set<Long> taskIds =
			_buildToTasksEntityRelationshipDALO.getChildEntityIds(build);

		for (TaskEntity taskEntity : getAll()) {
			if (!taskIds.contains(taskEntity.getId())) {
				continue;
			}

			taskEntity.setBuild(build);

			build.addTaskEntity(taskEntity);

			buildTaskEntities.add(taskEntity);
		}

		return buildTaskEntities;
	}

	public Set<TaskEntity> getAll(Project project) {
		Set<TaskEntity> projectTaskEntities = new HashSet<>();

		Set<Long> taskIds =
			_projectToTasksEntityRelationshipDALO.getChildEntityIds(project);

		for (TaskEntity taskEntity : getAll()) {
			if (!taskIds.contains(taskEntity.getId())) {
				continue;
			}

			taskEntity.setProject(project);

			project.addTaskEntity(taskEntity);

			projectTaskEntities.add(taskEntity);
		}

		return projectTaskEntities;
	}

	@Override
	public TaskEntityDALO getEntityDALO() {
		return _taskEntityDALO;
	}

	@Autowired
	private BuildToTasksEntityRelationshipDALO
		_buildToTasksEntityRelationshipDALO;

	@Autowired
	private ProjectToTasksEntityRelationshipDALO
		_projectToTasksEntityRelationshipDALO;

	@Autowired
	private TaskEntityDALO _taskEntityDALO;

}