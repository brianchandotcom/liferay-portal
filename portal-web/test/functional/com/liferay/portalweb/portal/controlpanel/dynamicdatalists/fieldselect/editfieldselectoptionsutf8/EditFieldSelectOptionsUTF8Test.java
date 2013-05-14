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

package com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldselect.editfieldselectoptionsutf8;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditFieldSelectOptionsUTF8Test extends BaseTestCase {
	public void testEditFieldSelectOptionsUTF8() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[9]/div/label"));
		selenium.doubleClickAt("//div[@class='diagram-builder-drop-container']/div[9]",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible("//div[@class='yui3-datatable-x-scroller']");
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//tr[10]/td[1]"));
		assertEquals(RuntimeVariables.replace("option 1, option 2, option 3"),
			selenium.getText("//tr[10]/td[2]"));
		selenium.doubleClickAt("//tr[10]/td[2]",
			RuntimeVariables.replace("option 1, option 2, option 3"));
		selenium.waitForVisible("//div[@class='celleditor-edit-label']");
		assertEquals(RuntimeVariables.replace("Edit option(s)"),
			selenium.getText("//div[@class='celleditor-edit-label']"));
		selenium.type("//input[@value='option 1']",
			RuntimeVariables.replace("\u308a\u3093\u3054"));
		selenium.type("//input[@value='option 2']",
			RuntimeVariables.replace("\u30d0\u30ca\u30ca"));
		selenium.type("//input[@value='option 3']",
			RuntimeVariables.replace("\u30af\u30e9\u30f3\u30d9\u30ea\u30fc"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace(
				"\u308a\u3093\u3054, \u30d0\u30ca\u30ca, \u30af\u30e9\u30f3\u30d9\u30ea\u30fc"),
			selenium.getText("//tr[10]/td[2]"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
	}
}