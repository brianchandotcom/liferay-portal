/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.comparator.ProjectComparator;
import com.liferay.jethr0.project.dalo.ProjectComparatorEntityDALO;
import com.liferay.jethr0.project.dalo.ProjectPrioritizerToProjectComparatorsEntityRelationshipDALO;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizerEntity;

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
		ProjectPrioritizerEntity projectPrioritizerEntity, long position,
		ProjectComparator.Type type, String value) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"position", position
		).put(
			"r_projectPrioritizerToProjectComparators_c_projectPrioritizerId",
			projectPrioritizerEntity.getId()
		).put(
			"type", type.getJSONObject()
		).put(
			"value", value
		);

		ProjectComparator projectComparator = add(jsonObject);

		projectComparator.setProjectPrioritizerEntity(projectPrioritizerEntity);

		projectPrioritizerEntity.addProjectComparator(projectComparator);

		return projectComparator;
	}

	public Set<ProjectComparator> getAll(
		ProjectPrioritizerEntity projectPrioritizerEntity) {

		Set<ProjectComparator> projectComparators = new HashSet<>();

		Set<Long> projectComparatorIds =
			_projectPrioritizerToProjectComparatorsEntityRelationshipDALO.
				getChildEntityIds(projectPrioritizerEntity);

		for (ProjectComparator projectComparator : getAll()) {
			if (!projectComparatorIds.contains(projectComparator.getId())) {
				continue;
			}

			projectPrioritizerEntity.addProjectComparator(projectComparator);

			projectComparator.setProjectPrioritizerEntity(
				projectPrioritizerEntity);

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
			ProjectPrioritizerEntity projectPrioritizerEntity = null;

			long jenkinsServerId =
				projectComparator.getProjectPrioritizerEntityId();

			if (jenkinsServerId != 0) {
				projectPrioritizerEntity =
					_projectPrioritizerEntityRepository.getById(
						jenkinsServerId);
			}

			projectComparator.setProjectPrioritizerEntity(
				projectPrioritizerEntity);
		}
	}

	public void setProjectPrioritizerEntityRepository(
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