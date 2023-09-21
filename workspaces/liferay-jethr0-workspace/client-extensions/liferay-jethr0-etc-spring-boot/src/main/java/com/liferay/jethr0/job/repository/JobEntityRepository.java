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
import com.liferay.jethr0.util.StringUtil;

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

	public JobEntity add(
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

		return add(jsonObject);
	}

	@Override
	public JobEntity getById(long id) {
		if (hasEntity(id)) {
			return super.getById(id);
		}

		JobEntity jobEntity = _jobEntityDALO.get(id);

		jobEntity.addBuildEntities(_buildEntityRepository.getAll(jobEntity));

		for (BuildEntity buildEntity : jobEntity.getBuildEntities()) {
			buildEntity.setJobEntity(jobEntity);
		}

		return add(jobEntity);
	}

	public Set<JobEntity> getByState(JobEntity.State... states) {
		return addAll(_jobEntityDALO.getJobsByState(states));
	}

	@Override
	public JobEntityDALO getEntityDALO() {
		return _jobEntityDALO;
	}

	@Override
	public void initialize() {
	}

	@Override
	public synchronized void initializeRelationships() {
		if (_initializedRelationships) {
			return;
		}

		for (JobEntity jobEntity : getAll()) {
			jobEntity.addBuildEntities(
				_buildEntityRepository.getAll(jobEntity));
		}

		_initializedRelationships = true;
	}

	public void setBuildEntityRepository(
		BuildEntityRepository buildEntityRepository) {

		_buildEntityRepository = buildEntityRepository;
	}

	private BuildEntityRepository _buildEntityRepository;
	private boolean _initializedRelationships;

	@Autowired
	private JobEntityDALO _jobEntityDALO;

	@Autowired
	private JobToBuildsEntityRelationshipDALO
		_jobToBuildsEntityRelationshipDALO;

}