/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jrp;

import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
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
public class QueueJobEventHandler extends BaseJRPEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Queuing job from JRP at ",
					StringUtil.toString(new Date())));
		}

		JobEntity jobEntity = getJobEntity(getJobJSONObject());

		jobEntity.setState(JobEntity.State.QUEUED);

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		jobEntityRepository.update(jobEntity);

		BuildQueue buildQueue = Jethr0ContextUtil.getBuildQueue();

		buildQueue.addJobEntity(jobEntity);

		JenkinsQueue jenkinsQueue = Jethr0ContextUtil.getJenkinsQueue();

		jenkinsQueue.invoke();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Queued job ", jobEntity.getEntityURL(), " from JRP at ",
					StringUtil.toString(new Date())));
		}

		return jobEntity.toString();
	}

	protected QueueJobEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private static final Log _log = LogFactory.getLog(
		QueueJobEventHandler.class);

}