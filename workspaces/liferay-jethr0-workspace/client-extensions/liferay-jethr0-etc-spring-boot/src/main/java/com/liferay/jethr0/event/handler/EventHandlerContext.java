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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class EventHandlerContext {

	public BuildParameterRepository getBuildParameterRepository() {
		return _buildParameterRepository;
	}

	public BuildQueue getBuildQueue() {
		return _buildQueue;
	}

	public BuildRepository getBuildRepository() {
		return _buildRepository;
	}

	public BuildRunRepository getBuildRunRepository() {
		return _buildRunRepository;
	}

	public JenkinsCohortRepository getJenkinsCohortRepository() {
		return _jenkinsCohortRepository;
	}

	public JenkinsNodeRepository getJenkinsNodeRepository() {
		return _jenkinsNodeRepository;
	}

	public JenkinsQueue getJenkinsQueue() {
		return _jenkinsQueue;
	}

	public JenkinsServerRepository getJenkinsServerRepository() {
		return _jenkinsServerRepository;
	}

	public JMSEventHandler getJMSEventHandler() {
		return _jmsEventHandler;
	}

	public ProjectRepository getProjectRepository() {
		return _projectRepository;
	}

	public void setJMSEventHandler(JMSEventHandler jmsEventHandler) {
		_jmsEventHandler = jmsEventHandler;
	}

	@Autowired
	private BuildParameterRepository _buildParameterRepository;

	@Autowired
	private BuildQueue _buildQueue;

	@Autowired
	private BuildRepository _buildRepository;

	@Autowired
	private BuildRunRepository _buildRunRepository;

	@Autowired
	private JenkinsCohortRepository _jenkinsCohortRepository;

	@Autowired
	private JenkinsNodeRepository _jenkinsNodeRepository;

	@Autowired
	private JenkinsQueue _jenkinsQueue;

	@Autowired
	private JenkinsServerRepository _jenkinsServerRepository;

	private JMSEventHandler _jmsEventHandler;

	@Autowired
	private ProjectRepository _projectRepository;

}