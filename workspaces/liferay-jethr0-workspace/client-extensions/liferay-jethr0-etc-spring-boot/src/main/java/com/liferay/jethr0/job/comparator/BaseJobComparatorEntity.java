/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.comparator;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.prioritizer.JobPrioritizerEntity;

import java.util.Comparator;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJobComparatorEntity
	extends BaseEntity implements Comparator<JobEntity>, JobComparatorEntity {

	@Override
	public JobPrioritizerEntity getJobPrioritizerEntity() {
		return _jobPrioritizerEntity;
	}

	@Override
	public long getJobPrioritizerEntityId() {
		return _jobPrioritizerEntityId;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		JobComparatorEntity.Type type = getType();

		jsonObject.put(
			"position", getPosition()
		).put(
			"r_jobPrioritizerToJobComparators_c_jobPrioritizerId",
			getJobPrioritizerEntityId()
		).put(
			"type", type.getJSONObject()
		).put(
			"value", getValue()
		);

		return jsonObject;
	}

	@Override
	public int getPosition() {
		return _position;
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public String getValue() {
		return _value;
	}

	@Override
	public void setJobPrioritizerEntity(
		JobPrioritizerEntity jobPrioritizerEntity) {

		_jobPrioritizerEntity = jobPrioritizerEntity;

		if (_jobPrioritizerEntity != null) {
			_jobPrioritizerEntityId = _jobPrioritizerEntity.getId();
		}
		else {
			_jobPrioritizerEntityId = 0;
		}
	}

	@Override
	public void setPosition(int position) {
		_position = position;
	}

	@Override
	public void setValue(String value) {
		_value = value;
	}

	protected BaseJobComparatorEntity(
		JobPrioritizerEntity jobPrioritizerEntity, JSONObject jsonObject) {

		super(jsonObject);

		setJobPrioritizerEntity(jobPrioritizerEntity);

		_position = jsonObject.getInt("position");
		_type = Type.get(jsonObject.getJSONObject("type"));
		_value = jsonObject.optString("value");
	}

	protected BaseJobComparatorEntity(JSONObject jsonObject) {
		super(jsonObject);

		_position = jsonObject.getInt("position");
		_jobPrioritizerEntityId = jsonObject.optLong(
			"r_jobPrioritizerToJobComparators_c_jobPrioritizerId");
		_type = Type.get(jsonObject.getJSONObject("type"));
		_value = jsonObject.optString("value");
	}

	private JobPrioritizerEntity _jobPrioritizerEntity;
	private long _jobPrioritizerEntityId;
	private int _position;
	private final Type _type;
	private String _value;

}