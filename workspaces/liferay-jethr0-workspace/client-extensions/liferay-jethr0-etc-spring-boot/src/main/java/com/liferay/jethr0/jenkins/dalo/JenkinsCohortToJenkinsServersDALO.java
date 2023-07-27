/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohort;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortFactory;
import com.liferay.jethr0.jenkins.server.JenkinsServer;
import com.liferay.jethr0.jenkins.server.JenkinsServerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsCohortToJenkinsServersDALO
	extends BaseEntityRelationshipDALO<JenkinsCohort, JenkinsServer> {

	@Override
	public EntityFactory<JenkinsServer> getChildEntityFactory() {
		return _jenkinsServerFactory;
	}

	@Override
	public EntityFactory<JenkinsCohort> getParentEntityFactory() {
		return _jenkinsCohortFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "jenkinsCohortToJenkinsServers";
	}

	@Autowired
	private JenkinsCohortFactory _jenkinsCohortFactory;

	@Autowired
	private JenkinsServerFactory _jenkinsServerFactory;

}