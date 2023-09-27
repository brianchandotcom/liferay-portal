/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.repository;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.dalo.BuildRunEntityDALO;
import com.liferay.jethr0.bui1d.dalo.BuildToBuildRunsEntityRelationshipDALO;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.entity.dalo.EntityDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildRunEntityRepository
	extends BaseEntityRepository<BuildRunEntity> {

	public BuildRunEntity create(
		BuildEntity buildEntity, BuildRunEntity.State state) {

		JSONObject jsonObject = new JSONObject();

		if (state == null) {
			state = BuildRunEntity.State.OPENED;
		}

		jsonObject.put(
			"r_buildToBuildRuns_c_buildId", buildEntity.getId()
		).put(
			"state", state.getJSONObject()
		);

		return create(jsonObject);
	}

	@Override
	public EntityDALO<BuildRunEntity> getEntityDALO() {
		return _buildRunEntityDALO;
	}

	@Override
	public void initialize() {
	}

	@Override
	public synchronized void initializeRelationships() {
	}

	public void setBuildEntityRepository(
		BuildEntityRepository buildEntityRepository) {

		_buildEntityRepository = buildEntityRepository;
	}

	@Override
	protected BuildRunEntity updateRelationshipsFromDALO(
		BuildRunEntity buildRunEntity) {

		_buildEntityRepository.relateBuildToBuildRun(
			_buildEntityRepository.getById(buildRunEntity.getBuildEntityId()),
			buildRunEntity);

		return buildRunEntity;
	}

	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private BuildRunEntityDALO _buildRunEntityDALO;

	@Autowired
	private BuildToBuildRunsEntityRelationshipDALO
		_buildToBuildRunsEntityRelationshipDALO;

}