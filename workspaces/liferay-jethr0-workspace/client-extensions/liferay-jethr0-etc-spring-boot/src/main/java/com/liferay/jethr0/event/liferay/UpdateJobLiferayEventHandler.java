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
public class UpdateJobLiferayEventHandler extends BaseJobLiferayEventHandler {

	@Override
	public String process() {
		if (_log.isInfoEnabled()) {
			_log.info("Updating job at " + StringUtil.toString(new Date()));
		}

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		JSONObject jobJSONObject = getJobJSONObject();

		JobEntity jobEntity = jobEntityRepository.getById(
			jobJSONObject.getLong("id"));

		if (jobEntity == null) {
			return null;
		}

		jobEntity.setJSONObject(jobJSONObject);

		JobQueue jobQueue = Jethr0ContextUtil.getJobQueue();

		jobQueue.sort();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Updated job ", jobEntity.getEntityURL(), " at ",
					StringUtil.toString(new Date())));
		}

		return String.valueOf(jobEntity);
	}

	protected UpdateJobLiferayEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private static final Log _log = LogFactory.getLog(
		UpdateJobLiferayEventHandler.class);

}