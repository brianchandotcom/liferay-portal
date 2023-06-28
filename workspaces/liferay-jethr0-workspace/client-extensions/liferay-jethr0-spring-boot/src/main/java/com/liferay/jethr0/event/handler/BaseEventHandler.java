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

import com.liferay.jethr0.build.queue.BuildQueue;
import com.liferay.jethr0.build.repository.BuildParameterRepository;
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.build.repository.BuildRunRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeRepository;
import com.liferay.jethr0.jms.JMSEventHandler;
import com.liferay.jethr0.project.repository.ProjectRepository;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseEventHandler implements EventHandler {

	protected BaseEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		_eventHandlerContext = eventHandlerContext;
		_messageJSONObject = messageJSONObject;
	}

	protected BuildParameterRepository getBuildParameterRepository() {
		return _eventHandlerContext.getBuildParameterRepository();
	}

	protected BuildQueue getBuildQueue() {
		return _eventHandlerContext.getBuildQueue();
	}

	protected BuildRepository getBuildRepository() {
		return _eventHandlerContext.getBuildRepository();
	}

	protected BuildRunRepository getBuildRunRepository() {
		return _eventHandlerContext.getBuildRunRepository();
	}

	protected JenkinsNodeRepository getJenkinsNodeRepository() {
		return _eventHandlerContext.getJenkinsNodeRepository();
	}

	protected JenkinsQueue getJenkinsQueue() {
		return _eventHandlerContext.getJenkinsQueue();
	}

	protected JMSEventHandler getJMSEventHandler() {
		return _eventHandlerContext.getJMSEventHandler();
	}

	protected JSONObject getMessageJSONObject() {
		return _messageJSONObject;
	}

	protected ProjectRepository getProjectRepository() {
		return _eventHandlerContext.getProjectRepository();
	}

	private final EventHandlerContext _eventHandlerContext;
	private final JSONObject _messageJSONObject;

}