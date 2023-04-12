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

package com.liferay.jethr0.environment.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.environment.dalo.EnvironmentDALO;
import com.liferay.jethr0.project.dalo.ProjectToTasksDALO;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Michael Hashimoto
 */
public class EnvironmentRepository extends BaseEntityRepository<Environment> {

	@Override
	public EnvironmentDALO getEntityDALO() {
		return _environmentDALO;
	}

	@Override
	public Environment updateEntityRelationshipsInDatabase(
		Environment environment) {

		return environment;
	}

	@Override
	protected Environment updateEntityRelationshipsFromDatabase(
		Environment environment) {

		return environment;
	}

	@Autowired
	private EnvironmentDALO _environmentDALO;

	@Autowired
	private ProjectToTasksDALO _projectToTasksDALO;

}