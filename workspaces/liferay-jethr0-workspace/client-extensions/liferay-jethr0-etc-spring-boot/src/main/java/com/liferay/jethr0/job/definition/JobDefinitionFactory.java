/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Michael Hashimoto
 */
public class JobDefinitionFactory {

	public static Set<JobDefinition> getJobDefinitions() {
		Set<JobDefinition> jobDefinitions = new TreeSet<>();

		for (JobEntity.Type jobEntityType : JobEntity.Type.values()) {
			jobDefinitions.add(newJobDefinition(jobEntityType));
		}

		return jobDefinitions;
	}

	public static JobDefinition newJobDefinition(JobEntity.Type jobEntityType) {
		return null;
	}

}