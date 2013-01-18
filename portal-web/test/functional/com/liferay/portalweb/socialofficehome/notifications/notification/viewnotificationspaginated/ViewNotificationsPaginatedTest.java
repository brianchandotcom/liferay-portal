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

package com.liferay.portalweb.socialofficehome.notifications.notification.viewnotificationspaginated;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewNotificationsPaginatedTest extends BaseTestCase {
	public void testViewNotificationsPaginated() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		selenium.waitForVisible("//span[@class='notification-count']");
		assertEquals(RuntimeVariables.replace("6"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.mouseOver("//span[@class='notification-count']");
		selenium.waitForElementPresent(
			"//div[contains(@class, 'user-notification-events-container')]");
		assertTrue(selenium.isVisible(
				"//div[contains(@class, 'user-notification-events-container')]"));
		assertTrue(selenium.isPartialText("//span[@class='view-all']/a",
				"View All"));
		selenium.clickAt("//span[@class='view-all']/a",
			RuntimeVariables.replace("View All"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//td[@id='_6_WAR_soportlet_userNotificationEventsSearchContainer_col-notifications_row-1']",
				"Social03 Office03 User03 sent you a message."));
		assertTrue(selenium.isPartialText(
				"//td[@id='_6_WAR_soportlet_userNotificationEventsSearchContainer_col-notifications_row-2']",
				"Social03 Office03 User03 would like to add you as a connection."));
		assertTrue(selenium.isPartialText(
				"//td[@id='_6_WAR_soportlet_userNotificationEventsSearchContainer_col-notifications_row-3']",
				"Social02 Office02 User02 sent you a message."));
		assertTrue(selenium.isPartialText(
				"//td[@id='_6_WAR_soportlet_userNotificationEventsSearchContainer_col-notifications_row-4']",
				"Social02 Office02 User02 would like to add you as a connection."));
		assertTrue(selenium.isPartialText(
				"//td[@id='_6_WAR_soportlet_userNotificationEventsSearchContainer_col-notifications_row-5']",
				"Social01 Office01 User01 sent you a message."));
		assertTrue(selenium.isPartialText(
				"//td[@id='_6_WAR_soportlet_userNotificationEventsSearchContainer_col-notifications_row-6']",
				"Social01 Office01 User01 would like to add you as a connection."));
		assertEquals("10",
			selenium.getSelectedLabel(
				"//select[@id='_6_WAR_soportlet_userNotificationEventsSearchContainerPageIteratorBottom_itemsPerPage']"));
		selenium.select("//select[@id='_6_WAR_soportlet_userNotificationEventsSearchContainerPageIteratorBottom_itemsPerPage']",
			RuntimeVariables.replace("5"));
		selenium.waitForText("//div[@class='search-results']",
			"Showing 1 - 5 of 6 results.");
		assertTrue(selenium.isPartialText(
				"//td[@id='_6_WAR_soportlet_userNotificationEventsSearchContainer_col-notifications_row-1']",
				"Social03 Office03 User03 sent you a message."));
		assertTrue(selenium.isPartialText(
				"//td[@id='_6_WAR_soportlet_userNotificationEventsSearchContainer_col-notifications_row-2']",
				"Social03 Office03 User03 would like to add you as a connection."));
		assertTrue(selenium.isPartialText(
				"//td[@id='_6_WAR_soportlet_userNotificationEventsSearchContainer_col-notifications_row-3']",
				"Social02 Office02 User02 sent you a message."));
		assertTrue(selenium.isPartialText(
				"//td[@id='_6_WAR_soportlet_userNotificationEventsSearchContainer_col-notifications_row-4']",
				"Social02 Office02 User02 would like to add you as a connection."));
		assertTrue(selenium.isPartialText(
				"//td[@id='_6_WAR_soportlet_userNotificationEventsSearchContainer_col-notifications_row-5']",
				"Social01 Office01 User01 sent you a message."));
		assertTrue(selenium.isElementNotPresent(
				"//td[@id='_6_WAR_soportlet_userNotificationEventsSearchContainer_col-notifications_row-6']"));
		assertEquals(RuntimeVariables.replace("Showing 1 - 5 of 6 results."),
			selenium.getText("//div[@class='search-results']"));
		assertTrue(selenium.isPartialText("//a[@class='next']", "Next"));
		selenium.clickAt("//a[@class='next']", RuntimeVariables.replace("Next"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//td[@id='_6_WAR_soportlet_userNotificationEventsSearchContainer_col-notifications_row-1']",
				"Social01 Office01 User01 would like to add you as a connection."));
		assertTrue(selenium.isElementNotPresent(
				"//td[@id='_6_WAR_soportlet_userNotificationEventsSearchContainer_col-notifications_row-2']"));
		assertEquals(RuntimeVariables.replace("Showing 6 - 6 of 6 results."),
			selenium.getText("//div[@class='search-results']"));
	}
}