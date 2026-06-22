/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class ActivityGroup {

	public String getActivityType() {
		return _activityType;
	}

	public Date getDay() {
		if (_day == null) {
			return null;
		}

		return new Date(_day.getTime());
	}

	@JsonProperty("_embedded")
	public Map<String, Object> getEmbeddedResources() {
		return _embeddedResources;
	}

	public Date getEndTime() {
		if (_endTime == null) {
			return null;
		}

		return new Date(_endTime.getTime());
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getOwnerId() {
		return _ownerId;
	}

	public String getOwnerType() {
		return _ownerType;
	}

	public Date getStartTime() {
		if (_startTime == null) {
			return null;
		}

		return new Date(_startTime.getTime());
	}

	public void setActivityType(String activityType) {
		_activityType = activityType;
	}

	public void setDay(Date day) {
		if (day != null) {
			_day = new Date(day.getTime());
		}
	}

	public void setEmbeddedResources(Map<String, Object> embeddedResources) {
		_embeddedResources = embeddedResources;
	}

	public void setEndTime(Date endTime) {
		if (endTime != null) {
			_endTime = new Date(endTime.getTime());
		}
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOwnerId(String ownerId) {
		_ownerId = ownerId;
	}

	public void setOwnerType(String ownerType) {
		_ownerType = ownerType;
	}

	public void setStartTime(Date startTime) {
		if (startTime != null) {
			_startTime = new Date(startTime.getTime());
		}
	}

	private String _activityType;
	private Date _day;
	private Map<String, Object> _embeddedResources = new HashMap<>();
	private Date _endTime;
	private String _id;
	private String _name;
	private String _ownerId;
	private String _ownerType;
	private Date _startTime;

}