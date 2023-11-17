/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.repository;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.dalo.BuildEntityDALO;
import com.liferay.jethr0.bui1d.dalo.BuildToBuildRunsEntityRelationshipDALO;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.entity.dalo.EntityDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.dalo.JobToBuildsEntityRelationshipDALO;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import java.util.Map;

import org.json.JSONArray;
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
		String name, Map<String, String> parameters, BuildEntity.State state) {

		JSONArray parametersJSONArray = new JSONArray();

		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			JSONObject parameterJSONObject = new JSONObject();

			parameterJSONObject.put(
				"name", parameter.getKey()
			).put(
				"value", parameter.getValue()
			);

			parametersJSONArray.put(parameterJSONObject);
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"initialBuild", initialBuild
		).put(
			"jenkinsJobName", jenkinsJobName
		).put(
			"name", name
		).put(
			"parameters", parametersJSONArray.toString()
		).put(
			"state", state.getJSONObject()
		);

		return create(jobEntity, jsonObject);
	}

	public BuildEntity create(JobEntity jobEntity, JSONObject jsonObject) {
		jsonObject.put("r_jobToBuilds_c_jobId", jobEntity.getId());

		return create(jsonObject);
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
	}

	public void relateBuildToBuildRun(
		BuildEntity buildEntity, BuildRunEntity buildRunEntity) {

		buildEntity.addBuildRunEntity(buildRunEntity);

		buildRunEntity.setBuildEntity(buildEntity);
	}

	public void setBuildRunEntityRepository(
		BuildRunEntityRepository buildRunEntityRepository) {

		_buildRunEntityRepository = buildRunEntityRepository;
	}

	public void setJobEntityRepository(
		JobEntityRepository jobEntityRepository) {

		_jobEntityRepository = jobEntityRepository;
	}

	@Override
	protected BuildEntity updateRelationshipsFromDALO(BuildEntity buildEntity) {
		_jobEntityRepository.relateJobToBuild(
			_jobEntityRepository.getById(buildEntity.getJobEntityId()),
			buildEntity);

		return _updateBuildToBuildRunRelationshipsFromDALO(buildEntity);
	}

	@Override
	protected BuildEntity updateRelationshipsToDALO(BuildEntity buildEntity) {
		_buildToBuildRunsEntityRelationshipDALO.updateChildEntities(
			buildEntity);

		return buildEntity;
	}

	private BuildEntity _updateBuildToBuildRunRelationshipsFromDALO(
		BuildEntity parentBuildEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentBuildEntity, _buildToBuildRunsEntityRelationshipDALO,
			_buildRunEntityRepository,
			(buildEntity, buildRunEntity) -> relateBuildToBuildRun(
				buildEntity, buildRunEntity),
			buildEntity -> buildEntity.getBuildRunEntities(),
			(buildEntity, buildRunEntity) -> buildEntity.removeBuildRunEntity(
				buildRunEntity));
	}

	@Autowired
	private BuildEntityDALO _buildEntityDALO;

	private BuildRunEntityRepository _buildRunEntityRepository;

	@Autowired
	private BuildToBuildRunsEntityRelationshipDALO
		_buildToBuildRunsEntityRelationshipDALO;

	private JobEntityRepository _jobEntityRepository;

	@Autowired
	private JobToBuildsEntityRelationshipDALO
		_jobToBuildsEntityRelationshipDALO;

}