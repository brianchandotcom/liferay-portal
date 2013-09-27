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

import com.liferay.portalweb.portal.util.TestPropsValues;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * @author Brian Wing Shun Chan
 */
public class FirefoxWebDriverImpl extends BaseWebDriverImpl {

	public FirefoxWebDriverImpl(String projectDir, String browserURL) {
		super(projectDir, browserURL, new FirefoxDriver(_firefoxProfile));
	}

	private static FirefoxProfile _firefoxProfile = new FirefoxProfile();

	static {
		_firefoxProfile.setPreference(
			"browser.download.dir", TestPropsValues.OUTPUT_DIR);
		_firefoxProfile.setPreference("browser.download.folderList", 2);
		_firefoxProfile.setPreference(
			"browser.download.manager.showWhenStarting", false);
		_firefoxProfile.setPreference("browser.download.useDownloadDir", true);
		_firefoxProfile.setPreference(
			"browser.helperApps.alwaysAsk.force", false);
		_firefoxProfile.setPreference(
			"browser.helperApps.neverAsk.saveToDisk", "application/zip");
		_firefoxProfile.setPreference("dom.max_chrome_script_run_time", 300);
		_firefoxProfile.setPreference("dom.max_script_run_time", 300);
	}

}