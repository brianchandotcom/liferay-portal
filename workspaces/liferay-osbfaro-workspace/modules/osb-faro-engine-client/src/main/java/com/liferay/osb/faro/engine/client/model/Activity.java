/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Date;

/**
 * @author Matthew Kong
 */
public class Activity {

	public String getActivityKey() {
		return _activityKey;
	}

	public String getActivityType() {
		return _activityType;
	}

	public String getApplicationId() {
		return _applicationId;
	}

	public Date getDay() {
		if (_day == null) {
			return null;
		}

		return new Date(_day.getTime());
	}

	public String getDescription() {
		return _description;
	}

	public Date getEndTime() {
		if (_endTime == null) {
			return null;
		}

		return new Date(_endTime.getTime());
	}

	public String getEventId() {
		return _eventId;
	}

	public String getGroupId() {
		return _groupId;
	}

	public String getGroupName() {
		return _groupName;
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public ActionObject getObject() {
		return _object;
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

	public void setActivityKey(String activityKey) {
		_activityKey = activityKey;
	}

	public void setActivityType(String activityType) {
		_activityType = activityType;
	}

	public void setApplicationId(String applicationId) {
		_applicationId = applicationId;
	}

	public void setDay(Date day) {
		if (day != null) {
			_day = new Date(day.getTime());
		}
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setEndTime(Date endTime) {
		if (endTime != null) {
			_endTime = new Date(endTime.getTime());
		}
	}

	public void setEventId(String eventId) {
		_eventId = eventId;
	}

	public void setGroupId(String groupId) {
		_groupId = groupId;
	}

	public void setGroupName(String groupName) {
		_groupName = groupName;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setObject(ActionObject object) {
		_object = object;
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

	public static class ActionObject {

		public String getCanonicalUrl() {
			return _canonicalUrl;
		}

		public String getDataSourceAssetPK() {
			return _dataSourceAssetPK;
		}

		public String getDescription() {
			return _description;
		}

		public String getId() {
			return _id;
		}

		public String getName() {
			return _name;
		}

		public String getObjectType() {
			return _objectType;
		}

		public String getUrl() {
			return _url;
		}

		public void setCanonicalUrl(String canonicalUrl) {
			_canonicalUrl = canonicalUrl;
		}

		public void setDataSourceAssetPK(String dataSourceAssetPK) {
			_dataSourceAssetPK = dataSourceAssetPK;
		}

		public void setDescription(String description) {
			_description = description;
		}

		public void setId(String id) {
			_id = id;
		}

		public void setName(String name) {
			_name = name;
		}

		public void setObjectType(String objectType) {
			_objectType = objectType;
		}

		public void setUrl(String url) {
			_url = url;
		}

		private String _canonicalUrl;
		private String _dataSourceAssetPK;
		private String _description;
		private String _id;
		private String _name;
		private String _objectType;
		private String _url;

	}

	public enum EventId {

		commentPosted, documentDownloaded, documentPreviewed, formSubmitted,
		formViewed, pageViewed

	}

	private String _activityKey;
	private String _activityType;
	private String _applicationId;
	private Date _day;
	private String _description;
	private Date _endTime;
	private String _eventId;
	private String _groupId;
	private String _groupName;
	private String _id;
	private String _name;
	private ActionObject _object;
	private String _ownerId;
	private String _ownerType;
	private Date _startTime;

}