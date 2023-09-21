/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.entity;

import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildParameterEntityRepository;
import com.liferay.jethr0.bui1d.repository.BuildRunEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsServerEntityRepository;
import com.liferay.jethr0.job.repository.JobComparatorEntityRepository;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.job.repository.JobPrioritizerEntityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class EntityInitializer {

	public void initialize() {
		_buildEntityRepository.setBuildParameterEntityRepository(
			_buildParameterEntityRepository);
		_buildEntityRepository.setBuildRunEntityRepository(
			_buildRunEntityRepository);
		_buildEntityRepository.setJobEntityRepository(_jobEntityRepository);

		_buildParameterEntityRepository.setBuildRepository(
			_buildEntityRepository);

		_buildRunEntityRepository.setBuildEntityRepository(
			_buildEntityRepository);

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

		_jobPrioritizerEntityRepository.setJobComparatorEntityRepository(
			_jobComparatorEntityRepository);

		_buildEntityRepository.initialize();
		_buildParameterEntityRepository.initialize();
		_buildRunEntityRepository.initialize();
		_jenkinsCohortEntityRepository.initialize();
		_jenkinsNodeEntityRepository.initialize();
		_jenkinsServerEntityRepository.initialize();
		_jobComparatorEntityRepository.initialize();
		_jobEntityRepository.initialize();
		_jobPrioritizerEntityRepository.initialize();

		_buildEntityRepository.initializeRelationships();
		_buildParameterEntityRepository.initializeRelationships();
		_buildRunEntityRepository.initializeRelationships();
		_jenkinsCohortEntityRepository.initializeRelationships();
		_jenkinsNodeEntityRepository.initializeRelationships();
		_jenkinsServerEntityRepository.initializeRelationships();
		_jobComparatorEntityRepository.initializeRelationships();
		_jobEntityRepository.initializeRelationships();
		_jobPrioritizerEntityRepository.initializeRelationships();
	}

	@Autowired
	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private BuildParameterEntityRepository _buildParameterEntityRepository;

	@Autowired
	private BuildRunEntityRepository _buildRunEntityRepository;

	@Autowired
	private JenkinsCohortEntityRepository _jenkinsCohortEntityRepository;

	@Autowired
	private JenkinsNodeEntityRepository _jenkinsNodeEntityRepository;

	@Autowired
	private JenkinsServerEntityRepository _jenkinsServerEntityRepository;

	@Autowired
	private JobComparatorEntityRepository _jobComparatorEntityRepository;

	@Autowired
	private JobEntityRepository _jobEntityRepository;

	@Autowired
	private JobPrioritizerEntityRepository _jobPrioritizerEntityRepository;

}