/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.util;

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

import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public class Jethr0ContextUtil {

	public static BuildEntityRepository getBuildEntityRepository() {
		return _buildEntityRepository;
	}

	public static BuildQueue getBuildQueue() {
		return _buildQueue;
	}

	public static BuildRunEntityRepository getBuildRunEntityRepository() {
		return _buildRunEntityRepository;
	}

	public static GitBranchEntityRepository getGitBranchEntityRepository() {
		return _gitBranchEntityRepository;
	}

	public static GitCommitEntityRepository getGitCommitEntityRepository() {
		return _gitCommitEntityRepository;
	}

	public static GitHubClient getGitHubClient() {
		return _gitHubClient;
	}

	public static GitHubFactory getGitHubFactory() {
		return _gitHubFactory;
	}

	public static GitPullRequestEntityRepository
		getGitPullRequestEntityRepository() {

		return _gitPullRequestEntityRepository;
	}

	public static GitUserEntityRepository getGitUserEntityRepository() {
		return _gitUserEntityRepository;
	}

	public static JenkinsClient getJenkinsClient() {
		return _jenkinsClient;
	}

	public static JenkinsCohortEntityRepository
		getJenkinsCohortEntityRepository() {

		return _jenkinsCohortEntityRepository;
	}

	public static JenkinsEventProcessor getJenkinsEventProcessor() {
		return _jenkinsEventProcessor;
	}

	public static JenkinsNodeEntityRepository getJenkinsNodeEntityRepository() {
		return _jenkinsNodeEntityRepository;
	}

	public static JenkinsQueue getJenkinsQueue() {
		return _jenkinsQueue;
	}

	public static JenkinsServerEntityRepository
		getJenkinsServerEntityRepository() {

		return _jenkinsServerEntityRepository;
	}

	public static JobComparatorEntityRepository
		getJobComparatorEntityRepository() {

		return _jobComparatorEntityRepository;
	}

	public static JobEntityRepository getJobEntityRepository() {
		return _jobEntityRepository;
	}

	public static JobPrioritizerEntityRepository
		getJobPrioritizerEntityRepository() {

		return _jobPrioritizerEntityRepository;
	}

	public static JobQueue getJobQueue() {
		return _jobQueue;
	}

	public static JRPEventProcessor getJRPEventProcessor() {
		return _jrpEventProcessor;
	}

	public static URL getLiferayPortalURL() {
		return _liferayPortalURL;
	}

	public static RoutineEntityRepository getRoutineEntityRepository() {
		return _routineEntityRepository;
	}

	public static RoutineEntityScheduler getRoutineEntityScheduler() {
		return _routineEntityScheduler;
	}

	public static void setBuildEntityRepository(
		BuildEntityRepository buildEntityRepository) {

		_buildEntityRepository = buildEntityRepository;
	}

	public static void setBuildQueue(BuildQueue buildQueue) {
		_buildQueue = buildQueue;
	}

	public static void setBuildRunEntityRepository(
		BuildRunEntityRepository buildRunEntityRepository) {

		_buildRunEntityRepository = buildRunEntityRepository;
	}

	public static void setGitBranchEntityRepository(
		GitBranchEntityRepository gitBranchEntityRepository) {

		_gitBranchEntityRepository = gitBranchEntityRepository;
	}

	public static void setGitCommitEntityRepository(
		GitCommitEntityRepository gitCommitEntityRepository) {

		_gitCommitEntityRepository = gitCommitEntityRepository;
	}

	public static void setGitHubClient(GitHubClient gitHubClient) {
		_gitHubClient = gitHubClient;
	}

	public static void setGitHubFactory(GitHubFactory gitHubFactory) {
		_gitHubFactory = gitHubFactory;
	}

	public static void setGitPullRequestEntityRepository(
		GitPullRequestEntityRepository gitPullRequestEntityRepository) {

		_gitPullRequestEntityRepository = gitPullRequestEntityRepository;
	}

	public static void setGitUserEntityRepository(
		GitUserEntityRepository gitUserEntityRepository) {

		_gitUserEntityRepository = gitUserEntityRepository;
	}

	public static void setJenkinsClient(JenkinsClient jenkinsClient) {
		_jenkinsClient = jenkinsClient;
	}

	public static void setJenkinsCohortEntityRepository(
		JenkinsCohortEntityRepository jenkinsCohortEntityRepository) {

		_jenkinsCohortEntityRepository = jenkinsCohortEntityRepository;
	}

	public static void setJenkinsEventProcessor(
		JenkinsEventProcessor jenkinsEventProcessor) {

		_jenkinsEventProcessor = jenkinsEventProcessor;
	}

	public static void setJenkinsNodeEntityRepository(
		JenkinsNodeEntityRepository jenkinsNodeEntityRepository) {

		_jenkinsNodeEntityRepository = jenkinsNodeEntityRepository;
	}

	public static void setJenkinsQueue(JenkinsQueue jenkinsQueue) {
		_jenkinsQueue = jenkinsQueue;
	}

	public static void setJenkinsServerEntityRepository(
		JenkinsServerEntityRepository jenkinsServerEntityRepository) {

		_jenkinsServerEntityRepository = jenkinsServerEntityRepository;
	}

	public static void setJobComparatorEntityRepository(
		JobComparatorEntityRepository jobComparatorEntityRepository) {

		_jobComparatorEntityRepository = jobComparatorEntityRepository;
	}

	public static void setJobEntityRepository(
		JobEntityRepository jobEntityRepository) {

		_jobEntityRepository = jobEntityRepository;
	}

	public static void setJobPrioritizerEntityRepository(
		JobPrioritizerEntityRepository jobPrioritizerEntityRepository) {

		_jobPrioritizerEntityRepository = jobPrioritizerEntityRepository;
	}

	public static void setJobQueue(JobQueue jobQueue) {
		_jobQueue = jobQueue;
	}

	public static void setJRPEventProcessor(
		JRPEventProcessor jrpEventProcessor) {

		_jrpEventProcessor = jrpEventProcessor;
	}

	public static void setLiferayPortalURL(URL liferayPortalURL) {
		_liferayPortalURL = liferayPortalURL;
	}

	public static void setRoutineEntityRepository(
		RoutineEntityRepository routineEntityRepository) {

		_routineEntityRepository = routineEntityRepository;
	}

	public static void setRoutineEntityScheduler(
		RoutineEntityScheduler routineEntityScheduler) {

		_routineEntityScheduler = routineEntityScheduler;
	}

	private static BuildEntityRepository _buildEntityRepository;
	private static BuildQueue _buildQueue;
	private static BuildRunEntityRepository _buildRunEntityRepository;
	private static GitBranchEntityRepository _gitBranchEntityRepository;
	private static GitCommitEntityRepository _gitCommitEntityRepository;
	private static GitHubClient _gitHubClient;
	private static GitHubFactory _gitHubFactory;
	private static GitPullRequestEntityRepository
		_gitPullRequestEntityRepository;
	private static GitUserEntityRepository _gitUserEntityRepository;
	private static JenkinsClient _jenkinsClient;
	private static JenkinsCohortEntityRepository _jenkinsCohortEntityRepository;
	private static JenkinsEventProcessor _jenkinsEventProcessor;
	private static JenkinsNodeEntityRepository _jenkinsNodeEntityRepository;
	private static JenkinsQueue _jenkinsQueue;
	private static JenkinsServerEntityRepository _jenkinsServerEntityRepository;
	private static JobComparatorEntityRepository _jobComparatorEntityRepository;
	private static JobEntityRepository _jobEntityRepository;
	private static JobPrioritizerEntityRepository
		_jobPrioritizerEntityRepository;
	private static JobQueue _jobQueue;
	private static JRPEventProcessor _jrpEventProcessor;
	private static URL _liferayPortalURL;
	private static RoutineEntityRepository _routineEntityRepository;
	private static RoutineEntityScheduler _routineEntityScheduler;

}