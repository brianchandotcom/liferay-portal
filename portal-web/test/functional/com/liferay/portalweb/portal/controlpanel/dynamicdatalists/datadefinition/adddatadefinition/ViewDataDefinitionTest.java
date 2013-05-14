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

package com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinition;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDataDefinitionTest extends BaseTestCase {
	public void testViewDataDefinition() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Manage Data Definitions"),
			selenium.getText(
				"//span[@class='lfr-toolbar-button view-structures ']/a"));
		selenium.clickAt("//span[@class='lfr-toolbar-button view-structures ']/a",
			RuntimeVariables.replace("Manage Data Definitions"));
		selenium.waitForVisible("//iframe[contains(@src,'dynamicdatalists')]");
		selenium.selectFrame("//iframe[contains(@src,'dynamicdatalists')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/event-tap/event-tap-min.js')]");
		selenium.waitForVisible("//input[@name='_166_keywords']");
		selenium.type("//input[@name='_166_keywords']",
			RuntimeVariables.replace("Data"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Data Definition"),
			selenium.getText("//tr[3]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Data Definition"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[1]/div/label"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[2]/div/label"));
		assertEquals(RuntimeVariables.replace("Decimal"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[3]/div/label"));
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[4]/div/label"));
		assertEquals(RuntimeVariables.replace("File Upload"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[5]/div/label"));
		assertEquals(RuntimeVariables.replace("HTML"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[6]/div/label"));
		assertEquals(RuntimeVariables.replace("Integer"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[7]/div/label"));
		assertEquals(RuntimeVariables.replace("Link to Page"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[8]/div/label"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[9]/div/label"));
		assertEquals(RuntimeVariables.replace("Radio"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[10]/div/label"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[11]/div/label"));
		selenium.selectFrame("relative=top");
	}
}