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

package com.liferay.jethr0.build.dalo;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.BuildFactory;
import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.environment.EnvironmentFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildToEnvironmentsDALO
	extends BaseEntityRelationshipDALO<Build, Environment> {

	@Override
	public EntityFactory<Environment> getChildEntityFactory() {
		return _environmentFactory;
	}

	@Override
	public EntityFactory<Build> getParentEntityFactory() {
		return _buildFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "buildToEnvironments";
	}

	@Autowired
	private BuildFactory _buildFactory;

	@Autowired
	private EnvironmentFactory _environmentFactory;

}