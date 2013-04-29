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

package com.liferay.portalweb.socialofficeprofile.profile.viewactivitiesdashboardactivitiesprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewActivitiesDashboardActivitiesProfileTest extends BaseTestCase {
	public void testViewActivitiesDashboardActivitiesProfile()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/joebloggs/so/profile");
		selenium.waitForVisible("//li[contains(@class, 'selected')]/a/span");
		assertEquals(RuntimeVariables.replace("Profile"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xPath=(//div[@class='lfr-contact-name']/a)[2]"));
		assertEquals(RuntimeVariables.replace("test@liferay.com"),
			selenium.getText("//div[@class='lfr-contact-extra']"));
		assertEquals(RuntimeVariables.replace(
				"Social01 Office01 User01 added a new task for Joe."),
			selenium.getText("xPath=(//div[@class='activity-title'])[1]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='activity-body'])[1]", "Task4 Description"));
		assertEquals(RuntimeVariables.replace(
				"Joe added a new task for Social01 Office01 User01."),
			selenium.getText("xPath=(//div[@class='activity-title'])[2]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='activity-body'])[2]", "Task2 Description"));
		assertEquals(RuntimeVariables.replace("Joe added a new task."),
			selenium.getText("xPath=(//div[@class='activity-title'])[3]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='activity-body'])[3]", "Task1 Description"));
		assertEquals(RuntimeVariables.replace(
				"Joe uploaded a new document, DM Folder Document1 Title."),
			selenium.getText("xPath=(//div[@class='activity-title'])[4]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='activity-body'])[4]", "View Document"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='activity-body'])[4]", "Go to Folder"));
		assertEquals(RuntimeVariables.replace("#Microblogs Post2"),
			selenium.getText("xPath=(//div[@class='activity-title'])[5]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='activity-body'])[5]", "Joe"));
		assertEquals(RuntimeVariables.replace("Microblogs Post1"),
			selenium.getText("xPath=(//div[@class='activity-title'])[6]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='activity-body'])[6]", "Joe"));
		assertFalse(selenium.isTextPresent("Microblogs Post3"));
		assertFalse(selenium.isTextPresent("Microblogs Post4"));
		assertFalse(selenium.isTextPresent("DM Folder Document2 Title"));
		assertFalse(selenium.isTextPresent("Task3 Description"));
	}
}