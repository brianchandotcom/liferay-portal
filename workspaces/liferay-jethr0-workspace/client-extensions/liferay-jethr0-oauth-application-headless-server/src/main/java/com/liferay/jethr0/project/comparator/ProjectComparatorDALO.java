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

package com.liferay.jethr0.project.comparator;

import com.liferay.jethr0.object.ObjectDALO;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;
import com.liferay.jethr0.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectComparatorDALO extends ObjectDALO {

	public ProjectComparator createProjectComparator(
		ProjectPrioritizer projectPrioritizer, int position,
		ProjectComparator.Type type, String value) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"position", position
		).put(
			"r_projectPrioritizerToProjectComparators_c_projectPrioritizerId",
			projectPrioritizer.getID()
		).put(
			"type", type.getJSONObject()
		).put(
			"value", value
		);

		return _newProjectComparator(projectPrioritizer, create(jsonObject));
	}

	public void deleteProjectComparator(ProjectComparator projectComparator) {
		if (projectComparator == null) {
			return;
		}

		ProjectPrioritizer projectPrioritizer =
			projectComparator.getProjectPrioritizer();

		projectPrioritizer.removeProjectComparator(projectComparator);

		delete(projectComparator.getID());
	}

	public List<ProjectComparator> retrieveProjectComparators(
		ProjectPrioritizer projectPrioritizer) {

		List<ProjectComparator> projectComparators = new ArrayList<>();

		String objectURLPath = StringUtil.combine(
			"/o/c/projectprioritizers/", projectPrioritizer.getID(),
			"/projectPrioritizerToProjectComparators");

		for (JSONObject jsonObject : retrieve(objectURLPath)) {
			projectComparators.add(
				_newProjectComparator(projectPrioritizer, jsonObject));
		}

		return projectComparators;
	}

	public ProjectComparator updateProjectComparator(
		ProjectComparator projectComparator) {

		update(projectComparator.getJSONObject());

		return projectComparator;
	}

	@Override
	protected String getObjectName() {
		return "Project Comparator";
	}

	private ProjectComparator _newProjectComparator(
		ProjectPrioritizer projectPrioritizer, JSONObject jsonObject) {

		ProjectComparator.Type type = ProjectComparator.Type.get(
			jsonObject.getJSONObject("type"));

		if (type == ProjectComparator.Type.FIFO) {
			return new FIFOProjectComparator(projectPrioritizer, jsonObject);
		}
		else if (type == ProjectComparator.Type.PROJECT_PRIORITY) {
			return new PriorityProjectComparator(
				projectPrioritizer, jsonObject);
		}

		throw new UnsupportedOperationException();
	}

}