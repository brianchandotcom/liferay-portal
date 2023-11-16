/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event;

import com.liferay.jethr0.event.jenkins.BuildCompletedEventHandler;
import com.liferay.jethr0.event.jenkins.BuildStartedEventHandler;
import com.liferay.jethr0.event.jenkins.ComputerIdleEventHandler;
import com.liferay.jethr0.event.jenkins.ComputerUpdateEventHandler;
import com.liferay.jethr0.event.jenkins.JenkinsEventHandler;
import com.liferay.jethr0.event.jrp.CreateBuildEventHandler;
import com.liferay.jethr0.event.jrp.CreateBuildRunEventHandler;
import com.liferay.jethr0.event.jrp.CreateJenkinsCohortEventHandler;
import com.liferay.jethr0.event.jrp.CreateJobEventHandler;
import com.liferay.jethr0.event.jrp.JRPEventHandler;
import com.liferay.jethr0.event.jrp.QueueJobEventHandler;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class EventHandlerFactory {

	public EventHandler newEventHandler(
		EventHandler.EventType eventType, JSONObject messageJSONObject) {

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
		else if (eventType == EventHandler.EventType.CREATE_BUILD_RUN) {
			eventHandler = new CreateBuildRunEventHandler(
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

		if (eventHandler == null) {
			throw new IllegalArgumentException(
				"Invalid event type: " + eventType);
		}

		return eventHandler;
	}

	public JenkinsEventHandler newJenkinsEventHandler(
		JenkinsEventHandler.EventType eventType, JSONObject messageJSONObject) {

		JenkinsEventHandler jenkinsEventHandler = null;

		if (eventType == JenkinsEventHandler.EventType.BUILD_COMPLETED) {
			jenkinsEventHandler = new BuildCompletedEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if (eventType == JenkinsEventHandler.EventType.BUILD_STARTED) {
			jenkinsEventHandler = new BuildStartedEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if ((eventType == JenkinsEventHandler.EventType.COMPUTER_BUSY) ||
				 (eventType ==
					 JenkinsEventHandler.EventType.COMPUTER_OFFLINE) ||
				 (eventType == JenkinsEventHandler.EventType.COMPUTER_ONLINE) ||
				 (eventType ==
					 JenkinsEventHandler.EventType.
						 COMPUTER_TEMPORARILY_OFFLINE) ||
				 (eventType ==
					 JenkinsEventHandler.EventType.
						 COMPUTER_TEMPORARILY_ONLINE)) {

			jenkinsEventHandler = new ComputerUpdateEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if (eventType == JenkinsEventHandler.EventType.COMPUTER_IDLE) {
			jenkinsEventHandler = new ComputerIdleEventHandler(
				_eventHandlerContext, messageJSONObject);
		}

		if (jenkinsEventHandler == null) {
			throw new IllegalArgumentException(
				"Invalid event type: " + eventType);
		}

		return jenkinsEventHandler;
	}

	public JRPEventHandler newJRPEventHandler(
		JRPEventHandler.EventType eventType, JSONObject messageJSONObject) {

		JRPEventHandler jrpEventHandler = null;

		if (eventType == JRPEventHandler.EventType.CREATE_BUILD) {
			jrpEventHandler = new CreateBuildEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if (eventType == JRPEventHandler.EventType.CREATE_BUILD_RUN) {
			jrpEventHandler = new CreateBuildRunEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if (eventType == JRPEventHandler.EventType.CREATE_JENKINS_COHORT) {
			jrpEventHandler = new CreateJenkinsCohortEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if (eventType == JRPEventHandler.EventType.CREATE_JOB) {
			jrpEventHandler = new CreateJobEventHandler(
				_eventHandlerContext, messageJSONObject);
		}
		else if (eventType == JRPEventHandler.EventType.QUEUE_JOB) {
			jrpEventHandler = new QueueJobEventHandler(
				_eventHandlerContext, messageJSONObject);
		}

		if (jrpEventHandler == null) {
			throw new IllegalArgumentException(
				"Invalid event type: " + eventType);
		}

		return jrpEventHandler;
	}

	@Autowired
	private EventHandlerContext _eventHandlerContext;

}