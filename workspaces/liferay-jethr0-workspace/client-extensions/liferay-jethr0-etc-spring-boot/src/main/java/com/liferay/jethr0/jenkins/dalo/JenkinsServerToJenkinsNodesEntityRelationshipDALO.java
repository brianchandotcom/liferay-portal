/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntityFactory;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsServerToJenkinsNodesEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<JenkinsServerEntity, JenkinsNodeEntity> {

	@Override
	public EntityFactory<JenkinsNodeEntity> getChildEntityFactory() {
		return _jenkinsNodeEntityFactory;
	}

	@Override
	public EntityFactory<JenkinsServerEntity> getParentEntityFactory() {
		return _jenkinsServerEntityFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "jenkinsServerToJenkinsNodes";
	}

	@Autowired
	private JenkinsNodeEntityFactory _jenkinsNodeEntityFactory;

	@Autowired
	private JenkinsServerEntityFactory _jenkinsServerEntityFactory;

}