/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.queue;

import com.liferay.jethr0.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJobQueueOrderEntity
	extends BaseEntity implements JobQueueOrderEntity {

	@Override
	public List<Long> getJobIds() {
		return _jobIds;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("jobIds", String.valueOf(getJobIds()));

		return jsonObject;
	}

	@Override
	public void setJobIds(List<Long> jobIds) {
		_jobIds = jobIds;
	}

	protected BaseJobQueueOrderEntity(JSONObject jsonObject) {
		super(jsonObject);

		String jobIds = jsonObject.getString("jobIds");

		JSONArray jobIdsJSONArray = new JSONArray(jobIds);

		for (int i = 0; i < jobIdsJSONArray.length(); i++) {
			_jobIds.add(jobIdsJSONArray.getLong(i));
		}
	}

	private List<Long> _jobIds = new ArrayList<>();

}