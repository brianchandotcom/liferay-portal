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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portalweb.portal.util.TestPropsValues;

import java.io.File;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * @author Brian Wing Shun Chan
 */
public class FirefoxWebDriverImpl extends BaseWebDriverImpl {

	public FirefoxWebDriverImpl(String projectDirName, String browserURL) {
		super(projectDirName, browserURL, new FirefoxDriver(_firefoxProfile));
	}

	private static final FirefoxProfile _firefoxProfile = new FirefoxProfile();

	static {
		try {
			File file = new File(StringPool.PERIOD);

			String absolutePath = file.getAbsolutePath();

			if (absolutePath.endsWith(StringPool.PERIOD)) {
				absolutePath = absolutePath.substring(
					0, absolutePath.length() - 1);

				absolutePath = StringUtil.replace(
					absolutePath, StringPool.BACK_SLASH,
					StringPool.FORWARD_SLASH);
			}

			_firefoxProfile.addExtension(
				new File(
					TestPropsValues.SELENIUM_EXECUTABLE_DIR_NAME +
						"addons/jserrorcollector.xpi"));
		}
		catch (Exception e) {
		}

		_firefoxProfile.setPreference(
			"browser.download.dir", TestPropsValues.OUTPUT_DIR_NAME);
		_firefoxProfile.setPreference("browser.download.folderList", 2);
		_firefoxProfile.setPreference(
			"browser.download.manager.showWhenStarting", false);
		_firefoxProfile.setPreference("browser.download.useDownloadDir", true);
		_firefoxProfile.setPreference(
			"browser.helperApps.alwaysAsk.force", false);
		_firefoxProfile.setPreference(
			"browser.helperApps.neverAsk.saveToDisk",
			"application/excel,application/msword,application/pdf," +
				"application/zip,audio/mpeg3,image/jpeg,image/png,text/plain");
		_firefoxProfile.setPreference("dom.max_chrome_script_run_time", 300);
		_firefoxProfile.setPreference("dom.max_script_run_time", 300);

		if (TestPropsValues.MOBILE_DEVICE_ENABLED) {
			_firefoxProfile.setPreference(
				"general.useragent.override",
				TestPropsValues.MOBILE_DEVICE_USER_AGENT);
		}
	}

}