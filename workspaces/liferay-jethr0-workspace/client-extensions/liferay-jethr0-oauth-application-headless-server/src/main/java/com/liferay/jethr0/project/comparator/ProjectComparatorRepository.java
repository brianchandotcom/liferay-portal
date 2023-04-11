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

import com.liferay.jethr0.dalo.ProjectPrioritizerToProjectComparatorsDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectComparatorRepository
	extends BaseEntityRepository<ProjectComparator> {

	@Override
	public ProjectComparatorDALO getEntityDALO() {
		return _projectComparatorDALO;
	}

	@Override
	public ProjectComparator updateEntityRelationshipsInDatabase(
		ProjectComparator projectComparator) {

		_projectPrioritizerToProjectComparatorsDALO.updateParentEntities(
			projectComparator);

		return projectComparator;
	}

	@Override
	protected ProjectComparator updateEntityRelationshipsFromDatabase(
		ProjectComparator projectComparator) {

		for (ProjectPrioritizer projectPrioritizer :
				_projectPrioritizerToProjectComparatorsDALO.getParentEntities(
					projectComparator)) {

			projectComparator.setProjectPrioritizer(projectPrioritizer);

			projectPrioritizer.addProjectComparator(projectComparator);
		}

		return projectComparator;
	}

	@Autowired
	private ProjectComparatorDALO _projectComparatorDALO;

	@Autowired
	private ProjectPrioritizerRepository _projectPrioritizerRepository;

	@Autowired
	private ProjectPrioritizerToProjectComparatorsDALO
		_projectPrioritizerToProjectComparatorsDALO;

}