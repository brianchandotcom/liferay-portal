/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine.scheduler;

import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.queue.JobQueue;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.routine.CronRoutineEntity;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.routine.repository.RoutineEntityRepository;
import com.liferay.jethr0.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class RoutineEntityScheduler {

	public JobDetail getJobDetail(RoutineEntity routineEntity) {
		if (!_isRoutineCron(routineEntity)) {
			return null;
		}

		JobKey jobKey = JobKey.jobKey("Routine_" + routineEntity.getId());

		Scheduler scheduler = getScheduler();

		try {
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);

			if (jobDetail != null) {
				return jobDetail;
			}

			JobDataMap jobDataMap = new JobDataMap();

			jobDataMap.put("buildEntityRepository", _buildEntityRepository);
			jobDataMap.put("buildQueue", _buildQueue);
			jobDataMap.put("jenkinsQueue", _jenkinsQueue);
			jobDataMap.put("jobEntityRepository", _jobEntityRepository);
			jobDataMap.put("routineEntity", routineEntity);

			return JobBuilder.newJob(
				RoutineEntityJob.class
			).withIdentity(
				jobKey
			).usingJobData(
				jobDataMap
			).build();
		}
		catch (SchedulerException schedulerException) {
			if (_log.isWarnEnabled()) {
				_log.warn(schedulerException);
			}

			return null;
		}
	}

	public Scheduler getScheduler() {
		if (_scheduler != null) {
			return _scheduler;
		}

		try {
			_scheduler = StdSchedulerFactory.getDefaultScheduler();

			_scheduler.start();

			_scheduler.setJobFactory(_routineEntityJobFactory);
		}
		catch (SchedulerException schedulerException) {
			if (_log.isWarnEnabled()) {
				_log.warn(schedulerException);
			}
		}

		return _scheduler;
	}

	public Trigger getTrigger(RoutineEntity routineEntity) {
		if (!_isRoutineCron(routineEntity)) {
			return null;
		}

		CronRoutineEntity cronRoutineEntity = (CronRoutineEntity)routineEntity;

		TriggerKey triggerKey = TriggerKey.triggerKey(
			"Routine_" + routineEntity.getId());

		Scheduler scheduler = getScheduler();

		try {
			Trigger trigger = scheduler.getTrigger(triggerKey);

			if (trigger != null) {
				return trigger;
			}

			return TriggerBuilder.newTrigger(
			).withIdentity(
				triggerKey
			).withSchedule(
				CronScheduleBuilder.cronSchedule(cronRoutineEntity.getCron())
			).build();
		}
		catch (SchedulerException schedulerException) {
			if (_log.isWarnEnabled()) {
				_log.warn(schedulerException);
			}

			return null;
		}
	}

	public void initialize() {
		for (RoutineEntity routineEntity : _routineEntityRepository.getAll()) {
			scheduleRoutineEntity(routineEntity);
		}
	}

	public void scheduleRoutineEntity(RoutineEntity routineEntity) {
		if (!_isRoutineCron(routineEntity)) {
			return;
		}

		Scheduler scheduler = getScheduler();

		try {
			Trigger trigger = getTrigger(routineEntity);

			Trigger currentTrigger = scheduler.getTrigger(trigger.getKey());

			if (currentTrigger != null) {
				scheduler.unscheduleJob(trigger.getKey());
			}

			scheduler.scheduleJob(getJobDetail(routineEntity), trigger);

			if (_log.isInfoEnabled()) {
				CronRoutineEntity cronRoutineEntity =
					(CronRoutineEntity)routineEntity;

				_log.info(
					StringUtil.combine(
						"Scheduled routine ID ", cronRoutineEntity.getId(),
						" with cron '", cronRoutineEntity.getCron(), "'"));
			}
		}
		catch (SchedulerException schedulerException) {
			if (_log.isWarnEnabled()) {
				_log.warn(schedulerException);
			}
		}
	}

	public void unscheduleRoutineEntity(RoutineEntity routineEntity) {
		if (!_isRoutineCron(routineEntity)) {
			return;
		}

		Scheduler scheduler = getScheduler();

		try {
			Trigger trigger = getTrigger(routineEntity);

			scheduler.unscheduleJob(trigger.getKey());

			if (_log.isInfoEnabled()) {
				_log.info("Unscheduled routine ID " + routineEntity.getId());
			}
		}
		catch (SchedulerException schedulerException) {
			if (_log.isWarnEnabled()) {
				_log.warn(schedulerException);
			}
		}
	}

	private boolean _isRoutineCron(RoutineEntity routineEntity) {
		if (!(routineEntity instanceof CronRoutineEntity)) {
			return false;
		}

		CronRoutineEntity cronRoutineEntity = (CronRoutineEntity)routineEntity;

		if (StringUtil.isNullOrEmpty(cronRoutineEntity.getCron())) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactory.getLog(JobQueue.class);

	@Autowired
	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private BuildQueue _buildQueue;

	@Autowired
	private JenkinsQueue _jenkinsQueue;

	@Autowired
	private JobEntityRepository _jobEntityRepository;

	@Autowired
	private RoutineEntityJobFactory _routineEntityJobFactory;

	@Autowired
	private RoutineEntityRepository _routineEntityRepository;

	private Scheduler _scheduler;

}