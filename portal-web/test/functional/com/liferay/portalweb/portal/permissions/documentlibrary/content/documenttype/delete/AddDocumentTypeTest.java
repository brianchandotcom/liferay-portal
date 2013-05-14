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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documenttype.delete;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDocumentTypeTest extends BaseTestCase {
	public void testAddDocumentType() throws Exception {
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
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Manage']/ul/li/strong/a",
			RuntimeVariables.replace("Manage"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		assertEquals(RuntimeVariables.replace("Document Types"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
		selenium.click("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		selenium.waitForVisible("//iframe");
		selenium.selectFrame("//iframe");
		selenium.waitForVisible("link=Add");
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForVisible("//input[@id='_20_name']");
		selenium.type("//input[@id='_20_name']",
			RuntimeVariables.replace("Document Type Name"));
		selenium.waitForVisible(
			"xPath=(//div[@class='diagram-builder-field-label'])[11]");
		selenium.dragAndDropToObject("xPath=(//div[@class='diagram-builder-field-label'])[11]",
			"xPath=(//div[@class='diagram-builder-field-label'])[11]");
		selenium.waitForVisible(
			"//div[contains(@class,'aui-form-builder-text-field-content')]/label");
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText(
				"//div[contains(@class,'aui-form-builder-text-field-content')]/label"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
	}
}