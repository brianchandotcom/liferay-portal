/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohort;
import com.liferay.jethr0.jenkins.dalo.JenkinsCohortDALO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsCohortRepository
	extends BaseEntityRepository<JenkinsCohort> {

	@Override
	public JenkinsCohortDALO getEntityDALO() {
		return _jenkinsCohortDALO;
	}

	@Autowired
	private JenkinsCohortDALO _jenkinsCohortDALO;

}