/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.liferay;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.queue.JobQueue;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class DeleteJobLiferayEventHandler extends BaseJobLiferayEventHandler {

	@Override
	public String process() {
		JobEntityRepository jobEntityRepository = getJobEntityRepository();

		JSONObject jobJSONObject = getJobJSONObject();

		JobEntity jobEntity = jobEntityRepository.getById(
			jobJSONObject.getLong("id"));

		if (jobEntity != null) {
			JobQueue jobQueue = getJobQueue();

			jobQueue.removeJobEntity(jobEntity);
		}

		return String.valueOf(jobEntity);
	}

	protected DeleteJobLiferayEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject jsonObject) {

		super(eventHandlerContext, jsonObject);
	}

}