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

package com.liferay.portalweb.portlet.wikidisplay.attachment.deletewdfrontpageattachment;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteWDFrontPageAttachmentTest extends BaseTestCase {
	public void testDeleteWDFrontPageAttachment() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("1 Attachment"),
			selenium.getText("//div[@class='article-actions']/span[2]/a/span"));
		selenium.clickAt("//div[@class='article-actions']/span[2]/a/span",
			RuntimeVariables.replace("1 Attachment"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Document_1.jpg"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("Remove"),
			selenium.getText("//td[3]/span/a/span"));
		selenium.click(RuntimeVariables.replace("//td[3]/span/a/span"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The selected item was moved to the Recycle Bin. Undo"),
			selenium.getText(
				"//div[@class='portlet-msg-success taglib-trash-undo']"));
		assertEquals(RuntimeVariables.replace(
				"This page does not have file attachments."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertFalse(selenium.isTextPresent("Document_1.jpg"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("0 Attachments"),
			selenium.getText("//div[@class='article-actions']/span[2]/a/span"));
		assertFalse(selenium.isTextPresent("link=1 Attachment"));
	}
}