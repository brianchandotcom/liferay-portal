/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.repository;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.dalo.JobEntityDALO;
import com.liferay.jethr0.job.dalo.JobToBuildsEntityRelationshipDALO;
import com.liferay.jethr0.job.queue.JobQueue;
import com.liferay.jethr0.util.StringUtil;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Michael Hashimoto
 */
@Configuration
@EnableScheduling
public class JobEntityRepository extends BaseEntityRepository<JobEntity> {

	@Override
	public JobEntity create(JSONObject jsonObject) {
		Object parameters = jsonObject.opt("parameters");

		if (parameters != null) {
			jsonObject.put("parameters", String.valueOf(parameters));
		}

		return super.create(jsonObject);
	}

	public JobEntity create(
		String name, int priority, Date startDate, JobEntity.State state,
		JobEntity.Type type) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"name", name
		).put(
			"priority", priority
		);

		if (startDate != null) {
			jsonObject.put("startDate", StringUtil.toString(startDate));
		}

		if (state == null) {
			state = JobEntity.State.OPENED;
		}

		jsonObject.put(
			"state", state.getJSONObject()
		).put(
			"type", type.getJSONObject()
		);

		return create(jsonObject);
	}

	public Set<JobEntity> getByState(JobEntity.State... states) {
		Set<JobEntity> jobEntities = new HashSet<>();

		for (JobEntity jobEntity : getAll()) {
			for (JobEntity.State state : states) {
				if (state == jobEntity.getState()) {
					jobEntities.add(jobEntity);
				}
			}
		}

		return jobEntities;
	}

	@Override
	public JobEntityDALO getEntityDALO() {
		return _jobEntityDALO;
	}

	public JobQueue getJobQueue() {
		return _jobQueue;
	}

	@Override
	public void initialize() {
		Date date = new Date(System.currentTimeMillis() - _getJobArchiveAge());

		addAll(_jobEntityDALO.getAllAfterModifiedDate(date));

		addAll(
			_jobEntityDALO.getJobsByState(
				JobEntity.State.OPENED, JobEntity.State.QUEUED,
				JobEntity.State.RUNNING));
	}

	@Override
	public synchronized void initializeRelationships() {
	}

	public void relateJobToBuild(JobEntity jobEntity, BuildEntity buildEntity) {
		jobEntity.addBuildEntity(buildEntity);

		buildEntity.setJobEntity(jobEntity);
	}

	@Override
	public void remove(JobEntity jobEntity) {
		JobQueue jobQueue = getJobQueue();

		jobQueue.removeJobEntities(Collections.singleton(jobEntity));

		super.remove(jobEntity);
	}

	@Override
	public void remove(Set<JobEntity> jobEntities) {
		JobQueue jobQueue = getJobQueue();

		jobQueue.removeJobEntities(jobEntities);

		super.remove(jobEntities);
	}

	@Scheduled(cron = "${liferay.jethr0.job.archive.cron}")
	public void scheduledArchive() {
		Date date = new Date(System.currentTimeMillis() - _getJobArchiveAge());

		Set<Long> jobEntityIds = new HashSet<>();

		for (JobEntity jobEntity : getAll()) {
			if (date.before(jobEntity.getModifiedDate()) ||
				(jobEntity.getState() != JobEntity.State.COMPLETED)) {

				continue;
			}

			jobEntityIds.add(jobEntity.getId());
		}

		Map<Long, JobEntity> entitiesMap = getEntitiesMap();

		long jobCount = entitiesMap.size();

		for (Long jobEntityId : jobEntityIds) {
			entitiesMap.remove(jobEntityId);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Archived ", jobEntityIds.size(), " of ", jobCount,
					" jobs"));
		}
	}

	public void setBuildEntityRepository(
		BuildEntityRepository buildEntityRepository) {

		_buildEntityRepository = buildEntityRepository;
	}

	public void setJobQueue(JobQueue jobQueue) {
		_jobQueue = jobQueue;
	}

	@Override
	protected JobEntity updateRelationshipsFromDALO(JobEntity jobEntity) {
		return _updateJobToBuildsRelationshipsFromDALO(jobEntity);
	}

	@Override
	protected JobEntity updateRelationshipsToDALO(JobEntity jobEntity) {
		_jobToBuildsEntityRelationshipDALO.updateChildEntities(jobEntity);

		return jobEntity;
	}

	private long _getJobArchiveAge() {
		return Long.valueOf(_jobArchiveAgeInDays) * 1000 * 60 * 60 * 24;
	}

	private JobEntity _updateJobToBuildsRelationshipsFromDALO(
		JobEntity parentJobEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentJobEntity, _jobToBuildsEntityRelationshipDALO,
			_buildEntityRepository,
			(jobEntity, buildEntity) -> relateJobToBuild(
				jobEntity, buildEntity),
			jobEntity -> jobEntity.getBuildEntities(),
			(jobEntity, buildEntity) -> jobEntity.removeBuildEntity(
				buildEntity));
	}

	private static final Log _log = LogFactory.getLog(
		JobEntityRepository.class);

	private BuildEntityRepository _buildEntityRepository;

	@Value("${JETHR0_JOB_ARCHIVE_AGE_IN_DAYS:7}")
	private String _jobArchiveAgeInDays;

	@Autowired
	private JobEntityDALO _jobEntityDALO;

	private JobQueue _jobQueue;

	@Autowired
	private JobToBuildsEntityRelationshipDALO
		_jobToBuildsEntityRelationshipDALO;

}