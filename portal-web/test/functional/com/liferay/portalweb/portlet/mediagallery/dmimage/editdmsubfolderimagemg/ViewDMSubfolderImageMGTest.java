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

package com.liferay.portalweb.portlet.mediagallery.dmimage.editdmsubfolderimagemg;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMSubfolderImageMGTest extends BaseTestCase {
	public void testViewDMSubfolderImageMG() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//ul[@class='top-links-navigation']/li[1]/span"));
		assertEquals(RuntimeVariables.replace("Recent"),
			selenium.getText("//ul[@class='top-links-navigation']/li[2]/span"));
		assertEquals(RuntimeVariables.replace("Mine"),
			selenium.getText("//ul[@class='top-links-navigation']/li[3]/span"));
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Subfolder Name"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("DM Subfolder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Subfolder Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("DM Subfolder Image Title Edit"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("DM Subfolder Image Title Edit"));
		Thread.sleep(1000);
		selenium.waitForVisible("//img[@class='image-viewer-image']");
		assertEquals(RuntimeVariables.replace(
				"DM Subfolder Image Title Edit - DM Subfolder Image Description Edit"),
			selenium.getText("//div[@class='image-viewer-caption']"));
		assertEquals(RuntimeVariables.replace("Image 1 of 1"),
			selenium.getText("//div[@class='image-viewer-info']"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-image-gallery-actions']/div/div/span[1]/a/img[@alt='Download (13k)']"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-image-gallery-actions']/div/div/span[2]/a/img[@alt='View']"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-image-gallery-actions']/div/div/span[3]/a/img[@alt='Edit']"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-image-gallery-actions']/div/div/span[4]/a/img[@alt='Permissions']"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-image-gallery-actions']/div/div/span[5]/a/img[@alt='Move to the Recycle Bin']"));
		assertTrue(selenium.isVisible(
				"//span[@class='image-gallery-player-content']/span/span/button[@id='play']"));
		assertTrue(selenium.isVisible(
				"//span[@class='image-gallery-player-content']/span/span/button[@id='pause']"));
	}
}