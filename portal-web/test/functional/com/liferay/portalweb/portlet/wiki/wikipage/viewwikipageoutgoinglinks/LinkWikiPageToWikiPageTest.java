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

package com.liferay.portalweb.portlet.wiki.wikipage.viewwikipageoutgoinglinks;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LinkWikiPageToWikiPageTest extends BaseTestCase {
	public void testLinkWikiPageToWikiPage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=All Pages", RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki Page1 Title"),
			selenium.getText("//tr[4]/td[contains(.,'Wiki Page1 Title')]/a"));
		selenium.clickAt("//tr[4]/td[contains(.,'Wiki Page1 Title')]/a",
			RuntimeVariables.replace("Wiki Page1 Title"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Wiki Page2 Test"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//div[3]/span[contains(.,'Edit')]/a/span"));
		selenium.clickAt("//div[3]/span[contains(.,'Edit')]/a/span",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//div[@id='cke_1_contents']/iframe"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible(
			"//a[@class='cke_button cke_button__source cke_button_on']");
		selenium.waitForVisible("//div[@id='cke_1_contents']/textarea");
		selenium.type("//div[@id='cke_1_contents']/textarea",
			RuntimeVariables.replace(
				"Wiki Page1 Content\n\n[[Wiki Page2 Title]]"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForElementPresent("//div[@id='cke_1_contents']/iframe");
		assertTrue(selenium.isVisible("//div[@id='cke_1_contents']/iframe"));
		selenium.selectFrame("//div[@id='cke_1_contents']/iframe");
		selenium.waitForText("//body", "Wiki Page1 Content \n Wiki Page2 Title");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@id='_36_publishButton']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Wiki Page2 Title"),
			selenium.getText("//div[@class='wiki-body']/p/a"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=All Pages", RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki Page1 Title"),
			selenium.getText("//tr[4]/td[contains(.,'Wiki Page1 Title')]/a"));
		selenium.clickAt("//tr[4]/td[contains(.,'Wiki Page1 Title')]/a",
			RuntimeVariables.replace("Wiki Page1 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki Page1 Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertEquals(RuntimeVariables.replace("Wiki Page2 Title"),
			selenium.getText("//div[@class='wiki-body']/p/a"));
		selenium.clickAt("//div[@class='wiki-body']/p/a",
			RuntimeVariables.replace("Wiki Page2 Title"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Wiki Page2 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Wiki Page2 Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
	}
}