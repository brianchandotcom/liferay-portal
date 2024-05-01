/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine;

import com.liferay.jethr0.entity.factory.BaseEntityFactory;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class RoutineEntityFactory extends BaseEntityFactory<RoutineEntity> {

	@Override
	public RoutineEntity newEntity(JSONObject jsonObject) {
		RoutineEntity.Type type = RoutineEntity.Type.get(
			jsonObject.get("type"));

		if (type == RoutineEntity.Type.CRON) {
			return new DefaultCronRoutineEntity(jsonObject);
		}
		else if (type == RoutineEntity.Type.UPSTREAM_BRANCH) {
			return new DefaultUpstreamBranchRoutineEntity(jsonObject);
		}
		else if (type == RoutineEntity.Type.UPSTREAM_BRANCH_CRON) {
			return new DefaultUpstreamBranchCronRoutineEntity(jsonObject);
		}

		return new ManualRoutineEntity(jsonObject);
	}

	protected RoutineEntityFactory() {
		super(RoutineEntity.class);
	}

}