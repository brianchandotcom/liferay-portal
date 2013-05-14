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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.editwikifrontpageminorchange;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewEditWikiFrontPageMinorChangeTest extends BaseTestCase {
	public void testViewEditWikiFrontPageMinorChange()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//div[@class='page-actions top-actions']/span/a[contains(.,'Details')]");
		assertEquals(RuntimeVariables.replace("Details"),
			selenium.getText(
				"//div[@class='page-actions top-actions']/span/a[contains(.,'Details')]"));
		selenium.clickAt("//div[@class='page-actions top-actions']/span/a[contains(.,'Details')]",
			RuntimeVariables.replace("Details"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=History", RuntimeVariables.replace("History"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("FrontPage"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'FrontPage')]"));
		assertEquals(RuntimeVariables.replace("Recent Changes"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'Recent Changes')]"));
		assertEquals(RuntimeVariables.replace("All Pages"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'All Pages')]"));
		assertEquals(RuntimeVariables.replace("Orphan Pages"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'Orphan Pages')]"));
		assertEquals(RuntimeVariables.replace("Draft Pages"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'Draft Pages')]"));
		assertTrue(selenium.isVisible(
				"//span[@class='search-bar']/span/span/span/input"));
		assertTrue(selenium.isVisible("//input[@title='Search Pages']"));
		assertEquals(RuntimeVariables.replace("FrontPage"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertTrue(selenium.isVisible(
				"//div[@class='button-holder ']/span/span/input"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[1]"));
		assertEquals(RuntimeVariables.replace("Page"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]"));
		assertEquals(RuntimeVariables.replace("Status"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]"));
		assertEquals(RuntimeVariables.replace("Revision"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[4]"));
		assertEquals(RuntimeVariables.replace("User"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[5]"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[6]"));
		assertEquals(RuntimeVariables.replace("Summary"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[7]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[8]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'1.2 (Minor Edit)')]/td[1]/input"));
		assertEquals(RuntimeVariables.replace("FrontPage"),
			selenium.getText("//tr[contains(.,'1.2 (Minor Edit)')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//tr[contains(.,'1.2 (Minor Edit)')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("1.2 (Minor Edit)"),
			selenium.getText("//tr[contains(.,'1.2 (Minor Edit)')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[contains(.,'1.2 (Minor Edit)')]/td[5]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'1.2 (Minor Edit)')]/td[6]/a"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'1.2 (Minor Edit)')]/td[7]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'1.2 (Minor Edit)')]/td[8]"));
		assertTrue(selenium.isVisible("//tr[contains(.,'1.1')]/td[1]/input"));
		assertEquals(RuntimeVariables.replace("FrontPage"),
			selenium.getText("//tr[contains(.,'1.1')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//tr[contains(.,'1.1')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("1.1"),
			selenium.getText("//tr[contains(.,'1.1')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[contains(.,'1.1')]/td[5]/a"));
		assertTrue(selenium.isVisible("//tr[contains(.,'1.1')]/td[6]/a"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'1.1')]/td[7]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'1.1')]/td[8]/span/a/img"));
		assertEquals(RuntimeVariables.replace("Revert"),
			selenium.getText("//tr[contains(.,'1.1')]/td[8]/span/a/span"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'1.0 (Minor Edit)')]/td[1]/input"));
		assertEquals(RuntimeVariables.replace("FrontPage"),
			selenium.getText("//tr[contains(.,'1.0 (Minor Edit)')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//tr[contains(.,'1.0 (Minor Edit)')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("1.0 (Minor Edit)"),
			selenium.getText("//tr[contains(.,'1.0 (Minor Edit)')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'1.0 (Minor Edit)')]/td[5]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'1.0 (Minor Edit)')]/td[6]/a"));
		assertEquals(RuntimeVariables.replace("New"),
			selenium.getText("//tr[contains(.,'1.0 (Minor Edit)')]/td[7]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'1.0 (Minor Edit)')]/td[8]/span/a/img"));
		assertEquals(RuntimeVariables.replace("Revert"),
			selenium.getText(
				"//tr[contains(.,'1.0 (Minor Edit)')]/td[8]/span/a/span"));
	}
}