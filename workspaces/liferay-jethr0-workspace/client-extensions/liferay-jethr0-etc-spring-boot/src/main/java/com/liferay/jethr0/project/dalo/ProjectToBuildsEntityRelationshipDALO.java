/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.dalo;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.BuildEntityFactory;
import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.ProjectEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectToBuildsEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<Project, Build> {

	@Override
	public EntityFactory<Build> getChildEntityFactory() {
		return _buildEntityFactory;
	}

	@Override
	public EntityFactory<Project> getParentEntityFactory() {
		return _projectEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "projectToBuilds";
	}

	@Autowired
	private BuildEntityFactory _buildEntityFactory;

	@Autowired
	private ProjectEntityFactory _projectEntityFactory;

}