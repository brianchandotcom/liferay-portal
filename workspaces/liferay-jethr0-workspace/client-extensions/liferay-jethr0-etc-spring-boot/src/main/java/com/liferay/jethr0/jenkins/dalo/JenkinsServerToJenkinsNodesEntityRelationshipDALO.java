/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.jenkins.node.JenkinsNode;
import com.liferay.jethr0.jenkins.node.JenkinsNodeFactory;
import com.liferay.jethr0.jenkins.server.JenkinsServer;
import com.liferay.jethr0.jenkins.server.JenkinsServerFactory;

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
		return _jenkinsNodeFactory;
	}

	@Override
	public EntityFactory<JenkinsServer> getParentEntityFactory() {
		return _jenkinsServerFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "jenkinsServerToJenkinsNodes";
	}

	@Autowired
	private JenkinsNodeFactory _jenkinsNodeFactory;

	@Autowired
	private JenkinsServerFactory _jenkinsServerFactory;

}