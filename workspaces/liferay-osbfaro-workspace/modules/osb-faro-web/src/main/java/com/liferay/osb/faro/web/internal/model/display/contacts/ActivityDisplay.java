/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.constants.ActivityConstants;
import com.liferay.osb.faro.engine.client.model.Activity;
import com.liferay.osb.faro.web.internal.model.display.main.EntityDisplay;

import java.util.Date;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class ActivityDisplay extends EntityDisplay {

	public ActivityDisplay() {
	}

	public ActivityDisplay(Activity activity) {
		setId(activity.getId());

		_action = ActivityConstants.getAction(activity.getEventId());
		_activityKey = activity.getActivityKey();
		_assetType = activity.getApplicationId();
		_day = activity.getDay();
		_eventId = activity.getEventId();
		_groupName = activity.getGroupName();
		_startTime = activity.getStartTime();

		Activity.ActionObject actionObject = activity.getObject();

		_canonicalUrl = actionObject.getCanonicalUrl();
		_dataSourceAssetPK = actionObject.getDataSourceAssetPK();
		_name = actionObject.getName();
		_url = actionObject.getUrl();
	}

	private int _action;
	private String _activityKey;
	private String _assetType;
	private String _canonicalUrl;
	private String _dataSourceAssetPK;
	private Date _day;
	private String _eventId;
	private String _groupName;
	private String _name;
	private Date _startTime;
	private String _url;

}