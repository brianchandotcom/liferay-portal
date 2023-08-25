/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntityFactory;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsCohortToJenkinsServersEntityRelationshipDALO
	extends BaseEntityRelationshipDALO
		<JenkinsCohortEntity, JenkinsServerEntity> {

	@Override
	public EntityFactory<JenkinsServerEntity> getChildEntityFactory() {
		return _jenkinsServerEntityFactory;
	}

	@Override
	public EntityFactory<JenkinsCohortEntity> getParentEntityFactory() {
		return _jenkinsCohortEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "jenkinsCohortToJenkinsServers";
	}

	@Autowired
	private JenkinsCohortEntityFactory _jenkinsCohortEntityFactory;

	@Autowired
	private JenkinsServerEntityFactory _jenkinsServerEntityFactory;

}