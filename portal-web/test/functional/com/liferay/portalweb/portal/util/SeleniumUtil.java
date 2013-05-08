/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portalweb.portal.util.liferayselenium.ChromeWebDriverImpl;
import com.liferay.portalweb.portal.util.liferayselenium.DefaultSeleniumImpl;
import com.liferay.portalweb.portal.util.liferayselenium.FirefoxWebDriverImpl;
import com.liferay.portalweb.portal.util.liferayselenium.InternetExplorerWebDriverImpl;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;
import com.liferay.portalweb.portal.util.liferayselenium.LoggerImpl;

import com.thoughtworks.selenium.Selenium;

import java.io.File;

import org.openqa.selenium.WebDriver;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleniumUtil extends TestPropsValues {

	public static LiferaySelenium getSelenium() {
		return _instance._getSelenium();
	}

	public static String getTimestamp() {
		return _instance._getTimestamp();
	}

	public static void startSelenium() {
		_instance._startSelenium();
	}

	public static void stopSelenium() {
		_instance._stopSelenium();
	}

	private SeleniumUtil() {
		_timestamp = Time.getTimestamp();
	}

	private LiferaySelenium _getSelenium() {
		if (_selenium == null) {
			_startSelenium();
		}

		return _selenium;
	}

	private String _getTimestamp() {
		return _timestamp;
	}

	private void _startSelenium() {
		File file = new File(StringPool.PERIOD);

		String absolutePath = file.getAbsolutePath();

		String projectDir = absolutePath.substring(
			0, absolutePath.length() - 1);

		if (SELENIUM_IMPLEMENTATION.equals(Selenium.class.getName())) {
			DefaultSeleniumImpl defaultSelenium = new DefaultSeleniumImpl(
				projectDir, PORTAL_URL);

			Class<?> clazz = getClass();

			defaultSelenium.setContext(clazz.getName());

			_selenium = new LoggerImpl(projectDir, defaultSelenium);
		}
		else if (SELENIUM_IMPLEMENTATION.equals(WebDriver.class.getName())) {
			if (BROWSER_TYPE.equals("*chrome") ||
				BROWSER_TYPE.equals("*firefox")) {

				FirefoxWebDriverImpl firefoxWebDriver =
					new FirefoxWebDriverImpl(projectDir, PORTAL_URL);

				_selenium = new LoggerImpl(projectDir, firefoxWebDriver);
			}
			else if (BROWSER_TYPE.equals("*googlechrome")) {
				ChromeWebDriverImpl chromeWebDriver = new ChromeWebDriverImpl(
					projectDir, PORTAL_URL);

				_selenium = new LoggerImpl(projectDir, chromeWebDriver);

			}
			else if (BROWSER_TYPE.equals("*iehta") ||
					 BROWSER_TYPE.equals("*iexplore")) {

				InternetExplorerWebDriverImpl internetExplorerWebDriver =
					new InternetExplorerWebDriverImpl(projectDir, PORTAL_URL);

				_selenium = new LoggerImpl(
					projectDir, internetExplorerWebDriver);
			}
			else {
				throw new RuntimeException(
					"Invalid browser type " + BROWSER_TYPE);
			}
		}
	}

	private void _stopSelenium() {
		if (_selenium != null) {
			_selenium.stop();
		}

		_selenium = null;
	}

	private static SeleniumUtil _instance = new SeleniumUtil();

	private LiferaySelenium _selenium;
	private String _timestamp;

}