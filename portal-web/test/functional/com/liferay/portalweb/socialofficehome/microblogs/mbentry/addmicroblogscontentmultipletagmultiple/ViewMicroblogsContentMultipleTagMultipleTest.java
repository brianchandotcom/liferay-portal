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

package com.liferay.portalweb.socialofficehome.microblogs.mbentry.addmicroblogscontentmultipletagmultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMicroblogsContentMultipleTagMultipleTest extends BaseTestCase {
	public void testViewMicroblogsContentMultipleTagMultiple()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard");
		assertTrue(selenium.isElementPresent(
				"//div[contains(@id,'_2_WAR_microblogsportlet_autocompleteContent')]"));
		assertEquals(RuntimeVariables.replace("Microblogs2 Post3"),
			selenium.getText("//div[@class='content']"));
		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xPath=(//div[@class='user-name']/span/a)[1]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xPath=(//div[@class='user-name']/span/a)[2]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xPath=(//div[@class='user-name']/span/a)[3]"));
		assertEquals(RuntimeVariables.replace("Microblogs2 Post3"),
			selenium.getText("xPath=(//div[@class='content'])[1]"));
		assertEquals(RuntimeVariables.replace("Microblogs1 Post2"),
			selenium.getText("xPath=(//div[@class='content'])[2]"));
		assertEquals(RuntimeVariables.replace("Microblogs1 Post1"),
			selenium.getText("xPath=(//div[@class='content'])[3]"));
		assertEquals(RuntimeVariables.replace("Microblogs2"),
			selenium.getText("xPath=(//div[@class='content']/span/a)[1]"));
		assertEquals(RuntimeVariables.replace("Microblogs1"),
			selenium.getText("xPath=(//div[@class='content']/span/a)[2]"));
		assertEquals(RuntimeVariables.replace("Microblogs1"),
			selenium.getText("xPath=(//div[@class='content']/span/a)[3]"));
		selenium.clickAt("xPath=(//div[@class='content']/span/a)[1]",
			RuntimeVariables.replace("Microblogs2"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Microblogs2"),
			selenium.getText(
				"//ul[contains(@class,'tabview-list')]/li[contains(.,'Microblogs2')]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//div[@class='user-name']/span/a"));
		assertEquals(RuntimeVariables.replace("Microblogs2 Post3"),
			selenium.getText("//div[@class='content']"));
		assertEquals(RuntimeVariables.replace("Microblogs2"),
			selenium.getText("//div[@class='content']/span/a"));
		assertFalse(selenium.isTextPresent("Microblogs1"));
		assertFalse(selenium.isTextPresent("Microblogs1 Post2"));
		assertFalse(selenium.isTextPresent("Microblogs1 Post1"));
		selenium.open("/user/joebloggs/so/dashboard");
		assertTrue(selenium.isElementPresent(
				"//div[contains(@id,'_2_WAR_microblogsportlet_autocompleteContent')]"));
		assertEquals(RuntimeVariables.replace("Microblogs2 Post3"),
			selenium.getText("//div[@class='content']"));
		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xPath=(//div[@class='user-name']/span/a)[1]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xPath=(//div[@class='user-name']/span/a)[2]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xPath=(//div[@class='user-name']/span/a)[3]"));
		assertEquals(RuntimeVariables.replace("Microblogs2 Post3"),
			selenium.getText("xPath=(//div[@class='content'])[1]"));
		assertEquals(RuntimeVariables.replace("Microblogs1 Post2"),
			selenium.getText("xPath=(//div[@class='content'])[2]"));
		assertEquals(RuntimeVariables.replace("Microblogs1 Post1"),
			selenium.getText("xPath=(//div[@class='content'])[3]"));
		assertEquals(RuntimeVariables.replace("Microblogs2"),
			selenium.getText("xPath=(//div[@class='content']/span/a)[1]"));
		assertEquals(RuntimeVariables.replace("Microblogs1"),
			selenium.getText("xPath=(//div[@class='content']/span/a)[2]"));
		assertEquals(RuntimeVariables.replace("Microblogs1"),
			selenium.getText("xPath=(//div[@class='content']/span/a)[3]"));
		selenium.clickAt("xPath=(//div[@class='content']/span/a)[2]",
			RuntimeVariables.replace("Microblogs1"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Microblogs1"),
			selenium.getText(
				"//ul[contains(@class,'tabview-list')]/li[contains(.,'Microblogs1')]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xPath=(//div[@class='user-name']/span/a)[1]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xPath=(//div[@class='user-name']/span/a)[2]"));
		assertEquals(RuntimeVariables.replace("Microblogs1 Post2"),
			selenium.getText("xPath=(//div[@class='content'])[1]"));
		assertEquals(RuntimeVariables.replace("Microblogs1 Post1"),
			selenium.getText("xPath=(//div[@class='content'])[2]"));
		assertEquals(RuntimeVariables.replace("Microblogs1"),
			selenium.getText("xPath=(//div[@class='content']/span/a)[1]"));
		assertEquals(RuntimeVariables.replace("Microblogs1"),
			selenium.getText("xPath=(//div[@class='content']/span/a)[2]"));
		assertFalse(selenium.isTextPresent("Microblogs2"));
		assertFalse(selenium.isTextPresent("Microblogs2 Post3"));
	}
}