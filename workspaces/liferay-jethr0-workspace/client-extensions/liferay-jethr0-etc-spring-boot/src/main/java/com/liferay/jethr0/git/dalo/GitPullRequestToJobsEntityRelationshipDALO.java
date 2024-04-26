/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.git.pullrequest.GitPullRequestEntity;
import com.liferay.jethr0.git.pullrequest.GitPullRequestEntityFactory;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.JobEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitPullRequestToJobsEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<GitPullRequestEntity, JobEntity> {

	@Override
	public EntityFactory<JobEntity> getChildEntityFactory() {
		return _jobEntityFactory;
	}

	@Override
	public EntityFactory<GitPullRequestEntity> getParentEntityFactory() {
		return _gitPullRequestEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "gitPullRequestToJobs";
	}

	@Autowired
	private GitPullRequestEntityFactory _gitPullRequestEntityFactory;

	@Autowired
	private JobEntityFactory _jobEntityFactory;

}