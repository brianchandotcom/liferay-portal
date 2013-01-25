/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmdocument;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMDocumentTest extends BaseTestCase {
	public void testViewDMDocument() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		assertEquals(RuntimeVariables.replace("My Documents"),
			selenium.getText("//nav/ul/li[contains(.,'My Documents')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'My Documents')]/a/span",
			RuntimeVariables.replace("My Documents"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@class='document-library-breadcrumb']/ul/li/span/a"));
		assertEquals(RuntimeVariables.replace(
				"Access these files offline using Liferay Sync."),
			selenium.getText("//div[@id='_20_syncNotificationContent']/a"));
		assertTrue(selenium.isVisible("//span[@class='document-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("DM Document Title"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Document Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertEquals(RuntimeVariables.replace("Download"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Download')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Checkout"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Checkout')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Edit')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Move')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Permissions')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//span[@class='aui-toolbar-content']/button[contains(.,'Delete')]/span[2]"));
		assertEquals(RuntimeVariables.replace("DM Document Title"),
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
			selenium.getText(
				"//fieldset[@class='aui-fieldset add-comment ']/div"));
		assertEquals(RuntimeVariables.replace("Be the first."),
			selenium.getText(
				"//fieldset[@class='aui-fieldset add-comment ']/div/a"));
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
		assertEquals(RuntimeVariables.replace(
				"Automatically Extracted Metadata"),
			selenium.getText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div/div/span"));
		assertEquals(RuntimeVariables.replace("Content Type application/msword"),
			selenium.getText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div[1]"));
		assertEquals(RuntimeVariables.replace("Last Author Liferay"),
			selenium.getText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div[2]"));
		assertEquals(RuntimeVariables.replace(
				"Application Name Microsoft Office Word"),
			selenium.getText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div[3]"));
		assertEquals(RuntimeVariables.replace("# of Characters 170"),
			selenium.getText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div[4]"));
		assertEquals(RuntimeVariables.replace(
				"Last Saved Fri Aug 10 18:39:00 GMT 2012"),
			selenium.getText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div[5]"));
		assertEquals(RuntimeVariables.replace("# of Pages 1"),
			selenium.getText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div[6]"));
		assertEquals(RuntimeVariables.replace("Revision Number 2"),
			selenium.getText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div[7]"));
		assertEquals(RuntimeVariables.replace("# of Words 50"),
			selenium.getText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div[8]"));
		assertEquals(RuntimeVariables.replace("Template Normal.dotm"),
			selenium.getText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div[9]"));
		assertEquals(RuntimeVariables.replace("Author Liferay"),
			selenium.getText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div[10]"));
		assertEquals(RuntimeVariables.replace(
				"Creation Date Fri Aug 10 18:39:00 GMT 2012"),
			selenium.getText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div[11]"));
	}
}