/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.task.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.environment.EnvironmentEntity;
import com.liferay.jethr0.environment.EnvironmentEntityFactory;
import com.liferay.jethr0.task.TaskEntity;
import com.liferay.jethr0.task.TaskEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TaskToEnvironmentsEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<TaskEntity, EnvironmentEntity> {

	@Override
	public EntityFactory<EnvironmentEntity> getChildEntityFactory() {
		return _environmentEntityFactory;
	}

	@Override
	public EntityFactory<TaskEntity> getParentEntityFactory() {
		return _taskEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "taskToEnvironments";
	}

	@Autowired
	private EnvironmentEntityFactory _environmentEntityFactory;

	@Autowired
	private TaskEntityFactory _taskEntityFactory;

}