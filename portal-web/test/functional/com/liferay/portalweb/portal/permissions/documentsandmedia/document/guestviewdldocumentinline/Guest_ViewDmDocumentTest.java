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

package com.liferay.portalweb.portal.permissions.documentsandmedia.document.guestviewdldocumentinline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_ViewDmDocumentTest extends BaseTestCase {
	public void testGuest_ViewDmDocument() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Documents and Media Test Page");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Document Title"),
			selenium.getText(
				"//a[@class='document-link']/span[@class='entry-title']"));
		selenium.clickAt("//a[@class='document-link']/span[@class='entry-title']",
			RuntimeVariables.replace("DM Document Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Download"),
			selenium.getText("//span[@class='toolbar-content']/button[1]"));
		assertEquals(RuntimeVariables.replace("DM Document Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isPartialText(
				"//span[@class='user-date']/span/span", "Uploaded by Joe Bloggs"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Download (10.0k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
	}
}