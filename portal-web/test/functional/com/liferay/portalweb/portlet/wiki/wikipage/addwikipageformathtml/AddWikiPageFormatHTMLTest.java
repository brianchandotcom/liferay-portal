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

package com.liferay.portalweb.portlet.wiki.wikipage.addwikipageformathtml;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWikiPageFormatHTMLTest extends BaseTestCase {
	public void testAddWikiPageFormatHTML() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=All Pages", RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Page']",
			RuntimeVariables.replace("Add Page"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_36_format']",
			RuntimeVariables.replace("HTML"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^You may lose some formatting when switching from Creole to HTML. Do you want to continue[\\s\\S]$"));
		selenium.type("//input[@id='_36_title']",
			RuntimeVariables.replace("Wiki Page Title"));
		selenium.waitForElementPresent("//textarea[@id='_36_content']");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//a[@class='cke_button_source cke_on']");
		selenium.waitForVisible("//td[@id='cke_contents__36_content']/textarea");
		selenium.type("//td[@id='cke_contents__36_content']/textarea",
			RuntimeVariables.replace(
				"<a herf=http://www.liferay.com>Welcome to LIFERAY</a>"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//textarea[@id='_36_content']"));
		assertTrue(selenium.isVisible(
				"//td[@id='cke_contents__36_content']/iframe"));
		assertTrue(selenium.isElementPresent(
				"//body[@class='html-editor portlet portlet-wiki cke_show_borders']/p/a"));
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=All Pages", RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki Page Title"),
			selenium.getText("xPath=(//tr[4]/td[1]/a)"));
		selenium.clickAt("//tr[4]/td[1]/a",
			RuntimeVariables.replace("Wiki Page Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki Page Title"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)"));
		assertEquals(RuntimeVariables.replace("Welcome to LIFERAY"),
			selenium.getText("xPath=(//div[@class='wiki-body']/p/a)"));
		assertEquals(RuntimeVariables.replace("Details"),
			selenium.getText("//div[3]/span[contains(.,'Details')]/a/span"));
		selenium.clickAt("//div[3]/span[contains(.,'Details')]/a/span",
			RuntimeVariables.replace("Details"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("HTML"),
			selenium.getText("xPath=(//tr[2]/td)"));
	}
}