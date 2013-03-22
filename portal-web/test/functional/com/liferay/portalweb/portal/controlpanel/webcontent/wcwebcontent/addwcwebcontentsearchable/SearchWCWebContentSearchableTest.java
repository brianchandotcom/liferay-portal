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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontentsearchable;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchWCWebContentSearchableTest extends BaseTestCase {
	public void testSearchWCWebContentSearchable() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Search Test Page",
			RuntimeVariables.replace("Search Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@type='text' and @title='Search']",
			RuntimeVariables.replace("WebContent"));
		selenium.clickAt("//input[@type='image' and @title='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Liferay (1)"),
			selenium.getText(
				"//ul[@class='lfr-component scopes']/li[contains(.,'Liferay')]"));
		assertEquals(RuntimeVariables.replace("Web Content (1)"),
			selenium.getText(
				"//ul[@class='asset-type lfr-component']/li[contains(.,'Web Content')]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs (1)"),
			selenium.getText(
				"//ul[@class='lfr-component users']/li[contains(.,'Joe Bloggs')]"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//span[@class='asset-entry-title']/a"));
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//span[@class='asset-entry-summary']"));
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//span[@class='asset-entry-type']"));
		assertEquals(RuntimeVariables.replace("Showing 1 - 1."),
			selenium.getText("//div[@class='search-results']"));
		selenium.type("//input[@id='_3_keywords']",
			RuntimeVariables.replace("WebContent1"));
		selenium.clickAt("//input[@id='_3_search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		selenium.waitForVisible("//div[@class='portlet-msg-info']");
		assertEquals(RuntimeVariables.replace(
				"No results were found that matched the keywords: WebContent1."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertFalse(selenium.isTextPresent("WC WebContent Title"));
		assertFalse(selenium.isTextPresent("WC WebContent Content"));
	}
}