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

package com.liferay.portalweb.portal.controlpanel.polls;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddVoteTest extends BaseTestCase {
	public void testAddVote() throws Exception {
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
		selenium.clickAt("link=Polls", RuntimeVariables.replace("Polls"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Poll Question"),
			selenium.getText("//tr[contains(.,'Test Poll Question')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Test Poll Question')]/td[1]/a",
			RuntimeVariables.replace("Test Poll Question"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("a. Test Choice A"),
			selenium.getText("//span[@class='field field-choice']/span/label"));
		selenium.clickAt("//input[@name='_25_choiceId']",
			RuntimeVariables.replace("Test Choice A"));
		assertTrue(selenium.isChecked("//input[@name='_25_choiceId']"));
		selenium.clickAt("//input[@value='Vote']",
			RuntimeVariables.replace("Vote"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Test Poll Question"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"This is a test poll description."),
			selenium.getText("//fieldset/div/span"));
		assertEquals(RuntimeVariables.replace("100%"),
			selenium.getText("//tr[contains(.,'Test Choice A')]/td[1]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//tr[contains(.,'Test Choice A')]/td[2]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Test Choice A')]/td[3]/div/div/div[contains(@style,'width: 100%')]"));
		assertEquals(RuntimeVariables.replace("a."),
			selenium.getText("//tr[contains(.,'Test Choice A')]/td[4]"));
		assertEquals(RuntimeVariables.replace("Test Choice A"),
			selenium.getText("//tr[contains(.,'Test Choice A')]/td[5]"));
	}
}