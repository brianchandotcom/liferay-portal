/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.node;

import com.liferay.jethr0.entity.factory.BaseEntityFactory;
import com.liferay.jethr0.event.jenkins.client.JenkinsClient;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsNodeEntityFactory
	extends BaseEntityFactory<JenkinsNodeEntity> {

	@Override
	public JenkinsNodeEntity newEntity(JSONObject jsonObject) {
		JenkinsNodeEntity.Type type = JenkinsNodeEntity.Type.get(
			jsonObject.getJSONObject("type"));

		if (type == JenkinsNodeEntity.Type.MASTER) {
			return new MasterJenkinsNodeEntity(_jenkinsClient, jsonObject);
		}
		else if (type == JenkinsNodeEntity.Type.SLAVE) {
			return new SlaveJenkinsNodeEntity(_jenkinsClient, jsonObject);
		}

		throw new UnsupportedOperationException();
	}

	protected JenkinsNodeEntityFactory() {
		super(JenkinsNodeEntity.class);
	}

	@Autowired
	private JenkinsClient _jenkinsClient;

}