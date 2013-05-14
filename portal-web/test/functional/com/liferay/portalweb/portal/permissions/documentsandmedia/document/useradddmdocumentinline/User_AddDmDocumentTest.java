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

package com.liferay.portalweb.portal.permissions.documentsandmedia.document.useradddmdocumentinline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_AddDmDocumentTest extends BaseTestCase {
	public void testUser_AddDmDocument() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Documents and Media Test Page");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Basic Document"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_20_file']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portal\\permissions\\documentsandmedia\\dependencies\\document_1.doc"));
		selenium.type("//input[@id='_20_title']",
			RuntimeVariables.replace("DM Document Title"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//div[@class='portlet-msg-success']",
			"Your request completed successfully.");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("DM Document Title"),
			selenium.getText(
				"//a[@class='document-link']/span[@class='entry-title']"));
		selenium.clickAt("//a[@class='document-link']/span[@class='entry-title']",
			RuntimeVariables.replace("DM Document Title"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//span[@class='toolbar-content']/button[1]",
			"Download");
		assertEquals(RuntimeVariables.replace("Download"),
			selenium.getText("//span[@class='toolbar-content']/button[1]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//span[@class='toolbar-content']/button[2]"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText("//span[@class='toolbar-content']/button[3]"));
		assertEquals(RuntimeVariables.replace("Checkout"),
			selenium.getText("//span[@class='toolbar-content']/button[4]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//span[@class='toolbar-content']/button[5]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("//span[@class='toolbar-content']/button[6]"));
		assertEquals(RuntimeVariables.replace("DM Document Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isPartialText(
				"//span[@class='user-date']/span/span",
				"Uploaded by userfn userln"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
			selenium.getText("//h3[contains(@class,'version')]"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Download (10.0k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
	}
}