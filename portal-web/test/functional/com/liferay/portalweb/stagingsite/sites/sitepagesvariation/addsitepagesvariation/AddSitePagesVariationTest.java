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

package com.liferay.portalweb.stagingsite.sites.sitepagesvariation.addsitepagesvariation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSitePagesVariationTest extends BaseTestCase {
	public void testAddSitePagesVariation() throws Exception {
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
		selenium.waitForVisible("link=Site Name");
		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'local-staging')]"));
		assertTrue(selenium.isPartialText("//span/a[contains(.,'Staging')]",
				"Staging"));
		selenium.clickAt("//span/a[contains(.,'Staging')]",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Main Site Pages Variation of Site Name"),
			selenium.getText("//span[@class='layout-set-branch-description']"));
		Thread.sleep(1000);
		selenium.clickAt("//strong/a",
			RuntimeVariables.replace("Staging dropdown"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Manage Site Pages Variations')]/a");
		assertEquals(RuntimeVariables.replace("Manage Site Pages Variations"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Manage Site Pages Variations')]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Manage Site Pages Variations')]/a",
			RuntimeVariables.replace("Manage Site Pages Variations"));
		selenium.waitForVisible("//iframe[contains(@id,'layoutSetBranches')]");
		selenium.selectFrame("//iframe[contains(@id,'layoutSetBranches')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//input[@id='_170_addBranchButton']");
		selenium.clickAt("//input[@id='_170_addBranchButton']",
			RuntimeVariables.replace("Add Site Pages Variation"));
		selenium.waitForVisible("//input[@id='_170_name']");
		selenium.type("//input[@id='_170_name']",
			RuntimeVariables.replace("Site Pages Variation Name"));
		selenium.type("//textarea[@id='_170_description']",
			RuntimeVariables.replace("Site Pages Variation Description"));
		selenium.clickAt("//input[@value='Add']",
			RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site page variation was added."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Site Name");
		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'local-staging')]"));
		assertTrue(selenium.isPartialText("//span/a[contains(.,'Staging')]",
				"Staging"));
		selenium.clickAt("//span/a[contains(.,'Staging')]",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Main Variation"),
			selenium.getText(
				"//ul[@class='tabview-list site-variations-tabview-list']/li[contains(.,'Main Variation')]/span/span/span"));
		assertEquals(RuntimeVariables.replace("Site Pages Variation Name"),
			selenium.getText(
				"//ul[@class='tabview-list site-variations-tabview-list']/li[contains(.,'Site Pages Variation Name')]/span/span/a"));
		assertEquals(RuntimeVariables.replace("Manage Site Pages Variations"),
			selenium.getText("//a[@id='_170_manageLayoutSetBranches']"));
	}
}