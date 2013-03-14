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
		super(projectDir, browserURL, new FirefoxDriver(_profile));
	}

	private static FirefoxProfile _profile = new FirefoxProfile();

	static {
		String downloadDir = TestPropsValues.OUTPUT_DIR;

		_profile.setPreference("browser.download.dir", downloadDir);
		_profile.setPreference(
			"browser.download.manager.showWhenStarting", false);
		_profile.setPreference("browser.download.useDownloadDir", true);
		_profile.setPreference("browser.download.folderList", 2);
		_profile.setPreference(
			"browser.helperApps.neverAsk.saveToDisk", "application/zip");
		_profile.setPreference("browser.helperApps.alwaysAsk.force", false);
	}

}