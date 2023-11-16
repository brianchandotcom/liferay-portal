/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jrp;

import com.liferay.jethr0.event.BaseEventHandlerFactory;
import com.liferay.jethr0.event.EventHandler;
import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.util.StringUtil;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JRPEventHandlerFactory extends BaseEventHandlerFactory {

	@Override
	public EventHandler newEventHandler(JSONObject messageJSONObject)
		throws IllegalArgumentException {

		String eventType = messageJSONObject.optString("eventType");

		if (StringUtil.isNullOrEmpty(eventType)) {
			throw new IllegalArgumentException(
				"Missing \"eventType\" from message JSON");
		}

		EventHandlerContext eventHandlerContext = getEventHandlerContext();

		if (eventType.equals("CREATE_BUILD")) {
			return new CreateBuildEventHandler(
				eventHandlerContext, messageJSONObject);
		}
		else if (eventType.equals("CREATE_BUILD_RUN")) {
			return new CreateBuildRunEventHandler(
				eventHandlerContext, messageJSONObject);
		}
		else if (eventType.equals("CREATE_JENKINS_COHORT")) {
			return new CreateJenkinsCohortEventHandler(
				eventHandlerContext, messageJSONObject);
		}
		else if (eventType.equals("CREATE_JOB")) {
			return new CreateJobEventHandler(
				eventHandlerContext, messageJSONObject);
		}
		else if (eventType.equals("QUEUE_JOB")) {
			return new QueueJobEventHandler(
				eventHandlerContext, messageJSONObject);
		}

		throw new IllegalArgumentException(
			"Invalid \"eventType\": " + eventType);
	}

}