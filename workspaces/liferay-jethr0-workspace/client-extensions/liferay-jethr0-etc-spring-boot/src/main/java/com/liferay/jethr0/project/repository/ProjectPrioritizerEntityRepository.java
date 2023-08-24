/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.dalo.ProjectPrioritizerEntityDALO;
import com.liferay.jethr0.project.dalo.ProjectPrioritizerToProjectComparatorsEntityRelationshipDALO;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizerFactory;

import java.util.Objects;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectPrioritizerEntityRepository
	extends BaseEntityRepository<ProjectPrioritizer> {

	public ProjectPrioritizer add(String name) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("name", name);

		return add(jsonObject);
	}

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
	public ProjectPrioritizerEntityDALO getEntityDALO() {
		return _projectPrioritizerEntityDALO;
	}

	@Override
	public void initializeRelationships() {
		for (ProjectPrioritizer projectPrioritizer : getAll()) {
			for (long projectComparatorId :
					_projectPrioritizerToProjectComparatorsEntityRelationshipDALO.
						getChildEntityIds(projectPrioritizer)) {

				if (projectComparatorId == 0) {
					continue;
				}

				projectPrioritizer.addProjectComparator(
					_projectComparatorEntityRepository.getById(
						projectComparatorId));
			}
		}
	}

	public void setProjectComparatorRepository(
		ProjectComparatorEntityRepository projectComparatorEntityRepository) {

		_projectComparatorEntityRepository = projectComparatorEntityRepository;
	}

	private ProjectComparatorEntityRepository
		_projectComparatorEntityRepository;

	@Autowired
	private ProjectPrioritizerEntityDALO _projectPrioritizerEntityDALO;

	@Autowired
	private ProjectPrioritizerFactory _projectPrioritizerFactory;

	@Autowired
	private ProjectPrioritizerToProjectComparatorsEntityRelationshipDALO
		_projectPrioritizerToProjectComparatorsEntityRelationshipDALO;

}