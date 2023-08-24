/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.environment.repository;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.dalo.BuildToEnvironmentsEntityRelationshipDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.environment.dalo.EnvironmentEntityDALO;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class EnvironmentRepository extends BaseEntityRepository<Environment> {

	public Set<Environment> getAll(Build build) {
		Set<Environment> buildEnvironments = new HashSet<>();

		Set<Long> environmentIds =
			_buildToEnvironmentsEntityRelationshipDALO.getChildEntityIds(build);

		for (Environment environment : getAll()) {
			if (!environmentIds.contains(environment.getId())) {
				continue;
			}

			build.addEnvironment(environment);

			environment.setBuild(build);

			buildEnvironments.add(environment);
		}

		return buildEnvironments;
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