/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.definition.parameter.JenkinsBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalHotfixReleaseURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalPatchTicketURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalPatcherBuildIDJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalPatcherRequestKeyJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalPatcherUserIDJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalReleaseVersionParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalUpstreamBranchNameJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestSuiteNameJobParameterDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class PortalHotfixReleaseJobDefinition extends BaseJobDefinition {

	@Override
	public Set<JobParameterDefinition> getJobParameterDefinitions() {
		Set<JobParameterDefinition> jobParameterDefinitions = new HashSet<>();

		jobParameterDefinitions.add(
			new JenkinsBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalHotfixReleaseURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalPatcherBuildIDJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalPatcherRequestKeyJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalPatcherUserIDJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalPatchTicketURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalReleaseVersionParameterDefinition());
		jobParameterDefinitions.add(
			new PortalUpstreamBranchNameJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestSuiteNameJobParameterDefinition("portal-hotfix-release"));

		return jobParameterDefinitions;
	}

	protected PortalHotfixReleaseJobDefinition(JobEntity.Type type) {
		super(type);
	}

}