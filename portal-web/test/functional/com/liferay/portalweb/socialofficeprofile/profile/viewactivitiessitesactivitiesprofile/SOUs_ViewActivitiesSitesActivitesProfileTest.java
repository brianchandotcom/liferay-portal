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

package com.liferay.portalweb.socialofficeprofile.profile.viewactivitiessitesactivitiesprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewActivitiesSitesActivitesProfileTest extends BaseTestCase {
	public void testSOUs_ViewActivitiesSitesActivitesProfile()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialoffice01/so/profile");
		selenium.waitForVisible("//li[contains(@class, 'selected')]/a/span");
		assertEquals(RuntimeVariables.replace("Profile"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		assertEquals(RuntimeVariables.replace("socialoffice01@liferay.com"),
			selenium.getText("//div[@class='lfr-contact-extra']"));
		assertEquals(RuntimeVariables.replace(
				"Social01 added a new bookmarks entry, Bookmarks Entry2 Name, in Open Site Name."),
			selenium.getText("xPath=(//div[@class='activity-title'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"Social01 updated a wiki page, FrontPage, in Open Site Name."),
			selenium.getText("xPath=(//div[@class='activity-title'])[2]"));
		assertEquals(RuntimeVariables.replace(
				"Social01 wrote a new blog entry, Blogs Entry2 Title, in Open Site Name."),
			selenium.getText("xPath=(//div[@class='activity-title'])[3]"));
		assertEquals(RuntimeVariables.replace(
				"Social01 wrote a new message board post, MB Category Thread2 Message Subject, in Open Site Name."),
			selenium.getText("xPath=(//div[@class='activity-title'])[4]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='activity-body'])[4]", "Go to Category"));
		assertEquals(RuntimeVariables.replace(
				"Social01 uploaded a new document, DM Folder Document2 Title, in Open Site Name."),
			selenium.getText("xPath=(//div[@class='activity-title'])[5]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='activity-body'])[5]", "View Document"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='activity-body'])[5]", "Go to Folder"));
		assertEquals(RuntimeVariables.replace(
				"Social01 added a new calendar event, Calendar Event2 Title, in Open Site Name."),
			selenium.getText("xPath=(//div[@class='activity-title'])[6]"));
		assertFalse(selenium.isTextPresent("Bookmarks Entry1 Name"));
		assertFalse(selenium.isTextPresent("Blogs Entry1 Title"));
		assertFalse(selenium.isTextPresent(
				"MB Category Thread1 Message Subject"));
		assertFalse(selenium.isTextPresent("DM Folder Document1 Title"));
		assertFalse(selenium.isTextPresent("Calendar Event1 Title"));
	}
}