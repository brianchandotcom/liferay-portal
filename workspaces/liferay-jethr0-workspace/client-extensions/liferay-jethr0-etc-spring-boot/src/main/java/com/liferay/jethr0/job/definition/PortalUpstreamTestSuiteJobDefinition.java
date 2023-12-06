/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class PortalUpstreamTestSuiteJobDefinition extends BaseJobDefinition {

	@Override
	public Set<JobParameterDefinition> getJobParameterDefinitions() {
		Set<JobParameterDefinition> jobParameterDefinitions = new HashSet<>();

		jobParameterDefinitions.add(
			new JenkinsBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalBranchSHAJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalBuildProfileJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalUpstreamBranchNameJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestSuiteNameJobParameterDefinition("default"));

		return jobParameterDefinitions;
	}

	protected PortalUpstreamTestSuiteJobDefinition(
		JobEntity.Type jobEntityType) {

		super(jobEntityType);
	}

}