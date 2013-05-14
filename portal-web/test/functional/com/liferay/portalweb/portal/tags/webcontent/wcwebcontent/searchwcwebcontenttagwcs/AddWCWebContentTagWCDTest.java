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

package com.liferay.portalweb.portal.tags.webcontent.wcwebcontent.searchwcwebcontenttagwcs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCWebContentTagWCDTest extends BaseTestCase {
	public void testAddWCWebContentTagWCD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//div[@class='journal-content-article']/p"));
		selenium.clickAt("//img[@alt='Edit Web Content']",
			RuntimeVariables.replace("Edit Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isVisible("//span[@class='workflow-id']"));
		assertEquals(RuntimeVariables.replace("Version: 1.0"),
			selenium.getText("//span[@class='workflow-version']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertTrue(selenium.isPartialText("//a[@id='_15_categorizationLink']",
				"Categorization"));
		selenium.clickAt("//a[@id='_15_categorizationLink']",
			RuntimeVariables.replace("Categorization"));
		selenium.waitForVisible(
			"//input[@class='lfr-tag-selector-input field-input-text']");
		selenium.type("//input[@class='lfr-tag-selector-input field-input-text']",
			RuntimeVariables.replace("tag"));
		selenium.clickAt("//button[@id='add']", RuntimeVariables.replace("Add"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//div[@class='journal-content-article']/p"));
		selenium.clickAt("//img[@alt='Edit Web Content']",
			RuntimeVariables.replace("Edit Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isVisible("//span[@class='workflow-id']"));
		assertEquals(RuntimeVariables.replace("Version: 1.1"),
			selenium.getText("//span[@class='workflow-version']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertTrue(selenium.isPartialText("//a[@id='_15_categorizationLink']",
				"Categorization"));
		selenium.clickAt("//a[@id='_15_categorizationLink']",
			RuntimeVariables.replace("Categorization"));
		selenium.waitForVisible(
			"//div[contains(@id,'assetTagsSelector')]/ul/li/span/span[contains(.,'tag')]");
		assertEquals(RuntimeVariables.replace("tag"),
			selenium.getText(
				"//div[contains(@id,'assetTagsSelector')]/ul/li/span/span[contains(.,'tag')]"));
	}
}