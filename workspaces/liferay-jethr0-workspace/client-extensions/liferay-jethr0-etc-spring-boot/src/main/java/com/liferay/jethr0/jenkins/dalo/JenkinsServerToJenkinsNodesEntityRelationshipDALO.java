/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.jenkins.node.JenkinsNode;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntityFactory;
import com.liferay.jethr0.jenkins.server.JenkinsServer;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsServerToJenkinsNodesEntityRelationshipDALO
	extends BaseEntityRelationshipDALO<JenkinsServer, JenkinsNode> {

	@Override
	public EntityFactory<JenkinsNode> getChildEntityFactory() {
		return _jenkinsNodeEntityFactory;
	}

	@Override
	public EntityFactory<JenkinsServer> getParentEntityFactory() {
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