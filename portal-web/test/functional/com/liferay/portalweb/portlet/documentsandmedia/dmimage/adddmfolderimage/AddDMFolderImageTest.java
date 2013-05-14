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

package com.liferay.portalweb.portlet.documentsandmedia.dmimage.adddmfolderimage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMFolderImageTest extends BaseTestCase {
	public void testAddDMFolderImage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForText("//li[contains(@class,'folder selected')]/a/span[2]",
			"DM Folder Name");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//li[contains(@class,'folder selected')]/a/span[2]"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]");
		assertEquals(RuntimeVariables.replace("Basic Document"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]",
			RuntimeVariables.replace("Basic Document"));
		selenium.waitForPageToLoad("30000");
		selenium.uploadCommonFile("//input[@id='_20_file']",
			RuntimeVariables.replace("Document_1.jpg"));
		selenium.type("//input[@id='_20_title']",
			RuntimeVariables.replace("DM Folder Image Title"));
		selenium.type("//textarea[@id='_20_description']",
			RuntimeVariables.replace("DM Folder Image Description"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForText("//div[@class='portlet-msg-success']",
			"Your request completed successfully.");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("DM Folder Image Title"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Image Title"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//h2[@class='document-title']",
			"DM Folder Image Title");
		assertEquals(RuntimeVariables.replace("DM Folder Image Title"),
			selenium.getText("//h2[@class='document-title']"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-preview-file-image-container']/img"));
		assertEquals(RuntimeVariables.replace("DM Folder Image Description"),
			selenium.getText("//span[@class='document-description']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Download (13k)"),
			selenium.getText("//span[@class='download-document']"));
		assertEquals(RuntimeVariables.replace("Content Type image/jpeg"),
			selenium.getText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div/div[1]"));
	}
}