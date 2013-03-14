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
public class Member_ReplyMessageTest extends BaseTestCase {
	public void testMember_ReplyMessage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Category Name"),
			selenium.getText("//tr[contains(.,'Category Name')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Category Name')]/td[1]/a",
			RuntimeVariables.replace("Category Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Thread Subject"),
			selenium.getText("//tr[contains(.,'Thread Subject')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Thread Subject')]/td[1]/a",
			RuntimeVariables.replace("Thread Subject"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Reply"),
			selenium.getText(
				"//ul[@class='edit-controls lfr-component']/li[contains(.,'Reply')]/span/a"));
		selenium.clickAt("//ul[@class='edit-controls lfr-component']/li[contains(.,'Reply')]/span/a",
			RuntimeVariables.replace("Reply"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__unlink') and contains(@class,' cke_button_disabled')]");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
			RuntimeVariables.replace("Thread Body Reply"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//ul[@class='edit-controls lfr-component']/li[contains(.,'Edit')]/span/a"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//ul[@class='edit-controls lfr-component']/li[contains(.,'Permissions')]/span/a"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//ul[@class='edit-controls lfr-component']/li[contains(.,'Delete')]/span/a"));
		assertEquals(RuntimeVariables.replace("Thread Body Reply"),
			selenium.getText("xPath=(//div[@class='thread-body'])[2]"));
	}
}