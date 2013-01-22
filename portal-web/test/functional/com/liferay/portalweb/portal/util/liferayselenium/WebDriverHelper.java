/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

/**
 * @author Brian Wing Shun Chan
 */
public class WebDriverHelper {

	public static void setDefaultTimeoutImplicit(WebDriver webDriver) {
		int timeout = TestPropsValues.TIMEOUT_IMPLICIT_WAIT * 1000;

		WebDriverHelper.setTimeoutImplicit(webDriver, String.valueOf(timeout));
	}

	public static void setTimeoutImplicit(WebDriver webDriver, String timeout) {
		WebDriver.Options options = webDriver.manage();

		WebDriver.Timeouts timeouts = options.timeouts();

		timeouts.implicitlyWait(
			GetterUtil.getInteger(timeout), TimeUnit.MILLISECONDS);
	}

}