/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.definition.parameter.CallbackURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JenkinsBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PluginsMarketplaceAppFileNameJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PluginsMarketplaceAppTypeJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PluginsMarketplaceAppURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalReleaseVersionParameterDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class PluginsMarketplaceAppsJobDefinition extends BaseJobDefinition {

	@Override
	public Set<JobParameterDefinition> getJobParameterDefinitions() {
		Set<JobParameterDefinition> jobParameterDefinitions = new HashSet<>();

		jobParameterDefinitions.add(new CallbackURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new JenkinsBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PluginsMarketplaceAppFileNameJobParameterDefinition());
		jobParameterDefinitions.add(
			new PluginsMarketplaceAppTypeJobParameterDefinition());
		jobParameterDefinitions.add(
			new PluginsMarketplaceAppURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalReleaseVersionParameterDefinition());

		return jobParameterDefinitions;
	}

	protected PluginsMarketplaceAppsJobDefinition(JobEntity.Type type) {
		super(type);
	}

}