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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.editwcsubfolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditWCSubfolderTest extends BaseTestCase {
	public void testEditWCSubfolder() throws Exception {
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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC Folder Name"),
			selenium.getText(
				"//div[@data-title='WC Folder Name']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='WC Folder Name']/a/span[@class='entry-title']",
			RuntimeVariables.replace("WC Folder Name"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//div[@data-title='WC Subfolder Name']/a/div[@class='entry-thumbnail']/img");
		assertEquals(RuntimeVariables.replace("WC Subfolder Name"),
			selenium.getText(
				"//div[@data-title='WC Subfolder Name']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='WC Subfolder Name']/span[@class='entry-action overlay']/span/ul/li/strong/a",
			RuntimeVariables.replace("WC Subfolder Name Action Overlay"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_15_name']",
			RuntimeVariables.replace("WC Subfolder Name Edited"));
		selenium.type("//textarea[@id='_15_description']",
			RuntimeVariables.replace("WC Subfolder Description Edited"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible(
				"//div[@data-title='WC Subfolder Name Edited']/a/div[@class='entry-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("WC Subfolder Name Edited"),
			selenium.getText(
				"//div[@data-title='WC Subfolder Name Edited']/a/span[@class='entry-title']"));
	}
}