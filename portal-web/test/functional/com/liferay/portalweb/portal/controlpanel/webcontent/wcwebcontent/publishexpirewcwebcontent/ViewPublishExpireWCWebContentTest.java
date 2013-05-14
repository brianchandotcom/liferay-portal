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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.publishexpirewcwebcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPublishExpireWCWebContentTest extends BaseTestCase {
	public void testViewPublishExpireWCWebContent() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
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
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC WebContent Title')]/td[1]/input[@type='checkbox']"));

		String webcontentID = selenium.getText(
				"//tr[contains(.,'WC WebContent Title')]/td[2]");
		RuntimeVariables.setValue("webcontentID", webcontentID);
		assertEquals(RuntimeVariables.replace("${webcontentID}"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[2]"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText(
				"//tr[contains(.,'WC WebContent Title')]/td[3]/span/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[4]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC WebContent Title')]/td[5]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC WebContent Title')]/td[6]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[7]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC WebContent Title')]/td[8]/span/span/ul/li/strong/a"));
		selenium.clickAt("//tr[contains(.,'WC WebContent Title')]/td[8]/span/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Permissions')]"));
		assertEquals(RuntimeVariables.replace("View (Opens New Window)"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'View')]"));
		assertEquals(RuntimeVariables.replace("Copy"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Copy')]"));
		assertEquals(RuntimeVariables.replace("Expire"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Expire')]"));
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
		selenium.clickAt("//tr[contains(.,'WC WebContent Title')]/td[3]/span/a",
			RuntimeVariables.replace("WC WebContent Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace(
				"Web Content can be any content you would like to add to a site, such as articles, a FAQ, or a news item. Administrators can manage content, as well as assign user roles and permissions. Users may add, edit, approve, or view content depending on their role. Or disable for all portlets."),
			selenium.getText("//div[@id='cpContextPanelTemplate']"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isPartialText("//span[@class='workflow-id']",
				RuntimeVariables.getValue("webcontentID")));
		assertEquals(RuntimeVariables.replace("Version: 1.1"),
			selenium.getText("//span[@class='workflow-version']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//button[.='Permissions']"));
		assertEquals(RuntimeVariables.replace("View History"),
			selenium.getText("//button[.='View History']"));
		assertEquals(RuntimeVariables.replace("Default"),
			selenium.getText("//span[@id='_15_structureNameLabel']"));
		assertEquals(RuntimeVariables.replace("None"),
			selenium.getText("//span[@class='template-name-label']"));
		assertEquals(RuntimeVariables.replace("English (United States)"),
			selenium.getText("//span[@id='_15_textLanguageId']"));
		assertEquals("WC WebContent Title",
			selenium.getValue("//input[@id='_15_title_en_US']"));
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.selectFrame("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.waitForText("//body[contains(.,'WC WebContent Content')]",
			"WC WebContent Content");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//body[contains(.,'WC WebContent Content')]"));
		selenium.selectFrame("relative=top");
		assertTrue(selenium.isChecked("//input[@id='_15_indexableCheckbox']"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//span[@class='article-name']"));
		assertEquals(RuntimeVariables.replace("Content (Modified)"),
			selenium.getText("//a[@id='_15_contentLink']"));
		assertEquals(RuntimeVariables.replace("Abstract (Modified)"),
			selenium.getText("//a[@id='_15_abstractLink']"));
		assertEquals(RuntimeVariables.replace("Categorization (Modified)"),
			selenium.getText("//a[@id='_15_categorizationLink']"));
		assertEquals(RuntimeVariables.replace("Schedule (Modified)"),
			selenium.getText("//a[@id='_15_scheduleLink']"));
		assertEquals(RuntimeVariables.replace("Display Page (Modified)"),
			selenium.getText("//a[@id='_15_displayPageLink']"));
		assertEquals(RuntimeVariables.replace("Related Assets (Modified)"),
			selenium.getText("//a[@id='_15_relatedAssetsLink']"));
		assertEquals(RuntimeVariables.replace("Custom Fields (Modified)"),
			selenium.getText("//a[@id='_15_customFieldsLink']"));
		assertEquals(RuntimeVariables.replace(
				"A new version will be created automatically if this content is modified."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertEquals("Save as Draft",
			selenium.getValue("//input[@value='Save as Draft']"));
		assertEquals("Publish", selenium.getValue("//input[@value='Publish']"));
		assertEquals("Cancel", selenium.getValue("//input[@value='Cancel']"));
		selenium.clickAt("//button[.='View History']",
			RuntimeVariables.replace("View History"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertTrue(selenium.isVisible("//input[@value='Expire']"));
		assertTrue(selenium.isVisible("//input[@value='Delete']"));
		assertTrue(selenium.isVisible("//tr[3]/td[1]/input[@type='checkbox']"));
		assertEquals(RuntimeVariables.replace("${webcontentID}"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("1.0"),
			selenium.getText("//tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("Expired"),
			selenium.getText("//tr[3]/td[5]"));
		assertTrue(selenium.isVisible("//tr[3]/td[6]"));
		assertTrue(selenium.isVisible("//tr[3]/td[7]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[3]/td[8]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[3]/td[9]/span[@title='Actions']/ul/li/strong/a/span"));
		assertTrue(selenium.isVisible("//tr[4]/td[1]/input[@type='checkbox']"));
		assertEquals(RuntimeVariables.replace("${webcontentID}"),
			selenium.getText("//tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//tr[4]/td[3]"));
		assertEquals(RuntimeVariables.replace("1.1"),
			selenium.getText("//tr[4]/td[4]"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//tr[4]/td[5]"));
		assertTrue(selenium.isVisible("//tr[4]/td[6]"));
		assertTrue(selenium.isVisible("//tr[4]/td[7]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[4]/td[8]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[4]/td[9]/span[@title='Actions']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Showing 2 results."),
			selenium.getText("//div[@class='search-results']"));
	}
}