/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.repository;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.dalo.BuildEntityDALO;
import com.liferay.jethr0.entity.dalo.EntityDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.dalo.JobToBuildsEntityRelationshipDALO;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildEntityRepository extends BaseEntityRepository<BuildEntity> {

	public BuildEntity create(
		JobEntity jobEntity, boolean initialBuild, String jenkinsJobName,
		String name, BuildEntity.State state) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"initialBuild", initialBuild
		).put(
			"jenkinsJobName", jenkinsJobName
		).put(
			"name", name
		).put(
			"state", state.getJSONObject()
		);

		return create(jobEntity, jsonObject);
	}

	public BuildEntity create(JobEntity jobEntity, JSONObject jsonObject) {
		jsonObject.put("r_jobToBuilds_c_jobId", jobEntity.getId());

		BuildEntity buildEntity = create(jsonObject);

		buildEntity.setJobEntity(jobEntity);

		jobEntity.addBuildEntity(buildEntity);

		return add(buildEntity);
	}

	public Set<BuildEntity> getAll(JobEntity jobEntity) {
		Set<BuildEntity> buildEntities = new HashSet<>(
			_jobToBuildsEntityRelationshipDALO.getChildEntities(jobEntity));

		for (BuildEntity buildEntity : buildEntities) {
			buildEntity.setJobEntity(jobEntity);

			buildEntity.addBuildParameterEntities(
				_buildParameterEntityRepository.getAll(buildEntity));
			buildEntity.addBuildRunEntities(
				_buildRunEntityRepository.getAll(buildEntity));
		}

		return addAll(buildEntities);
	}

	@Override
	public EntityDALO<BuildEntity> getEntityDALO() {
		return _buildEntityDALO;
	}

	@Override
	public void initialize() {
	}

	@Override
	public synchronized void initializeRelationships() {
		if (_initializedRelationships) {
			return;
		}

		_jobEntityRepository.initializeRelationships();

		for (BuildEntity buildEntity : getAll()) {
			JobEntity jobEntity = null;

			long jobEntityId = buildEntity.getJobEntityId();

			if (jobEntityId != 0) {
				jobEntity = _jobEntityRepository.getById(jobEntityId);
			}

			buildEntity.setJobEntity(jobEntity);

			buildEntity.addBuildParameterEntities(
				_buildParameterEntityRepository.getAll(buildEntity));

			buildEntity.addBuildRunEntities(
				_buildRunEntityRepository.getAll(buildEntity));
		}

		_initializedRelationships = true;
	}

	public void setBuildParameterEntityRepository(
		BuildParameterEntityRepository buildParameterEntityRepository) {

		_buildParameterEntityRepository = buildParameterEntityRepository;
	}

	public void setBuildRunEntityRepository(
		BuildRunEntityRepository buildRunEntityRepository) {

		_buildRunEntityRepository = buildRunEntityRepository;
	}

	public void setJobEntityRepository(
		JobEntityRepository jobEntityRepository) {

		_jobEntityRepository = jobEntityRepository;
	}

	@Autowired
	private BuildEntityDALO _buildEntityDALO;

	private BuildParameterEntityRepository _buildParameterEntityRepository;
	private BuildRunEntityRepository _buildRunEntityRepository;
	private boolean _initializedRelationships;
	private JobEntityRepository _jobEntityRepository;

	@Autowired
	private JobToBuildsEntityRelationshipDALO
		_jobToBuildsEntityRelationshipDALO;

}