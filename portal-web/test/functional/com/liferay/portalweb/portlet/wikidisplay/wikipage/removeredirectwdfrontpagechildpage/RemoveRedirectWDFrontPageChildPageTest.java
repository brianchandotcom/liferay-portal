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
		selenium.waitForElementPresent(
			"//script[contains(@src,'/html/js/editor/ckeditor/plugins/wikilink/plugin.js')]");
		selenium.waitForText("//span[@class='cke_toolbar']/span[contains(.,'Normal')]/a",
			"Normal");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible(
			"//a[@class='cke_button cke_button__source cke_button_on']");
		selenium.waitForVisible("//div[@id='cke_1_contents']/textarea");
		selenium.type("//div[@id='cke_1_contents']/textarea",
			RuntimeVariables.replace("Wiki FrontPage ChildPage Content Rename"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible(
			"//a[@class='cke_button cke_button__source cke_button_off']");
		assertTrue(selenium.isVisible("//div[@id='cke_1_contents']/iframe"));
		selenium.selectFrame("//div[@id='cke_1_contents']/iframe");
		selenium.waitForText("//body[@class='html-editor portlet portlet-wiki cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']/p",
			"Wiki FrontPage ChildPage Content Rename");
		selenium.selectFrame("relative=top");
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