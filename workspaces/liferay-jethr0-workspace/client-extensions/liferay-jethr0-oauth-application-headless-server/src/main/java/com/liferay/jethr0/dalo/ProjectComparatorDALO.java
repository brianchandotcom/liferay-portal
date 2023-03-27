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

import com.liferay.jethr0.project.comparator.ProjectComparator;
import com.liferay.jethr0.project.comparator.ProjectComparatorFactory;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectComparatorDALO extends BaseDALO {

	public ProjectComparator createProjectComparator(
		ProjectPrioritizer projectPrioritizer, int position,
		ProjectComparator.Type type, String value) {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"position", position
		).put(
			"r_projectPrioritizerToProjectComparators_c_projectPrioritizerId",
			projectPrioritizer.getId()
		).put(
			"type", type.getJSONObject()
		).put(
			"value", value
		);

		JSONObject responseJSONObject = create(requestJSONObject);

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return ProjectComparatorFactory.newProjectComparator(
			projectPrioritizer, responseJSONObject);
	}

	public void deleteProjectComparator(ProjectComparator projectComparator) {
		if (projectComparator == null) {
			return;
		}

		ProjectPrioritizer projectPrioritizer =
			projectComparator.getProjectPrioritizer();

		projectPrioritizer.removeProjectComparator(projectComparator);

		delete(projectComparator.getId());

		ProjectComparatorFactory.removeProjectComparator(projectComparator);
	}

	public ProjectComparator updateProjectComparator(
		ProjectComparator projectComparator) {

		JSONObject responseJSONObject = update(
			projectComparator.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return projectComparator;
	}

	@Override
	protected String getObjectDefinitionLabel() {
		return "Project Comparator";
	}

}