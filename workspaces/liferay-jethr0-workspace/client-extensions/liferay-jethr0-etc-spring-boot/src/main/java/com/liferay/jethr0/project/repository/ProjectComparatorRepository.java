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

package com.liferay.jethr0.project.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.comparator.ProjectComparator;
import com.liferay.jethr0.project.dalo.ProjectComparatorDALO;
import com.liferay.jethr0.project.dalo.ProjectPrioritizerToProjectComparatorsDALO;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectComparatorRepository
	extends BaseEntityRepository<ProjectComparator> {

	public ProjectComparator add(
		ProjectPrioritizer projectPrioritizer, long position,
		ProjectComparator.Type type, String value) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"position", position
		).put(
			"r_projectPrioritizerToProjectComparators_c_projectPrioritizerId",
			projectPrioritizer.getId()
		).put(
			"type", type.getJSONObject()
		).put(
			"value", value
		);

		ProjectComparator projectComparator = add(jsonObject);

		projectComparator.setProjectPrioritizer(projectPrioritizer);

		projectPrioritizer.addProjectComparator(projectComparator);

		return projectComparator;
	}

	public Set<ProjectComparator> getAll(
		ProjectPrioritizer projectPrioritizer) {

		Set<ProjectComparator> projectComparators = new HashSet<>();

		Set<Long> projectComparatorIds =
			_projectPrioritizerToProjectComparatorsDALO.getChildEntityIds(
				projectPrioritizer);

		for (ProjectComparator projectComparator : getAll()) {
			if (!projectComparatorIds.contains(projectComparator.getId())) {
				continue;
			}

			projectPrioritizer.addProjectComparator(projectComparator);

			projectComparator.setProjectPrioritizer(projectPrioritizer);

			projectComparators.add(projectComparator);
		}

		return projectComparators;
	}

	@Override
	public ProjectComparatorDALO getEntityDALO() {
		return _projectComparatorDALO;
	}

	@Autowired
	private ProjectComparatorDALO _projectComparatorDALO;

	@Autowired
	private ProjectPrioritizerRepository _projectPrioritizerRepository;

	@Autowired
	private ProjectPrioritizerToProjectComparatorsDALO
		_projectPrioritizerToProjectComparatorsDALO;

}