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

package com.liferay.portalweb.socialofficesite.documents.dmdocument.cancelcheckoutdmfolderdocumentsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewCancelCheckoutDMFolderDocumentSiteTest extends BaseTestCase {
	public void testViewCancelCheckoutDMFolderDocumentSite()
		throws Exception {
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
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@class='document-library-breadcrumb']/ul/li/span/a"));
		assertEquals(RuntimeVariables.replace(
				"Access these files offline using Liferay Sync."),
			selenium.getText("//div[@id='_20_syncNotificationContent']/a"));
		assertTrue(selenium.isVisible("//span[@class='document-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		assertTrue(selenium.isElementNotPresent("//img[@class='locked-icon']"));
		Thread.sleep(1000);
		assertTrue(selenium.isVisible("//button[@title='Descriptive View']"));
		selenium.clickAt("//button[@title='Descriptive View']",
			RuntimeVariables.replace("Descriptive View"));
		selenium.waitForElementPresent(
			"//button[contains(@class,'active') and contains(@title,'Descriptive')]");
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		assertTrue(selenium.isElementNotPresent("//img[@class='locked-icon']"));
		Thread.sleep(1000);
		assertTrue(selenium.isVisible("//button[@title='List View']"));
		selenium.clickAt("//button[@title='List View']",
			RuntimeVariables.replace("List View"));
		selenium.waitForElementPresent(
			"//button[contains(@class,'active') and contains(@title,'List')]");
		selenium.waitForVisible(
			"//td[contains(@id,'SearchContainer_col-title_row-1')]");
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText(
				"//td[contains(@id,'SearchContainer_col-title_row-1')]"));
		assertTrue(selenium.isElementNotPresent("//img[@title='Lock']"));
		Thread.sleep(1000);
		assertTrue(selenium.isVisible("//button[@title='Icon View']"));
		selenium.clickAt("//button[@title='Icon View']",
			RuntimeVariables.replace("Icon View"));
		selenium.waitForElementPresent(
			"//button[contains(@class,'active') and contains(@title,'Icon')]");
		selenium.waitForVisible(
			"//a[contains(@class,'document-link')]/span[@class='entry-title']");
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Document Title"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent(
				"You now have a lock on this document. No one else can edit this document until you unlock it."));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertEquals(RuntimeVariables.replace("Download"),
			selenium.getText(
				"//span[@class='toolbar-content']/button[contains(.,'Download')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Checkout"),
			selenium.getText(
				"//span[@class='toolbar-content']/button[.='Checkout']/span[2]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//span[@class='toolbar-content']/button[contains(.,'Edit')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//span[@class='toolbar-content']/button[contains(.,'Move')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//span[@class='toolbar-content']/button[contains(.,'Permissions')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//span[@class='toolbar-content']/button[contains(.,'Delete')]/span[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//span[@class='document-title']"));
		assertTrue(selenium.isVisible("//span[@class='document-thumbnail']/img"));
		assertTrue(selenium.isPartialText(
				"//span[@class='user-date']/span/span", "Uploaded by Joe Bloggs"));
		assertTrue(selenium.isVisible("//span[@class='lfr-asset-ratings']"));
		assertEquals(RuntimeVariables.replace("Version History"),
			selenium.getText(
				"//div[@class='lfr-document-library-versions']/div/div/div"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
			selenium.getText("//span[@class='version-number']"));
		assertEquals(RuntimeVariables.replace("By: Joe Bloggs"),
			selenium.getText("//span[@class='user-name']"));
		assertTrue(selenium.isVisible("//span[@class='modified-date']"));
		assertEquals(RuntimeVariables.replace("Size: 21.5k"),
			selenium.getText("//span[@class='size']"));
		assertEquals(RuntimeVariables.replace(
				"No comments yet. Be the first. Subscribe to Comments"),
			selenium.getText("//fieldset[@class='fieldset add-comment ']/div"));
		assertEquals(RuntimeVariables.replace("Be the first."),
			selenium.getText("//fieldset[@class='fieldset add-comment ']/div/a"));
		assertEquals(RuntimeVariables.replace("Subscribe to Comments"),
			selenium.getText("//span[@class='subscribe-link']/a/span"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
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