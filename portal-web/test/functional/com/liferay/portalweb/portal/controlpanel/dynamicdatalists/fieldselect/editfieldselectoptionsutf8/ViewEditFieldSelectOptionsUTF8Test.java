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
public class ViewEditFieldSelectOptionsUTF8Test extends BaseTestCase {
	public void testViewEditFieldSelectOptionsUTF8() throws Exception {
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
		assertEquals(RuntimeVariables.replace("\u308a\u3093\u3054"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[9]/div/select/option[1]"));
		assertEquals(RuntimeVariables.replace("\u30d0\u30ca\u30ca"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[9]/div/select/option[2]"));
		assertEquals(RuntimeVariables.replace(
				"\u30af\u30e9\u30f3\u30d9\u30ea\u30fc"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[9]/div/select/option[3]"));
		selenium.selectFrame("relative=top");
	}
}