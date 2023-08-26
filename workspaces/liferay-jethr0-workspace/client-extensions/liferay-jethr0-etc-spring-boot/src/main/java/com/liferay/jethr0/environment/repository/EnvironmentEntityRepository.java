/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.environment.repository;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.dalo.BuildToEnvironmentsEntityRelationshipDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.environment.EnvironmentEntity;
import com.liferay.jethr0.environment.dalo.EnvironmentEntityDALO;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class EnvironmentEntityRepository
	extends BaseEntityRepository<EnvironmentEntity> {

	public Set<EnvironmentEntity> getAll(BuildEntity buildEntity) {
		Set<EnvironmentEntity> buildEnvironmentEntities = new HashSet<>();

		Set<Long> environmentIds =
			_buildToEnvironmentsEntityRelationshipDALO.getChildEntityIds(
				buildEntity);

		for (EnvironmentEntity environmentEntity : getAll()) {
			if (!environmentIds.contains(environmentEntity.getId())) {
				continue;
			}

			buildEntity.addEnvironmentEntity(environmentEntity);

			environmentEntity.setBuildEntity(buildEntity);

			buildEnvironmentEntities.add(environmentEntity);
		}

		return buildEnvironmentEntities;
	}

	@Override
	public EnvironmentEntityDALO getEntityDALO() {
		return _environmentEntityDALO;
	}

	@Autowired
	private BuildToEnvironmentsEntityRelationshipDALO
		_buildToEnvironmentsEntityRelationshipDALO;

	@Autowired
	private EnvironmentEntityDALO _environmentEntityDALO;

}