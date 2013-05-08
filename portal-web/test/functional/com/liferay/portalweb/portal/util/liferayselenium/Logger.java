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

package com.liferay.portalweb.portal.util.liferayselenium;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Brian Wing Shun Chan
 */
public class Logger {

	public Logger(String projectDir) {
		_projectDir = projectDir;

		_webDriver = new FirefoxDriver();

		WebDriver.Options options = _webDriver.manage();

		WebDriver.Window window = options.window();

		window.setPosition(new Point(1000, 50));
		window.setSize(new Dimension(600, 700));

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)_webDriver;

		javascriptExecutor.executeScript("window.name = 'log window';");

		_webDriver.get(
			"file:///" + _projectDir +
				"portal-web/test/functional/com/liferay/portalweb/portal/" +
					"util/liferayselenium/dependencies/Logger.html");
	}

	public void stop() {
		_webDriver.quit();
	}

	private String _projectDir;
	private WebDriver _webDriver;

}