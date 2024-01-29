/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.definition.parameter.JenkinsBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalPullRequestURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.SubrepositoryBranchSHAJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.SubrepositoryBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.SubrepositoryUpstreamBranchNameJobParameterDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class MergePortalSubrepositoryJobDefinition extends BaseJobDefinition {

	@Override
	public Set<JobParameterDefinition> getJobParameterDefinitions() {
		Set<JobParameterDefinition> jobParameterDefinitions = new HashSet<>();

		jobParameterDefinitions.add(
			new JenkinsBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalPullRequestURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new SubrepositoryBranchSHAJobParameterDefinition());
		jobParameterDefinitions.add(
			new SubrepositoryBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new SubrepositoryUpstreamBranchNameJobParameterDefinition());

		return jobParameterDefinitions;
	}

	protected MergePortalSubrepositoryJobDefinition(JobEntity.Type type) {
		super(type);
	}

}