/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.demo.internal.data.creator;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.DeviceType;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;

/**
 * @author Matthew Kong
 */
public class PageContextsDataCreator extends DataCreator {

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		String userAgentString = internet.userAgentAny();

		UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);

		ObjectValuePair<String, String> page = _pages.get(_count++);

		OperatingSystem operatingSystem = userAgent.getOperatingSystem();

		return HashMapBuilder.<String, Object>put(
			"browserName",
			() -> {
				Browser browser = userAgent.getBrowser();

				return browser.getName();
			}
		).put(
			"canonicalUrl", page.getKey()
		).put(
			"contentLanguageId", "en-US"
		).put(
			"crawler", "False"
		).put(
			"deviceType",
			() -> {
				String deviceName = null;

				DeviceType deviceType = operatingSystem.getDeviceType();

				if (deviceType == DeviceType.COMPUTER) {
					deviceName = "Desktop";
				}
				else {
					deviceName = deviceType.getName();
				}

				return deviceName;
			}
		).put(
			"languageId", "en-US"
		).put(
			"platformName", operatingSystem.getName()
		).put(
			"referrer", page.getKey()
		).put(
			"title", page.getValue()
		).put(
			"url", page.getKey()
		).put(
			"userAgent", userAgentString
		).build();
	}

	private static int _count;

	private final List<ObjectValuePair<String, String>> _pages = Arrays.asList(
		new ObjectValuePair<>(
			"https://www.beryl.com", "Beryl Agriculture Wholesale"),
		new ObjectValuePair<>(
			"https://www.beryl.com/account/orderhistory", "Order History"),
		new ObjectValuePair<>(
			"https://www.beryl.com/blogs/loyaltyrewards",
			"Beryl Loyalty Rewards Program"),
		new ObjectValuePair<>(
			"https://www.beryl.com/delivery",
			"Delivery | Beryl Agriculture Wholesale"),
		new ObjectValuePair<>(
			"https://www.beryl.com/design/indoorplanning",
			"Indoor Urban Farming | Beryl Agriculture Wholesale"),
		new ObjectValuePair<>(
			"https://www.beryl.com/products/blog/best-irrigation-strategies",
			"Best Irrigation Strategies by Crop Type | Beryl Blogs"),
		new ObjectValuePair<>(
			"https://www.beryl.com/products/commercial/irrigation",
			"Irrigation | Beryl Agriculture Wholesale"),
		new ObjectValuePair<>(
			"https://www.beryl.com/products/commercial/irrigation/FF-2100",
			"FF-2100 Center Pivot Irrigator | Beryl Agriculture Wholesale"),
		new ObjectValuePair<>(
			"https://www.beryl.com/products/vehicles",
			"Vehicle Leasing | Beryl Agriculture Wholesale"),
		new ObjectValuePair<>(
			"https://www.beryl.com/recalls",
			"Recall Information | Beryl Agriculture Wholesale"));

}