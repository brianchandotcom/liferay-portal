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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portalweb.portal.util.TestPropsValues;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * @author Kenji Heigel
 */
public class WebDriverHelper {

	public static String getLocation(WebDriver webDriver) {
		return webDriver.getCurrentUrl();
	}

	public static boolean isElementPresent(
		WebDriver webDriver, String locator) {

		List<WebElement> webElements = getWebElements(webDriver, locator, "1");

		return !webElements.isEmpty();
	}

	public void makeVisible(String locator) {
		WebElement bodyWebElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)webDriver;

		StringBundler sb = new StringBundler(4);

		sb.append("var element = arguments[0];");
		sb.append("element.style.cssText = 'display:inline !important';");
		sb.append("element.style.overflow = 'visible';");
		sb.append("element.style.minHeight = '1px';");
		sb.append("element.style.minWidth = '1px';");
		sb.append("element.style.opacity = '1';");
		sb.append("element.style.visibility = 'visible';");

		WebElement locatorWebElement = getWebElement(locator);

		javascriptExecutor.executeScript(sb.toString(), locatorWebElement);
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

	public static void setDefaultTimeoutImplicit(WebDriver webDriver) {
		int timeout = TestPropsValues.TIMEOUT_IMPLICIT_WAIT * 1000;

		setTimeoutImplicit(webDriver, String.valueOf(timeout));
	}

	public static void setTimeoutImplicit(WebDriver webDriver, String timeout) {
		WebDriver.Options options = webDriver.manage();

		WebDriver.Timeouts timeouts = options.timeouts();

		timeouts.implicitlyWait(
			GetterUtil.getInteger(timeout), TimeUnit.MILLISECONDS);
	}

	protected static WebElement getWebElement(
		WebDriver webDriver, String locator) {

		return getWebElement(webDriver, locator, null);
	}

	protected static WebElement getWebElement(
		WebDriver webDriver, String locator, String timeout) {

		List<WebElement> webElements = getWebElements(
			webDriver, locator, timeout);

		if (!webElements.isEmpty()) {
			return webElements.get(0);
		}

		return null;
	}

	protected static List<WebElement> getWebElements(
		WebDriver webDriver, String locator) {

		return getWebElements(webDriver, locator, null);
	}

	protected static List<WebElement> getWebElements(
		WebDriver webDriver, String locator, String timeout) {

		if (timeout != null) {
			setTimeoutImplicit(webDriver, timeout);
		}

		try {
			if (locator.startsWith("//")) {
				return webDriver.findElements(By.xpath(locator));
			}
			else if (locator.startsWith("class=")) {
				locator = locator.substring(6);

				return webDriver.findElements(By.className(locator));
			}
			else if (locator.startsWith("css=")) {
				locator = locator.substring(4);

				return webDriver.findElements(By.cssSelector(locator));
			}
			else if (locator.startsWith("link=")) {
				locator = locator.substring(5);

				return webDriver.findElements(By.linkText(locator));
			}
			else if (locator.startsWith("name=")) {
				locator = locator.substring(5);

				return webDriver.findElements(By.name(locator));
			}
			else if (locator.startsWith("tag=")) {
				locator = locator.substring(4);

				return webDriver.findElements(By.tagName(locator));
			}
			else if (locator.startsWith("xpath=") ||
					 locator.startsWith("xPath=")) {

				locator = locator.substring(6);

				return webDriver.findElements(By.xpath(locator));
			}
			else {
				return webDriver.findElements(By.id(locator));
			}
		}
		finally {
			if (timeout != null) {
				setDefaultTimeoutImplicit(webDriver);
			}
		}
	}

}