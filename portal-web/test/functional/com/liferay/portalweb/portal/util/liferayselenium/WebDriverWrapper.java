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

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Brian Wing Shun Chan
 */
public class WebDriverWrapper implements WebDriver {

	public WebDriverWrapper(WebDriver webDriver) {
		_webDriver = webDriver;
	}

	@Override
	public void close() {
		_webDriver.close();
	}

	@Override
	public WebElement findElement(By by) {
		return _webDriver.findElement(by);
	}

	@Override
	public List<WebElement> findElements(By by) {
		return _webDriver.findElements(by);
	}

	@Override
	public void get(String url) {
		_webDriver.get(url);
	}

	@Override
	public String getCurrentUrl() {
		return _webDriver.getCurrentUrl();
	}

	@Override
	public String getPageSource() {
		return _webDriver.getPageSource();
	}

	@Override
	public String getTitle() {
		return _webDriver.getTitle();
	}

	@Override
	public String getWindowHandle() {
		return _webDriver.getWindowHandle();
	}

	@Override
	public Set<String> getWindowHandles() {
		return _webDriver.getWindowHandles();
	}

	public WebDriver getWrappedWebDriver() {
		return _webDriver;
	}

	@Override
	public Options manage() {
		return _webDriver.manage();
	}

	@Override
	public Navigation navigate() {
		return _webDriver.navigate();
	}

	@Override
	public void quit() {
		_webDriver.quit();
	}

	@Override
	public TargetLocator switchTo() {
		return _webDriver.switchTo();
	}

	private final WebDriver _webDriver;

}