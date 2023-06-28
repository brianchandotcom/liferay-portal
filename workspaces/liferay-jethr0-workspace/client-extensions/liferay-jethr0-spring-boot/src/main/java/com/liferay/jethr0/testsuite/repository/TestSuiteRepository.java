/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jethr0.testsuite.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.testsuite.TestSuite;
import com.liferay.jethr0.testsuite.dalo.ProjectsToTestSuitesDALO;
import com.liferay.jethr0.testsuite.dalo.TestSuiteDALO;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TestSuiteRepository extends BaseEntityRepository<TestSuite> {

	public Set<TestSuite> getAll(Project project) {
		Set<TestSuite> projectTestSuites = new HashSet<>();

		Set<Long> testSuiteIds = _projectsToTestSuitesDALO.getChildEntityIds(
			project);

		for (TestSuite testSuite : getAll()) {
			if (!testSuiteIds.contains(testSuite.getId())) {
				continue;
			}

			project.addTestSuite(testSuite);

			testSuite.addProject(project);

			projectTestSuites.add(testSuite);
		}

		return projectTestSuites;
	}

	@Override
	public TestSuiteDALO getEntityDALO() {
		return _testSuiteDALO;
	}

	@Autowired
	private ProjectsToTestSuitesDALO _projectsToTestSuitesDALO;

	@Autowired
	private TestSuiteDALO _testSuiteDALO;

}