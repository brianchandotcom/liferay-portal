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

package com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldboolean.editfieldbooleanname;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditFieldBooleanNameTest extends BaseTestCase {
	public void testEditFieldBooleanName() throws Exception {
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
		selenium.clickAt("link=Dynamic Data Lists",
			RuntimeVariables.replace("Dynamic Data Lists"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@id='_167_manageDDMStructuresLink']");
		selenium.clickAt("//a[@id='_167_manageDDMStructuresLink']",
			RuntimeVariables.replace("Manage Data Definitions"));
		selenium.waitForVisible("//iframe");
		selenium.selectFrame("//iframe");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Edit')]/a");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Edit')]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Edit')]/a"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[1]/div/label"));
		selenium.doubleClickAt("//div[@class='diagram-builder-drop-container']/div[1]/div/label",
			RuntimeVariables.replace("Boolean"));
		selenium.waitForVisible("//div[@class='yui3-datatable-x-scroller']");
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText("//tr[4]/td[1]"));
		assertTrue(selenium.isPartialText("//tr[4]/td[2]", "boolean"));
		selenium.doubleClickAt("//tr[4]/td[2]",
			RuntimeVariables.replace("boolean"));
		selenium.waitForVisible(
			"//input[contains(@class,'aui-celleditor-element')]");
		selenium.type("//input[contains(@class,'aui-celleditor-element')]",
			RuntimeVariables.replace("booleannameedited"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//input[@name='booleannameedited']");
		assertTrue(selenium.isVisible("//input[@name='booleannameedited']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
	}
}