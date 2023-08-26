/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.testsuite.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.project.ProjectEntity;
import com.liferay.jethr0.project.ProjectEntityFactory;
import com.liferay.jethr0.testsuite.TestSuiteEntity;
import com.liferay.jethr0.testsuite.TestSuiteEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectsToTestSuitesEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<ProjectEntity, TestSuiteEntity> {

	@Override
	public EntityFactory<TestSuiteEntity> getChildEntityFactory() {
		return _testSuiteEntityFactory;
	}

	@Override
	public EntityFactory<ProjectEntity> getParentEntityFactory() {
		return _projectEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "projectsToTestSuites";
	}

	@Autowired
	private ProjectEntityFactory _projectEntityFactory;

	@Autowired
	private TestSuiteEntityFactory _testSuiteEntityFactory;

}