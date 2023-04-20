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
public class ProjectsToTestSuitesDALO
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