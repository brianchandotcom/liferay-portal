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

package com.liferay.jethr0.build.repository;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.dalo.BuildParameterDALO;
import com.liferay.jethr0.build.dalo.BuildToBuildParametersDALO;
import com.liferay.jethr0.build.parameter.BuildParameter;
import com.liferay.jethr0.entity.dalo.EntityDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Michael Hashimoto
 */
public class BuildParameterRepository
	extends BaseEntityRepository<BuildParameter> {

	@Override
	public EntityDALO<BuildParameter> getEntityDALO() {
		return _buildParameterDALO;
	}

	@Override
	protected BuildParameter updateEntityRelationshipsFromDatabase(
		BuildParameter buildParameter) {

		for (Build build :
				_buildToBuildParametersDALO.getParentEntities(buildParameter)) {

			buildParameter.setBuild(build);

			build.addBuildParameter(buildParameter);
		}

		return buildParameter;
	}

	@Override
	protected BuildParameter updateEntityRelationshipsInDatabase(
		BuildParameter buildParameter) {

		_buildToBuildParametersDALO.updateParentEntities(buildParameter);

		return buildParameter;
	}

	@Autowired
	private BuildParameterDALO _buildParameterDALO;

	@Autowired
	private BuildToBuildParametersDALO _buildToBuildParametersDALO;

}