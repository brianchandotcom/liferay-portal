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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewportletshowlocaleswebcontentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletShowLocalesWebContentWCDTest extends BaseTestCase {
	public void testViewPortletShowLocalesWebContentWCD()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content Display"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertTrue(selenium.isVisible(
				"//div[@class='locale-actions']/span/img[@title='English (United States)']"));
		assertTrue(selenium.isVisible(
				"//div[@class='locale-actions']/span/a/img[@title='\u4e2d\u6587 (\u4e2d\u56fd)']"));
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//div[@class='journal-content-article']/p"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//span[@class='icon-action icon-action-edit']/a/span"));
		assertEquals(RuntimeVariables.replace("Select Web Content"),
			selenium.getText(
				"//span[@class='icon-action icon-action-configuration']/a/span"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText(
				"//span[@class='icon-action icon-action-add']/a/span"));
		selenium.clickAt("//div[@class='locale-actions']/span/a/img[@title='\u4e2d\u6587 (\u4e2d\u56fd)']",
			RuntimeVariables.replace("\u4e2d\u6587 (\u4e2d\u56fd)"));
		selenium.waitForPageToLoad("30000");
		selenium.open("/zh/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"\u7f51\u9875\u5185\u5bb9\u5c55\u793a"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertTrue(selenium.isVisible(
				"//div[@class='locale-actions']/span/a/img[@title='English (United States)']"));
		assertTrue(selenium.isVisible(
				"//div[@class='locale-actions']/span/img[@title='\u4e2d\u6587 (\u4e2d\u56fd)']"));
		assertEquals(RuntimeVariables.replace(
				"\u4e16\u754c\u60a8\u597d Page Description"),
			selenium.getText("//div[@class='journal-content-article']/p"));
		assertEquals(RuntimeVariables.replace("\u7f16\u8f91"),
			selenium.getText(
				"//span[@class='icon-action icon-action-edit']/a/span"));
		assertEquals(RuntimeVariables.replace(
				"\u9009\u62e9\u7f51\u9875\u5185\u5bb9"),
			selenium.getText(
				"//span[@class='icon-action icon-action-configuration']/a/span"));
		assertEquals(RuntimeVariables.replace("\u6dfb\u52a0"),
			selenium.getText(
				"//span[@class='icon-action icon-action-add']/a/span"));
		selenium.open("/zh/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div[@class='locale-actions']/span/a/img[@title='English (United States)']",
			RuntimeVariables.replace("English (United States)"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//div[@class='journal-content-article']/p"));
		assertEquals(RuntimeVariables.replace("Web Content Display"),
			selenium.getText("//span[@class='portlet-title-text']"));
	}
}