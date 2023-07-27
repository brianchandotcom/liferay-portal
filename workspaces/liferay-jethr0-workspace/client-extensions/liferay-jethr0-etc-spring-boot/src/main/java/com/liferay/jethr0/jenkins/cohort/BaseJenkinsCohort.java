/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.cohort;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.jenkins.server.JenkinsServer;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJenkinsCohort
	extends BaseEntity implements JenkinsCohort {

	@Override
	public void addJenkinsServer(JenkinsServer jenkinsServer) {
		addRelatedEntity(jenkinsServer);

		jenkinsServer.setJenkinsCohort(this);
	}

	@Override
	public void addJenkinsServers(Set<JenkinsServer> jenkinsNodes) {
		for (JenkinsServer jenkinsServer : jenkinsNodes) {
			addJenkinsServer(jenkinsServer);
		}
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void removeJenkinsServer(JenkinsServer jenkinsServer) {
		removeRelatedEntity(jenkinsServer);
	}

	@Override
	public void removeJenkinsServers(Set<JenkinsServer> jenkinsServers) {
		removeRelatedEntities(jenkinsServers);
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	protected BaseJenkinsCohort(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.optString("name");
	}

	private String _name;

}