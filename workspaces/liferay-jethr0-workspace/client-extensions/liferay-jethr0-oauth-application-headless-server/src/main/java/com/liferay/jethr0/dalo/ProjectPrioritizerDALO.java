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

import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizerFactory;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectPrioritizerDALO extends BaseDALO {

	public ProjectPrioritizer createProjectPrioritizer(String name) {
		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("name", name);

		JSONObject responseJSONObject = create(requestJSONObject);

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return ProjectPrioritizerFactory.newProjectPrioritizer(
			responseJSONObject);
	}

	public void deleteProjectPrioritizer(
		ProjectPrioritizer projectPrioritizer) {

		if (projectPrioritizer == null) {
			return;
		}

		delete(projectPrioritizer.getId());

		ProjectPrioritizerFactory.removeProjectPrioritizer(projectPrioritizer);
	}

	public List<ProjectPrioritizer> retrieveProjectPrioritizers() {
		List<ProjectPrioritizer> projectPrioritizers = new ArrayList<>();

		for (JSONObject responseJSONObject : retrieve()) {
			ProjectPrioritizer projectPrioritizer =
				ProjectPrioritizerFactory.newProjectPrioritizer(
					responseJSONObject);

			projectPrioritizer.addProjectComparators(
				_projectPrioritizerToProjectComparatorsDALO.
					retrieveProjectComparators(projectPrioritizer));

			projectPrioritizers.add(projectPrioritizer);
		}

		return projectPrioritizers;
	}

	public ProjectPrioritizer updateProjectPrioritizer(
		ProjectPrioritizer projectPrioritizer) {

		_projectPrioritizerToProjectComparatorsDALO.updateRelationships(
			projectPrioritizer);

		JSONObject responseJSONObject = update(
			projectPrioritizer.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return projectPrioritizer;
	}

	@Override
	protected String getObjectDefinitionLabel() {
		return "Project Prioritizer";
	}

	@Autowired
	private ProjectPrioritizerToProjectComparatorsDALO
		_projectPrioritizerToProjectComparatorsDALO;

}