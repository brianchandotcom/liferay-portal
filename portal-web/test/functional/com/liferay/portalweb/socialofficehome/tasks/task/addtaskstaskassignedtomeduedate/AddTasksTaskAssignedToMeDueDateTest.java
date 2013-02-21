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

package com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtomeduedate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddTasksTaskAssignedToMeDueDateTest extends BaseTestCase {
	public void testAddTasksTaskAssignedToMeDueDate() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard/");
				selenium.clickAt("//nav/ul/li[contains(.,'Tasks')]/a/span",
					RuntimeVariables.replace("Tasks"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Tasks"),
					selenium.getText("//span[@class='portlet-title-default']"));
				assertEquals(RuntimeVariables.replace("No tasks were found."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertEquals(RuntimeVariables.replace("Add Task"),
					selenium.getText("link=Add Task"));
				selenium.clickAt("link=Add Task",
					RuntimeVariables.replace("Add Task"));
				selenium.waitForVisible(
					"//input[@id='_1_WAR_tasksportlet_title']");
				selenium.type("//input[@id='_1_WAR_tasksportlet_title']",
					RuntimeVariables.replace("Task Description"));
				selenium.select("//select[@name='_1_WAR_tasksportlet_assigneeUserId']",
					RuntimeVariables.replace("label=Joe Bloggs"));
				selenium.select("//select[@name='_1_WAR_tasksportlet_priority']",
					RuntimeVariables.replace("label=Normal"));
				assertEquals(RuntimeVariables.replace("Add Due Date"),
					selenium.getText("//a[@id='toggleDueDate']"));
				selenium.clickAt("//a[@id='toggleDueDate']",
					RuntimeVariables.replace("Add Due Date"));
				selenium.waitForText("//a[@id='toggleDueDate']",
					"Remove Due Date");

				boolean newDueDateMonthSelectPresent = selenium.isElementPresent(
						"//select[@id='_1_WAR_tasksportlet_duedatemonth']");

				if (!newDueDateMonthSelectPresent) {
					label = 2;

					continue;
				}

				selenium.select("//select[@id='_1_WAR_tasksportlet_duedatemonth']",
					RuntimeVariables.replace("label=March"));
				selenium.select("//select[@id='_1_WAR_tasksportlet_duedateday']",
					RuntimeVariables.replace("label=16"));
				selenium.select("//select[@id='_1_WAR_tasksportlet_duedateyear']",
					RuntimeVariables.replace("label=2016"));

			case 2:

				boolean oldDueDateMonthSelectPresent = selenium.isElementPresent(
						"//select[@id='_1_WAR_tasksportlet_dueDateMonth']");

				if (!oldDueDateMonthSelectPresent) {
					label = 3;

					continue;
				}

				selenium.select("//select[@id='_1_WAR_tasksportlet_dueDateMonth']",
					RuntimeVariables.replace("label=March"));
				selenium.select("//select[@id='_1_WAR_tasksportlet_dueDateDay']",
					RuntimeVariables.replace("label=16"));
				selenium.select("//select[@id='_1_WAR_tasksportlet_dueDateYear']",
					RuntimeVariables.replace("label=2016"));

			case 3:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForText("//h1[@class='header-title']",
					"Task Description");
				assertEquals(RuntimeVariables.replace("Task Description"),
					selenium.getText("//h1[@class='header-title']"));
				assertEquals(RuntimeVariables.replace("Assigned to Joe Bloggs"),
					selenium.getText("//div[@class='task-data assignee']"));

			case 100:
				label = -1;
			}
		}
	}
}