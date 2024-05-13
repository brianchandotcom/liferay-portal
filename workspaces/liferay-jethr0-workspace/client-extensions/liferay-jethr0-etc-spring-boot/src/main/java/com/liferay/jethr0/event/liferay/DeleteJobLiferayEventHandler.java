/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.liferay;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.queue.JobQueue;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class DeleteJobLiferayEventHandler extends BaseJobLiferayEventHandler {

	@Override
	public String process() {
		if (_log.isInfoEnabled()) {
			_log.info("Deleting job at " + StringUtil.toString(new Date()));
		}

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		JSONObject jobJSONObject = getJobJSONObject();

		JobEntity jobEntity = jobEntityRepository.getById(
			jobJSONObject.getLong("id"));

		if (jobEntity == null) {
			return null;
		}

		JobQueue jobQueue = Jethr0ContextUtil.getJobQueue();

		jobQueue.removeJobEntity(jobEntity);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Deleted job ", jobEntity.getEntityURL(), " at ",
					StringUtil.toString(new Date())));
		}

		return String.valueOf(jobEntity);
	}

	protected DeleteJobLiferayEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private static final Log _log = LogFactory.getLog(
		DeleteJobLiferayEventHandler.class);

}