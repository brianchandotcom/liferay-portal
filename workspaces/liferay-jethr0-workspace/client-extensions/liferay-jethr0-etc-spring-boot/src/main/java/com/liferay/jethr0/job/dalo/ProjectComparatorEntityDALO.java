/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.job.comparator.JobComparatorEntity;
import com.liferay.jethr0.job.comparator.ProjectComparatorEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectComparatorEntityDALO
	extends BaseEntityDALO<JobComparatorEntity> {

	@Override
	public EntityFactory<JobComparatorEntity> getEntityFactory() {
		return _projectComparatorEntityFactory;
	}

	@Autowired
	private ProjectComparatorEntityFactory _projectComparatorEntityFactory;

}