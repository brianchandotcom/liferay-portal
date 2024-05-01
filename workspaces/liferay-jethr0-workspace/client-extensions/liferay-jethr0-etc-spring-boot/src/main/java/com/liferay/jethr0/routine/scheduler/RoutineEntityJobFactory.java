/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine.scheduler;

import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.routine.CronRoutineEntity;
import com.liferay.jethr0.routine.UpstreamBranchRoutineEntity;
import com.liferay.jethr0.routine.repository.RoutineEntityRepository;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.spi.TriggerFiredBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * @author Michael Hashimoto
 */
@Component
public class RoutineEntityJobFactory extends AdaptableJobFactory {

	public BuildEntityRepository getBuildEntityRepository() {
		return _buildEntityRepository;
	}

	public BuildQueue getBuildQueue() {
		return _buildQueue;
	}

	public JenkinsQueue getJenkinsQueue() {
		return _jenkinsQueue;
	}

	public JobEntityRepository getJobEntityRepository() {
		return _jobEntityRepository;
	}

	public RoutineEntityRepository getRoutineEntityRepository() {
		return _routineEntityRepository;
	}

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) {
		JobDetail jobDetail = bundle.getJobDetail();

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		Object routineEntityObject = jobDataMap.get("routineEntity");

		RoutineEntityJob routineEntityJob = null;

		if (routineEntityObject instanceof UpstreamBranchRoutineEntity) {
			routineEntityJob = new UpstreamGitBranchCronRoutineEntityJob();
		}
		else if (routineEntityObject instanceof CronRoutineEntity) {
			routineEntityJob = new CronRoutineEntityJob();
		}

		if (routineEntityJob == null) {
			throw new RuntimeException("Unsupported routine entity type");
		}

		routineEntityJob.setRoutineEntityJobFactory(this);

		return routineEntityJob;
	}

	@Autowired
	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private BuildQueue _buildQueue;

	@Autowired
	private JenkinsQueue _jenkinsQueue;

	@Autowired
	private JobEntityRepository _jobEntityRepository;

	@Autowired
	private RoutineEntityRepository _routineEntityRepository;

}