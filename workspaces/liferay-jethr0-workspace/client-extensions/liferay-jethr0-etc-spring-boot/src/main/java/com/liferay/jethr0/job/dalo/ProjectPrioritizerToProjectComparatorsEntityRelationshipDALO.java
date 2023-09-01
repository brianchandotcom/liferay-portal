/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.job.comparator.ProjectComparatorEntity;
import com.liferay.jethr0.job.comparator.ProjectComparatorEntityFactory;
import com.liferay.jethr0.job.prioritizer.ProjectPrioritizerEntity;
import com.liferay.jethr0.job.prioritizer.ProjectPrioritizerEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectPrioritizerToProjectComparatorsEntityRelationshipDALO
	extends BaseEntityRelationshipDALO
		<ProjectPrioritizerEntity, ProjectComparatorEntity> {

	@Override
	public EntityFactory<ProjectComparatorEntity> getChildEntityFactory() {
		return _projectComparatorEntityFactory;
	}

	@Override
	public EntityFactory<ProjectPrioritizerEntity> getParentEntityFactory() {
		return _projectPrioritizerEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "projectPrioritizerToProjectComparators";
	}

	@Autowired
	private ProjectComparatorEntityFactory _projectComparatorEntityFactory;

	@Autowired
	private ProjectPrioritizerEntityFactory _projectPrioritizerEntityFactory;

}