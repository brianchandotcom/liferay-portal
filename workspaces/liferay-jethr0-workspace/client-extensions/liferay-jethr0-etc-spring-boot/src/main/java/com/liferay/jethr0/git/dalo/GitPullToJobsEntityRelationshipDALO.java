/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.git.pull.GitPullEntity;
import com.liferay.jethr0.git.pull.GitPullEntityFactory;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.JobEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Michael Hashimoto
 */
public class GitPullToJobsEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<GitPullEntity, JobEntity> {

	@Override
	public EntityFactory<JobEntity> getChildEntityFactory() {
		return _jobEntityFactory;
	}

	@Override
	public EntityFactory<GitPullEntity> getParentEntityFactory() {
		return _gitPullEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "gitPullToJobs";
	}

	@Autowired
	private GitPullEntityFactory _gitPullEntityFactory;

	@Autowired
	private JobEntityFactory _jobEntityFactory;

}