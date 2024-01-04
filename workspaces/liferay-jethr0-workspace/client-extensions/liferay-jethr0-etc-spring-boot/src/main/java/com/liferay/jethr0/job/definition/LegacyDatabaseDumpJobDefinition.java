/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.definition.parameter.JenkinsBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalLegacyBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.PortalLegacyVersionsJobParameterDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class LegacyDatabaseDumpJobDefinition extends BaseJobDefinition {

	@Override
	public Set<JobParameterDefinition> getJobParameterDefinitions() {
		Set<JobParameterDefinition> jobParameterDefinitions = new HashSet<>();

		jobParameterDefinitions.add(
			new JenkinsBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalLegacyBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new PortalLegacyVersionsJobParameterDefinition());

		return jobParameterDefinitions;
	}

	protected LegacyDatabaseDumpJobDefinition(JobEntity.Type type) {
		super(type);
	}

}