/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jenkins;

import com.liferay.jethr0.event.EventHandler;
import com.liferay.jethr0.event.EventHandlerFactory;
import com.liferay.jethr0.util.StringUtil;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsEventHandlerFactory implements EventHandlerFactory {

	@Override
	public EventHandler newEventHandler(JSONObject messageJSONObject)
		throws IllegalArgumentException {

		String eventType = messageJSONObject.optString("eventType");

		if (StringUtil.isNullOrEmpty(eventType)) {
			throw new IllegalArgumentException(
				"Missing \"eventType\" from message JSON");
		}

		if (eventType.equals("BUILD_COMPLETED")) {
			return new BuildCompletedEventHandler(messageJSONObject);
		}
		else if (eventType.equals("BUILD_STARTED")) {
			return new BuildStartedEventHandler(messageJSONObject);
		}
		else if (eventType.equals("COMPUTER_BUSY")) {
			return new ComputerBusyEventHandler(messageJSONObject);
		}
		else if (eventType.equals("COMPUTER_OFFLINE") ||
				 eventType.equals("COMPUTER_TEMPORARILY_OFFLINE")) {

			return new ComputerOfflineEventHandler(messageJSONObject);
		}
		else if (eventType.equals("COMPUTER_ONLINE") ||
				 eventType.equals("COMPUTER_TEMPORARILY_ONLINE")) {

			return new ComputerOnlineEventHandler(messageJSONObject);
		}
		else if (eventType.equals("COMPUTER_IDLE")) {
			return new ComputerIdleEventHandler(messageJSONObject);
		}

		throw new IllegalArgumentException(
			"Invalid \"eventType\": " + eventType);
	}

}