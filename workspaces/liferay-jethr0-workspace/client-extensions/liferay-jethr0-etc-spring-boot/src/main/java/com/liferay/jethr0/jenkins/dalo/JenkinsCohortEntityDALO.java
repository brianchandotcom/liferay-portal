/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsCohortEntityDALO
	extends BaseEntityDALO<JenkinsCohortEntity> {

	@Override
	protected EntityFactory<JenkinsCohortEntity> getEntityFactory() {
		return _jenkinsCohortEntityFactory;
	}

	@Autowired
	private JenkinsCohortEntityFactory _jenkinsCohortEntityFactory;

}