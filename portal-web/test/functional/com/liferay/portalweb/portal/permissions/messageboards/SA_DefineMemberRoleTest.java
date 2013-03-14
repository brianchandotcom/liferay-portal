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
public class SA_DefineMemberRoleTest extends BaseTestCase {
	public void testSA_DefineMemberRole() throws Exception {
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
					RuntimeVariables.replace("Member"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Member"),
					selenium.getText("//tr[contains(.,'Member')]/td[1]/a"));
				selenium.clickAt("//tr[contains(.,'Member')]/td[1]/a",
					RuntimeVariables.replace("Member"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Define Permissions",
					RuntimeVariables.replace("Define Permissions"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("Message Boards"));
				selenium.waitForPageToLoad("30000");

				boolean messageBoardsCategoryAddFileChecked = selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']");

				if (messageBoardsCategoryAddFileChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']",
					RuntimeVariables.replace("MBCategory Add File"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']"));

				boolean messageBoardsCategoryAddMessageChecked = selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_MESSAGE']");

				if (messageBoardsCategoryAddMessageChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_MESSAGE']",
					RuntimeVariables.replace("MBCategory Add Message"));

			case 3:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_MESSAGE']"));

				boolean messageBoardsCategoryReplyToMessage = selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryREPLY_TO_MESSAGE']");

				if (messageBoardsCategoryReplyToMessage) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.messageboards.model.MBCategoryREPLY_TO_MESSAGE']",
					RuntimeVariables.replace("MBCategory Reply to Message"));

			case 4:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryREPLY_TO_MESSAGE']"));

				boolean messageBoardsCategorySubscribe = selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategorySUBSCRIBE']");

				if (messageBoardsCategorySubscribe) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.messageboards.model.MBCategorySUBSCRIBE']",
					RuntimeVariables.replace("MBCategory Subscribe"));

			case 5:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategorySUBSCRIBE']"));

				boolean messageBoardsCategoryView = selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryVIEW']");

				if (messageBoardsCategoryView) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.messageboards.model.MBCategoryVIEW']",
					RuntimeVariables.replace("MBCategory View"));

			case 6:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryVIEW']"));

				boolean messageBoardsMessageSubscribe = selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBMessageSUBSCRIBE']");

				if (messageBoardsMessageSubscribe) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.messageboards.model.MBMessageSUBSCRIBE']",
					RuntimeVariables.replace("MBMessage Subscribe"));

			case 7:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBMessageSUBSCRIBE']"));

				boolean messageBoardsMessageView = selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBMessageVIEW']");

				if (messageBoardsMessageView) {
					label = 8;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.messageboards.model.MBMessageVIEW']",
					RuntimeVariables.replace("MBMessage View"));

			case 8:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBMessageVIEW']"));
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
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_MESSAGE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryREPLY_TO_MESSAGE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategorySUBSCRIBE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBCategoryVIEW']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBMessageSUBSCRIBE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.messageboards.model.MBMessageVIEW']"));

			case 100:
				label = -1;
			}
		}
	}
}