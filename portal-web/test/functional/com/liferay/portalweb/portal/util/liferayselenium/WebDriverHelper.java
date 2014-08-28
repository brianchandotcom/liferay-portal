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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portalweb.portal.util.TestPropsValues;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Kenji Heigel
 */
public class WebDriverHelper {

	public static String getLocation(WebDriver webDriver) {
		return webDriver.getCurrentUrl();
	}

	public boolean isElementPresent(String locator) {
		List<WebElement> webElements = getWebElements(locator, "1");

		return !webElements.isEmpty();
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

	public void setDefaultTimeoutImplicit() {
		int timeout = TestPropsValues.TIMEOUT_IMPLICIT_WAIT * 1000;

		setTimeoutImplicit(String.valueOf(timeout));
	}

	public void setTimeoutImplicit(String timeout) {
		WebDriver.Options options = manage();

		WebDriver.Timeouts timeouts = options.timeouts();

		timeouts.implicitlyWait(
			GetterUtil.getInteger(timeout), TimeUnit.MILLISECONDS);
	}

	protected WebElement getWebElement(String locator) {
		return getWebElement(locator, null);
	}

	protected WebElement getWebElement(String locator, String timeout) {
		List<WebElement> webElements = getWebElements(locator, timeout);

		if (!webElements.isEmpty()) {
			return webElements.get(0);
		}

		return null;
	}

	protected List<WebElement> getWebElements(String locator) {
		return getWebElements(locator, null);
	}

	protected List<WebElement> getWebElements(String locator, String timeout) {
		if (timeout != null) {
			setTimeoutImplicit(timeout);
		}

		try {
			if (locator.startsWith("//")) {
				return findElements(By.xpath(locator));
			}
			else if (locator.startsWith("class=")) {
				locator = locator.substring(6);

				return findElements(By.className(locator));
			}
			else if (locator.startsWith("css=")) {
				locator = locator.substring(4);

				return findElements(By.cssSelector(locator));
			}
			else if (locator.startsWith("link=")) {
				locator = locator.substring(5);

				return findElements(By.linkText(locator));
			}
			else if (locator.startsWith("name=")) {
				locator = locator.substring(5);

				return findElements(By.name(locator));
			}
			else if (locator.startsWith("tag=")) {
				locator = locator.substring(4);

				return findElements(By.tagName(locator));
			}
			else if (locator.startsWith("xpath=") ||
					 locator.startsWith("xPath=")) {

				locator = locator.substring(6);

				return findElements(By.xpath(locator));
			}
			else {
				return findElements(By.id(locator));
			}
		}
		finally {
			if (timeout != null) {
				setDefaultTimeoutImplicit();
			}
		}
	}

}