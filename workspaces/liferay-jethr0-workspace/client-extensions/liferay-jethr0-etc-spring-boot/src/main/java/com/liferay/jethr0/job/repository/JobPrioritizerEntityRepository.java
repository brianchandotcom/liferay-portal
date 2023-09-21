/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.job.dalo.JobPrioritizerEntityDALO;
import com.liferay.jethr0.job.dalo.JobPrioritizerToJobComparatorsEntityRelationshipDALO;
import com.liferay.jethr0.job.prioritizer.JobPrioritizerEntity;
import com.liferay.jethr0.job.prioritizer.JobPrioritizerEntityFactory;

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
		for (JobPrioritizerEntity jobPrioritizerEntity : getAll()) {
			for (long jobComparatorEntityId :
					_jobPrioritizerToJobComparatorsEntityRelationshipDALO.
						getChildEntityIds(jobPrioritizerEntity)) {

				if (jobComparatorEntityId == 0) {
					continue;
				}

				jobPrioritizerEntity.addJobComparatorEntity(
					_jobComparatorEntityRepository.getById(
						jobComparatorEntityId));
			}
		}
	}

	public void setJobComparatorEntityRepository(
		JobComparatorEntityRepository jobComparatorEntityRepository) {

		_jobComparatorEntityRepository = jobComparatorEntityRepository;
	}

	private JobComparatorEntityRepository _jobComparatorEntityRepository;

	@Autowired
	private JobPrioritizerEntityDALO _jobPrioritizerEntityDALO;

	@Autowired
	private JobPrioritizerEntityFactory _jobPrioritizerEntityFactory;

	@Autowired
	private JobPrioritizerToJobComparatorsEntityRelationshipDALO
		_jobPrioritizerToJobComparatorsEntityRelationshipDALO;

}