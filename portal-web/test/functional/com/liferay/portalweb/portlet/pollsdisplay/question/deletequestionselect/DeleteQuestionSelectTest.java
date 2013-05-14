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

package com.liferay.portalweb.portlet.pollsdisplay.question.deletequestionselect;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteQuestionSelectTest extends BaseTestCase {
	public void testDeleteQuestionSelect() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Polls Display Test Page",
			RuntimeVariables.replace("Polls Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//div/span[1]/span/span/input"));
		assertTrue(selenium.isElementPresent("//div/span[2]/span/span/input"));
		assertTrue(selenium.isElementPresent("//div/span[3]/span/span/input"));
		assertEquals(RuntimeVariables.replace("a. PD Question ChoiceA"),
			selenium.getText("//div/span[1]/span/label"));
		assertEquals(RuntimeVariables.replace("b. PD Question ChoiceB"),
			selenium.getText("//div/span[2]/span/label"));
		assertEquals(RuntimeVariables.replace("c. PD Question ChoiceC"),
			selenium.getText("//div/span[3]/span/label"));
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
		selenium.clickAt("link=Polls", RuntimeVariables.replace("Polls"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]");
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]",
			RuntimeVariables.replace("Delete"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Polls Display Test Page",
			RuntimeVariables.replace("Polls Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent("//div/span[1]/span/span/input"));
		assertTrue(selenium.isElementNotPresent("//div/span[2]/span/span/input"));
		assertTrue(selenium.isElementNotPresent("//div/span[3]/span/span/input"));
		assertFalse(selenium.isTextPresent("PD Question ChoiceA"));
		assertFalse(selenium.isTextPresent("PD Question ChoiceB"));
		assertFalse(selenium.isTextPresent("PD Question ChoiceC"));
		assertEquals(RuntimeVariables.replace(
				"Please configure this portlet to make it visible to all users."),
			selenium.getText(
				"//div[@class='portlet-configuration portlet-msg-info']"));
	}
}