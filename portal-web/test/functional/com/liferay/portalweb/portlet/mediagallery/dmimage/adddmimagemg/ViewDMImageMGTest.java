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

package com.liferay.portalweb.portlet.mediagallery.dmimage.adddmimagemg;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMImageMGTest extends BaseTestCase {
	public void testViewDMImageMG() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Mine"),
			selenium.getText("//ul[@class='top-links-navigation']/li[3]/span"));
		assertTrue(selenium.isVisible("//input[@title='Search Documents']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertTrue(selenium.isVisible(
				"//a[@title='DM Image Title - DM Image Description']/span[@class='image-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("DM Image Title"),
			selenium.getText(
				"//a[@title='DM Image Title - DM Image Description']/span[@class='image-title']"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
		assertTrue(selenium.isVisible("//span[@class='lfr-asset-avatar']/img"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//div[@class='lfr-asset-name']/h4"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Permissions')]"));
		assertEquals(RuntimeVariables.replace("Add Folder"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Add Folder')]"));
		assertEquals(RuntimeVariables.replace("Add Repository"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Add Repository')]"));
		assertEquals(RuntimeVariables.replace("Multiple Media"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Multiple Media')]"));
		assertEquals(RuntimeVariables.replace("Add Media"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Add Media')]"));
		assertEquals(RuntimeVariables.replace("View Slide Show"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'View Slide Show')]"));
		assertEquals(RuntimeVariables.replace("Add Shortcut"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Add Shortcut')]"));
		assertEquals(RuntimeVariables.replace("Access from Desktop"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Access from Desktop')]"));
		selenium.clickAt("//a[@title='DM Image Title - DM Image Description']/span[@class='image-title']",
			RuntimeVariables.replace("DM Image Title"));
		selenium.waitForElementNotPresent(
			"//div[contains(@class,'aui-image-gallery-hidden')]");
		selenium.waitForVisible("//img[@class='aui-image-viewer-image']");
		selenium.waitForText("//div[@class='aui-image-viewer-caption']",
			"DM Image Title - DM Image Description");
		assertEquals(RuntimeVariables.replace("Image 1 of 1"),
			selenium.getText("//div[@class='aui-image-viewer-info']"));
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
				"//span[@class='aui-image-gallery-player-content']/span/span/button[@id='play']"));
		assertTrue(selenium.isVisible(
				"//span[@class='aui-image-gallery-player-content']/span/span/button[@id='pause']"));
	}
}