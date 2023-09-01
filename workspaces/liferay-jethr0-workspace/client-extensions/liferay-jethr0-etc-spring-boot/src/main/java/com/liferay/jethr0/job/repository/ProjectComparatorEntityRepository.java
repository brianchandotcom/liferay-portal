/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.job.comparator.JobComparatorEntity;
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
	extends BaseEntityRepository<JobComparatorEntity> {

	public JobComparatorEntity add(
		JobPrioritizerEntity jobPrioritizerEntity, long position,
		JobComparatorEntity.Type type, String value) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"position", position
		).put(
			"r_jobPrioritizerToJobComparators_c_jobPrioritizerId",
			jobPrioritizerEntity.getId()
		).put(
			"type", type.getJSONObject()
		).put(
			"value", value
		);

		JobComparatorEntity jobComparatorEntity = add(jsonObject);

		jobComparatorEntity.setJobPrioritizerEntity(jobPrioritizerEntity);

		jobPrioritizerEntity.addJobComparatorEntity(jobComparatorEntity);

		return jobComparatorEntity;
	}

	public Set<JobComparatorEntity> getAll(
		JobPrioritizerEntity jobPrioritizerEntity) {

		Set<JobComparatorEntity> jobComparatorEntities = new HashSet<>();

		Set<Long> jobComparatorIds =
			_projectPrioritizerToProjectComparatorsEntityRelationshipDALO.
				getChildEntityIds(jobPrioritizerEntity);

		for (JobComparatorEntity jobComparatorEntity : getAll()) {
			if (!jobComparatorIds.contains(jobComparatorEntity.getId())) {
				continue;
			}

			jobPrioritizerEntity.addJobComparatorEntity(jobComparatorEntity);

			jobComparatorEntity.setJobPrioritizerEntity(jobPrioritizerEntity);

			jobComparatorEntities.add(jobComparatorEntity);
		}

		return jobComparatorEntities;
	}

	@Override
	public ProjectComparatorEntityDALO getEntityDALO() {
		return _projectComparatorEntityDALO;
	}

	@Override
	public void initializeRelationships() {
		for (JobComparatorEntity jobComparatorEntity : getAll()) {
			JobPrioritizerEntity jobPrioritizerEntity = null;

			long jenkinsServerId =
				jobComparatorEntity.getJobPrioritizerEntityId();

			if (jenkinsServerId != 0) {
				jobPrioritizerEntity =
					_projectPrioritizerEntityRepository.getById(
						jenkinsServerId);
			}

			jobComparatorEntity.setJobPrioritizerEntity(jobPrioritizerEntity);
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