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

package com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtome;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownTasksTaskAssignedToMeTest extends BaseTestCase {
	public void testTearDownTasksTaskAssignedToMe() throws Exception {
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
				selenium.clickAt("link=Assigned to Me",
					RuntimeVariables.replace("Assigned to Me"));
				selenium.waitForPageToLoad("30000");

				boolean showCompleted1Checked = selenium.isChecked(
						"//td[1]/input");

				if (showCompleted1Checked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//td[1]/input",
					RuntimeVariables.replace("Check Show Completed Tasks"));

			case 2:

				boolean task1Present = selenium.isElementPresent(
						"//td[1]/div[1]/a");

				if (!task1Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//td[1]/div[1]/a",
					RuntimeVariables.replace("Task"));
				selenium.waitForText("//div[2]/div[2]/div[1]",
					"Assigned to Joe Bloggs");
				assertEquals(RuntimeVariables.replace("Assigned to Joe Bloggs"),
					selenium.getText("//div[2]/div[2]/div[1]"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this entry[\\s\\S]$"));

			case 3:
				selenium.clickAt("link=I Have Created",
					RuntimeVariables.replace("I Have Created"));
				selenium.waitForPageToLoad("30000");

				boolean showCompleted2Checked = selenium.isChecked(
						"//td[1]/input");

				if (showCompleted2Checked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//td[1]/input",
					RuntimeVariables.replace("Check Show Completed Tasks"));

			case 4:

				boolean task2Present = selenium.isElementPresent(
						"//td[1]/div[1]/a");

				if (!task2Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//td[1]/div[1]/a",
					RuntimeVariables.replace("Task"));
				selenium.waitForText("//div[2]/div[2]/div[1]",
					"Assigned to Joe Bloggs");
				assertEquals(RuntimeVariables.replace("Assigned to Joe Bloggs"),
					selenium.getText("//div[2]/div[2]/div[1]"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this entry[\\s\\S]$"));

			case 5:
				selenium.clickAt("link=I Have Created",
					RuntimeVariables.replace("I Have Created"));
				selenium.waitForPageToLoad("30000");

				boolean showCompleted3Checked = selenium.isChecked(
						"//td[1]/input");

				if (showCompleted3Checked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//td[1]/input",
					RuntimeVariables.replace("Check Show Completed Tasks"));

			case 6:

				boolean task3Present = selenium.isElementPresent(
						"//td[1]/div[1]/a");

				if (!task3Present) {
					label = 7;

					continue;
				}

				selenium.clickAt("//td[1]/div[1]/a",
					RuntimeVariables.replace("Task"));
				selenium.waitForText("//div[2]/div[2]/div[1]",
					"Assigned to Joe Bloggs");
				assertEquals(RuntimeVariables.replace("Assigned to Joe Bloggs"),
					selenium.getText("//div[2]/div[2]/div[1]"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this entry[\\s\\S]$"));

			case 7:
				selenium.clickAt("link=I Have Created",
					RuntimeVariables.replace("I Have Created"));
				selenium.waitForPageToLoad("30000");

				boolean showCompleted4Checked = selenium.isChecked(
						"//td[1]/input");

				if (showCompleted4Checked) {
					label = 8;

					continue;
				}

				selenium.clickAt("//td[1]/input",
					RuntimeVariables.replace("Check Show Completed Tasks"));

			case 8:

				boolean task4Present = selenium.isElementPresent(
						"//td[1]/div[1]/a");

				if (!task4Present) {
					label = 9;

					continue;
				}

				selenium.clickAt("//td[1]/div[1]/a",
					RuntimeVariables.replace("Task"));
				selenium.waitForText("//div[2]/div[2]/div[1]",
					"Assigned to Joe Bloggs");
				assertEquals(RuntimeVariables.replace("Assigned to Joe Bloggs"),
					selenium.getText("//div[2]/div[2]/div[1]"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this entry[\\s\\S]$"));

			case 9:
				selenium.clickAt("link=I Have Created",
					RuntimeVariables.replace("I Have Created"));
				selenium.waitForPageToLoad("30000");

				boolean showCompleted5Checked = selenium.isChecked(
						"//td[1]/input");

				if (showCompleted5Checked) {
					label = 10;

					continue;
				}

				selenium.clickAt("//td[1]/input",
					RuntimeVariables.replace("Check Show Completed Tasks"));

			case 10:

				boolean task5Present = selenium.isElementPresent(
						"//td[1]/div[1]/a");

				if (!task5Present) {
					label = 11;

					continue;
				}

				selenium.clickAt("//td[1]/div[1]/a",
					RuntimeVariables.replace("Task"));
				selenium.waitForText("//div[2]/div[2]/div[1]",
					"Assigned to Joe Bloggs");
				assertEquals(RuntimeVariables.replace("Assigned to Joe Bloggs"),
					selenium.getText("//div[2]/div[2]/div[1]"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this entry[\\s\\S]$"));

			case 11:
			case 100:
				label = -1;
			}
		}
	}
}