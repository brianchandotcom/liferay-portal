/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.build.queue.BuildQueue;
import com.liferay.jethr0.build.repository.BuildParameterRepository;
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.build.repository.BuildRunRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsServerRepository;
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

	protected JenkinsCohortRepository getJenkinsCohortRepository() {
		return _eventHandlerContext.getJenkinsCohortRepository();
	}

	protected JenkinsNodeRepository getJenkinsNodeRepository() {
		return _eventHandlerContext.getJenkinsNodeRepository();
	}

	protected JenkinsQueue getJenkinsQueue() {
		return _eventHandlerContext.getJenkinsQueue();
	}

	protected JenkinsServerRepository getJenkinsServerRepository() {
		return _eventHandlerContext.getJenkinsServerRepository();
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