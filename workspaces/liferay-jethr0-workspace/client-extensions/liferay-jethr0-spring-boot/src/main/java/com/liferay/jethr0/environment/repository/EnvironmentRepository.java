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

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.dalo.BuildToEnvironmentsDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.environment.dalo.EnvironmentDALO;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class EnvironmentRepository extends BaseEntityRepository<Environment> {

	public Set<Environment> getAll(Build build) {
		Set<Environment> buildEnvironments = new HashSet<>();

		Set<Long> environmentIds = _buildToEnvironmentsDALO.getChildEntityIds(
			build);

		for (Environment environment : getAll()) {
			if (!environmentIds.contains(environment.getId())) {
				continue;
			}

			build.addEnvironment(environment);

			environment.setBuild(build);

			buildEnvironments.add(environment);
		}

		return buildEnvironments;
	}

	@Override
	public EnvironmentDALO getEntityDALO() {
		return _environmentDALO;
	}

	@Autowired
	private BuildToEnvironmentsDALO _buildToEnvironmentsDALO;

	@Autowired
	private EnvironmentDALO _environmentDALO;

}