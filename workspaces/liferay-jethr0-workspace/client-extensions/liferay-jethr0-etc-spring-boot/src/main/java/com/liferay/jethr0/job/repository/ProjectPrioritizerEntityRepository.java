/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.job.dalo.ProjectPrioritizerEntityDALO;
import com.liferay.jethr0.job.dalo.ProjectPrioritizerToProjectComparatorsEntityRelationshipDALO;
import com.liferay.jethr0.job.prioritizer.JobPrioritizerEntity;
import com.liferay.jethr0.job.prioritizer.ProjectPrioritizerEntityFactory;

import java.util.Objects;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectPrioritizerEntityRepository
	extends BaseEntityRepository<JobPrioritizerEntity> {

	public JobPrioritizerEntity add(String name) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("name", name);

		return add(jsonObject);
	}

	public JobPrioritizerEntity getByName(String name) {
		for (JobPrioritizerEntity jobPrioritizerEntity : getAll()) {
			if (!Objects.equals(jobPrioritizerEntity.getName(), name)) {
				continue;
			}

			return jobPrioritizerEntity;
		}

		return null;
	}

	@Override
	public ProjectPrioritizerEntityDALO getEntityDALO() {
		return _projectPrioritizerEntityDALO;
	}

	@Override
	public void initializeRelationships() {
		for (JobPrioritizerEntity jobPrioritizerEntity : getAll()) {
			for (long projectComparatorEntityId :
					_projectPrioritizerToProjectComparatorsEntityRelationshipDALO.
						getChildEntityIds(jobPrioritizerEntity)) {

				if (projectComparatorEntityId == 0) {
					continue;
				}

				jobPrioritizerEntity.addProjectComparatorEntity(
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