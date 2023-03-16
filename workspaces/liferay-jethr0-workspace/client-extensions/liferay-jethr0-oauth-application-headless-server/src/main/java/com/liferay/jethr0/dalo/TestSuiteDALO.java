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

package com.liferay.jethr0.dalo;

import com.liferay.jethr0.testsuite.TestSuite;
import com.liferay.jethr0.testsuite.TestSuiteFactory;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TestSuiteDALO extends BaseDALO {

	public TestSuite createTestSuite(String name) {
		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("name", name);

		JSONObject responseJSONObject = create(requestJSONObject);

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return TestSuiteFactory.newTestSuite(responseJSONObject);
	}

	public void deleteTestSuite(TestSuite testSuite) {
		if (testSuite == null) {
			return;
		}

		delete(testSuite.getId());

		TestSuiteFactory.removeTestSuite(testSuite);
	}

	public List<TestSuite> retrieveTestSuites() {
		List<TestSuite> testSuites = new ArrayList<>();

		for (JSONObject jsonObject : retrieve()) {
			TestSuite testSuite = TestSuiteFactory.newTestSuite(jsonObject);

			testSuite.addProjects(
				_projectsToTestSuitesDALO.retrieveProjects(testSuite));

			testSuites.add(testSuite);
		}

		return testSuites;
	}

	public TestSuite updateTestSuite(TestSuite testSuite) {
		_projectsToTestSuitesDALO.updateRelationships(testSuite);

		JSONObject responseJSONObject = update(testSuite.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return testSuite;
	}

	@Override
	protected String getObjectDefinitionLabel() {
		return "Test Suite";
	}

	@Autowired
	private ProjectsToTestSuitesDALO _projectsToTestSuitesDALO;

}