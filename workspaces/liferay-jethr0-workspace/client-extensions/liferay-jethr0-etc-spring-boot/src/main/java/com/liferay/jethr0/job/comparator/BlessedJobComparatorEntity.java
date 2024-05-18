/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.comparator;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.prioritizer.JobPrioritizerEntity;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BlessedJobComparatorEntity extends BaseJobComparatorEntity {

	public BlessedJobComparatorEntity(
		JobPrioritizerEntity jobPrioritizerEntity, JSONObject jsonObject) {

		super(jobPrioritizerEntity, jsonObject);
	}

	public BlessedJobComparatorEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	public int compare(JobEntity jobEntity1, JobEntity jobEntity2) {
		boolean blessed1 = jobEntity1.getBlessed();
		boolean blessed2 = jobEntity2.getBlessed();

		if (blessed1 && blessed2) {
			return 0;
		}

		if (blessed1) {
			return -1;
		}

		if (blessed2) {
			return 1;
		}

		return 0;
	}

}