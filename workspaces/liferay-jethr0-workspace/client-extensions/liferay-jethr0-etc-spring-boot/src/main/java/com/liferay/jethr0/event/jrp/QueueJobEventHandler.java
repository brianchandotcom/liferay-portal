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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class QueueJobEventHandler extends BaseJRPEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		JobEntity jobEntity = getJobEntity(getJobJSONObject());

		jobEntity.setState(JobEntity.State.QUEUED);

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		jobEntityRepository.update(jobEntity);

		BuildQueue buildQueue = Jethr0ContextUtil.getBuildQueue();

		buildQueue.addJobEntity(jobEntity);

		JenkinsQueue jenkinsQueue = Jethr0ContextUtil.getJenkinsQueue();

		jenkinsQueue.invoke();

		return jobEntity.toString();
	}

	protected QueueJobEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

}