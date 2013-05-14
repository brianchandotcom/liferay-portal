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

package com.liferay.portalweb.portlet.mediagallery.dmimage.adddmfolderimagemultiplemg;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFolderImageMultipleMGTest extends BaseTestCase {
	public void testViewDMFolderImageMultipleMG() throws Exception {
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
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("DM Folder Image1 Title"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("DM Folder Image1 Title"));
		Thread.sleep(1000);
		selenium.waitForVisible("//img[@class='image-viewer-image']");
		assertEquals(RuntimeVariables.replace(
				"DM Folder Image1 Title - DM Folder Image1 Description"),
			selenium.getText("//div[@class='image-viewer-caption']"));
		assertEquals(RuntimeVariables.replace("Image 1 of 3"),
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
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("DM Folder Image2 Title"),
			selenium.getText("xPath=(//span[@class='image-title'])[2]"));
		selenium.clickAt("xPath=(//span[@class='image-title'])[2]",
			RuntimeVariables.replace("DM Folder Image2 Title"));
		Thread.sleep(1000);
		selenium.waitForVisible("//img[@class='image-viewer-image']");
		assertEquals(RuntimeVariables.replace(
				"DM Folder Image2 Title - DM Folder Image2 Description"),
			selenium.getText("//div[@class='image-viewer-caption']"));
		assertEquals(RuntimeVariables.replace("Image 2 of 3"),
			selenium.getText("//div[@class='image-viewer-info']"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-image-gallery-actions']/div/div/span[1]/a/img[@alt='Download (30k)']"));
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
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("DM Folder Image3 Title"),
			selenium.getText("xPath=(//span[@class='image-title'])[3]"));
		selenium.clickAt("xPath=(//span[@class='image-title'])[3]",
			RuntimeVariables.replace("DM Folder Image3 Title"));
		Thread.sleep(1000);
		selenium.waitForVisible("//img[@class='image-viewer-image']");
		assertEquals(RuntimeVariables.replace(
				"DM Folder Image3 Title - DM Folder Image3 Description"),
			selenium.getText("//div[@class='image-viewer-caption']"));
		assertEquals(RuntimeVariables.replace("Image 3 of 3"),
			selenium.getText("//div[@class='image-viewer-info']"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-image-gallery-actions']/div/div/span[1]/a/img[@alt='Download (16k)']"));
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