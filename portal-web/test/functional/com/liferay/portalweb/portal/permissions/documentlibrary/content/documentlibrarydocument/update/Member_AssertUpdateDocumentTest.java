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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrarydocument.update;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_AssertUpdateDocumentTest extends BaseTestCase {
	public void testMember_AssertUpdateDocument() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Documents and Media",
			RuntimeVariables.replace("Documents and Media"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//span[2]/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
		assertEquals(RuntimeVariables.replace("Checkout"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[4]/a"));
		assertEquals(RuntimeVariables.replace("TestDocument.txt"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("TestDocument.txt"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("TestDocument.txt"),
			selenium.getText("//h2[@class='document-title']"));
		assertTrue(selenium.isTextPresent("Edit"));
		assertEquals(RuntimeVariables.replace("Download"),
			selenium.getText("//div[@id='_20_fileEntryToolbar']/span/button[1]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//div[@id='_20_fileEntryToolbar']/span/button[2]"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText("//div[@id='_20_fileEntryToolbar']/span/button[3]"));
		assertEquals(RuntimeVariables.replace("Checkout"),
			selenium.getText("//div[@id='_20_fileEntryToolbar']/span/button[4]"));
		selenium.clickAt("//div[@id='_20_fileEntryToolbar']/span/button[2]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_20_title']",
			RuntimeVariables.replace("TestDocument Edit.txt"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//div[@class='portlet-msg-success']",
			"Your request completed successfully.");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.waitForText("//h2[@class='document-title']",
			"TestDocument Edit.txt");
		assertEquals(RuntimeVariables.replace("TestDocument Edit.txt"),
			selenium.getText("//h2[@class='document-title']"));
	}
}