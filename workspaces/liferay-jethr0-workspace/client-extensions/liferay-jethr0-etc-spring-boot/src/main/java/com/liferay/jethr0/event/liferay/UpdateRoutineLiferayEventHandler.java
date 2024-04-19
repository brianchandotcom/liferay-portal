/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.liferay;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.routine.repository.RoutineEntityRepository;
import com.liferay.jethr0.routine.scheduler.RoutineEntityScheduler;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class UpdateRoutineLiferayEventHandler
	extends BaseRoutineLiferayEventHandler {

	@Override
	public String process() {
		RoutineEntityRepository routineEntityRepository =
			getRoutineEntityRepository();
		RoutineEntityScheduler routineEntityScheduler =
			getRoutineEntityScheduler();

		JSONObject routineJSONObject = getRoutineJSONObject();

		long routineId = routineJSONObject.getLong("id");

		RoutineEntity routineEntity = null;

		if (routineEntityRepository.contains(routineId)) {
			routineEntity = routineEntityRepository.getById(routineId);

			routineEntity.setJSONObject(routineJSONObject);
		}
		else {
			routineEntityRepository.add(routineJSONObject);
		}

		routineEntityScheduler.unscheduleRoutineEntity(routineEntity);

		routineEntityScheduler.scheduleRoutineEntity(routineEntity);

		return String.valueOf(routineEntity);
	}

	protected UpdateRoutineLiferayEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject jsonObject) {

		super(eventHandlerContext, jsonObject);
	}

}