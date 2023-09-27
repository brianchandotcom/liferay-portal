/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.dalo;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.BuildEntityFactory;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.bui1d.run.BuildRunEntityFactory;
import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildToBuildRunsEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<BuildEntity, BuildRunEntity> {

	@Override
	public EntityFactory<BuildRunEntity> getChildEntityFactory() {
		return _buildRunEntityFactory;
	}

	@Override
	public EntityFactory<BuildEntity> getParentEntityFactory() {
		return _buildEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "buildToBuildRuns";
	}

	@Autowired
	private BuildEntityFactory _buildEntityFactory;

	@Autowired
	private BuildRunEntityFactory _buildRunEntityFactory;

}