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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrarydocument.adddiscussion;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDocumentTest extends BaseTestCase {
	public void testAddDocument() throws Exception {
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
		assertTrue(selenium.isVisible("link=Add"));
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a");
		assertEquals(RuntimeVariables.replace("Basic Document"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[5]/a",
			RuntimeVariables.replace("Basic Document"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//input[@id='_20_file']"));
		selenium.type("//input[@id='_20_file']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portal\\permissions\\documentlibrary\\content\\dependencies\\TestDocument.txt"));
		selenium.select("//select[@id='_20_inputPermissionsViewRole']",
			RuntimeVariables.replace("Owner"));
		selenium.clickAt("link=More Options \u00bb",
			RuntimeVariables.replace("More Options \u00bb"));
		selenium.waitForVisible(
			"//input[@id='_20_guestPermissions_ADD_DISCUSSION']");
		assertTrue(selenium.isChecked(
				"//input[@id='_20_guestPermissions_ADD_DISCUSSION']"));
		selenium.clickAt("//input[@id='_20_guestPermissions_ADD_DISCUSSION']",
			RuntimeVariables.replace("Add Discussion"));
		assertFalse(selenium.isChecked(
				"//input[@id='_20_guestPermissions_ADD_DISCUSSION']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_20_groupPermissions_ADD_DISCUSSION']"));
		selenium.clickAt("//input[@id='_20_groupPermissions_ADD_DISCUSSION']",
			RuntimeVariables.replace("Add Discussion"));
		assertFalse(selenium.isChecked(
				"//input[@id='_20_groupPermissions_ADD_DISCUSSION']"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.waitForVisible(
			"//a[contains(@class,'document-link')]/span[@class='entry-title']");
		assertEquals(RuntimeVariables.replace("TestDocument.txt"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
	}
}