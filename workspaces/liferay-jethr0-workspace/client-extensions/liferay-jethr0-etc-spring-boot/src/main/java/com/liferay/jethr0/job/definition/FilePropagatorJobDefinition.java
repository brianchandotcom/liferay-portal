/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.definition.parameter.CleanUpCommandJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.FileNamesJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JenkinsBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.SourceDirURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TargetDirPathJobParameterDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class FilePropagatorJobDefinition extends BaseJobDefinition {

	@Override
	public Set<JobParameterDefinition> getJobParameterDefinitions() {
		Set<JobParameterDefinition> jobParameterDefinitions = new HashSet<>();

		jobParameterDefinitions.add(new CleanUpCommandJobParameterDefinition());
		jobParameterDefinitions.add(new FileNamesJobParameterDefinition());
		jobParameterDefinitions.add(
			new JenkinsBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(new SourceDirURLJobParameterDefinition());
		jobParameterDefinitions.add(new TargetDirPathJobParameterDefinition());

		return jobParameterDefinitions;
	}

	protected FilePropagatorJobDefinition(JobEntity.Type type) {
		super(type);
	}

}