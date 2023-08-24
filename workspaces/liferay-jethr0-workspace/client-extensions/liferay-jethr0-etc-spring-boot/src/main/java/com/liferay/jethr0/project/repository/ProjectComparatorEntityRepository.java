/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.comparator.ProjectComparator;
import com.liferay.jethr0.project.dalo.ProjectComparatorEntityDALO;
import com.liferay.jethr0.project.dalo.ProjectPrioritizerToProjectComparatorsEntityRelationshipDALO;
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
public class ProjectComparatorEntityRepository
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
			_projectPrioritizerToProjectComparatorsEntityRelationshipDALO.
				getChildEntityIds(projectPrioritizer);

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
	public ProjectComparatorEntityDALO getEntityDALO() {
		return _projectComparatorEntityDALO;
	}

	@Override
	public void initializeRelationships() {
		for (ProjectComparator projectComparator : getAll()) {
			ProjectPrioritizer projectPrioritizer = null;

			long jenkinsServerId = projectComparator.getProjectPrioritizerId();

			if (jenkinsServerId != 0) {
				projectPrioritizer =
					_projectPrioritizerEntityRepository.getById(
						jenkinsServerId);
			}

			projectComparator.setProjectPrioritizer(projectPrioritizer);
		}
	}

	public void setProjectPrioritizerRepository(
		ProjectPrioritizerEntityRepository projectPrioritizerEntityRepository) {

		_projectPrioritizerEntityRepository =
			projectPrioritizerEntityRepository;
	}

	@Autowired
	private ProjectComparatorEntityDALO _projectComparatorEntityDALO;

	private ProjectPrioritizerEntityRepository
		_projectPrioritizerEntityRepository;

	@Autowired
	private ProjectPrioritizerToProjectComparatorsEntityRelationshipDALO
		_projectPrioritizerToProjectComparatorsEntityRelationshipDALO;

}