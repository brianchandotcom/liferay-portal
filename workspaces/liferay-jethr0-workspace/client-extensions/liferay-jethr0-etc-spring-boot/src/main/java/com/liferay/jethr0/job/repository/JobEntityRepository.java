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
import java.util.Set;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JobEntityRepository extends BaseEntityRepository<JobEntity> {

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
		return addAll(_jobEntityDALO.getJobsByState(states));
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

	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private JobEntityDALO _jobEntityDALO;

	private JobQueue _jobQueue;

	@Autowired
	private JobToBuildsEntityRelationshipDALO
		_jobToBuildsEntityRelationshipDALO;

}