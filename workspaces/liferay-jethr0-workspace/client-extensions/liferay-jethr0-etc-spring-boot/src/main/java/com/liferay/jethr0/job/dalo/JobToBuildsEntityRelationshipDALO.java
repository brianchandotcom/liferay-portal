/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.dalo;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.BuildEntityFactory;
import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.JobEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JobToBuildsEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<JobEntity, BuildEntity> {

	@Override
	public EntityFactory<BuildEntity> getChildEntityFactory() {
		return _buildEntityFactory;
	}

	@Override
	public EntityFactory<JobEntity> getParentEntityFactory() {
		return _jobEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "jobToBuilds";
	}

	@Autowired
	private BuildEntityFactory _buildEntityFactory;

	@Autowired
	private JobEntityFactory _jobEntityFactory;

}