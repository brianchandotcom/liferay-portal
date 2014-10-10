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

import io.appium.java_client.AppiumDriver;

import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author Kwang Lee
 */
public class AppiumMobileDriverImpl extends BaseMobileDriverImpl {

	public AppiumMobileDriverImpl(String projectDirName, String browserURL) {
		super(
			projectDirName, browserURL,
			new AppiumDriver(_url, _desiredCapabilities));
	}

	private static final DesiredCapabilities _desiredCapabilities;
	private static final URL _url;

	static {
		_desiredCapabilities = DesiredCapabilities.android();

		_desiredCapabilities.setCapability("browserName", "Browser");
		_desiredCapabilities.setCapability("deviceName", "deviceName");
		_desiredCapabilities.setCapability("platformName", "Android");
		_desiredCapabilities.setCapability("platformVersion", "4.4");

		URL url = null;

		try {
			url = new URL("http://0.0.0.0:4723/wd/hub/");
		}
		catch (Exception e) {
		}

		_url = url;
	}

}