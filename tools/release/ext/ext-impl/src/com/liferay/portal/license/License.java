/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.license;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 * @author Amos Fong
 */
public class License implements Comparable<License>, Serializable {

	public License(
		String accountEntryName, String owner, String description,
		String productEntryName, String productId, String productVersion,
		String licenseEntryName, String licenseEntryType, String licenseVersion,
		Date startDate, Date expirationDate, int maxClusterNodes,
		int maxServers, int maxHttpSessions, long maxConcurrentUsers,
		long maxUsers, String instanceSize, String[] hostNames,
		String[] ipAddresses, String[] macAddresses, String[] serverIds,
		String key) {

		_accountEntryName = accountEntryName;
		_owner = owner;
		_description = description;
		_productEntryName = productEntryName;
		_productId = productId;
		_productVersion = productVersion;
		_licenseEntryName = licenseEntryName;
		_licenseEntryType = licenseEntryType;
		_licenseVersion = licenseVersion;
		_startDate = startDate;
		_expirationDate = expirationDate;
		_maxClusterNodes = maxClusterNodes;
		_maxServers = maxServers;
		_maxHttpSessions = maxHttpSessions;
		_maxConcurrentUsers = maxConcurrentUsers;
		_maxUsers = maxUsers;
		_instanceSize = instanceSize;
		_hostNames = hostNames;
		_ipAddresses = ipAddresses;
		_macAddresses = macAddresses;
		_serverIds = serverIds;
		_key = key;
	}

