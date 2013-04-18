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

package com.liferay.portalweb.portlet.wikidisplay.attachment.addwdfrontpageattachment;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWDFrontPageAttachmentTest extends BaseTestCase {
	public void testAddWDFrontPageAttachment() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("0 Attachments"),
			selenium.getText("//div[@class='article-actions']/span[2]/a/span"));
		selenium.clickAt("//div[@class='article-actions']/span[2]/a/span",
			RuntimeVariables.replace("0 Attachments"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"This page does not have file attachments."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("//input[@value='Add Attachments']",
			RuntimeVariables.replace("Add Attachments"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[contains(@class,'yui3-uploader')]");
		selenium.waitForElementPresent("//input[@type='file']");
		selenium.uploadCommonFile("//input[@type='file']",
			RuntimeVariables.replace("Document_1.jpg"));
		selenium.waitForElementPresent(
			"//ul[contains(@id,'_fileListContent')]/li[contains(@class,'upload-complete')]");
		selenium.clickAt("xpath=(//input[@value='Save'])[2]",
			RuntimeVariables.replace("Save"));
		selenium.waitForText("//span[@class='success-message']",
			"Successfully saved.");
		assertEquals(RuntimeVariables.replace("Successfully saved."),
			selenium.getText("//span[@class='success-message']"));
		assertEquals(RuntimeVariables.replace("Document_1.jpg"),
			selenium.getText("//span[@class='file-title']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("1 Attachment"),
			selenium.getText("//div[@class='article-actions']/span[2]/a/span"));
		assertFalse(selenium.isTextPresent("0 Attachments"));
	}
}