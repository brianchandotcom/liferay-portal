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

package com.liferay.portalweb.socialofficesite.documents.dmdocument.editdmfolderdocumentsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditDMFolderDocumentSiteTest extends BaseTestCase {
	public void testEditDMFolderDocumentSite() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isVisible("//input[@class='search-input']"));
		selenium.type("//input[@class='search-input']",
			RuntimeVariables.replace("Open"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
			RuntimeVariables.replace("Open Site Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Documents"),
			selenium.getText("//nav/ul/li[contains(.,'Documents')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Documents')]/a/span",
			RuntimeVariables.replace("Documents"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForText("//li[@class='folder selected']/a",
			"DM Folder Name");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//li[@class='folder selected']/a"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Document Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//span[@class='document-title']"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//button[contains(.,'Edit')]"));
		selenium.clickAt("//button[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_20_title']",
			RuntimeVariables.replace("DM Folder Document Title Edit"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title Edit"),
			selenium.getText("//span[@class='document-title']"));
		assertTrue(selenium.isVisible("//span[@class='document-thumbnail']/img"));
		assertTrue(selenium.isPartialText(
				"//span[@class='user-date']/span/span", "Uploaded by Joe Bloggs"));
		assertTrue(selenium.isVisible("//span[@class='lfr-asset-ratings']"));
		assertEquals(RuntimeVariables.replace("Version History"),
			selenium.getText(
				"//div[@class='lfr-document-library-versions']/div/div/div"));
		assertEquals(RuntimeVariables.replace("Version 1.1"),
			selenium.getText("xPath=(//span[@class='version-number'])[1]"));
		assertEquals(RuntimeVariables.replace("By: Joe Bloggs"),
			selenium.getText("xPath=(//span[@class='user-name'])[1]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[@class='modified-date'])[1]"));
		assertEquals(RuntimeVariables.replace("Size: 21.5k"),
			selenium.getText("xPath=(//span[@class='size'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"No comments yet. Be the first. Subscribe to Comments"),
			selenium.getText(
				"xPath=(//fieldset[@class='fieldset add-comment ']/div)[1]"));
		assertEquals(RuntimeVariables.replace("Be the first."),
			selenium.getText(
				"xPath=(//fieldset[@class='fieldset add-comment ']/div/a)[1]"));
		assertEquals(RuntimeVariables.replace("Subscribe to Comments"),
			selenium.getText(
				"xPath=(//span[@class='subscribe-link']/a/span)[1]"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
			selenium.getText("xPath=(//span[@class='version-number'])[2]"));
		assertEquals(RuntimeVariables.replace("By: Joe Bloggs"),
			selenium.getText("xPath=(//span[@class='user-name'])[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[@class='modified-date'])[2]"));
		assertEquals(RuntimeVariables.replace("Size: 21.5k"),
			selenium.getText("xPath=(//span[@class='size'])[2]"));
		assertEquals(RuntimeVariables.replace(
				"No comments yet. Be the first. Subscribe to Comments"),
			selenium.getText(
				"xPath=(//fieldset[@class='fieldset add-comment ']/div)[2]"));
		assertEquals(RuntimeVariables.replace("Be the first."),
			selenium.getText(
				"xPath=(//fieldset[@class='fieldset add-comment ']/div/a)[2]"));
		assertEquals(RuntimeVariables.replace("Subscribe to Comments"),
			selenium.getText(
				"xPath=(//span[@class='subscribe-link']/a/span)[2]"));
		assertEquals(RuntimeVariables.replace("Version 1.1"),
			selenium.getText("//h3[contains(@class,'version')]"));
		assertEquals(RuntimeVariables.replace("Last Updated by Joe Bloggs"),
			selenium.getText("//div[@class='lfr-asset-icon lfr-asset-author']"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-asset-icon lfr-asset-date']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Download (21.5k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
		assertEquals(RuntimeVariables.replace("Get URL or WebDAV URL."),
			selenium.getText("//span[@class='webdav-url']"));
	}
}