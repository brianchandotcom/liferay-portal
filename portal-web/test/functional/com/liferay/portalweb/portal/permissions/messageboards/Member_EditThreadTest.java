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
public class Member_EditThreadTest extends BaseTestCase {
	public void testMember_EditThread() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Thread Subject 2"),
			selenium.getText("//tr[contains(.,'Thread Subject 2')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Thread Subject 2')]/td[1]/a",
			RuntimeVariables.replace("Thread Subject 2"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible(
				"//ul[@class='edit-controls lfr-component']/li[2]/span/a/span[contains(.,'Edit')]"));
		selenium.clickAt("//ul[@class='edit-controls lfr-component']/li[2]/span/a/span[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@id='_19_subject']");
		selenium.type("//input[@id='_19_subject']",
			RuntimeVariables.replace("Thread Subject 2 Edited"));
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__unlink') and contains(@class,' cke_button_disabled')]");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
			RuntimeVariables.replace("Thread Body 2 Edited"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Thread Subject 2 Edited"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Thread Subject 2 Edited"),
			selenium.getText("//div[@class='subject']/a/strong"));
		assertEquals(RuntimeVariables.replace("Thread Body 2 Edited"),
			selenium.getText("//div[@class='thread-body']"));
	}
}