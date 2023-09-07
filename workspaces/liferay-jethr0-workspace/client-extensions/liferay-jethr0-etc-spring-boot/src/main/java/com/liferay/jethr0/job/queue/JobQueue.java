/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.queue;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildParameterEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.gitbranch.repository.GitBranchEntityRepository;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.comparator.BaseJobComparatorEntity;
import com.liferay.jethr0.job.comparator.JobComparatorEntity;
import com.liferay.jethr0.job.prioritizer.JobPrioritizerEntity;
import com.liferay.jethr0.job.repository.JobComparatorEntityRepository;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.job.repository.JobPrioritizerEntityRepository;
import com.liferay.jethr0.task.repository.TaskEntityRepository;
import com.liferay.jethr0.testsuite.repository.TestSuiteEntityRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Michael Hashimoto
 */
@Configuration
@EnableScheduling
public class JobQueue {

	public void addJobEntities(Set<JobEntity> jobEntities) {
		if (jobEntities == null) {
			return;
		}

		jobEntities.removeAll(Collections.singleton(null));

		if (jobEntities.isEmpty()) {
			return;
		}

		boolean sort = false;

		for (JobEntity jobEntity : jobEntities) {
			if (_jobEntities.contains(jobEntity)) {
				continue;
			}

			_jobEntities.add(jobEntity);

			sort = true;
		}

		if (sort) {
			sort();
		}
	}

	public void addJobEntity(JobEntity jobEntity) {
		addJobEntities(Collections.singleton(jobEntity));
	}

	public List<JobEntity> getJobEntities() {
		synchronized (_jobEntities) {
			return _jobEntities;
		}
	}

	public JobPrioritizerEntity getJobPrioritizerEntity() {
		return _jobPrioritizerEntity;
	}

	public void initialize() {
		_jobComparatorEntityRepository.initialize();
		_jobPrioritizerEntityRepository.initialize();

		_jobComparatorEntityRepository.setJobPrioritizerEntityRepository(
			_jobPrioritizerEntityRepository);

		_jobPrioritizerEntityRepository.setJobComparatorEntityRepository(
			_jobComparatorEntityRepository);

		_jobComparatorEntityRepository.initializeRelationships();
		_jobPrioritizerEntityRepository.initializeRelationships();

		_buildParameterEntityRepository.initialize();
		_buildEntityRepository.initialize();
		_buildRunEntityRepository.initialize();
		_jobEntityRepository.initialize();

		_buildEntityRepository.setBuildParameterEntityRepository(
			_buildParameterEntityRepository);
		_buildEntityRepository.setBuildRunEntityRepository(
			_buildRunEntityRepository);
		_buildEntityRepository.setJobEntityRepository(_jobEntityRepository);

		_buildRunEntityRepository.setBuildEntityRepository(
			_buildEntityRepository);

		_buildParameterEntityRepository.setBuildRepository(
			_buildEntityRepository);

		_jobEntityRepository.setBuildEntityRepository(_buildEntityRepository);

		_buildParameterEntityRepository.initializeRelationships();
		_buildEntityRepository.initializeRelationships();
		_buildRunEntityRepository.initializeRelationships();
		_jobEntityRepository.initializeRelationships();

		setJobPrioritizerEntity(_getDefaultJobPrioritizerEntity());

		addJobEntities(_jobEntityRepository.getAll());

		update();
	}

	public void removeJobEntities(Set<JobEntity> jobEntities) {
		if (jobEntities == null) {
			return;
		}

		jobEntities.removeAll(Collections.singleton(null));

		if (jobEntities.isEmpty()) {
			return;
		}

		_jobEntities.removeAll(jobEntities);
	}

	@Scheduled(cron = "${liferay.jethr0.job.queue.update.cron}")
	public void scheduledUpdate() {
		if (_log.isInfoEnabled()) {
			_log.info("Updating job queue");
		}

		update();
	}

	public void setJobPrioritizerEntity(
		JobPrioritizerEntity jobPrioritizerEntity) {

		_jobPrioritizerEntity = jobPrioritizerEntity;

		sort();
	}

