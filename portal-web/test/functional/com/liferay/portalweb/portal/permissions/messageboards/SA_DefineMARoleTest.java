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

package com.liferay.portalweb.portal.permissions.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_DefineMARoleTest extends BaseTestCase {
	public void testSA_DefineMARole() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_128_keywords']",
					RuntimeVariables.replace("Message Boards"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Message Boards Administrator"),
					selenium.getText(
						"//tr[contains(.,'Message Boards Administrator')]/td[1]/a"));
				selenium.clickAt("//tr[contains(.,'Message Boards Administrator')]/td[1]/a",
					RuntimeVariables.replace("Message Boards Administrator"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Define Permissions",
					RuntimeVariables.replace("Define Permissions"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("Message Boards"));
				selenium.waitForPageToLoad("30000");

				boolean selectAllMessageBoardsChecked = selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])[1]");

				if (selectAllMessageBoardsChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("xPath=(//input[@name='_128_allRowIds'])[1]",
					RuntimeVariables.replace("Select All [Message Boards]"));

			case 2:
				assertTrue(selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])[1]"));

				boolean selectAllMessageBoardsCategoryChecked = selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])[2]");

				if (selectAllMessageBoardsCategoryChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("xPath=(//input[@name='_128_allRowIds'])[2]",
					RuntimeVariables.replace(
						"Select All [Message Board Category]"));

			case 3:
				assertTrue(selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])[2]"));

				boolean selectAllMessageBoardMessageChecked = selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])[3]");

				if (selectAllMessageBoardMessageChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("xPath=(//input[@name='_128_allRowIds'])[3]",
					RuntimeVariables.replace(
						"Select All [Message Board Message]"));

			case 4:
				assertTrue(selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])[3]"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("Message Boards"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboardsADD_CATEGORY']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboardsADD_FILE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboardsADD_MESSAGE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboardsLOCK_THREAD']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboardsMOVE_THREAD']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboardsPERMISSIONS']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboardsREPLY_TO_MESSAGE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboardsSUBSCRIBE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboardsUPDATE_THREAD_PRIORITY']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboardsVIEW']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_MESSAGE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_SUBCATEGORY']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']"));

			case 100:
				label = -1;
			}
		}
	}
}