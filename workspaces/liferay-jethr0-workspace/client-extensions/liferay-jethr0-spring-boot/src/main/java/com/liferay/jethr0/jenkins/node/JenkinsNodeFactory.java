/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jethr0.jenkins.node;

import com.liferay.jethr0.entity.factory.BaseEntityFactory;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsNodeFactory extends BaseEntityFactory<JenkinsNode> {

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

	protected JenkinsNodeFactory() {
		super(JenkinsNode.class);
	}

}