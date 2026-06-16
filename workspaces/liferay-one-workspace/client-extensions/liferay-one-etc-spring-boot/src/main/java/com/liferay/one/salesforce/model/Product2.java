/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.salesforce.model;

import org.json.JSONObject;

/**
 * @author Kyle Bischof
 */
public class Product2 {

	public Product2(JSONObject jsonObject) {
		_description = jsonObject.optString("Description");
		_id = jsonObject.optString("Id");
		_name = jsonObject.optString("Name");
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

	private final String _description;
	private final String _id;
	private final String _name;

}