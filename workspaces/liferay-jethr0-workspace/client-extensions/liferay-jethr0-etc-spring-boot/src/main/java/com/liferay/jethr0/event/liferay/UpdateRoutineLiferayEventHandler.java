/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.liferay;

import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.routine.repository.RoutineEntityRepository;
import com.liferay.jethr0.routine.scheduler.RoutineEntityScheduler;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class UpdateRoutineLiferayEventHandler
	extends BaseRoutineLiferayEventHandler {

	@Override
	public String process() {
		if (_log.isInfoEnabled()) {
			_log.info("Updating routine at " + StringUtil.toString(new Date()));
		}

		RoutineEntityRepository routineEntityRepository =
			Jethr0ContextUtil.getRoutineEntityRepository();
		RoutineEntityScheduler routineEntityScheduler =
			Jethr0ContextUtil.getRoutineEntityScheduler();

		JSONObject routineJSONObject = getRoutineJSONObject();

		long routineId = routineJSONObject.getLong("id");

		RoutineEntity routineEntity = null;

		if (routineEntityRepository.contains(routineId)) {
			routineEntity = routineEntityRepository.getById(routineId);

			routineEntity.setJSONObject(routineJSONObject);
		}
		else {
			routineEntity = routineEntityRepository.add(routineJSONObject);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Updated routine ", routineEntity.getEntityURL(), " at ",
					StringUtil.toString(new Date())));
		}

		routineEntityScheduler.unscheduleRoutineEntity(routineEntity);

		routineEntityScheduler.scheduleRoutineEntity(routineEntity);

		return String.valueOf(routineEntity);
	}

	protected UpdateRoutineLiferayEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private static final Log _log = LogFactory.getLog(
		UpdateRoutineLiferayEventHandler.class);

}