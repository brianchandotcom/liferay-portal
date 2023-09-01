/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.job.comparator.JobComparatorEntity;
import com.liferay.jethr0.job.comparator.JobComparatorEntityFactory;
import com.liferay.jethr0.job.prioritizer.JobPrioritizerEntity;
import com.liferay.jethr0.job.prioritizer.JobPrioritizerEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JobPrioritizerToJobComparatorsEntityRelationshipDALO
	extends BaseEntityRelationshipDALO
		<JobPrioritizerEntity, JobComparatorEntity> {

	@Override
	public EntityFactory<JobComparatorEntity> getChildEntityFactory() {
		return _jobComparatorEntityFactory;
	}

	@Override
	public EntityFactory<JobPrioritizerEntity> getParentEntityFactory() {
		return _jobPrioritizerEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "jobPrioritizerToJobComparators";
	}

	@Autowired
	private JobComparatorEntityFactory _jobComparatorEntityFactory;

	@Autowired
	private JobPrioritizerEntityFactory _jobPrioritizerEntityFactory;

}