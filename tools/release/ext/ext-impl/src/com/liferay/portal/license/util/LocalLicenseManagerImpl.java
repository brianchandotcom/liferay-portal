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

package com.liferay.portal.license.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.license.LicenseInfo;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.license.LicenseManager;
import com.liferay.portal.util.LicenseUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Amos Fong
 */
public class LocalLicenseManagerImpl
	implements com.liferay.portal.kernel.license.util.LicenseManager {

	public void checkLicense(String productId) {
		LicenseManager.checkBinaryLicense(productId);
	}

	public List<Map<String, String>> getClusterLicenseProperties(
		String clusterNodeId) {

		return LicenseManager.getClusterLicenseProperties(clusterNodeId);
	}

	public String getHostName() {
		return PortalUtil.getComputerName();
	}

	public Set<String> getIpAddresses() {
		return LicenseUtil.getIpAddresses();
	}

	public LicenseInfo getLicenseInfo(String productId) {
		return LicenseManager.getLicenseInfo(productId);
	}

	public List<Map<String, String>> getLicenseProperties() {
		return LicenseManager.getLicenseProperties();
	}

	public Map<String, String> getLicenseProperties(String productId) {
		return LicenseManager.getLicenseProperties(productId);
	}

	public int getLicenseState(Map<String, String> licenseProperties) {
		String productId = licenseProperties.get("productId");

		return getLicenseState(productId);
	}

	public int getLicenseState(String productId) {
		return LicenseManager.getLicenseState(productId);
	}

	public Set<String> getMacAddresses() {
		return LicenseUtil.getMacAddresses();
	}

	public void registerLicense(JSONObject jsonObject) throws Exception {
		String licenseXML = jsonObject.getString("licenseXML");

		LicenseManager.registerLicense(licenseXML);
	}

}