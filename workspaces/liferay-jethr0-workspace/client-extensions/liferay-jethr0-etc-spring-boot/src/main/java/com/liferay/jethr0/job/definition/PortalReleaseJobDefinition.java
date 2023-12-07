/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.definition.parameter.JenkinsBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalBranchSHAJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalBuildProfileJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalReleaseDependenciesURLParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalReleaseOSGiURLParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalReleaseSQLURLParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalReleaseTomcatURLParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalReleaseToolsURLParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalReleaseVersionParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalReleaseWarURLParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalUpstreamBranchNameJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestSuiteNameJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestrayBuildNameParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestrayRoutineNameParameterDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class PortalReleaseJobDefinition extends BaseJobDefinition {

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
			new PortalReleaseDependenciesURLParameterDefinition());
		jobParameterDefinitions.add(
			new PortalReleaseOSGiURLParameterDefinition());
		jobParameterDefinitions.add(
			new PortalReleaseSQLURLParameterDefinition());
		jobParameterDefinitions.add(
			new PortalReleaseTomcatURLParameterDefinition());
		jobParameterDefinitions.add(
			new PortalReleaseToolsURLParameterDefinition());
		jobParameterDefinitions.add(
			new PortalReleaseVersionParameterDefinition());
		jobParameterDefinitions.add(
			new PortalReleaseWarURLParameterDefinition());
		jobParameterDefinitions.add(
			new PortalUpstreamBranchNameJobParameterDefinition());
		jobParameterDefinitions.add(new TestrayBuildNameParameterDefinition());
		jobParameterDefinitions.add(
			new TestrayRoutineNameParameterDefinition());
		jobParameterDefinitions.add(
			new TestSuiteNameJobParameterDefinition("portal-release"));

		return jobParameterDefinitions;
	}

	protected PortalReleaseJobDefinition(JobEntity.Type type) {
		super(type);
	}

}