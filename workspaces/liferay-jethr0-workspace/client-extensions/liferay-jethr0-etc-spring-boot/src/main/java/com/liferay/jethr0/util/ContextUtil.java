/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.util;

import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.git.repository.GitBranchEntityRepository;
import com.liferay.jethr0.git.repository.GitCommitEntityRepository;
import com.liferay.jethr0.git.repository.GitPullRequestEntityRepository;
import com.liferay.jethr0.git.repository.GitUserEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsServerEntityRepository;
import com.liferay.jethr0.job.queue.JobQueue;
import com.liferay.jethr0.job.repository.JobComparatorEntityRepository;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.job.repository.JobPrioritizerEntityRepository;
import com.liferay.jethr0.routine.repository.RoutineEntityRepository;
import com.liferay.jethr0.routine.scheduler.RoutineEntityScheduler;

/**
 * @author Michael Hashimoto
 */
public class ContextUtil {

	public static BuildEntityRepository getBuildEntityRepository() {
		return _buildEntityRepository;
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

	public static GitPullRequestEntityRepository
		getGitPullRequestEntityRepository() {

		return _gitPullRequestEntityRepository;
	}

	public static GitUserEntityRepository getGitUserEntityRepository() {
		return _gitUserEntityRepository;
	}

	public static JenkinsCohortEntityRepository
		getJenkinsCohortEntityRepository() {

		return _jenkinsCohortEntityRepository;
	}

	public static JenkinsNodeEntityRepository getJenkinsNodeEntityRepository() {
		return _jenkinsNodeEntityRepository;
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

	public static void setGitPullRequestEntityRepository(
		GitPullRequestEntityRepository gitPullRequestEntityRepository) {

		_gitPullRequestEntityRepository = gitPullRequestEntityRepository;
	}

	public static void setGitUserEntityRepository(
		GitUserEntityRepository gitUserEntityRepository) {

		_gitUserEntityRepository = gitUserEntityRepository;
	}

	public static void setJenkinsCohortEntityRepository(
		JenkinsCohortEntityRepository jenkinsCohortEntityRepository) {

		_jenkinsCohortEntityRepository = jenkinsCohortEntityRepository;
	}

	public static void setJenkinsNodeEntityRepository(
		JenkinsNodeEntityRepository jenkinsNodeEntityRepository) {

		_jenkinsNodeEntityRepository = jenkinsNodeEntityRepository;
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

	public static void setRoutineEntityRepository(
		RoutineEntityRepository routineEntityRepository) {

		_routineEntityRepository = routineEntityRepository;
	}

	public static void setRoutineEntityScheduler(
		RoutineEntityScheduler routineEntityScheduler) {

		_routineEntityScheduler = routineEntityScheduler;
	}

	private static BuildEntityRepository _buildEntityRepository;
	private static BuildRunEntityRepository _buildRunEntityRepository;
	private static GitBranchEntityRepository _gitBranchEntityRepository;
	private static GitCommitEntityRepository _gitCommitEntityRepository;
	private static GitPullRequestEntityRepository
		_gitPullRequestEntityRepository;
	private static GitUserEntityRepository _gitUserEntityRepository;
	private static JenkinsCohortEntityRepository _jenkinsCohortEntityRepository;
	private static JenkinsNodeEntityRepository _jenkinsNodeEntityRepository;
	private static JenkinsServerEntityRepository _jenkinsServerEntityRepository;
	private static JobComparatorEntityRepository _jobComparatorEntityRepository;
	private static JobEntityRepository _jobEntityRepository;
	private static JobPrioritizerEntityRepository
		_jobPrioritizerEntityRepository;
	private static JobQueue _jobQueue;
	private static RoutineEntityRepository _routineEntityRepository;
	private static RoutineEntityScheduler _routineEntityScheduler;

}