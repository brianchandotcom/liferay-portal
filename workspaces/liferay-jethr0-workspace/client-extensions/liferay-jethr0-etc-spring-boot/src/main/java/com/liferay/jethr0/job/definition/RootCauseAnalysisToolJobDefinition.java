/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.definition.parameter.JenkinsBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalBatchNameJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalBatchTestSelectorJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalBranchSHAsJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalCherryPickSHAsJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalUpstreamBranchNameJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.RetestCountJobParameterDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class RootCauseAnalysisToolJobDefinition extends BaseJobDefinition {

	@Override
	public Set<JobParameterDefinition> getJobParameterDefinitions() {
		Set<JobParameterDefinition> jobParameterDefinitions = new HashSet<>();

		jobParameterDefinitions.add(
			new JenkinsBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalBatchNameJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalBatchTestSelectorJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalBranchSHAsJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalCherryPickSHAsJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalUpstreamBranchNameJobParameterDefinition());
		jobParameterDefinitions.add(new RetestCountJobParameterDefinition());

		return jobParameterDefinitions;
	}

	protected RootCauseAnalysisToolJobDefinition(JobEntity.Type jobEntityType) {
		super(jobEntityType);
	}

}