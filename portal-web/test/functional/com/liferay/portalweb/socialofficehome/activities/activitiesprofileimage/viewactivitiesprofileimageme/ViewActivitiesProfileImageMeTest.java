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

package com.liferay.portalweb.socialofficehome.activities.activitiesprofileimage.viewactivitiesprofileimageme;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewActivitiesProfileImageMeTest extends BaseTestCase {
	public void testViewActivitiesProfileImageMe() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("user/joebloggs/so/dashboard");
		assertEquals(RuntimeVariables.replace("Activities"),
			selenium.getText("xPath=(//h1[@class='portlet-title']/span)[2]"));
		assertEquals(RuntimeVariables.replace("Me"), selenium.getText("link=Me"));
		selenium.clickAt("link=Me", RuntimeVariables.replace("Me"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"xpath=(//span[@class='avatar']/img[@alt='Social01 Office01 User01'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"Social01 Office01 User01 commented on Joe's blog entry, Blogs Entry Comment Body..., in Open Site Name."),
			selenium.getText("xpath=(//div[@class='activity-title'])[1]"));
		assertTrue(selenium.isElementPresent(
				"xpath=(//span[@class='avatar']/img[@alt='Social01 Office01 User01'])[2]"));
		assertEquals(RuntimeVariables.replace(
				"Social01 Office01 User01 replied to Joe's message board post, RE: MB Category Thread Message Subject, in Open Site Name."),
			selenium.getText("xpath=(//div[@class='activity-title'])[2]"));
		assertTrue(selenium.isElementPresent(
				"xpath=(//span[@class='avatar']/img[@alt='Social01 Office01 User01'])[3]"));
		assertEquals(RuntimeVariables.replace("@Joe: Microblogs Post Comment"),
			selenium.getText("xpath=(//div[@class='activity-title'])[3]"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='activity-body'])[3]",
				"Social01 Office01 User01"));
		assertTrue(selenium.isElementPresent(
				"xpath=(//span[@class='avatar']/img[@alt='Joe Bloggs'])[1]"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='activity-title'])[4]",
				"Joe wrote a new blog entry"));
		assertTrue(selenium.isElementPresent(
				"xpath=(//span[@class='avatar']/img[@alt='Joe Bloggs'])[2]"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='activity-title'])[5]",
				"Joe wrote a new message board post"));
		assertTrue(selenium.isElementPresent(
				"xpath=(//span[@class='avatar']/img[@alt='Joe Bloggs'])[3]"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("xpath=(//div[@class='activity-title'])[6]"));
	}
}