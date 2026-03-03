/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.model;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;

import java.util.List;

import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class AccountUsage {

	public AccountUsage(
		List<ProductPurchase> productPurchases, JSONObject usageJSONObject) {

		_usageStrategy = new SaaSUsageStrategy(
			productPurchases, usageJSONObject);
	}

	public AccountUsage(
		ProductPurchase productPurchase, JSONObject usageJSONObject) {

		_usageStrategy = new ExperienceUsageStrategy(
			productPurchase, usageJSONObject);
	}

	public JSONObject toJSONObject() {
		return _usageStrategy.toJSONObject();
	}

	private final UsageStrategy _usageStrategy;

}