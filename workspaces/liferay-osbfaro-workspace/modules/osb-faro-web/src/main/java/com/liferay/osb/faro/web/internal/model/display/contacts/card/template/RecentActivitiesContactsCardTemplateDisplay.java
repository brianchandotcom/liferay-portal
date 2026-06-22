/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts.card.template;

import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.constants.FieldMappingConstants;
import com.liferay.osb.faro.engine.client.model.ActivityGroup;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.engine.client.util.OrderByField;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.model.display.contacts.ActivityGroupDisplay;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collections;
import java.util.Map;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class RecentActivitiesContactsCardTemplateDisplay
	extends ContactsCardTemplateDisplay {

	public RecentActivitiesContactsCardTemplateDisplay() {
	}

	public RecentActivitiesContactsCardTemplateDisplay(
			String weDeployKey, ContactsCardTemplate contactsCardTemplate,
			int size, ContactsEngineClient contactsEngineClient)
		throws Exception {

		super(contactsCardTemplate, size, _SUPPORTED_SIZES);

		_filterType = MapUtil.getString(settings, "filterType");
		_query = MapUtil.getString(settings, "query");
	}

	@Override
	public Map<String, Object> getContactsCardData(
		FaroProject faroProject, FaroEntityDisplay faroEntityDisplay,
		ContactsEngineClient contactsEngineClient) {

		return HashMapBuilder.<String, Object>put(
			"activityGroups",
			() -> {
				OrderByField orderByField = new OrderByField(
					"startTime", "desc");

				Results<ActivityGroup> results =
					contactsEngineClient.getActivityGroups(
						faroProject, null, faroEntityDisplay.getId(),
						FieldMappingConstants.OWNER_TYPE_INDIVIDUAL, null, null,
						null, 1, 5, Collections.singletonList(orderByField));

				return TransformUtil.transform(
					results.getItems(), ActivityGroupDisplay::new);
			}
		).build();
	}

	private static final int[] _SUPPORTED_SIZES = {2};

	private String _filterType;
	private String _query;

}