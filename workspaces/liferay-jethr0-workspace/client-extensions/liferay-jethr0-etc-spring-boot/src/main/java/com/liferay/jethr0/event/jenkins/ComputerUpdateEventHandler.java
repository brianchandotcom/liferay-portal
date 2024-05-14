/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jenkins;

import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.util.Jethr0ContextUtil;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class ComputerUpdateEventHandler
	extends BaseJenkinsEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		JenkinsQueue jenkinsQueue = Jethr0ContextUtil.getJenkinsQueue();

		if (!jenkinsQueue.isInitialized()) {
			return "{\"message\": \"Jenkins queue is not initialized\"}";
		}

		JenkinsNodeEntity jenkinsNodeEntity = updateJenkinsNodeEntity();

		return jenkinsNodeEntity.toString();
	}

	protected ComputerUpdateEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

}