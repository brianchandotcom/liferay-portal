/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.dalo;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.BuildFactory;
import com.liferay.jethr0.bui1d.parameter.BuildParameter;
import com.liferay.jethr0.bui1d.parameter.BuildParameterFactory;
import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildToBuildParametersEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<Build, BuildParameter> {

	@Override
	public EntityFactory<BuildParameter> getChildEntityFactory() {
		return _buildParameterFactory;
	}

	@Override
	public EntityFactory<Build> getParentEntityFactory() {
		return _buildFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "buildToBuildParameters";
	}

	@Autowired
	private BuildFactory _buildFactory;

	@Autowired
	private BuildParameterFactory _buildParameterFactory;

}