/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.testsuite;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.job.JobEntity;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface TestSuiteEntity extends Entity {

	public void addJobEntities(Set<JobEntity> jobEntities);

	public void addJobEntity(JobEntity jobEntity);

	public Set<JobEntity> getJobEntities();

	public String getName();

	public void removeJobEntities(Set<JobEntity> jobEntities);

	public void removeJobEntity(JobEntity jobEntity);

	public void setName(String name);

}