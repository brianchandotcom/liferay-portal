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
public class AddTasksTask2AssignedToConnectionTest extends BaseTestCase {
	public void testAddTasksTask2AssignedToConnection()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.clickAt("//nav/ul/li[contains(.,'Tasks')]/a/span",
			RuntimeVariables.replace("Tasks"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Tasks"),
			selenium.getText("//span[@class='portlet-title-default']"));
		assertEquals(RuntimeVariables.replace("Add Task"),
			selenium.getText("link=Add Task"));
		selenium.clickAt("link=Add Task", RuntimeVariables.replace("Add Task"));
		selenium.waitForVisible("//input[@id='_1_WAR_tasksportlet_title']");
		selenium.type("//input[@id='_1_WAR_tasksportlet_title']",
			RuntimeVariables.replace("Task2 Description"));
		selenium.select("//select[@name='_1_WAR_tasksportlet_assigneeUserId']",
			RuntimeVariables.replace("label=Social01 Office01 User01"));
		selenium.select("//select[@name='_1_WAR_tasksportlet_priority']",
			RuntimeVariables.replace("label=Normal"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForText("//h1[@class='header-title']", "Task2 Description");
		assertEquals(RuntimeVariables.replace("Task2 Description"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace(
				"Assigned to Social01 Office01 User01"),
			selenium.getText("//div[@class='task-data assignee']"));
	}
}