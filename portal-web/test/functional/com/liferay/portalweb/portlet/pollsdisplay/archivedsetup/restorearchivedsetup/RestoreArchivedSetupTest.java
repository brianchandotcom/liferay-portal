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

package com.liferay.portalweb.portlet.pollsdisplay.archivedsetup.restorearchivedsetup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RestoreArchivedSetupTest extends BaseTestCase {
	public void testRestoreArchivedSetup() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Polls Display Test Page",
			RuntimeVariables.replace("Polls Display Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForVisible(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//div[@class='archived-setups']/span/a/span");
		assertEquals(RuntimeVariables.replace("Archive/Restore Setup"),
			selenium.getText("//div[@class='archived-setups']/span/a/span"));
		selenium.clickAt("//div[@class='archived-setups']/span/a/span",
			RuntimeVariables.replace("Archive/Restore Setup"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Archived Setup"),
			selenium.getText("//tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[3]/td[2]"));
		assertTrue(selenium.isVisible("//tr[3]/td[3]"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//a[@id='_86_ocerSearchContainer_1_menu_restore']");
		assertEquals(RuntimeVariables.replace("Restore"),
			selenium.getText(
				"//a[@id='_86_ocerSearchContainer_1_menu_restore']"));
		selenium.clickAt("//a[@id='_86_ocerSearchContainer_1_menu_restore']",
			RuntimeVariables.replace("Restore"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Polls Display Test Page",
			RuntimeVariables.replace("Polls Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent("//div/span[1]/span/span/input"));
		assertTrue(selenium.isElementNotPresent("//div/span[2]/span/span/input"));
		assertTrue(selenium.isElementNotPresent("//div/span[3]/span/span/input"));
		assertFalse(selenium.isTextPresent("PD Question ChoiceA"));
		assertFalse(selenium.isTextPresent("PD Question ChoiceB"));
		assertFalse(selenium.isTextPresent("PD Question ChoiceC"));
		assertEquals(RuntimeVariables.replace(
				"Please configure this portlet to make it visible to all users."),
			selenium.getText(
				"//div[@class='portlet-configuration portlet-msg-info']"));
	}
}