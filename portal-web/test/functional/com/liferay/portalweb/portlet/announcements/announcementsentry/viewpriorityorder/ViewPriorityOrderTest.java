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

package com.liferay.portalweb.portlet.announcements.announcementsentry.viewpriorityorder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPriorityOrderTest extends BaseTestCase {
	public void testViewPriorityOrder() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Announcements Test Page",
			RuntimeVariables.replace("Announcements Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("link=Entries"));
		assertTrue(selenium.isVisible("link=Manage Entries"));
		assertEquals(RuntimeVariables.replace(
				"Announcements Entry Title Priority Important"),
			selenium.getText("xPath=(//h3[@class='entry-title']/a)[1]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("xPath=(//td[@class='edit-entry']/span/a/span)[1]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"xPath=(//td[@class='delete-entry']/span/a/span)[1]"));
		assertEquals(RuntimeVariables.replace("Mark as Read"),
			selenium.getText("xPath=(//td[@class='control-entry']/a)[1]"));
		assertEquals(RuntimeVariables.replace("General"),
			selenium.getText("xpath=(//span[@class='entry-scope'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"Announcements Entry Content Priority Important"),
			selenium.getText(
				"xpath=(//div[@class=' entry-content entry-type-general']/p)[1]"));
		assertEquals(RuntimeVariables.replace(
				"Announcements Entry Title Priority Normal"),
			selenium.getText("xPath=(//h3[@class='entry-title']/a)[2]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("xPath=(//td[@class='edit-entry']/span/a/span)[2]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"xPath=(//td[@class='delete-entry']/span/a/span)[2]"));
		assertEquals(RuntimeVariables.replace("Mark as Read"),
			selenium.getText("xPath=(//td[@class='control-entry']/a)[2]"));
		assertEquals(RuntimeVariables.replace("General"),
			selenium.getText("xpath=(//span[@class='entry-scope'])[2]"));
		assertEquals(RuntimeVariables.replace(
				"Announcements Entry Content Priority Normal"),
			selenium.getText(
				"xpath=(//div[@class=' entry-content entry-type-general']/p)[2]"));
	}
}