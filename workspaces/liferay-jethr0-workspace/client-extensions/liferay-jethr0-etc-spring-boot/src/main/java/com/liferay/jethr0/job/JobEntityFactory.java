/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.entity.factory.BaseEntityFactory;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JobEntityFactory extends BaseEntityFactory<JobEntity> {

	@Override
	public JobEntity newEntity(JSONObject jsonObject) {
		JSONObject typeJSONObject = jsonObject.getJSONObject("type");

		JobEntity.Type jobEntityType = JobEntity.Type.getByKey(
			typeJSONObject.getString("key"));

		if (jobEntityType == JobEntity.Type.PORTAL_PULL_REQUEST_SF) {
			return new PortalPullRequestSFJobEntity(jsonObject);
		}

		return new DefaultJobEntity(jsonObject);
	}

	protected JobEntityFactory() {
		super(JobEntity.class);
	}

}