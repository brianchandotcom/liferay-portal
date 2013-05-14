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

package com.liferay.portalweb.portal.tags.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownTagTest extends BaseTestCase {
	public void testTearDownTag() throws Exception {
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
				selenium.clickAt("link=Tags", RuntimeVariables.replace("Tags"));
				selenium.waitForPageToLoad("30000");

				boolean tagsVisible = selenium.isElementPresent(
						"//span[@class='tag-item']/a");

				if (!tagsVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_99_checkAllTagsCheckbox']",
					RuntimeVariables.replace("Select All"));
				assertTrue(selenium.isVisible(
						"//input[@id='_99_checkAllTagsCheckbox']"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a[contains(.,'Delete')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a[contains(.,'Delete')]",
					RuntimeVariables.replace("Delete"));
				selenium.waitForConfirmation(
					"Are you sure you want to delete the selected tags?");
				selenium.waitForText("//div[@class='lfr-message-response portlet-msg-success']",
					"Your request processed successfully.");
				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText(
						"//div[@class='lfr-message-response portlet-msg-success']"));

			case 2:
				assertEquals(RuntimeVariables.replace("There are no tags."),
					selenium.getText(
						"//div[@class='lfr-message-response portlet-msg-info']"));

			case 100:
				label = -1;
			}
		}
	}
}