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

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.build.Build;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseEventHandler implements EventHandler {

	protected BaseEventHandler(EventHandlerHelper eventHandlerHelper) {
		_eventHandlerHelper = eventHandlerHelper;
	}

	protected EventHandlerHelper getEventHandlerHelper() {
		return _eventHandlerHelper;
	}

	protected JSONObject validateBuildJSONObject(JSONObject buildJSONObject)
		throws Exception {

		if (buildJSONObject == null) {
			throw new Exception("Invalid build JSON object");
		}

		String buildName = buildJSONObject.optString("buildName");

		if (buildName.isEmpty()) {
			throw new Exception("Invalid build 'buildName'");
		}

		String jobName = buildJSONObject.optString("jobName");

		if (jobName.isEmpty()) {
			throw new Exception("Invalid build 'jobName'");
		}

		Build.State state = Build.State.valueOf(
			buildJSONObject.optString("state", Build.State.OPENED.toString()));

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"buildName", buildName
		).put(
			"jobName", jobName
		).put(
			"parameters", buildJSONObject.optJSONObject("parameters")
		).put(
			"state", state.getJSONObject()
		);

		return jsonObject;
	}

	protected JSONArray validateBuildsJSONArray(JSONArray buildsJSONArray)
		throws Exception {

		JSONArray jsonArray = new JSONArray();

		if ((buildsJSONArray != null) && !buildsJSONArray.isEmpty()) {
			for (int i = 0; i < buildsJSONArray.length(); i++) {
				jsonArray.put(
					validateBuildJSONObject(buildsJSONArray.optJSONObject(i)));
			}
		}

		return jsonArray;
	}

	private final EventHandlerHelper _eventHandlerHelper;

}