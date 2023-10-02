/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.job.comparator.JobComparatorEntity;
import com.liferay.jethr0.job.dalo.JobComparatorEntityDALO;
import com.liferay.jethr0.job.prioritizer.JobPrioritizerEntity;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JobComparatorEntityRepository
	extends BaseEntityRepository<JobComparatorEntity> {

	public JobComparatorEntity create(
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

		return create(jsonObject);
	}

	@Override
	public JobComparatorEntityDALO getEntityDALO() {
		return _jobComparatorEntityDALO;
	}

	@Override
	public void initializeRelationships() {
	}

	public void setJobPrioritizerEntityRepository(
		JobPrioritizerEntityRepository jobPrioritizerEntityRepository) {

		_jobPrioritizerEntityRepository = jobPrioritizerEntityRepository;
	}

	@Override
	protected JobComparatorEntity updateRelationshipsFromDALO(
		JobComparatorEntity jobComparatorEntity) {

		_jobPrioritizerEntityRepository.relateJobPrioritizerToJobComparator(
			_jobPrioritizerEntityRepository.getById(
				jobComparatorEntity.getJobPrioritizerEntityId()),
			jobComparatorEntity);

		return jobComparatorEntity;
	}

	@Autowired
	private JobComparatorEntityDALO _jobComparatorEntityDALO;

	private JobPrioritizerEntityRepository _jobPrioritizerEntityRepository;

}