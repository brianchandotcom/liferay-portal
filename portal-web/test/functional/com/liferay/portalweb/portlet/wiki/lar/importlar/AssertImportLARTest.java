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

package com.liferay.portalweb.portlet.wiki.lar.importlar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertImportLARTest extends BaseTestCase {
	public void testAssertImportLAR() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//ul[@class='top-links-navigation']/li[contains(.,'All Pages')]/span/a"));
		selenium.clickAt("//ul[@class='top-links-navigation']/li[contains(.,'All Pages')]/span/a",
			RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Test Wiki Article"));
		selenium.clickAt("link=Test Wiki Article",
			RuntimeVariables.replace("Test Wiki Article"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Wiki Article"),
			selenium.getText(
				"xPath=(//div[@class='portlet-body']/div[2]/h1/span)"));
		assertEquals(RuntimeVariables.replace("this is italics"),
			selenium.getText("xPath=(//div[@class='wiki-body']/p/em)"));
		assertEquals(RuntimeVariables.replace("bold"),
			selenium.getText("xPath=(//div[@class='wiki-body']/p/strong)"));
		assertEquals(RuntimeVariables.replace("Link to Website"),
			selenium.getText("xPath=(//div[@class='wiki-body']/p/a)"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='wiki-body']/ul/li)", "this is a list item"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='wiki-body']/ul/li/ul)",
				"this is a sub list item"));
		assertEquals(RuntimeVariables.replace("Test"),
			selenium.getText("xPath=(//div[@class='child-pages']/ul/li/a)"));
		selenium.clickAt("//div[@class='child-pages']/ul/li/a",
			RuntimeVariables.replace("Test"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Child Article\\\\ #"),
			selenium.getText("xPath=(//div[@class='wiki-body']/h2)"));
		assertEquals(RuntimeVariables.replace("this is italics"),
			selenium.getText("xPath=(//div[@class='wiki-body']/p/em)"));
		assertEquals(RuntimeVariables.replace("bold"),
			selenium.getText("xPath=(//div[@class='wiki-body']/p/strong)"));
		assertEquals(RuntimeVariables.replace("Link to website"),
			selenium.getText("xPath=(//div[@class='wiki-body']/p/a)"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='wiki-body']/ul/li)", "this is a list item"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='wiki-body']/ul/li/ul/li)",
				"this is a sub list item"));
		selenium.clickAt("link=Second Edited Wiki Test",
			RuntimeVariables.replace("Second Edited Wiki Test"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Yes this is a second test article\\\\ #"),
			selenium.getText("//div[@class='wiki-body']/h4"));
		assertEquals(RuntimeVariables.replace(
				"I love liferay! This wiki has been EDITED!"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertEquals(RuntimeVariables.replace("Link Me 1"),
			selenium.getText("xPath=(//div[@class='child-pages']/ul/li/a)[1]"));
		assertEquals(RuntimeVariables.replace("Link Me 2"),
			selenium.getText("xPath=(//div[@class='child-pages']/ul/li/a)[2]"));
		selenium.clickAt("xPath=(//div[@class='child-pages']/ul/li/a)[1]",
			RuntimeVariables.replace("Link Me 1"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='wiki-body']/p)",
				"Hi Administrator! Hope you are well! Please link me to another page!"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='wiki-body']/p)", "-testing"));
		assertEquals(RuntimeVariables.replace("Frontpage"),
			selenium.getText("xPath=(//div[@class='wiki-body']/p[2])"));
		assertEquals(RuntimeVariables.replace("Link Me 2"),
			selenium.getText("xPath=(//div[@class='wiki-body']/p/a)[2]"));
		selenium.clickAt("xPath=(//div[@class='wiki-body']/p/a)[2]",
			RuntimeVariables.replace("Link Me 2"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='wiki-body']/p)", "Hi Administrator!"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='wiki-body']/p)",
				"I made another mistake! Oh me. Please link this article to another!"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='wiki-body']/p)", "-rj"));
		assertEquals(RuntimeVariables.replace("Frontpage"),
			selenium.getText("xPath=(//div[@class='wiki-body']/p/a)"));
		assertEquals(RuntimeVariables.replace("Link Me 1"),
			selenium.getText("xPath=(//div[@class='wiki-body']/p/a)[2]"));
	}
}