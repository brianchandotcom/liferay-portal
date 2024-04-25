/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.branch.GitBranchEntityFactory;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.routine.RoutineEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitBranchToRoutinesEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<GitBranchEntity, RoutineEntity> {

	@Override
	public EntityFactory<RoutineEntity> getChildEntityFactory() {
		return _routineEntityFactory;
	}

	@Override
	public EntityFactory<GitBranchEntity> getParentEntityFactory() {
		return _gitBranchEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "gitBranchToRoutines";
	}

	@Autowired
	private GitBranchEntityFactory _gitBranchEntityFactory;

	@Autowired
	private RoutineEntityFactory _routineEntityFactory;

}