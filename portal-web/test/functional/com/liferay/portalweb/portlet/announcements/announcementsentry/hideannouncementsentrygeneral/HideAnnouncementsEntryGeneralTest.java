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
		assertFalse(selenium.isVisible("//span[@class='entry-scope']"));
		assertFalse(selenium.isVisible("//p"));
		assertEquals(RuntimeVariables.replace("Show"),
			selenium.getText(
				"//td[@class='control-entry']/a[contains(.,'Show')]"));
		selenium.clickAt("//td[@class='control-entry']/a[contains(.,'Show')]",
			RuntimeVariables.replace("Show"));
		selenium.waitForText("//td[@class='control-entry']/a[contains(.,'Hide')]",
			"Hide");
		assertEquals(RuntimeVariables.replace("Hide"),
			selenium.getText(
				"//td[@class='control-entry']/a[contains(.,'Hide')]"));
		assertTrue(selenium.isVisible("//span[@class='entry-scope']"));
		assertTrue(selenium.isVisible("//p"));
		selenium.clickAt("//td[@class='control-entry']/a[contains(.,'Hide')]",
			RuntimeVariables.replace("Hide"));
		selenium.waitForText("//td[@class='control-entry']/a[contains(.,'Show')]",
			"Show");
		assertEquals(RuntimeVariables.replace("Show"),
			selenium.getText(
				"//td[@class='control-entry']/a[contains(.,'Show')]"));
		assertFalse(selenium.isVisible("//span[@class='entry-scope']"));
		assertFalse(selenium.isVisible("//p"));
	}
}