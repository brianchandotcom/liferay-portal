/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.model;

import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class LicenseKey {

	public LicenseKey(JSONObject jsonObject) {
		_accountEntryId = jsonObject.optLong(
			"r_accountEntryToLicenseKey_accountEntryId");
		_accountName = jsonObject.optString("accountName");
		_active = jsonObject.optBoolean("active");
		_additionalInfo = jsonObject.optString("additionalInfo");
		_clusterId = jsonObject.optLong("clusterId");
		_complimentary = jsonObject.optBoolean("complimentary");
		_customExpirationDate = jsonObject.optString("customExpirationDate");
		_description = jsonObject.optString("description");
		_domains = jsonObject.optString("domains");
		_entitlementId = jsonObject.optLong("entitlementId");
		_hostName = jsonObject.optString("hostName");
		_ipAddresses = jsonObject.optString("ipAddresses");
		_key = jsonObject.optString("key");
		_licenseKeyId = jsonObject.getLong("id");
		_licenseName = jsonObject.optString("licenseName");
		_licenseType = jsonObject.optString("licenseType");
		_licenseVersion = jsonObject.optInt("licenseVersion");
		_macAddresses = jsonObject.optString("macAddresses");
		_maxClusterNodes = jsonObject.optInt("maxClusterNodes");
		_maxConcurrentUsers = jsonObject.optLong("maxConcurrentUsers");
		_maxHttpSessions = jsonObject.optInt("maxHttpSessions");
		_maxServers = jsonObject.optInt("maxServers");
		_maxUsers = jsonObject.optLong("maxUsers");
		_name = jsonObject.optString("name");
		_orderId = jsonObject.optString("orderId");
		_owner = jsonObject.optString("owner");
		_productExternalId = jsonObject.optString("productExternalId");
		_productName = jsonObject.optString("productName");
		_productVersion = jsonObject.optString("productVersion");
		_productVersionLabel = jsonObject.optString("productVersionLabel");
		_serverId = jsonObject.optString("serverId");
		_sizing = jsonObject.optString("sizing");
		_startDate = jsonObject.optString("startDate");
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public String getAccountName() {
		return _accountName;
	}

	public String getAdditionalInfo() {
		return _additionalInfo;
	}

	public long getClusterId() {
		return _clusterId;
	}

	public String getCustomExpirationDate() {
		return _customExpirationDate;
	}

	public String getDescription() {
		return _description;
	}

	public String getDomains() {
		return _domains;
	}

	public long getEntitlementId() {
		return _entitlementId;
	}

	public String getHostName() {
		return _hostName;
	}

	public String getIpAddresses() {
		return _ipAddresses;
	}

	public String getKey() {
		return _key;
	}

	public long getLicenseKeyId() {
		return _licenseKeyId;
	}

	public String getLicenseName() {
		return _licenseName;
	}

	public String getLicenseType() {
		return _licenseType;
	}

	public int getLicenseVersion() {
		return _licenseVersion;
	}

	public String getMacAddresses() {
		return _macAddresses;
	}

	public int getMaxClusterNodes() {
		return _maxClusterNodes;
	}

	public long getMaxConcurrentUsers() {
		return _maxConcurrentUsers;
	}

	public int getMaxHttpSessions() {
		return _maxHttpSessions;
	}

	public int getMaxServers() {
		return _maxServers;
	}

	public long getMaxUsers() {
		return _maxUsers;
	}

	public String getName() {
		return _name;
	}

	public String getOrderId() {
		return _orderId;
	}

	public String getOwner() {
		return _owner;
	}

	public String getProductExternalId() {
		return _productExternalId;
	}

	public String getProductName() {
		return _productName;
	}

	public String getProductVersion() {
		return _productVersion;
	}

	public String getProductVersionLabel() {
		return _productVersionLabel;
	}

	public String getServerId() {
		return _serverId;
	}

	public String getSizing() {
		return _sizing;
	}

	public String getStartDate() {
		return _startDate;
	}

	public boolean isActive() {
		return _active;
	}

	public boolean isComplimentary() {
		return _complimentary;
	}

	private final long _accountEntryId;
	private final String _accountName;
	private final boolean _active;
	private final String _additionalInfo;
	private final long _clusterId;
	private final boolean _complimentary;
	private final String _customExpirationDate;
	private final String _description;
	private final String _domains;
	private final long _entitlementId;
	private final String _hostName;
	private final String _ipAddresses;
	private final String _key;
	private final long _licenseKeyId;
	private final String _licenseName;
	private final String _licenseType;
	private final int _licenseVersion;
	private final String _macAddresses;
	private final int _maxClusterNodes;
	private final long _maxConcurrentUsers;
	private final int _maxHttpSessions;
	private final int _maxServers;
	private final long _maxUsers;
	private final String _name;
	private final String _orderId;
	private final String _owner;
	private final String _productExternalId;
	private final String _productName;
	private final String _productVersion;
	private final String _productVersionLabel;
	private final String _serverId;
	private final String _sizing;
	private final String _startDate;

}