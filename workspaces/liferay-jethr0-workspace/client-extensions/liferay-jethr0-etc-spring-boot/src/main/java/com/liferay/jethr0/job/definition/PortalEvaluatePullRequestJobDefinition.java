/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.definition.parameter.JenkinsBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JenkinsBuildIDJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JenkinsBuildURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JenkinsSlaveLabelJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalPullRequestURLJobParameterDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class PortalEvaluatePullRequestJobDefinition extends BaseJobDefinition {

	@Override
	public Set<JobParameterDefinition> getJobParameterDefinitions() {
		Set<JobParameterDefinition> jobParameterDefinitions = new HashSet<>();

		jobParameterDefinitions.add(
			new JenkinsBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(new JenkinsBuildIDJobParameterDefinition());
		jobParameterDefinitions.add(
			new JenkinsBuildURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new JenkinsSlaveLabelJobParameterDefinition("!master"));
		jobParameterDefinitions.add(
			new PortalPullRequestURLJobParameterDefinition());

		return jobParameterDefinitions;
	}

	protected PortalEvaluatePullRequestJobDefinition(JobEntity.Type type) {
		super(type);
	}

}