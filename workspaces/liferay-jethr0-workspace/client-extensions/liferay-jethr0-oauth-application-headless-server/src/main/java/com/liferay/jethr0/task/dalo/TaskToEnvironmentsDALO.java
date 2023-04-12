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

package com.liferay.jethr0.task.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.environment.EnvironmentFactory;
import com.liferay.jethr0.task.Task;
import com.liferay.jethr0.task.TaskFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TaskToEnvironmentsDALO
	extends BaseEntityRelationshipDALO<Task, Environment> {

	@Override
	public EntityFactory<Environment> getChildEntityFactory() {
		return _environmentFactory;
	}

	@Override
	public EntityFactory<Task> getParentEntityFactory() {
		return _taskFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "taskToEnvironments";
	}

	@Autowired
	private EnvironmentFactory _environmentFactory;

	@Autowired
	private TaskFactory _taskFactory;

}