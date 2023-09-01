/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.comparator;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.prioritizer.JobPrioritizerEntity;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PriorityJobComparatorEntity extends BaseJobComparatorEntity {

	public PriorityJobComparatorEntity(
		JobPrioritizerEntity jobPrioritizerEntity, JSONObject jsonObject) {

		super(jobPrioritizerEntity, jsonObject);
	}

	public PriorityJobComparatorEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	public int compare(JobEntity jobEntity1, JobEntity jobEntity2) {
		return Integer.compare(
			jobEntity1.getPriority(), jobEntity2.getPriority());
	}

}