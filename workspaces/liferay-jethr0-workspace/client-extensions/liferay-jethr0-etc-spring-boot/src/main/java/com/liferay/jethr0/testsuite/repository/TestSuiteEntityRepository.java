/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.testsuite.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.job.ProjectEntity;
import com.liferay.jethr0.testsuite.TestSuiteEntity;
import com.liferay.jethr0.testsuite.dalo.ProjectsToTestSuitesEntityRelationshipDALO;
import com.liferay.jethr0.testsuite.dalo.TestSuiteEntityDALO;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TestSuiteEntityRepository
	extends BaseEntityRepository<TestSuiteEntity> {

	public Set<TestSuiteEntity> getAll(ProjectEntity projectEntity) {
		Set<TestSuiteEntity> testSuiteEntities = new HashSet<>();

		Set<Long> testSuiteEntityIds =
			_projectsToTestSuitesEntityRelationshipDALO.getChildEntityIds(
				projectEntity);

		for (TestSuiteEntity testSuiteEntity : getAll()) {
			if (!testSuiteEntityIds.contains(testSuiteEntity.getId())) {
				continue;
			}

			projectEntity.addTestSuiteEntity(testSuiteEntity);

			testSuiteEntity.addProjectEntity(projectEntity);

			testSuiteEntities.add(testSuiteEntity);
		}

		return testSuiteEntities;
	}

	@Override
	public TestSuiteEntityDALO getEntityDALO() {
		return _testSuiteEntityDALO;
	}

	@Autowired
	private ProjectsToTestSuitesEntityRelationshipDALO
		_projectsToTestSuitesEntityRelationshipDALO;

	@Autowired
	private TestSuiteEntityDALO _testSuiteEntityDALO;

}