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

package com.liferay.portalweb.portal.selenium.mouseactions.mousedown;

import com.liferay.portalweb.portal.BaseTestCase;

/**
 * @author Brian Wing Shun Chan
 */
public class MouseDown1Test extends BaseTestCase {
	public void testMouseDown1() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("//section[@id='portlet_58']/header/h1");
		selenium.mouseDown("//section[@id='portlet_58']/header/h1");
		Thread.sleep(1000);
		selenium.waitForVisible("//div[@class='yui3-dd-proxy']");
		selenium.mouseUp("//section[@id='portlet_58']/header/h1");
		Thread.sleep(1000);
		selenium.waitForNotVisible("//div[@class='yui3-dd-proxy']");
	}
}