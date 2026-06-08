/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.model;

import org.json.JSONObject;

/**
 * @author Kyle Bischof
 */
public class SalesforcePricebookEntry {

	public SalesforcePricebookEntry(JSONObject jsonObject) {
		_active = jsonObject.optBoolean("IsActive");
		_currencyIsoCode = jsonObject.optString("CurrencyIsoCode");
		_id = jsonObject.optString("Id");
		_product2Id = jsonObject.optString("Product2Id");
		_unitPrice = jsonObject.optDouble("UnitPrice");
	}

	public String getCurrencyIsoCode() {
		return _currencyIsoCode;
	}

	public String getId() {
		return _id;
	}

	public String getProduct2Id() {
		return _product2Id;
	}

	public double getUnitPrice() {
		return _unitPrice;
	}

	public boolean isActive() {
		return _active;
	}

	private final boolean _active;
	private final String _currencyIsoCode;
	private final String _id;
	private final String _product2Id;
	private final double _unitPrice;

}