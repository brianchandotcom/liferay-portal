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

package com.liferay.portalweb.portlet.announcements.announcementsentry.hideannouncementsentrygeneral;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class HideAnnouncementsEntryGeneralTest extends BaseTestCase {
	public void testHideAnnouncementsEntryGeneral() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Announcements Test Page",
			RuntimeVariables.replace("Announcements Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/store.js')]");
		assertEquals(RuntimeVariables.replace("Show"),
			selenium.getText("//a[contains(.,'Show')]"));
		selenium.clickAt("//a[contains(.,'Show')]",
			RuntimeVariables.replace("Show"));
		selenium.waitForText("//a[contains(.,'Hide')]", "Hide");
		assertEquals(RuntimeVariables.replace("Hide"),
			selenium.getText("//a[contains(.,'Hide')]"));
		assertTrue(selenium.isVisible("//p"));
		selenium.clickAt("//a[contains(.,'Hide')]",
			RuntimeVariables.replace("Hide"));
		selenium.waitForText("//a[contains(.,'Show')]", "Show");
		assertEquals(RuntimeVariables.replace("Show"),
			selenium.getText("//a[contains(.,'Show')]"));
		assertFalse(selenium.isVisible("//p"));
	}
}