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

package com.liferay.portalweb.portlet.webcontentdisplay.comment.deletewcwebcontentcommentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteWCWebContentCommentWCDTest extends BaseTestCase {
	public void testDeleteWCWebContentCommentWCD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//div[@class='journal-content-article']/p"));
		assertEquals(RuntimeVariables.replace("WC WebContent Comment"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
		selenium.mouseOver("//li[@class='lfr-discussion-delete']/span/a/span");
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("//li[@class='lfr-discussion-delete']/span/a/span"));
		selenium.clickAt("//li[@class='lfr-discussion-delete']/span/a/span",
			RuntimeVariables.replace("Delete"));
		selenium.waitForConfirmation(
			"Are you sure you want to delete this? It will be deleted immediately.");
		selenium.waitForVisible("//div[contains(@class,'portlet-msg-success')]");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[contains(@class,'portlet-msg-success')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-discussion-message']"));
		assertFalse(selenium.isTextPresent("WC WebContent Comment"));
	}
}