	public void sort() {
		if (_jobPrioritizerEntity == null) {
			return;
		}

		synchronized (_jobEntities) {
			_jobEntities.removeAll(Collections.singleton(null));

			for (JobEntity jobEntity : new ArrayList<>(_jobEntities)) {
				boolean keepJob = false;

				for (BuildEntity buildEntity : jobEntity.getBuildEntities()) {
					if (buildEntity.getState() != BuildEntity.State.COMPLETED) {
						keepJob = true;

						break;
					}
				}

				if (!keepJob) {
					_jobEntities.remove(jobEntity);
				}
			}

			_sortedJobComparatorEntities.clear();

			_sortedJobComparatorEntities.addAll(
				_jobPrioritizerEntity.getJobComparatorEntities());

			Collections.sort(
				_sortedJobComparatorEntities,
				Comparator.comparingInt(JobComparatorEntity::getPosition));

			_jobEntities.sort(new PrioritizedJobComparator());
		}
	}

	public void update() {
		synchronized (_jobEntities) {
			Set<JobEntity> completedJobEntities = new HashSet<>();

			for (JobEntity jobEntity : getJobEntities()) {
				if (jobEntity.getState() == JobEntity.State.COMPLETED) {
					completedJobEntities.add(jobEntity);

					continue;
				}

				System.out.println(jobEntity);

				for (BuildEntity buildEntity : jobEntity.getBuildEntities()) {
					if (buildEntity.getState() == BuildEntity.State.COMPLETED) {
						continue;
					}

					Set<BuildRunEntity> buildRunEntities =
						_buildRunEntityRepository.getAll(buildEntity);

					boolean blocked = false;

					for (BuildRunEntity buildRunEntity : buildRunEntities) {
						if (buildRunEntity.isBlocked()) {
							blocked = true;

							break;
						}
					}

					if (blocked) {
						buildEntity.setState(BuildEntity.State.BLOCKED);

						_buildEntityRepository.update(buildEntity);
					}

					System.out.println("> " + buildEntity);
				}
			}

			removeJobEntities(completedJobEntities);
		}
	}

	private JobPrioritizerEntity _getDefaultJobPrioritizerEntity() {
		JobPrioritizerEntity jobPrioritizerEntity =
			_jobPrioritizerEntityRepository.getByName(_liferayJobPrioritizer);

		if (jobPrioritizerEntity != null) {
			return jobPrioritizerEntity;
		}

		jobPrioritizerEntity = _jobPrioritizerEntityRepository.add(
			_liferayJobPrioritizer);

		_jobComparatorEntityRepository.add(
			jobPrioritizerEntity, 1, JobComparatorEntity.Type.JOB_START_DATE,
			null);
		_jobComparatorEntityRepository.add(
			jobPrioritizerEntity, 2, JobComparatorEntity.Type.JOB_PRIORITY,
			null);
		_jobComparatorEntityRepository.add(
			jobPrioritizerEntity, 3, JobComparatorEntity.Type.FIFO, null);

		return jobPrioritizerEntity;
	}

	private static final Log _log = LogFactory.getLog(JobQueue.class);

	@Autowired
	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private BuildParameterEntityRepository _buildParameterEntityRepository;

	@Autowired
	private BuildRunEntityRepository _buildRunEntityRepository;

	@Autowired
	private GitBranchEntityRepository _gitBranchEntityRepository;

	@Autowired
	private JobComparatorEntityRepository _jobComparatorEntityRepository;

	private final List<JobEntity> _jobEntities = new ArrayList<>();

	@Autowired
	private JobEntityRepository _jobEntityRepository;

	private JobPrioritizerEntity _jobPrioritizerEntity;

	@Autowired
	private JobPrioritizerEntityRepository _jobPrioritizerEntityRepository;

	@Value("${liferay.jethr0.job.prioritizer}")
	private String _liferayJobPrioritizer;

	private final List<JobComparatorEntity> _sortedJobComparatorEntities =
		new ArrayList<>();

	@Autowired
	private TaskEntityRepository _taskEntityRepository;

	@Autowired
	private TestSuiteEntityRepository _testSuiteEntityRepository;

	private class PrioritizedJobComparator implements Comparator<JobEntity> {

		@Override
		public int compare(JobEntity jobEntity1, JobEntity jobEntity2) {
			for (JobComparatorEntity jobComparatorEntity :
					_sortedJobComparatorEntities) {

				if (!(jobComparatorEntity instanceof BaseJobComparatorEntity)) {
					continue;
				}

				BaseJobComparatorEntity baseJobComparatorEntity =
					(BaseJobComparatorEntity)jobComparatorEntity;

				int result = baseJobComparatorEntity.compare(
					jobEntity1, jobEntity2);

				if (result != 0) {
					return result;
				}
			}

			return 0;
		}

	}

}