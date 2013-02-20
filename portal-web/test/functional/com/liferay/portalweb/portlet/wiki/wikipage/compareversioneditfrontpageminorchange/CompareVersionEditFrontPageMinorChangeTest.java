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

package com.liferay.portalweb.portlet.wiki.wikipage.compareversioneditfrontpageminorchange;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CompareVersionEditFrontPageMinorChangeTest extends BaseTestCase {
	public void testCompareVersionEditFrontPageMinorChange()
		throws Exception {
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
		selenium.clickAt("link=History", RuntimeVariables.replace("History"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("1.2 (Minor Edit)"),
			selenium.getText("//tr[contains(.,'1.2 (Minor Edit)')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("1.1"),
			selenium.getText("//tr[contains(.,'1.1')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("1.0 (Minor Edit)"),
			selenium.getText("//tr[contains(.,'1.0 (Minor Edit)')]/td[4]/a"));
		selenium.clickAt("//input[@name='_36_rowIds' and @value='1.2']",
			RuntimeVariables.replace("1.2 (Minor Edit)"));
		selenium.clickAt("//input[@name='_36_rowIds' and @value='1.1']",
			RuntimeVariables.replace("1.1"));
		selenium.uncheck("//input[@name='_36_rowIds' and @value='1.0']");
		selenium.clickAt("//input[@value='Compare Versions']",
			RuntimeVariables.replace("Compare Versions"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Comparing Versions 1.1 and 1.2 (Last Version)"),
			selenium.getText("//span[@class='central-title']/span"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage Content Edit"),
			selenium.getText("//p[contains(.,'Wiki FrontPage Content Edit')]"));
		assertEquals(RuntimeVariables.replace("Text Mode"),
			selenium.getText("//a[@class='change-mode']"));
		selenium.clickAt("//a[@class='change-mode']",
			RuntimeVariables.replace("Text Mode"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("FrontPage 1.1"),
			selenium.getText("//tr[contains(.,'FrontPage 1.1')]/td[1]"));
		assertEquals(RuntimeVariables.replace("FrontPage 1.2"),
			selenium.getText("//tr[contains(.,'FrontPage 1.2')]/td[2]"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage Content"),
			selenium.getText("xPath=(//tr[@class='lfr-top']/td[1])[1]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//tr[@class='lfr-top']/td[1])[2]",
				"Wiki FrontPage Content Edit"));
	}
}