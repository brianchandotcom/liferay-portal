/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.gitbranch.GitBranchEntity;
import com.liferay.jethr0.gitbranch.GitBranchEntityFactory;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.ProjectEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectsToGitBranchesEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<JobEntity, GitBranchEntity> {

	@Override
	public EntityFactory<GitBranchEntity> getChildEntityFactory() {
		return _gitBranchEntityFactory;
	}

	@Override
	public EntityFactory<JobEntity> getParentEntityFactory() {
		return _projectEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "projectsToGitBranches";
	}

	@Autowired
	private GitBranchEntityFactory _gitBranchEntityFactory;

	@Autowired
	private ProjectEntityFactory _projectEntityFactory;

}