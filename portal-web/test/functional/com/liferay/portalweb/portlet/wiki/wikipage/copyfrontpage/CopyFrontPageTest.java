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

package com.liferay.portalweb.portlet.wiki.wikipage.copyfrontpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CopyFrontPageTest extends BaseTestCase {
	public void testCopyFrontPage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Details"),
			selenium.getText(
				"//div[@class='page-actions top-actions']/span[contains(.,'Details')]/a/span"));
		selenium.clickAt("//div[@class='page-actions top-actions']/span[contains(.,'Details')]/a/span",
			RuntimeVariables.replace("Details"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Copy"),
			selenium.getText(
				"//ul[@class='lfr-component taglib-icon-list']/li[contains(.,'Copy')]/a/span"));
		selenium.clickAt("//ul[@class='lfr-component taglib-icon-list']/li[contains(.,'Copy')]/a/span",
			RuntimeVariables.replace("Copy"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_36_title']",
			RuntimeVariables.replace("FrontPage2"));
		Thread.sleep(1000);
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
		selenium.clickAt("//ul[@class='top-links-navigation']/li[contains(.,'All Pages')]/span/a/span",
			RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("FrontPage2"),
			selenium.getText("//tr[contains(.,'FrontPage2')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'FrontPage2')]/td[1]/a",
			RuntimeVariables.replace("FrontPage2"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("FrontPage2"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
	}
}