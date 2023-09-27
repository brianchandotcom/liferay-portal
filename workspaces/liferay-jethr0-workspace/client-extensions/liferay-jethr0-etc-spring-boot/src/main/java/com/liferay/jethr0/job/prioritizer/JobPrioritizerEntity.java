/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.prioritizer;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.job.comparator.JobComparatorEntity;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface JobPrioritizerEntity extends Entity {

	public void addJobComparatorEntities(
		Set<JobComparatorEntity> jobComparatorEntities);

	public void addJobComparatorEntity(JobComparatorEntity jobComparatorEntity);

	public Set<JobComparatorEntity> getJobComparatorEntities();

	public String getName();

	public void removeJobComparatorEntities(
		Set<JobComparatorEntity> jobComparatorEntities);

	public void removeJobComparatorEntity(
		JobComparatorEntity jobComparatorEntity);

	public void setName(String name);

}