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
public class StartDateJobComparatorEntity extends BaseJobComparatorEntity {

	public StartDateJobComparatorEntity(
		JobPrioritizerEntity jobPrioritizerEntity, JSONObject jsonObject) {

		super(jobPrioritizerEntity, jsonObject);
	}

	public StartDateJobComparatorEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	public int compare(JobEntity jobEntity1, JobEntity jobEntity2) {
		Date startDate1 = jobEntity1.getStartDate();
		Date startDate2 = jobEntity2.getStartDate();

		if ((startDate1 == null) && (startDate2 == null)) {
			return 0;
		}

		if (startDate1 == null) {
			return 1;
		}

		if (startDate2 == null) {
			return -1;
		}

		return startDate1.compareTo(startDate2);
	}

}