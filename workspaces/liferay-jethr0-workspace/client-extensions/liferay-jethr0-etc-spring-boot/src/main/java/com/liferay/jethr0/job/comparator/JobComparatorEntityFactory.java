/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.comparator;

import com.liferay.jethr0.entity.factory.BaseEntityFactory;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JobComparatorEntityFactory
	extends BaseEntityFactory<JobComparatorEntity> {

	@Override
	public JobComparatorEntity newEntity(JSONObject jsonObject) {
		JobComparatorEntity.Type type = JobComparatorEntity.Type.get(
			jsonObject.getJSONObject("type"));

		if (type == JobComparatorEntity.Type.FIFO) {
			return new FIFOJobComparatorEntity(jsonObject);
		}
		else if (type == JobComparatorEntity.Type.JOB_PRIORITY) {
			return new PriorityJobComparatorEntity(jsonObject);
		}
		else if (type == JobComparatorEntity.Type.JOB_START_DATE) {
			return new StartDateJobComparatorEntity(jsonObject);
		}

		throw new UnsupportedOperationException();
	}

	protected JobComparatorEntityFactory() {
		super(JobComparatorEntity.class);
	}

}