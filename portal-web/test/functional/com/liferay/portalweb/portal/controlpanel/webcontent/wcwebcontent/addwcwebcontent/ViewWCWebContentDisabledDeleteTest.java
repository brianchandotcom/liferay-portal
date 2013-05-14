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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCWebContentDisabledDeleteTest extends BaseTestCase {
	public void testViewWCWebContentDisabledDelete() throws Exception {
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
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//a[@class='entry-link']/span"));
		assertFalse(selenium.isChecked("//input[@id='_15_allRowIdsCheckbox']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_15_rowIdsJournalArticleCheckbox']"));
		assertFalse(selenium.isVisible(
				"//span[@title='Actions']/ul/li/strong/a/span"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
		selenium.clickAt("//input[@id='_15_rowIdsJournalArticleCheckbox']",
			RuntimeVariables.replace("WC WebContent Title CheckBox"));
		assertTrue(selenium.isChecked(
				"//input[@id='_15_rowIdsJournalArticleCheckbox']"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]");
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
		assertTrue(selenium.isChecked(
				"//input[@id='_15_rowIdsJournalArticleCheckbox']"));
		selenium.clickAt("//input[@id='_15_rowIdsJournalArticleCheckbox']",
			RuntimeVariables.replace("WC WebContent Title CheckBox"));
		assertFalse(selenium.isChecked(
				"//input[@id='_15_rowIdsJournalArticleCheckbox']"));
		assertFalse(selenium.isVisible(
				"//span[@title='Actions']/ul/li/strong/a/span"));
		assertFalse(selenium.isChecked(
				"//input[@id='_15_rowIdsJournalArticleCheckbox']"));
		selenium.clickAt("//input[@id='_15_rowIdsJournalArticleCheckbox']",
			RuntimeVariables.replace("WC WebContent Title CheckBox"));
		assertTrue(selenium.isChecked(
				"//input[@id='_15_rowIdsJournalArticleCheckbox']"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]");
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
		assertTrue(selenium.isChecked(
				"//input[@id='_15_rowIdsJournalArticleCheckbox']"));
		selenium.clickAt("//input[@id='_15_rowIdsJournalArticleCheckbox']",
			RuntimeVariables.replace("WC WebContent Title CheckBox"));
		assertFalse(selenium.isChecked(
				"//input[@id='_15_rowIdsJournalArticleCheckbox']"));
		assertFalse(selenium.isVisible(
				"//span[@title='Actions']/ul/li/strong/a/span"));
	}
}