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

import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portalweb.portal.util.liferayselenium.ChromeWebDriverImpl;
import com.liferay.portalweb.portal.util.liferayselenium.DefaultSeleniumImpl;
import com.liferay.portalweb.portal.util.liferayselenium.FirefoxWebDriverImpl;
import com.liferay.portalweb.portal.util.liferayselenium.InternetExplorerWebDriverImpl;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;
import com.liferay.portalweb.portal.util.liferayselenium.LoggerHandler;

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
			LiferaySelenium liferaySelenium = new DefaultSeleniumImpl(
				projectDir, PORTAL_URL);

			Class<?> clazz = getClass();

			liferaySelenium.setContext(clazz.getName());

			if (SELENIUM_LOGGER) {
				_selenium = _wrapWithLoggerHandler(liferaySelenium);
			}
			else {
				_selenium = liferaySelenium;
			}
		}
		else if (SELENIUM_IMPLEMENTATION.equals(WebDriver.class.getName())) {
			if (BROWSER_TYPE.equals("*chrome") ||
				BROWSER_TYPE.equals("*firefox")) {

				if (SELENIUM_LOGGER) {
					_selenium = _wrapWithLoggerHandler(
						new FirefoxWebDriverImpl(projectDir, PORTAL_URL));
				}
				else {
					_selenium = new FirefoxWebDriverImpl(projectDir, PORTAL_URL);
				}
			}
			else if (BROWSER_TYPE.equals("*googlechrome")) {
				if (SELENIUM_LOGGER) {
					_selenium = _wrapWithLoggerHandler(
						new ChromeWebDriverImpl(projectDir, PORTAL_URL));
				}
				else {
					_selenium = new ChromeWebDriverImpl(projectDir, PORTAL_URL);
				}
			}
			else if (BROWSER_TYPE.equals("*iehta") ||
					 BROWSER_TYPE.equals("*iexplore")) {

				if (SELENIUM_LOGGER) {
					_selenium = _wrapWithLoggerHandler(
						new InternetExplorerWebDriverImpl(
							projectDir, PORTAL_URL));
				}
				else {
					_selenium = new InternetExplorerWebDriverImpl(
						projectDir, PORTAL_URL);
				}
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

			_selenium.stopLogger();
		}

		_selenium = null;
	}

	private LiferaySelenium _wrapWithLoggerHandler(
		LiferaySelenium liferaySelenium) {

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return (LiferaySelenium)ProxyUtil.newProxyInstance(
			classLoader, new Class<?>[] {LiferaySelenium.class},
			new LoggerHandler(liferaySelenium));
	}

	private static SeleniumUtil _instance = new SeleniumUtil();

	private LiferaySelenium _selenium;
	private String _timestamp;

}