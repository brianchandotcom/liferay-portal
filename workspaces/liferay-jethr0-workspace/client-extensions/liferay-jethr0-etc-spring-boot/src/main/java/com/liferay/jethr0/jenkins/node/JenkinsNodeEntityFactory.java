/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.node;

import com.liferay.jethr0.entity.factory.BaseEntityFactory;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsNodeEntityFactory extends BaseEntityFactory<JenkinsNode> {

	@Override
	public JenkinsNode newEntity(JSONObject jsonObject) {
		JenkinsNode.Type type = JenkinsNode.Type.get(
			jsonObject.getJSONObject("type"));

		if (type == JenkinsNode.Type.MASTER) {
			return new MasterJenkinsNode(jsonObject);
		}
		else if (type == JenkinsNode.Type.SLAVE) {
			return new SlaveJenkinsNode(jsonObject);
		}

		throw new UnsupportedOperationException();
	}

	protected JenkinsNodeEntityFactory() {
		super(JenkinsNode.class);
	}

}