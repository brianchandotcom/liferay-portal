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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.removeredirectwdfrontpagechildpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemoveRedirectWDFrontPageChildPageTest extends BaseTestCase {
	public void testRemoveRedirectWDFrontPageChildPage()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("All Pages"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a[contains(.,'All Pages')]"));
		selenium.clickAt("//ul[@class='top-links-navigation']/li/span/a[contains(.,'All Pages')]",
			RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText(
				"//tr[contains(.,'Wiki FrontPage ChildPage Title')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Wiki FrontPage ChildPage Title')]/td[1]/a",
			RuntimeVariables.replace("Wiki FrontPage ChildPage Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Title Rename"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertEquals(RuntimeVariables.replace(
				"(Redirected from Wiki FrontPage ChildPage Title)"),
			selenium.getText("//div[@class='page-redirect']"));
		selenium.clickAt("//div[@class='page-redirect']",
			RuntimeVariables.replace(
				"(Redirected from Wiki FrontPage ChildPage Title)"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"This page is currently redirected to Wiki FrontPage ChildPage Title Rename."),
			selenium.getText("//div[@class='wiki-body']/div"));
		selenium.clickAt("//input[@value='Remove Redirect']",
			RuntimeVariables.replace("Remove Redirect"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]	");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
			RuntimeVariables.replace("Wiki FrontPage ChildPage Content Rename"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Content Rename"),
			selenium.getText("//div[@class='wiki-body']/p"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("All Pages"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a[contains(.,'All Pages')]"));
		selenium.clickAt("//ul[@class='top-links-navigation']/li/span/a[contains(.,'All Pages')]",
			RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText(
				"//tr[contains(.,'Wiki FrontPage ChildPage Title')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Wiki FrontPage ChildPage Title')]/td[1]/a",
			RuntimeVariables.replace("Wiki FrontPage ChildPage Title"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//h1[@class='header-title']/span",
			"Wiki FrontPage ChildPage Title");
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Content Rename"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertNotEquals(RuntimeVariables.replace(
				"Wiki FrontPage Child Page Title Rename"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertFalse(selenium.isTextPresent(
				"(Redirected from Wiki FrontPage ChildPage Title1)"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("All Pages"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a[contains(.,'All Pages')]"));
		selenium.clickAt("//ul[@class='top-links-navigation']/li/span/a[contains(.,'All Pages')]",
			RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Title Rename"),
			selenium.getText(
				"//tr[contains(.,'Wiki FrontPage ChildPage Title Rename')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Wiki FrontPage ChildPage Title Rename')]/td[1]/a",
			RuntimeVariables.replace("Wiki FrontPage ChildPage Title Rename"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//h1[@class='header-title']/span",
			"Wiki FrontPage ChildPage Title Rename");
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Title Rename"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertNotEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertFalse(selenium.isTextPresent(
				"(Redirected from Wiki FrontPage ChildPage Title)"));
	}
}