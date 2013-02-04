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

package com.liferay.portalweb.portal.permissions.blogs.portlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Portlet_DeleteCommentTest extends BaseTestCase {
	public void testPortlet_DeleteComment() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='entry-title']/h2/a");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title Temporary"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		selenium.clickAt("//div[@class='entry-title']/h2/a",
			RuntimeVariables.replace("Blogs Entry Title Temporary"));
		selenium.waitForPageToLoad("30000");
		selenium.mouseOver("//li[@class='lfr-discussion-delete']/span/a/span");
		selenium.waitForVisible(
			"//li[@class='lfr-discussion-delete']/span/a/span");
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("//li[@class='lfr-discussion-delete']/span/a/span"));
		selenium.clickAt("//li[@class='lfr-discussion-delete']/span/a/span",
			RuntimeVariables.replace("Delete"));
		selenium.waitForConfirmation(
			"Are you sure you want to delete this? It will be deleted immediately.");
		selenium.waitForVisible(
			"//div[@class='lfr-message-response portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText(
				"//div[@class='lfr-message-response portlet-msg-success']"));
		assertFalse(selenium.isTextPresent("Portlet Comment"));
	}
}