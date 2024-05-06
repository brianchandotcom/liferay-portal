/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jrp;

import com.liferay.jethr0.event.EventHandler;
import com.liferay.jethr0.event.EventHandlerFactory;
import com.liferay.jethr0.util.StringUtil;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JRPEventHandlerFactory implements EventHandlerFactory {

	@Override
	public EventHandler newEventHandler(JSONObject messageJSONObject)
		throws IllegalArgumentException {

		String eventType = messageJSONObject.optString("eventType");

		if (StringUtil.isNullOrEmpty(eventType)) {
			throw new IllegalArgumentException(
				"Missing \"eventType\" from message JSON");
		}

		if (eventType.equals("CREATE_BUILD")) {
			return new CreateBuildEventHandler(messageJSONObject);
		}
		else if (eventType.equals("CREATE_BUILD_RUN")) {
			return new CreateBuildRunEventHandler(messageJSONObject);
		}
		else if (eventType.equals("CREATE_JENKINS_COHORT")) {
			return new CreateJenkinsCohortEventHandler(messageJSONObject);
		}
		else if (eventType.equals("CREATE_JOB")) {
			return new CreateJobEventHandler(messageJSONObject);
		}
		else if (eventType.equals("QUEUE_JOB")) {
			return new QueueJobEventHandler(messageJSONObject);
		}

		throw new IllegalArgumentException(
			"Invalid \"eventType\": " + eventType);
	}

}