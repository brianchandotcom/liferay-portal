/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.job.comparator.JobComparatorEntity;
import com.liferay.jethr0.job.dalo.JobPrioritizerEntityDALO;
import com.liferay.jethr0.job.dalo.JobPrioritizerToJobComparatorsEntityRelationshipDALO;
import com.liferay.jethr0.job.prioritizer.JobPrioritizerEntity;

import java.util.Objects;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JobPrioritizerEntityRepository
	extends BaseEntityRepository<JobPrioritizerEntity> {

	public JobPrioritizerEntity create(String name) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("name", name);

		return create(jsonObject);
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
	public JobPrioritizerEntityDALO getEntityDALO() {
		return _jobPrioritizerEntityDALO;
	}

	@Override
	public void initializeRelationships() {
	}

	public void relateJobPrioritizerToJobComparator(
		JobPrioritizerEntity jobPrioritizerEntity,
		JobComparatorEntity jobComparatorEntity) {

		jobPrioritizerEntity.addJobComparatorEntity(jobComparatorEntity);

		jobComparatorEntity.setJobPrioritizerEntity(jobPrioritizerEntity);
	}

	public void setJobComparatorEntityRepository(
		JobComparatorEntityRepository jobComparatorEntityRepository) {

		_jobComparatorEntityRepository = jobComparatorEntityRepository;
	}

	@Override
	protected JobPrioritizerEntity updateRelationshipsFromDALO(
		JobPrioritizerEntity jobPrioritizerEntity) {

		return _updateJobPrioritizerToJobComparatorRelationshipsFromDALO(
			jobPrioritizerEntity);
	}

	@Override
	protected JobPrioritizerEntity updateRelationshipsToDALO(
		JobPrioritizerEntity jobPrioritizerEntity) {

		_jobPrioritizerToJobComparatorsEntityRelationshipDALO.
			updateChildEntities(jobPrioritizerEntity);

		return jobPrioritizerEntity;
	}

	private JobPrioritizerEntity
		_updateJobPrioritizerToJobComparatorRelationshipsFromDALO(
			JobPrioritizerEntity parentJobPrioritizerEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentJobPrioritizerEntity,
			_jobPrioritizerToJobComparatorsEntityRelationshipDALO,
			_jobComparatorEntityRepository,
			(jobPrioritizerEntity, jobComparatorEntity) ->
				relateJobPrioritizerToJobComparator(
					jobPrioritizerEntity, jobComparatorEntity),
			jobPrioritizerEntity ->
				jobPrioritizerEntity.getJobComparatorEntities(),
			(jobPrioritizerEntity, jobComparatorEntity) ->
				jobPrioritizerEntity.removeJobComparatorEntity(
					jobComparatorEntity));
	}

	private JobComparatorEntityRepository _jobComparatorEntityRepository;

	@Autowired
	private JobPrioritizerEntityDALO _jobPrioritizerEntityDALO;

	@Autowired
	private JobPrioritizerToJobComparatorsEntityRelationshipDALO
		_jobPrioritizerToJobComparatorsEntityRelationshipDALO;

}