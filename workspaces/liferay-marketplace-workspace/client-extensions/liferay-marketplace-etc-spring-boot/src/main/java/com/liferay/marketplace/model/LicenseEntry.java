/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.model;

import org.json.JSONObject;

/**
 * @author Caleb Hall
 */
public class LicenseEntry {

	public static LicenseEntry fromJson(JSONObject jsonObject) {
		return new LicenseEntry(
			jsonObject.optString("description"),
			jsonObject.optString("hostName"),
			jsonObject.optString("ipAddresses"),
			jsonObject.optString("macAddresses"), jsonObject.optLong("orderId"),
			jsonObject.optLong("productId"),
			jsonObject.optString("productPurchaseKey"),
			jsonObject.optString("productVersion"));
	}

	public LicenseEntry(
		String description, String hostName, String ipAddresses,
		String macAddresses, Long orderId, Long productId,
		String productPurchaseKey, String productVersion) {

		_description = description;
		_hostName = hostName;
		_ipAddresses = ipAddresses;
		_macAddresses = macAddresses;
		_orderId = orderId;
		_productId = productId;
		_productPurchaseKey = productPurchaseKey;
		_productVersion = productVersion;
	}

	public String getDescription() {
		return _description;
	}

	public String getHostName() {
		return _hostName;
	}

	public String getIpAddresses() {
		return _ipAddresses;
	}

	public String getMacAddresses() {
		return _macAddresses;
	}

	public Long getOrderId() {
		return _orderId;
	}

	public Long getProductId() {
		return _productId;
	}

	public String getProductPurchaseKey() {
		return _productPurchaseKey;
	}

	public String getProductVersion() {
		return _productVersion;
	}

	public void setProductId(Long productId) {
		_productId = productId;
	}

	public void setProductPurchaseKey(String productPurchaseKey) {
		_productPurchaseKey = productPurchaseKey;
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"description", _description
		).put(
			"hostName", _hostName
		).put(
			"ipAddresses", _ipAddresses
		).put(
			"macAddresses", _macAddresses
		).put(
			"productId", _productId
		).put(
			"productPurchaseKey", _productPurchaseKey
		).put(
			"productVersion", _productVersion
		);

		return jsonObject;
	}

	private final String _description;
	private final String _hostName;
	private final String _ipAddresses;
	private final String _macAddresses;
	private final Long _orderId;
	private Long _productId;
	private String _productPurchaseKey;
	private final String _productVersion;

}