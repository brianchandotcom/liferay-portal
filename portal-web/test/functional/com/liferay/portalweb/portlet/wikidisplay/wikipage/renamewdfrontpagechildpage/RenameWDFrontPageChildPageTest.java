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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.renamewdfrontpagechildpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RenameWDFrontPageChildPageTest extends BaseTestCase {
	public void testRenameWDFrontPageChildPage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//div[@class='child-pages']/ul/li/a",
			"Wiki FrontPage ChildPage Title");
		selenium.clickAt("//div[@class='child-pages']/ul/li/a",
			RuntimeVariables.replace("Wiki FrontPage ChildPage Title"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//h1[@class='header-title']/span",
			"Wiki FrontPage ChildPage Title");
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertEquals(RuntimeVariables.replace("Details"),
			selenium.getText(
				"//div[@class='page-actions top-actions']/span/a[contains(.,'Details')]"));
		selenium.clickAt("//div[@class='page-actions top-actions']/span/a[contains(.,'Details')]",
			RuntimeVariables.replace("Details"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//ul[@class='lfr-component taglib-icon-list']/li/a[contains(.,'Move')]",
			"Move");
		selenium.click(RuntimeVariables.replace(
				"//ul[@class='lfr-component taglib-icon-list']/li/a[contains(.,'Move')]"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//h1[@class='header-title']/span",
			"Wiki FrontPage ChildPage Title");
		selenium.type("//input[contains(@id,'_newTitle')]",
			RuntimeVariables.replace("Wiki FrontPage ChildPage Title Rename"));
		selenium.clickAt("//input[@value='Rename']",
			RuntimeVariables.replace("Rename"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Title Rename"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertNotEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"(Redirected from Wiki FrontPage ChildPage Title)"),
			selenium.getText("//div[@class='page-redirect']"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//div[@class='child-pages']/ul/li/a",
			"Wiki FrontPage ChildPage Title Rename");
		assertNotEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Title"),
			selenium.getText("//div[@class='child-pages']/ul/li/a"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=All Pages", RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText(
				"//tr[contains(.,'Wiki FrontPage ChildPage Title')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Title Rename"),
			selenium.getText(
				"//tr[contains(.,'Wiki FrontPage ChildPage Title Rename')]/td[1]/a"));
	}
}