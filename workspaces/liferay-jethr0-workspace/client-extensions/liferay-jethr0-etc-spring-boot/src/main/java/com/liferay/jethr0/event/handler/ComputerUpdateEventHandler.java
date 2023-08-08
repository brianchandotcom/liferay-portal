/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.jenkins.node.JenkinsNode;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ComputerUpdateEventHandler extends BaseJenkinsEventHandler {

	public ComputerUpdateEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	@Override
	public String process() throws Exception {
		JenkinsQueue jenkinsQueue = getJenkinsQueue();

		if (!jenkinsQueue.initialized()) {
			return "{\"message\":\"Jenkins Queue is not initialized\"}";
		}

		JenkinsNode jenkinsNode = updateJenkinsNode();

		return jenkinsNode.toString();
	}

}