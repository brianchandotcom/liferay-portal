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

package com.liferay.portalweb.portlet.webcontentdisplay.wcstructure.addwcstructure2parentstructurestructure1wcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemoveParentStructureStructure1Structure2WCDTest
	extends BaseTestCase {
	public void testRemoveParentStructureStructure1Structure2WCD()
		throws Exception {
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
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Manage']/ul/li/strong/a/span",
			RuntimeVariables.replace("Manage"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Structures')]");
		assertEquals(RuntimeVariables.replace("Structures"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Structures')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Structures')]",
			RuntimeVariables.replace("Structures"));
		selenium.waitForVisible("//iframe[contains(@src,'scopeStructureType')]");
		selenium.selectFrame("//iframe[contains(@src,'scopeStructureType')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/search_container.js')]");
		selenium.waitForVisible(
			"//tr[contains(.,'WC Structure2 Name')]/td[2]/a");
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure2 Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure2 Name"),
			selenium.getText("//tr[contains(.,'WC Structure2 Name')]/td[3]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure2 Name')]/td[4]/a"));
		selenium.clickAt("//tr[contains(.,'WC Structure2 Name')]/td[3]/a",
			RuntimeVariables.replace("WC Structure2 Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC Structure1 Name"),
			selenium.getText("//a[@id='_166_parentStructureName']"));
		selenium.clickAt("//input[@value='Remove']",
			RuntimeVariables.replace("Remove"));
		selenium.waitForTextNotPresent("//a[@id='_166_parentStructureName']");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure2 Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure2 Name"),
			selenium.getText("//tr[contains(.,'WC Structure2 Name')]/td[3]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure2 Name')]/td[4]/a"));
		selenium.selectFrame("relative=top");
	}
}