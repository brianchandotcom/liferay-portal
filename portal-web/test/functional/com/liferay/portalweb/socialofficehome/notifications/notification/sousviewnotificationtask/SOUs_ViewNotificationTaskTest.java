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

package com.liferay.portalweb.socialofficehome.notifications.notification.sousviewnotificationtask;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewNotificationTaskTest extends BaseTestCase {
	public void testSOUs_ViewNotificationTask() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertTrue(selenium.isElementPresent(
				"//li[@id='_145_notificationsMenu']"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.mouseOver("//li[@id='_145_notificationsMenu']");
		selenium.waitForVisible("//div[@class='notification-entry']");
		assertEquals(RuntimeVariables.replace("Joe Bloggs assigned you a task."),
			selenium.getText(
				"//div[@class='notification-entry']/div[@class='title']"));
		assertEquals(RuntimeVariables.replace("Task Description"),
			selenium.getText(
				"//div[@class='notification-entry']/div[@class='body']"));
		assertTrue(selenium.isVisible(
				"//div[@class='notification-entry']/div[@class='datetime']"));
		selenium.clickAt("//div[@class='notification-entry']/div[@class='title']",
			RuntimeVariables.replace("Joe Bloggs assigned you a task."));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Assigned to Me"),
			selenium.getText("link=Assigned to Me"));
		assertEquals(RuntimeVariables.replace("Task Description"),
			selenium.getText("//a[@class='tasks-title normal']"));
		assertEquals(RuntimeVariables.replace("Reporter: Joe Bloggs"),
			selenium.getText("//div[@class='result-data']/span"));
	}
}