	public int compareTo(License license) {
		boolean expired = isExpired();

		if (!Objects.equals(expired, license.isExpired())) {
			if (expired) {
				return -1;
			}

			return 1;
		}

		int result =
			license.getLicenseEntryTypeRank() - getLicenseEntryTypeRank();

		if (result != 0) {
			return result;
		}

		result = _expirationDate.compareTo(license.getExpirationDate());

		if (result != 0) {
			return result;
		}

		return _key.compareTo(license.getKey());
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof License)) {
			return false;
		}

		License license = (License)obj;

		if (Objects.equals(_key, license.getKey())) {
			return true;
		}

		return false;
	}

	public String getAccountEntryName() {
		return _accountEntryName;
	}

	public String getDescription() {
		return _description;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public String[] getHostNames() {
		return _hostNames;
	}

	public String getInstanceSize() {
		return _instanceSize;
	}

	public String[] getIpAddresses() {
		return _ipAddresses;
	}

	public String getKey() {
		return _key;
	}

	public long getLastAccessedTime() {
		return _lastAccessedTime;
	}

	public String getLicenseEntryName() {
		return _licenseEntryName;
	}

	public String getLicenseEntryType() {
		return _licenseEntryType;
	}

	public int getLicenseEntryTypeRank() {
		if (_licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER)) {
			return 8;
		}
		else if (_licenseEntryType.equals(
					LicenseConstants.TYPE_DEVELOPER_CLUSTER)) {

			return 7;
		}
		else if (_licenseEntryType.equals(LicenseConstants.TYPE_ENTERPRISE)) {
			return 2;
		}
		else if (_licenseEntryType.equals(LicenseConstants.TYPE_LIMITED)) {
			return 5;
		}
		else if (_licenseEntryType.equals(LicenseConstants.TYPE_OEM)) {
			return 1;
		}
		else if (_licenseEntryType.equals(LicenseConstants.TYPE_PER_USER)) {
			return 6;
		}
		else if (_licenseEntryType.equals(LicenseConstants.TYPE_PRODUCTION)) {
			return 4;
		}
		else if (_licenseEntryType.equals(
					LicenseConstants.TYPE_VIRTUAL_CLUSTER)) {

			return 3;
		}

		return 10;
	}

	public String getLicenseVersion() {
		return _licenseVersion;
	}

	public String[] getMacAddresses() {
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

	public int getMaxProcessorCores() {
		if (Objects.equals(_instanceSize, "Sizing 1")) {
			return 8;
		}
		else if (Objects.equals(_instanceSize, "Sizing 2")) {
			return 12;
		}
		else if (Objects.equals(_instanceSize, "Sizing 3")) {
			return 16;
		}
		else if (Objects.equals(_instanceSize, "Sizing 4")) {
			return Integer.MAX_VALUE;
		}

		return 0;
	}

	public int getMaxServers() {
		return _maxServers;
	}

	public long getMaxUsers() {
		return _maxUsers;
	}

	public String getOwner() {
		return _owner;
	}

	public String getProductEntryName() {
		return _productEntryName;
	}

	public String getProductId() {
		return _productId;
	}

	public String getProductVersion() {
		return _productVersion;
	}

	public Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<>();

		properties.put("version", _licenseVersion);

		if (!_licenseEntryType.equals(LicenseConstants.TYPE_TRIAL)) {
			properties.put("startDate", String.valueOf(_startDate.getTime()));
		}

		properties.put("description", _description);
		properties.put("owner", _owner);
		properties.put("type", _licenseEntryType);

		long lifetime = _expirationDate.getTime() - _startDate.getTime();

		if (_licenseEntryType.equals(LicenseConstants.TYPE_TRIAL)) {
			properties.put("lifetime", String.valueOf(lifetime));
		}
		else {
			properties.put(
				"expirationDate",
				String.valueOf(_startDate.getTime() + lifetime));
		}

		properties.put("productVersion", _productVersion);

		if (_productId.equals(LicenseConstants.PRODUCT_ID_PORTAL)) {
			properties.put("accountEntryName", _accountEntryName);
			properties.put("licenseEntryName", _licenseEntryName);
		}
		else {
			properties.put("productId", _productId);
		}

		properties.put("productEntryName", _productEntryName);

		if (_licenseEntryType.equals(LicenseConstants.TYPE_VIRTUAL_CLUSTER)) {
			properties.put("maxClusterNodes", String.valueOf(_maxClusterNodes));
		}

		if ((GetterUtil.getInteger(_licenseVersion) >= 4) &&
			(_licenseEntryType.equals(LicenseConstants.TYPE_LIMITED) ||
			 _licenseEntryType.equals(LicenseConstants.TYPE_PRODUCTION))) {

			properties.put("maxServers", String.valueOf(_maxServers));
		}

		if (_licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER) ||
			_licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER_CLUSTER)) {

			properties.put("maxHttpSessions", String.valueOf(_maxHttpSessions));
		}

		if (_licenseEntryType.equals(LicenseConstants.TYPE_PER_USER)) {
			if (_maxConcurrentUsers > 0) {
				properties.put(
					"maxConcurrentUsers", String.valueOf(_maxConcurrentUsers));
			}

			if (_maxUsers > 0) {
				properties.put("maxUsers", String.valueOf(_maxUsers));
			}
		}

		if (Validator.isNotNull(_instanceSize)) {
			properties.put("instanceSize", _instanceSize);
		}

		if (_licenseEntryType.equals(LicenseConstants.TYPE_LIMITED) ||
			_licenseEntryType.equals(LicenseConstants.TYPE_PER_USER) ||
			_licenseEntryType.equals(LicenseConstants.TYPE_PRODUCTION)) {

			properties.put("hostNames", StringUtil.merge(_hostNames));
			properties.put("ipAddresses", StringUtil.merge(_ipAddresses));
			properties.put(
				"macAddresses",
				StringUtil.replace(
					StringUtil.merge(_macAddresses), StringPool.DASH,
					StringPool.COLON));
			properties.put("serverIds", StringUtil.merge(_serverIds));
		}

		return properties;
	}

	public String[] getServerIds() {
		return _serverIds;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public int hashCode() {
		return _key.hashCode();
	}

	public boolean isExpired() {
		long now = System.currentTimeMillis();

		if ((now - (Time.DAY * 2)) > _expirationDate.getTime()) {
			return true;
		}

		return false;
	}

	public void setAccountEntryName(String accountEntryName) {
		_accountEntryName = accountEntryName;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public void setHostNames(String[] hostNames) {
		_hostNames = hostNames;
	}

	public void setInstanceSize(String instanceSize) {
		_instanceSize = instanceSize;
	}

	public void setIpAddresses(String[] ipAddresses) {
		_ipAddresses = ipAddresses;
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setLastAccessedTime(long lastAccessedTime) {
		_lastAccessedTime = lastAccessedTime;
	}

	public void setLicenseEntryName(String licenseEntryName) {
		_licenseEntryName = licenseEntryName;
	}

	public void setLicenseEntryType(String licenseEntryType) {
		_licenseEntryType = licenseEntryType;
	}

	public void setLicenseVersion(String licenseVersion) {
		_licenseVersion = licenseVersion;
	}

	public void setMacAddresses(String[] macAddresses) {
		_macAddresses = macAddresses;
	}

	public void setMaxConcurrentUsers(long maxConcurrentUsers) {
		_maxConcurrentUsers = maxConcurrentUsers;
	}

	public void setMaxHttpSessions(int maxHttpSessions) {
		_maxHttpSessions = maxHttpSessions;
	}

	public void setMaxServers(int maxServers) {
		_maxServers = maxServers;
	}

	public void setMaxUsers(int maxUsers) {
		_maxUsers = maxUsers;
	}

	public void setOwner(String owner) {
		_owner = owner;
	}

	public void setProductEntryName(String productEntryName) {
		_productEntryName = productEntryName;
	}

	public void setProductId(String productId) {
		_productId = productId;
	}

	public void setProductVersion(String productVersion) {
		_productVersion = productVersion;
	}

	public void setServerIds(String[] serverIds) {
		_serverIds = serverIds;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public String toString() {
		Map<String, String> properties = getProperties();

		return properties.toString();
	}

	private static final long serialVersionUID = 2779848304210680862L;

	private String _accountEntryName;
	private String _description;
	private Date _expirationDate;
	private String[] _hostNames;
	private String _instanceSize;
	private String[] _ipAddresses;
	private String _key;
	private long _lastAccessedTime;
	private String _licenseEntryName;
	private String _licenseEntryType;
	private String _licenseVersion;
	private String[] _macAddresses;
	private final int _maxClusterNodes;
	private long _maxConcurrentUsers;
	private int _maxHttpSessions;
	private int _maxServers;
	private long _maxUsers;
	private String _owner;
	private String _productEntryName;
	private String _productId;
	private String _productVersion;
	private String[] _serverIds;
	private Date _startDate;

}