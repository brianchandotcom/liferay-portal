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

package com.liferay.portal.license.validator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.license.License;
import com.liferay.portal.license.LicenseConstants;
import com.liferay.portal.util.LicenseUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Tina Tian
 * @author Amos Fong
 */
public class LicenseValidator {

	public void doValidateVersion(License license) throws Exception {
	}

	public String[] getValidTypes() {
		return _VALID_TYPES;
	}

	public void setNextValidator(LicenseValidator nextValidator) {
		_nextValidator = nextValidator;
	}

	public void validate(License license) throws Exception {
		String[] validTypes = getValidTypes();

		if (ArrayUtil.contains(validTypes, license.getLicenseEntryType())) {
			int version = GetterUtil.getInteger(license.getLicenseVersion());

			if (version < 3) {
				throw new Exception(
					"License version " + version + " is not supported.");
			}
			else {
				doValidateVersion(license);
			}
		}

		if (_nextValidator != null) {
			_nextValidator.validate(license);
		}
	}

	protected boolean isClustered() {
		if (PropsValues.CLUSTER_LINK_ENABLED) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void validateServer(License license) throws Exception {
		StringBundler sb = new StringBundler(5);

		String errorMessage = _validateHostNames(license.getHostNames());

		if (errorMessage == null) {
			return;
		}

		sb.append(errorMessage);
		sb.append(StringPool.COMMA_AND_SPACE);

		errorMessage = _validateIpAddresses(license.getIpAddresses());

		if (errorMessage == null) {
			return;
		}

		sb.append(errorMessage);
		sb.append(StringPool.COMMA_AND_SPACE);

		errorMessage = _validateMacAddresses(license.getMacAddresses());

		if (errorMessage == null) {
			return;
		}

		sb.append(errorMessage);

		throw new Exception(sb.toString());
	}

	private String _validateHostNames(String[] hostNames) {
		List<String> allowedHostNames = new ArrayList<>();

		for (String hostName : hostNames) {
			allowedHostNames.add(hostName.toLowerCase());
		}

		if ((allowedHostNames == null) || allowedHostNames.isEmpty()) {
			return "Your license does not have any allowed host names";
		}

		String localHostName = PortalUtil.getComputerName();

		if (!allowedHostNames.contains(localHostName.toLowerCase())) {
			return
				"Host name matching failed, allowed host names: " +
					StringUtil.merge(hostNames);
		}

		return null;
	}

	private String _validateIpAddresses(String[] ipAddresses) {
		List<String> allowedIpAddresses = ListUtil.fromArray(ipAddresses);

		if ((allowedIpAddresses == null) || allowedIpAddresses.isEmpty()) {
			return "Your license does not have any allowed IP addresses";
		}

		Set<String> localIpAddresses = new HashSet<>(
			LicenseUtil.getIpAddresses());

		if (localIpAddresses.isEmpty()) {
			return "Unable to read local server's IP addresses";
		}

		localIpAddresses.retainAll(allowedIpAddresses);

		if (localIpAddresses.isEmpty()) {
			return
				"IP address matching failed, allowed IP addresses: " +
					allowedIpAddresses;
		}

		return null;
	}

	private String _validateMacAddresses(String[] macAddresses)
		throws Exception {

		List<String> allowedMacAddresses = new ArrayList<>();

		for (String macAddress : macAddresses) {
			allowedMacAddresses.add(macAddress.toLowerCase());
		}

		if ((allowedMacAddresses == null) || allowedMacAddresses.isEmpty()) {
			return "Your license does not have any allowed MAC addresses";
		}

		Set<String> localMacAddresses = new HashSet<>(
			LicenseUtil.getMacAddresses());

		if (localMacAddresses.isEmpty()) {
			return "Unable to read local server's MAC addresses";
		}

		localMacAddresses.retainAll(allowedMacAddresses);

		if (localMacAddresses.isEmpty()) {
			return
				"MAC address matching failed, allowed MAC addresses: " +
					allowedMacAddresses;
		}

		return null;
	}

	private LicenseValidator _nextValidator;

	private static final String[] _VALID_TYPES = {
		LicenseConstants.TYPE_DEVELOPER,
		LicenseConstants.TYPE_DEVELOPER_CLUSTER,
		LicenseConstants.TYPE_ENTERPRISE, LicenseConstants.TYPE_LIMITED,
		LicenseConstants.TYPE_OEM, LicenseConstants.TYPE_PER_USER,
		LicenseConstants.TYPE_PRODUCTION, LicenseConstants.TYPE_VIRTUAL_CLUSTER
	};

}