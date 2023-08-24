/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.testsuite.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.ProjectFactory;
import com.liferay.jethr0.testsuite.TestSuite;
import com.liferay.jethr0.testsuite.TestSuiteFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectsToTestSuitesEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<Project, TestSuite> {

	@Override
	public EntityFactory<TestSuite> getChildEntityFactory() {
		return _testSuiteFactory;
	}

	@Override
	public EntityFactory<Project> getParentEntityFactory() {
		return _projectFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "projectsToTestSuites";
	}

	@Autowired
	private ProjectFactory _projectFactory;

	@Autowired
	private TestSuiteFactory _testSuiteFactory;

}