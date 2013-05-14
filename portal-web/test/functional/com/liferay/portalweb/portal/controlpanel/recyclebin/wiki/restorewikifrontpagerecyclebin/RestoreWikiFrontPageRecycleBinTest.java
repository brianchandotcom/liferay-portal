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

package com.liferay.portalweb.portal.controlpanel.recyclebin.wiki.restorewikifrontpagerecyclebin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RestoreWikiFrontPageRecycleBinTest extends BaseTestCase {
	public void testRestoreWikiFrontPageRecycleBin() throws Exception {
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
		selenium.clickAt("link=Recycle Bin",
			RuntimeVariables.replace("Recycle Bin"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("FrontPage"),
			selenium.getText("//tr[contains(.,'FrontPage')]/td[1]/span/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Restore')]");
		assertEquals(RuntimeVariables.replace("Restore"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Restore')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Restore')]",
			RuntimeVariables.replace("Restore"));
		selenium.waitForVisible("//div[@class='portlet-msg-alert']");
		assertEquals(RuntimeVariables.replace(
				"An entry with name FrontPage already exists."),
			selenium.getText("//div[@class='portlet-msg-alert']"));
		selenium.clickAt("//input[contains(@value,'Save')]",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("FrontPage"));
	}
}