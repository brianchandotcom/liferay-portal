/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.definition.parameter.JenkinsBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PoshiQueryJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.QAWebsitesBranchSHAJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.QAWebsitesBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.QAWebsitesProjectNameJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestSuiteNameJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestrayProjectNameJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestrayRoutineNameJobParameterDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class BaseQAWebsitesJobDefinition extends BaseJobDefinition {

	@Override
	public Set<JobParameterDefinition> getJobParameterDefinitions() {
		Set<JobParameterDefinition> jobParameterDefinitions = new HashSet<>();

		jobParameterDefinitions.add(
			new JenkinsBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(new PoshiQueryJobParameterDefinition());
		jobParameterDefinitions.add(
			new QAWebsitesBranchSHAJobParameterDefinition());
		jobParameterDefinitions.add(
			new QAWebsitesBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new QAWebsitesBranchSHAJobParameterDefinition());
		jobParameterDefinitions.add(
			new QAWebsitesProjectNameJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestrayProjectNameJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestrayRoutineNameJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestSuiteNameJobParameterDefinition(null));

		return jobParameterDefinitions;
	}

	protected BaseQAWebsitesJobDefinition(JobEntity.Type jobEntityType) {
		super(jobEntityType);
	}

}