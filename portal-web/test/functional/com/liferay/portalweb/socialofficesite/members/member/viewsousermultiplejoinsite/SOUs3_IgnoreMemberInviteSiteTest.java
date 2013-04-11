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

package com.liferay.portalweb.socialofficesite.members.member.viewsousermultiplejoinsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs3_IgnoreMemberInviteSiteTest extends BaseTestCase {
	public void testSOUs3_IgnoreMemberInviteSite() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice03/so/dashboard/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertTrue(selenium.isElementPresent(
				"//li[@id='_145_notificationsMenu']"));
		selenium.waitForVisible("//span[@class='notification-count']");
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.mouseOver("//span[@class='notification-count']");
		selenium.waitForElementPresent(
			"//div[contains(@class, 'user-notification-events-container')]");
		assertTrue(selenium.isVisible(
				"//div[contains(@class, 'user-notification-events-container')]"));
		assertTrue(selenium.isPartialText(
				"//div[contains(@class, 'user-notification-event-content')]/div[2]/div",
				"Joe Bloggs invited you to join"));
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"//div[contains(@class, 'user-notification-event-content')]/div[2]/div/a[2]"));
		assertEquals(RuntimeVariables.replace("Ignore"),
			selenium.getText(
				"//div[@class='notification-entry']/div[2]/span[2]/a"));
		selenium.clickAt("//div[@class='notification-entry']/div[2]/span[2]/a",
			RuntimeVariables.replace("Ignore"));
		selenium.waitForNotText("//span[@class='notification-count']", "1");
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.open("/user/socialoffice03/so/dashboard/");
		selenium.waitForVisible("//li[contains(@class, 'selected')]/a/span");
		assertEquals(RuntimeVariables.replace("Dashboard"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_5_WAR_soportlet_tabs1']", "My Sites"));
		selenium.select("//select[@id='_5_WAR_soportlet_tabs1']",
			RuntimeVariables.replace("My Sites"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("There are no results."),
			selenium.getText("//ul[@class='site-list']"));
		assertFalse(selenium.isTextPresent("Open Site Name"));
	}
}