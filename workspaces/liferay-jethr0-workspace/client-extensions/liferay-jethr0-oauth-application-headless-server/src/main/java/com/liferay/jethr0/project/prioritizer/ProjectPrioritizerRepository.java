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

package com.liferay.jethr0.project.prioritizer;

import com.liferay.jethr0.dalo.ProjectPrioritizerToProjectComparatorsDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.comparator.ProjectComparator;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectPrioritizerRepository
	extends BaseEntityRepository<ProjectPrioritizer> {

	public ProjectPrioritizer getByName(String name) {
		for (ProjectPrioritizer projectPrioritizer : getAll()) {
			if (!Objects.equals(projectPrioritizer.getName(), name)) {
				continue;
			}

			return projectPrioritizer;
		}

		return null;
	}

	@Override
	public ProjectPrioritizerDALO getEntityDALO() {
		return _projectPrioritizerDALO;
	}

	@Override
	public ProjectPrioritizer updateEntityRelationshipsInDatabase(
		ProjectPrioritizer projectPrioritizer) {

		_projectPrioritizerToProjectComparatorsDALO.updateChildEntities(
			projectPrioritizer);

		return projectPrioritizer;
	}

	@Override
	protected ProjectPrioritizer updateEntityRelationshipsFromDatabase(
		ProjectPrioritizer projectPrioritizer) {

		List<ProjectComparator> projectComparators =
			_projectPrioritizerToProjectComparatorsDALO.getChildEntities(
				projectPrioritizer);

		projectPrioritizer.addProjectComparators(projectComparators);

		for (ProjectComparator projectComparator : projectComparators) {
			projectComparator.setProjectPrioritizer(projectPrioritizer);
		}

		return projectPrioritizer;
	}

	@Autowired
	private ProjectPrioritizerDALO _projectPrioritizerDALO;

	@Autowired
	private ProjectPrioritizerToProjectComparatorsDALO
		_projectPrioritizerToProjectComparatorsDALO;

}