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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.documentversion;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDocumentVersionTest extends BaseTestCase {
	public void testAddDocumentVersion() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/document-library-document-version-community/");
		selenium.clickAt("link=Document Library Page",
			RuntimeVariables.replace("Document Library Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test1 Folder1"),
			selenium.getText("//div/a/span[2]"));
		selenium.clickAt("//div/a/span[2]",
			RuntimeVariables.replace("Test1 Folder1"));
		selenium.waitForVisible(
			"//li[@class='folder selected']/a/span[contains(.,'Test1 Folder1')]");
		assertEquals(RuntimeVariables.replace("Test1 Folder1"),
			selenium.getText(
				"//li[@class='folder selected']/a/span[contains(.,'Test1 Folder1')]"));
		selenium.clickAt("//h1[@class='portlet-title']",
			RuntimeVariables.replace("Documents and Media"));
		selenium.waitForElementPresent("//div[@id='column-1_shim']");
		selenium.clickAt("//span[2]/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_20_file']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portal\\dbupgrade\\sampledata610\\documentlibrary\\documentversion\\dependencies\\test_document2.txt"));
		selenium.type("//input[@id='_20_title']",
			RuntimeVariables.replace("Test2 Document2.txt"));
		selenium.type("//textarea[@id='_20_description']",
			RuntimeVariables.replace("This is test2 document2."));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Test2 Document2.txt"),
			selenium.getText("//div/a/span[2]"));
		selenium.clickAt("//div/a/span[2]",
			RuntimeVariables.replace("Test2 Document2.txt"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("1.1"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("1.0"),
			selenium.getText("//tr[4]/td[2]"));
	}
}