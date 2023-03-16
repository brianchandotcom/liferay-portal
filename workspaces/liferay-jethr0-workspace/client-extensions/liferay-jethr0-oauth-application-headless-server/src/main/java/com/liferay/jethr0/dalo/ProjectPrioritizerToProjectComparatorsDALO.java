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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectPrioritizerToProjectComparatorsDALO
	extends BaseRelationshipDALO {

	public JSONObject createRelationship(
		ProjectPrioritizer projectPrioritizer,
		ProjectComparator projectComparator) {

		return create(
			"/o/c/projectprioritizers", projectPrioritizer.getId(),
			projectComparator.getId());
	}

	public void deleteRelationship(
		ProjectPrioritizer projectPrioritizer,
		ProjectComparator projectComparator) {

		delete(
			"/o/c/projectprioritizers", projectPrioritizer.getId(),
			projectComparator.getId());
	}

	public List<ProjectComparator> retrieveProjectComparators(
		ProjectPrioritizer projectPrioritizer) {

		List<ProjectComparator> projectComparators = new ArrayList<>();

		for (JSONObject jsonObject :
				retrieve(
					"/o/c/projectprioritizers", projectPrioritizer.getId())) {

			projectComparators.add(
				ProjectComparatorFactory.newProjectComparator(
					projectPrioritizer, jsonObject));
		}

		return projectComparators;
	}

	public void updateRelationships(ProjectPrioritizer projectPrioritizer) {
		List<ProjectComparator> remoteProjectComparators =
			retrieveProjectComparators(projectPrioritizer);

		for (ProjectComparator projectComparator :
				projectPrioritizer.getProjectComparators()) {

			if (remoteProjectComparators.contains(projectComparator)) {
				remoteProjectComparators.removeAll(
					Collections.singletonList(projectComparator));

				continue;
			}

			createRelationship(projectPrioritizer, projectComparator);
		}

		for (ProjectComparator remoteProjectComparator :
				remoteProjectComparators) {

			deleteRelationship(projectPrioritizer, remoteProjectComparator);
		}
	}

	@Override
	protected String getObjectRelationshipName() {
		return "projectPrioritizerToProjectComparators";
	}

}