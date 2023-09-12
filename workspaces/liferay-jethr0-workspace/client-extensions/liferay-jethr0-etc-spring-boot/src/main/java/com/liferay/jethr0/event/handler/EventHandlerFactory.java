/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class EventHandlerFactory {

	public EventHandler newEventHandler(JSONObject messageJSONObject) {
		EventHandler.EventType eventType = EventHandler.EventType.valueOf(
			messageJSONObject.optString("eventTrigger"));

		EventHandler eventHandler = null;

		if (eventType == EventHandler.EventType.BUILD_COMPLETED) {
			eventHandler = new BuildCompletedEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if (eventType == EventHandler.EventType.BUILD_STARTED) {
			eventHandler = new BuildStartedEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if ((eventType == EventHandler.EventType.COMPUTER_BUSY) ||
				 (eventType == EventHandler.EventType.COMPUTER_OFFLINE) ||
				 (eventType == EventHandler.EventType.COMPUTER_ONLINE) ||
				 (eventType ==
					 EventHandler.EventType.COMPUTER_TEMPORARILY_OFFLINE) ||
				 (eventType ==
					 EventHandler.EventType.COMPUTER_TEMPORARILY_ONLINE)) {

			eventHandler = new ComputerUpdateEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if (eventType == EventHandler.EventType.COMPUTER_IDLE) {
			eventHandler = new ComputerIdleEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if (eventType == EventHandler.EventType.CREATE_BUILD) {
			eventHandler = new CreateBuildEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if (eventType == EventHandler.EventType.CREATE_JENKINS_COHORT) {
			eventHandler = new CreateJenkinsCohortEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if (eventType == EventHandler.EventType.CREATE_JOB) {
			eventHandler = new CreateJobEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if (eventType == EventHandler.EventType.QUEUE_JOB) {
			eventHandler = new QueueJobEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else {
			throw new IllegalArgumentException(
				"Invalid event type: " + eventType);
		}

		return eventHandler;
	}

	@Autowired
	private EventHandlerContext _eventHandlerContext;

}