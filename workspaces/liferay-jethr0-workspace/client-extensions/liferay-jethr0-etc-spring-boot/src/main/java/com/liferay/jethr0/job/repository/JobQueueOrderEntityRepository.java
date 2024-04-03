/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.job.dalo.JobQueueOrderEntityDALO;
import com.liferay.jethr0.job.queue.JobQueueOrderEntity;

import java.util.List;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JobQueueOrderEntityRepository
	extends BaseEntityRepository<JobQueueOrderEntity> {

	public JobQueueOrderEntity create(List<Long> jobIds) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("jobIds", String.valueOf(jobIds));

		return create(jsonObject);
	}

	@Override
	public JobQueueOrderEntityDALO getEntityDALO() {
		return _jobQueueOrderEntityDALO;
	}

	@Autowired
	private JobQueueOrderEntityDALO _jobQueueOrderEntityDALO;

}