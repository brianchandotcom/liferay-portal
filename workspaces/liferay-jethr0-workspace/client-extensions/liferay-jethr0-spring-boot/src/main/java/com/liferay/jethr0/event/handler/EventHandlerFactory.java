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
		else if (eventType == EventHandler.EventType.CREATE_PROJECT) {
			eventHandler = new CreateProjectEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if (eventType == EventHandler.EventType.QUEUE_PROJECT) {
			eventHandler = new QueueProjectEventHandler(
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