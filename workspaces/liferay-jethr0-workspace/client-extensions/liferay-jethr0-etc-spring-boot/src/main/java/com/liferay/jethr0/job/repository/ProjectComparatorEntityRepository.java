/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.job.comparator.ProjectComparatorEntity;
import com.liferay.jethr0.job.dalo.ProjectComparatorEntityDALO;
import com.liferay.jethr0.job.dalo.ProjectPrioritizerToProjectComparatorsEntityRelationshipDALO;
import com.liferay.jethr0.job.prioritizer.JobPrioritizerEntity;

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
	extends BaseEntityRepository<ProjectComparatorEntity> {

	public ProjectComparatorEntity add(
		JobPrioritizerEntity jobPrioritizerEntity, long position,
		ProjectComparatorEntity.Type type, String value) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"position", position
		).put(
			"r_projectPrioritizerToProjectComparators_c_projectPrioritizerId",
			jobPrioritizerEntity.getId()
		).put(
			"type", type.getJSONObject()
		).put(
			"value", value
		);

		ProjectComparatorEntity projectComparatorEntity = add(jsonObject);

		projectComparatorEntity.setJobPrioritizerEntity(jobPrioritizerEntity);

		jobPrioritizerEntity.addProjectComparatorEntity(
			projectComparatorEntity);

		return projectComparatorEntity;
	}

	public Set<ProjectComparatorEntity> getAll(
		JobPrioritizerEntity jobPrioritizerEntity) {

		Set<ProjectComparatorEntity> projectComparatorEntities =
			new HashSet<>();

		Set<Long> projectComparatorIds =
			_projectPrioritizerToProjectComparatorsEntityRelationshipDALO.
				getChildEntityIds(jobPrioritizerEntity);

		for (ProjectComparatorEntity projectComparatorEntity : getAll()) {
			if (!projectComparatorIds.contains(
					projectComparatorEntity.getId())) {

				continue;
			}

			jobPrioritizerEntity.addProjectComparatorEntity(
				projectComparatorEntity);

			projectComparatorEntity.setJobPrioritizerEntity(
				jobPrioritizerEntity);

			projectComparatorEntities.add(projectComparatorEntity);
		}

		return projectComparatorEntities;
	}

	@Override
	public ProjectComparatorEntityDALO getEntityDALO() {
		return _projectComparatorEntityDALO;
	}

	@Override
	public void initializeRelationships() {
		for (ProjectComparatorEntity projectComparatorEntity : getAll()) {
			JobPrioritizerEntity jobPrioritizerEntity = null;

			long jenkinsServerId =
				projectComparatorEntity.getJobPrioritizerEntityId();

			if (jenkinsServerId != 0) {
				jobPrioritizerEntity =
					_projectPrioritizerEntityRepository.getById(
						jenkinsServerId);
			}

			projectComparatorEntity.setJobPrioritizerEntity(
				jobPrioritizerEntity);
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