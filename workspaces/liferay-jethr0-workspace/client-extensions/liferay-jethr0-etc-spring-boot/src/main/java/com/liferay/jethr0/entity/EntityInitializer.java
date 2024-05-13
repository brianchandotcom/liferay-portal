/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.entity;

import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.event.github.GitHubFactory;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.jenkins.JenkinsEventProcessor;
import com.liferay.jethr0.event.jenkins.client.JenkinsClient;
import com.liferay.jethr0.event.jrp.JRPEventProcessor;
import com.liferay.jethr0.git.repository.GitBranchEntityRepository;
import com.liferay.jethr0.git.repository.GitCommitEntityRepository;
import com.liferay.jethr0.git.repository.GitPullRequestEntityRepository;
import com.liferay.jethr0.git.repository.GitUserEntityRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsServerEntityRepository;
import com.liferay.jethr0.job.queue.JobQueue;
import com.liferay.jethr0.job.repository.JobComparatorEntityRepository;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.job.repository.JobPrioritizerEntityRepository;
import com.liferay.jethr0.routine.repository.RoutineEntityRepository;
import com.liferay.jethr0.routine.scheduler.RoutineEntityScheduler;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class EntityInitializer {

	public void initialize() {
		_buildEntityRepository.setBuildRunEntityRepository(
			_buildRunEntityRepository);
		_buildEntityRepository.setJobEntityRepository(_jobEntityRepository);

		_buildRunEntityRepository.setBuildEntityRepository(
			_buildEntityRepository);

		_gitBranchEntityRepository.setGitCommitEntityRepository(
			_gitCommitEntityRepository);
		_gitBranchEntityRepository.setGitPullRequestEntityRepository(
			_gitPullRequestEntityRepository);
		_gitBranchEntityRepository.setGitUserEntityRepository(
			_gitUserEntityRepository);
		_gitBranchEntityRepository.setRoutineEntityRepository(
			_routineEntityRepository);

		_gitCommitEntityRepository.setJobEntityRepository(_jobEntityRepository);
		_gitCommitEntityRepository.setRoutineEntityRepository(
			_routineEntityRepository);

		_gitPullRequestEntityRepository.setGitBranchEntityRepository(
			_gitBranchEntityRepository);
		_gitPullRequestEntityRepository.setGitUserEntityRepository(
			_gitUserEntityRepository);
		_gitPullRequestEntityRepository.setJobEntityRepository(
			_jobEntityRepository);

		_gitUserEntityRepository.setGitBranchEntityRepository(
			_gitBranchEntityRepository);
		_gitUserEntityRepository.setGitPullRequestEntityRepository(
			_gitPullRequestEntityRepository);

		_jenkinsCohortEntityRepository.setJenkinsServerEntityRepository(
			_jenkinsServerEntityRepository);

		_jenkinsNodeEntityRepository.setJenkinsServerEntityRepository(
			_jenkinsServerEntityRepository);

		_jenkinsServerEntityRepository.setJenkinsCohortEntityRepository(
			_jenkinsCohortEntityRepository);
		_jenkinsServerEntityRepository.setJenkinsNodeEntityRepository(
			_jenkinsNodeEntityRepository);

		_jobComparatorEntityRepository.setJobPrioritizerEntityRepository(
			_jobPrioritizerEntityRepository);

		_jobEntityRepository.setBuildEntityRepository(_buildEntityRepository);
		_jobEntityRepository.setGitPullRequestEntityRepository(
			_gitPullRequestEntityRepository);
		_jobEntityRepository.setJobQueue(_jobQueue);
		_jobEntityRepository.setRoutineEntityRepository(
			_routineEntityRepository);

		_jobPrioritizerEntityRepository.setJobComparatorEntityRepository(
			_jobComparatorEntityRepository);

		_routineEntityRepository.setGitBranchEntityRepository(
			_gitBranchEntityRepository);
		_routineEntityRepository.setJobEntityRepository(_jobEntityRepository);

		Jethr0ContextUtil.setBuildEntityRepository(_buildEntityRepository);
		Jethr0ContextUtil.setBuildQueue(_buildQueue);
		Jethr0ContextUtil.setGitHubClient(_gitHubClient);
		Jethr0ContextUtil.setGitHubFactory(_gitHubFactory);
		Jethr0ContextUtil.setJenkinsClient(_jenkinsClient);
		Jethr0ContextUtil.setBuildRunEntityRepository(
			_buildRunEntityRepository);
		Jethr0ContextUtil.setGitBranchEntityRepository(
			_gitBranchEntityRepository);
		Jethr0ContextUtil.setGitCommitEntityRepository(
			_gitCommitEntityRepository);
		Jethr0ContextUtil.setGitPullRequestEntityRepository(
			_gitPullRequestEntityRepository);
		Jethr0ContextUtil.setGitUserEntityRepository(_gitUserEntityRepository);
		Jethr0ContextUtil.setJenkinsCohortEntityRepository(
			_jenkinsCohortEntityRepository);
		Jethr0ContextUtil.setJenkinsEventProcessor(_jenkinsEventProcessor);
		Jethr0ContextUtil.setJenkinsNodeEntityRepository(
			_jenkinsNodeEntityRepository);
		Jethr0ContextUtil.setJenkinsQueue(_jenkinsQueue);
		Jethr0ContextUtil.setJenkinsServerEntityRepository(
			_jenkinsServerEntityRepository);
		Jethr0ContextUtil.setJobComparatorEntityRepository(
			_jobComparatorEntityRepository);
		Jethr0ContextUtil.setJobEntityRepository(_jobEntityRepository);
		Jethr0ContextUtil.setJobPrioritizerEntityRepository(
			_jobPrioritizerEntityRepository);
		Jethr0ContextUtil.setJobQueue(_jobQueue);
		Jethr0ContextUtil.setJRPEventProcessor(_jrpEventProcessor);
		Jethr0ContextUtil.setLiferayPortalURL(
			StringUtil.toURL(_liferayPortalURL));
		Jethr0ContextUtil.setRoutineEntityRepository(_routineEntityRepository);
		Jethr0ContextUtil.setRoutineEntityScheduler(_routineEntityScheduler);

		_buildEntityRepository.initialize();
		_buildRunEntityRepository.initialize();
		_gitBranchEntityRepository.initialize();
		_gitCommitEntityRepository.initialize();
		_gitPullRequestEntityRepository.initialize();
		_gitUserEntityRepository.initialize();
		_jenkinsCohortEntityRepository.initialize();
		_jenkinsNodeEntityRepository.initialize();
		_jenkinsServerEntityRepository.initialize();
		_jobComparatorEntityRepository.initialize();
		_jobEntityRepository.initialize();
		_jobPrioritizerEntityRepository.initialize();
		_routineEntityRepository.initialize();

		_buildEntityRepository.initializeRelationships();
		_buildRunEntityRepository.initializeRelationships();
		_gitBranchEntityRepository.initializeRelationships();
		_gitCommitEntityRepository.initializeRelationships();
		_gitPullRequestEntityRepository.initializeRelationships();
		_gitUserEntityRepository.initializeRelationships();
		_jenkinsCohortEntityRepository.initializeRelationships();
		_jenkinsNodeEntityRepository.initializeRelationships();
		_jenkinsServerEntityRepository.initializeRelationships();
		_jobComparatorEntityRepository.initializeRelationships();
		_jobEntityRepository.initializeRelationships();
		_jobPrioritizerEntityRepository.initializeRelationships();
		_routineEntityRepository.initializeRelationships();

		_routineEntityScheduler.initialize();
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
	private GitCommitEntityRepository _gitCommitEntityRepository;

	@Autowired
	private GitHubClient _gitHubClient;

	@Autowired
	private GitHubFactory _gitHubFactory;

	@Autowired
	private GitPullRequestEntityRepository _gitPullRequestEntityRepository;

	@Autowired
	private GitUserEntityRepository _gitUserEntityRepository;

	@Autowired
	private JenkinsClient _jenkinsClient;

	@Autowired
	private JenkinsCohortEntityRepository _jenkinsCohortEntityRepository;

	@Autowired
	private JenkinsEventProcessor _jenkinsEventProcessor;

	@Autowired
	private JenkinsNodeEntityRepository _jenkinsNodeEntityRepository;

	@Autowired
	private JenkinsQueue _jenkinsQueue;

	@Autowired
	private JenkinsServerEntityRepository _jenkinsServerEntityRepository;

	@Autowired
	private JobComparatorEntityRepository _jobComparatorEntityRepository;

	@Autowired
	private JobEntityRepository _jobEntityRepository;

	@Autowired
	private JobPrioritizerEntityRepository _jobPrioritizerEntityRepository;

	@Autowired
	private JobQueue _jobQueue;

	@Autowired
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