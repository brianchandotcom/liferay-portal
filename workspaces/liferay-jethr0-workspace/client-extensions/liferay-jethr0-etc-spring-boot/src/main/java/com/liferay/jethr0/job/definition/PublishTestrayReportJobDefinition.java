/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.definition.parameter.JenkinsBranchURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JenkinsBuildURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.JobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestrayBuildNameJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestrayProductVersionJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestrayProjectNameJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestrayRoutineNameJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestrayServerTypeJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestrayServerURLJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestraySlackChannelsJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestraySlackIconEmojiJobParameterDefinition;
import com.liferay.jethr0.job.definition.parameter.TestraySlackUserNameJobParameterDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class PublishTestrayReportJobDefinition extends BaseJobDefinition {

	@Override
	public Set<JobParameterDefinition> getJobParameterDefinitions() {
		Set<JobParameterDefinition> jobParameterDefinitions = new HashSet<>();

		jobParameterDefinitions.add(
			new JenkinsBranchURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new JenkinsBuildURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestrayBuildNameJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestrayProductVersionJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestrayProjectNameJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestrayRoutineNameJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestrayServerTypeJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestrayServerURLJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestraySlackChannelsJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestraySlackIconEmojiJobParameterDefinition());
		jobParameterDefinitions.add(
			new TestraySlackUserNameJobParameterDefinition());

		return jobParameterDefinitions;
	}

	protected PublishTestrayReportJobDefinition(JobEntity.Type type) {
		super(type);
	}

}