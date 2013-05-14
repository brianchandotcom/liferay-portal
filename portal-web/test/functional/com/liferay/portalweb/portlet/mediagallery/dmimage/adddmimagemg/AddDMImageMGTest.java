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
public class AddDMImageMGTest extends BaseTestCase {
	public void testAddDMImageMG() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"There are no media files in this folder."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("Add Media"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Add Media')]"));
		selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Add Media')]",
			RuntimeVariables.replace("Add Media"));
		selenium.waitForVisible("//iframe[contains(@id,'selectFileEntryType')]");
		selenium.selectFrame("//iframe[contains(@id,'selectFileEntryType')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//tr[3]/td[1]/a");
		selenium.clickAt("//tr[3]/td[1]/a",
			RuntimeVariables.replace("Basic Document"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		Thread.sleep(5000);
		selenium.uploadCommonFile("//input[@class=\"field-input field-input-text\" and @type=\"file\"]",
			RuntimeVariables.replace("Document_1.jpg"));
		selenium.type("//input[contains(@class,'aui-field-input-text lfr-input-text') and @type=\"text\"]",
			RuntimeVariables.replace("DM Image Title"));
		selenium.type("//textarea[contains(@class,'aui-field-input-text lfr-textarea')]",
			RuntimeVariables.replace("DM Image Description"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible(
				"//a[@title='DM Image Title - DM Image Description']/span[@class='image-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("DM Image Title"),
			selenium.getText(
				"//a[@title='DM Image Title - DM Image Description']/span[@class='image-title']"));
	}
}