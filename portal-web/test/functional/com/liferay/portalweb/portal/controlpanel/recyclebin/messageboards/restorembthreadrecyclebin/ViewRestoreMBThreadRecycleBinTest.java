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

package com.liferay.portalweb.portal.controlpanel.recyclebin.messageboards.restorembthreadrecyclebin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewRestoreMBThreadRecycleBinTest extends BaseTestCase {
	public void testViewRestoreMBThreadRecycleBin() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Message Boards"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Message Boards Home"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'Message Boards Home')]/span"));
		assertEquals(RuntimeVariables.replace("Recent Posts"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a[contains(.,'Recent Posts')]/span"));
		assertEquals(RuntimeVariables.replace("My Posts"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a[contains(.,'My Posts')]/span"));
		assertEquals(RuntimeVariables.replace("My Subscriptions"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a[contains(.,'My Subscriptions')]/span"));
		assertEquals(RuntimeVariables.replace("Statistics"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a[contains(.,'Statistics')]/span"));
		assertEquals(RuntimeVariables.replace("Banned Users"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a[contains(.,'Banned Users')]/span"));
		assertTrue(selenium.isVisible("//input[@title='Search Messages']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertEquals(RuntimeVariables.replace("RSS"),
			selenium.getText(
				"//div[@class='category-subscription-types']/span/a[contains(.,'RSS')]/span"));
		assertEquals(RuntimeVariables.replace("Subscribe"),
			selenium.getText(
				"//div[@class='category-subscription-types']/span/a[contains(.,'Subscribe')]/span"));
		assertTrue(selenium.isVisible("//input[@value='Add Category']"));
		assertTrue(selenium.isVisible("//input[@value='Post New Thread']"));
		assertTrue(selenium.isVisible("//input[@value='Permissions']"));
		assertEquals(RuntimeVariables.replace("Threads"),
			selenium.getText(
				"//div[@class='lfr-panel-title']/span[contains(.,'Threads')]"));
		assertEquals(RuntimeVariables.replace("MB Thread Message Subject"),
			selenium.getText(
				"//tr[contains(.,'MB Thread Message Subject')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tr[contains(.,'MB Thread Message Subject')]/td[2]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//tr[contains(.,'MB Thread Message Subject')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//tr[contains(.,'MB Thread Message Subject')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//tr[contains(.,'MB Thread Message Subject')]/td[5]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'MB Thread Message Subject')]/td[6]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'MB Thread Message Subject')]/td[7]/span/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
	}
}