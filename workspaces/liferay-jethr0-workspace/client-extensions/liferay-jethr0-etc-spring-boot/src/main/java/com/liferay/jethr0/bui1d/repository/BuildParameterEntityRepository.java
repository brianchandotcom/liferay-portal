/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.repository;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.dalo.BuildParameterEntityDALO;
import com.liferay.jethr0.bui1d.dalo.BuildToBuildParametersEntityRelationshipDALO;
import com.liferay.jethr0.bui1d.parameter.BuildParameterEntity;
import com.liferay.jethr0.entity.dalo.EntityDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildParameterEntityRepository
	extends BaseEntityRepository<BuildParameterEntity> {

	public BuildParameterEntity create(
		BuildEntity buildEntity, JSONObject jsonObject) {

		return create(
			buildEntity, jsonObject.getString("name"),
			jsonObject.getString("value"));
	}

	public BuildParameterEntity create(
		BuildEntity buildEntity, String name, String value) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"name", name
		).put(
			"r_buildToBuildParameters_c_buildId", buildEntity.getId()
		).put(
			"value", value
		);

		BuildParameterEntity buildParameterEntity = create(jsonObject);

		buildEntity.addBuildParameterEntity(buildParameterEntity);

		buildParameterEntity.setBuildEntity(buildEntity);

		return add(buildParameterEntity);
	}

	public Set<BuildParameterEntity> getAll(BuildEntity buildEntity) {
		Set<BuildParameterEntity> buildParameterEntities = new HashSet<>(
			_buildToBuildParametersEntityRelationshipDALO.getChildEntities(
				buildEntity));

		for (BuildParameterEntity buildParameterEntity :
				buildParameterEntities) {

			buildParameterEntity.setBuildEntity(buildEntity);
		}

		return addAll(buildParameterEntities);
	}

	@Override
	public EntityDALO<BuildParameterEntity> getEntityDALO() {
		return _buildParameterEntityDALO;
	}

	@Override
	public void initialize() {
	}

	@Override
	public synchronized void initializeRelationships() {
		_buildEntityRepository.initializeRelationships();

		for (BuildParameterEntity buildParameterEntity : getAll()) {
			BuildEntity buildEntity = null;

			long buildEntityId = buildParameterEntity.getBuildEntityId();

			if (buildEntityId != 0) {
				buildEntity = _buildEntityRepository.getById(buildEntityId);
			}

			buildParameterEntity.setBuildEntity(buildEntity);
		}
	}

	public void setBuildRepository(
		BuildEntityRepository buildEntityRepository) {

		_buildEntityRepository = buildEntityRepository;
	}

	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private BuildParameterEntityDALO _buildParameterEntityDALO;

	@Autowired
	private BuildToBuildParametersEntityRelationshipDALO
		_buildToBuildParametersEntityRelationshipDALO;

}