/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsNodeEntityDALO extends BaseEntityDALO<JenkinsNodeEntity> {

	@Override
	protected EntityFactory<JenkinsNodeEntity> getEntityFactory() {
		return _jenkinsNodeEntityFactory;
	}

	@Autowired
	private JenkinsNodeEntityFactory _jenkinsNodeEntityFactory;

}