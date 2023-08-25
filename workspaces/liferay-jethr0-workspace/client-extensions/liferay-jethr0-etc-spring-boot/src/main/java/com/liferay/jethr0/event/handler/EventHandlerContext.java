/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildParameterEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsServerEntityRepository;
import com.liferay.jethr0.jms.JMSEventHandler;
import com.liferay.jethr0.project.repository.ProjectEntityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class EventHandlerContext {

	public BuildParameterEntityRepository getBuildParameterRepository() {
		return _buildParameterEntityRepository;
	}

	public BuildQueue getBuildQueue() {
		return _buildQueue;
	}

	public BuildEntityRepository getBuildRepository() {
		return _buildEntityRepository;
	}

	public BuildRunEntityRepository getBuildRunRepository() {
		return _buildRunEntityRepository;
	}

	public JenkinsCohortEntityRepository getJenkinsCohortRepository() {
		return _jenkinsCohortEntityRepository;
	}

	public JenkinsNodeEntityRepository getJenkinsNodeEntityRepository() {
		return _jenkinsNodeEntityRepository;
	}

	public JenkinsQueue getJenkinsQueue() {
		return _jenkinsQueue;
	}

	public JenkinsServerEntityRepository getJenkinsServerRepository() {
		return _jenkinsServerEntityRepository;
	}

	public JMSEventHandler getJMSEventHandler() {
		return _jmsEventHandler;
	}

	public ProjectEntityRepository getProjectRepository() {
		return _projectEntityRepository;
	}

	public void setJMSEventHandler(JMSEventHandler jmsEventHandler) {
		_jmsEventHandler = jmsEventHandler;
	}

	@Autowired
	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private BuildParameterEntityRepository _buildParameterEntityRepository;

	@Autowired
	private BuildQueue _buildQueue;

	@Autowired
	private BuildRunEntityRepository _buildRunEntityRepository;

	@Autowired
	private JenkinsCohortEntityRepository _jenkinsCohortEntityRepository;

	@Autowired
	private JenkinsNodeEntityRepository _jenkinsNodeEntityRepository;

	@Autowired
	private JenkinsQueue _jenkinsQueue;

	@Autowired
	private JenkinsServerEntityRepository _jenkinsServerEntityRepository;

	private JMSEventHandler _jmsEventHandler;

	@Autowired
	private ProjectEntityRepository _projectEntityRepository;

}