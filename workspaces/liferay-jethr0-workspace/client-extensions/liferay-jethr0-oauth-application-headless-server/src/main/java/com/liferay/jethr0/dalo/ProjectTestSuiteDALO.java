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

import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.ProjectFactory;
import com.liferay.jethr0.testsuite.TestSuite;
import com.liferay.jethr0.testsuite.TestSuiteFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectTestSuiteDALO extends BaseRelationshipDALO {

	public JSONObject createRelationship(Project project, TestSuite testSuite) {
		return create("/o/c/projects", project.getId(), testSuite.getId());
	}

	public void deleteRelationship(Project project, TestSuite testSuite) {
		delete("/o/c/projects", project.getId(), testSuite.getId());
	}

	public List<Project> retrieveProjects(TestSuite testSuite) {
		List<Project> projects = new ArrayList<>();

		for (JSONObject responseJSONObject :
				retrieve("/o/c/testsuites", testSuite.getId())) {

			projects.add(ProjectFactory.newProject(responseJSONObject));
		}

		return projects;
	}

	public List<TestSuite> retrieveTestSuites(Project project) {
		List<TestSuite> testSuites = new ArrayList<>();

		for (JSONObject responseJSONObject :
				retrieve("/o/c/projects", project.getId())) {

			testSuites.add(TestSuiteFactory.newTestSuite(responseJSONObject));
		}

		return testSuites;
	}

	public void updateRelationships(Project project) {
		List<TestSuite> remoteTestSuites = retrieveTestSuites(project);

		for (TestSuite testSuite : project.getTestSuites()) {
			if (remoteTestSuites.contains(testSuite)) {
				remoteTestSuites.removeAll(
					Collections.singletonList(testSuite));

				continue;
			}

			createRelationship(project, testSuite);
		}

		for (TestSuite remoteTestSuite : remoteTestSuites) {
			deleteRelationship(project, remoteTestSuite);
		}
	}

	public void updateRelationships(TestSuite testSuite) {
		List<Project> remoteProjects = retrieveProjects(testSuite);

		for (Project project : testSuite.getProjects()) {
			if (remoteProjects.contains(project)) {
				remoteProjects.removeAll(Collections.singletonList(project));

				continue;
			}

			createRelationship(project, testSuite);
		}

		for (Project remoteProject : remoteProjects) {
			deleteRelationship(remoteProject, testSuite);
		}
	}

	@Override
	protected String getObjectRelationshipName() {
		return "projectsToTestSuites";
	}

}