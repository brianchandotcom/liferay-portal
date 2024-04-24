/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event;

import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.event.github.GitHubFactory;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.jenkins.JenkinsEventProcessor;
import com.liferay.jethr0.event.jenkins.client.JenkinsClient;
import com.liferay.jethr0.event.jrp.JRPEventProcessor;
import com.liferay.jethr0.git.repository.GitBranchEntityRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsServerEntityRepository;
import com.liferay.jethr0.job.queue.JobQueue;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.routine.repository.RoutineEntityRepository;
import com.liferay.jethr0.routine.scheduler.RoutineEntityScheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class EventHandlerContext {

	public BuildQueue getBuildQueue() {
		return _buildQueue;
	}

	public BuildEntityRepository getBuildRepository() {
		return _buildEntityRepository;
	}

	public BuildRunEntityRepository getBuildRunRepository() {
		return _buildRunEntityRepository;
	}

	public GitBranchEntityRepository getGitBranchEntityRepository() {
		return _gitBranchEntityRepository;
	}

	public GitHubClient getGitHubClient() {
		return _gitHubClient;
	}

	public JenkinsClient getJenkinsClient() {
		return _jenkinsClient;
	}

	public JenkinsCohortEntityRepository getJenkinsCohortEntityRepository() {
		return _jenkinsCohortEntityRepository;
	}

	public JenkinsEventProcessor getJenkinsEventProcessor() {
		return _jenkinsEventProcessor;
	}

	public JenkinsNodeEntityRepository getJenkinsNodeEntityRepository() {
		return _jenkinsNodeEntityRepository;
	}

	public JenkinsQueue getJenkinsQueue() {
		return _jenkinsQueue;
	}

	public JenkinsServerEntityRepository getJenkinsServerEntityRepository() {
		return _jenkinsServerEntityRepository;
	}

	public JobEntityRepository getJobEntityRepository() {
		return _jobEntityRepository;
	}

	public JobQueue getJobQueue() {
		return _jobQueue;
	}

	public JRPEventProcessor getJRPEventProcessor() {
		return _jrpEventProcessor;
	}

	public String getLiferayPortalURL() {
		return _liferayPortalURL;
	}

	public RoutineEntityRepository getRoutineEntityRepository() {
		return _routineEntityRepository;
	}

	public RoutineEntityScheduler getRoutineEntityScheduler() {
		return _routineEntityScheduler;
	}

	public void setJenkinsEventProcessor(
		JenkinsEventProcessor jenkinsEventProcessor) {

		_jenkinsEventProcessor = jenkinsEventProcessor;
	}

	public void setJRPEventProcessor(JRPEventProcessor jrpEventProcessor) {
		_jrpEventProcessor = jrpEventProcessor;
	}

	protected GitHubFactory getGitHubFactory() {
		return _gitHubFactory;
	}

	@Autowired
	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private BuildQueue _buildQueue;

	@Autowired
	private BuildRunEntityRepository _buildRunEntityRepository;

	@Autowired
	private GitBranchEntityRepository _gitBranchEntityRepository;

	@Autowired
	private GitHubClient _gitHubClient;

	@Autowired
	private GitHubFactory _gitHubFactory;

	@Autowired
	private JenkinsClient _jenkinsClient;

	@Autowired
	private JenkinsCohortEntityRepository _jenkinsCohortEntityRepository;

	private JenkinsEventProcessor _jenkinsEventProcessor;

	@Autowired
	private JenkinsNodeEntityRepository _jenkinsNodeEntityRepository;

	@Autowired
	private JenkinsQueue _jenkinsQueue;

	@Autowired
	private JenkinsServerEntityRepository _jenkinsServerEntityRepository;

	@Autowired
	private JobEntityRepository _jobEntityRepository;

	@Autowired
	private JobQueue _jobQueue;

	private JRPEventProcessor _jrpEventProcessor;

	@Value(
		"${com.liferay.lxc.dxp.server.protocol}://${com.liferay.lxc.dxp.main.domain}"
	)
	private String _liferayPortalURL;

	@Autowired
	private RoutineEntityRepository _routineEntityRepository;

	@Autowired
	private RoutineEntityScheduler _routineEntityScheduler;

}