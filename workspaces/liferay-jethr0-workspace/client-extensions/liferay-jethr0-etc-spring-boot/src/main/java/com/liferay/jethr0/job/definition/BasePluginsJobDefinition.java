/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.definition.parameter.JenkinsBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PluginNameJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PluginsBranchSHAJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PluginsBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PluginsUpstreamBranchNameJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalReleaseVersionParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalUpstreamBranchNameJobParameterDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePluginsJobDefinition extends BaseJobDefinition {

	@Override
	public Set<JobParameterDefinition> getJobParameterDefinitions() {
		Set<JobParameterDefinition> jobParameterDefinitions = new HashSet<>();

		jobParameterDefinitions.add(
			new JenkinsBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(new PluginNameJobParameterDefinition());
		jobParameterDefinitions.add(
			new PluginsBranchSHAJobParameterDefinition());
		jobParameterDefinitions.add(
			new PluginsBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PluginsUpstreamBranchNameJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalReleaseVersionParameterDefinition());
		jobParameterDefinitions.add(
			new PortalUpstreamBranchNameJobParameterDefinition());

		return jobParameterDefinitions;
	}

	protected BasePluginsJobDefinition(JobEntity.Type type) {
		super(type);
	}

}