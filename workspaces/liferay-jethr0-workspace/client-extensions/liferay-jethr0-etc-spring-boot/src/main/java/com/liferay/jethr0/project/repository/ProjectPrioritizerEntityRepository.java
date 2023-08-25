/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.dalo.ProjectPrioritizerEntityDALO;
import com.liferay.jethr0.project.dalo.ProjectPrioritizerToProjectComparatorsEntityRelationshipDALO;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizerEntity;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizerEntityFactory;

import java.util.Objects;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectPrioritizerEntityRepository
	extends BaseEntityRepository<ProjectPrioritizerEntity> {

	public ProjectPrioritizerEntity add(String name) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("name", name);

		return add(jsonObject);
	}

	public ProjectPrioritizerEntity getByName(String name) {
		for (ProjectPrioritizerEntity projectPrioritizerEntity : getAll()) {
			if (!Objects.equals(projectPrioritizerEntity.getName(), name)) {
				continue;
			}

			return projectPrioritizerEntity;
		}

		return null;
	}

	@Override
	public ProjectPrioritizerEntityDALO getEntityDALO() {
		return _projectPrioritizerEntityDALO;
	}

	@Override
	public void initializeRelationships() {
		for (ProjectPrioritizerEntity projectPrioritizerEntity : getAll()) {
			for (long projectComparatorEntityId :
					_projectPrioritizerToProjectComparatorsEntityRelationshipDALO.
						getChildEntityIds(projectPrioritizerEntity)) {

				if (projectComparatorEntityId == 0) {
					continue;
				}

				projectPrioritizerEntity.addProjectComparatorEntity(
					_projectComparatorEntityRepository.getById(
						projectComparatorEntityId));
			}
		}
	}

	public void setProjectComparatorEntityRepository(
		ProjectComparatorEntityRepository projectComparatorEntityRepository) {

		_projectComparatorEntityRepository = projectComparatorEntityRepository;
	}

	private ProjectComparatorEntityRepository
		_projectComparatorEntityRepository;

	@Autowired
	private ProjectPrioritizerEntityDALO _projectPrioritizerEntityDALO;

	@Autowired
	private ProjectPrioritizerEntityFactory _projectPrioritizerEntityFactory;

	@Autowired
	private ProjectPrioritizerToProjectComparatorsEntityRelationshipDALO
		_projectPrioritizerToProjectComparatorsEntityRelationshipDALO;

}