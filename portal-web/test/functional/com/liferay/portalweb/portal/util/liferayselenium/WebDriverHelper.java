/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.util.liferayselenium;

import com.liferay.portalweb.portal.util.TestPropsValues;

import org.openqa.selenium.WebDriver;

/**
 * @author Kenji Heigel
 */
public class WebDriverHelper {

	public static String getLocation(WebDriver webDriver) {
		return webDriver.getCurrentUrl();
	}

	public static void open(WebDriver webDriver, String url) {
		String targetURL = "";

		if (url.startsWith("/")) {
			targetURL = TestPropsValues.PORTAL_URL + url;
		}
		else {
			targetURL = url;
		}

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_IMPLICIT_WAIT) {
				break;
			}

			try {
				webDriver.get(targetURL);

				if (TestPropsValues.BROWSER_TYPE.equals("*iehta") ||
					TestPropsValues.BROWSER_TYPE.equals("*iexplore")) {

					refresh(webDriver);
				}

				if (targetURL.equals(getLocation(webDriver))) {
					break;
				}

				Thread.sleep(1000);
			}
			catch (Exception e) {
			}
		}
	}

	public static void refresh(WebDriver webDriver) {
		WebDriver.Navigation navigation = webDriver.navigate();

		navigation.refresh();
	}

}