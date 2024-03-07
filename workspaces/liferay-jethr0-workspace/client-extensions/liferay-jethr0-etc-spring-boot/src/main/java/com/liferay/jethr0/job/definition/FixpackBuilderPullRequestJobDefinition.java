/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.definition.parameter.JenkinsBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PullRequestURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.QAWebsitesBranchSHAJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.QAWebsitesBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestSuiteNameJobParameterDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class FixpackBuilderPullRequestJobDefinition extends BaseJobDefinition {

	@Override
	public Set<JobParameterDefinition> getJobParameterDefinitions() {
		Set<JobParameterDefinition> jobParameterDefinitions = new HashSet<>();

		jobParameterDefinitions.add(
			new JenkinsBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(new PullRequestURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new QAWebsitesBranchSHAJobParameterDefinition());
		jobParameterDefinitions.add(
			new QAWebsitesBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestSuiteNameJobParameterDefinition(null));

		return jobParameterDefinitions;
	}

	protected FixpackBuilderPullRequestJobDefinition(JobEntity.Type type) {
		super(type);
	}

}