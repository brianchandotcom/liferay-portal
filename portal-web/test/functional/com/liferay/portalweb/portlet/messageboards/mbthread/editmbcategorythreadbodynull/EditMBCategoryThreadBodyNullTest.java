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

package com.liferay.portalweb.portlet.messageboards.mbthread.editmbcategorythreadbodynull;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditMBCategoryThreadBodyNullTest extends BaseTestCase {
	public void testEditMBCategoryThreadBodyNull() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//td[1]/a"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//td[7]/span/ul/li/strong/a"));
		selenium.clickAt("//td[7]/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__unlink') and contains(@class,' cke_button_disabled')]");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
			RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//div[@class='subject']/a/strong"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//div[@class='thread-body']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//a/strong"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//td[4]/a"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//td[1]/a"));
	}
}