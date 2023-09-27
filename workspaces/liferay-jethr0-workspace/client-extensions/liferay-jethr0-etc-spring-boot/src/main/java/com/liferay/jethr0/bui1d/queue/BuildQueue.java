/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.queue;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.queue.JobQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildQueue {

	public void addBuildEntities(Set<BuildEntity> buildEntities) {
		if (buildEntities == null) {
			return;
		}

		buildEntities.removeAll(Collections.singleton(null));

		if (buildEntities.isEmpty()) {
			return;
		}

		_sortedBuildEntities.addAll(buildEntities);

		sort();
	}

	public void addBuildEntity(BuildEntity buildEntity) {
		if (buildEntity == null) {
			return;
		}

		_sortedBuildEntities.add(buildEntity);

		sort();
	}

	public void addJobEntities(Set<JobEntity> jobEntities) {
		for (JobEntity jobEntity : jobEntities) {
			_jobQueue.addJobEntity(jobEntity);
		}

		sort();
	}

	public void addJobEntity(JobEntity jobEntity) {
		addJobEntities(Collections.singleton(jobEntity));
	}

	public List<BuildEntity> getBuildEntities() {
		synchronized (_sortedBuildEntities) {
			return _sortedBuildEntities;
		}
	}

	public JobQueue getJobQueue() {
		return _jobQueue;
	}

	public void initialize() {
		sort();

		for (BuildEntity buildEntity : getBuildEntities()) {
			System.out.println(buildEntity);
		}
	}

	public BuildEntity nextBuildEntity(JenkinsNodeEntity jenkinsNodeEntity) {
		synchronized (_sortedBuildEntities) {
			BuildEntity nextBuildEntity = null;

			for (BuildEntity buildEntity : _sortedBuildEntities) {
				if (!jenkinsNodeEntity.isCompatible(buildEntity)) {
					continue;
				}

				nextBuildEntity = buildEntity;

				break;
			}

			_sortedBuildEntities.remove(nextBuildEntity);

			return nextBuildEntity;
		}
	}

	public void setJobQueue(JobQueue jobQueue) {
		_jobQueue = jobQueue;

		sort();
	}

	public void sort() {
		synchronized (_sortedBuildEntities) {
			_sortedBuildEntities.clear();

			_jobQueue.sort();

			for (JobEntity jobEntity : _jobQueue.getJobEntities()) {
				List<BuildEntity> buildEntities = new ArrayList<>(
					jobEntity.getBuildEntities());

				buildEntities.removeAll(Collections.singleton(null));

				Collections.sort(buildEntities, new ParentBuildComparator());

				for (BuildEntity buildEntity : buildEntities) {
					if ((buildEntity.getState() == BuildEntity.State.BLOCKED) ||
						(buildEntity.getState() == BuildEntity.State.OPENED)) {

						_sortedBuildEntities.add(buildEntity);
					}
				}
			}
		}
	}

	public static class ParentBuildComparator
		implements Comparator<BuildEntity> {

		@Override
		public int compare(BuildEntity buildEntity1, BuildEntity buildEntity2) {
			if (buildEntity1.isParentBuildEntity(buildEntity2)) {
				return -1;
			}

			if (buildEntity2.isParentBuildEntity(buildEntity1)) {
				return 1;
			}

			return 0;
		}

	}

	@Autowired
	private JobQueue _jobQueue;

	private final List<BuildEntity> _sortedBuildEntities = new ArrayList<>();

}