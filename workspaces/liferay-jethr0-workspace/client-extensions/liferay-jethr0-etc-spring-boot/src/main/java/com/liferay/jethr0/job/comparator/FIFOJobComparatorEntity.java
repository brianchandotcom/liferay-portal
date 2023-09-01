/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.comparator;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.prioritizer.JobPrioritizerEntity;

import java.util.Date;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class FIFOJobComparatorEntity extends BaseJobComparatorEntity {

	public FIFOJobComparatorEntity(
		JobPrioritizerEntity jobPrioritizerEntity, JSONObject jsonObject) {

		super(jobPrioritizerEntity, jsonObject);
	}

	public FIFOJobComparatorEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	public int compare(JobEntity jobEntity1, JobEntity jobEntity2) {
		Date createdDate1 = jobEntity1.getCreatedDate();
		Date createdDate2 = jobEntity2.getCreatedDate();

		return createdDate1.compareTo(createdDate2);
	}

}