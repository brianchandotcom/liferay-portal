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

package com.liferay.portalweb.portlet.announcements.announcementsentry.editannouncementsentrygeneral;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditAnnouncementsEntryGeneralTest extends BaseTestCase {
	public void testEditAnnouncementsEntryGeneral() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Announcements Test Page",
			RuntimeVariables.replace("Announcements Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Announcements Entry Title"),
			selenium.getText("//h3[@class='entry-title']/a"));
		assertEquals(RuntimeVariables.replace("General"),
			selenium.getText("//span[@class='entry-scope']"));
		assertEquals(RuntimeVariables.replace("Announcements Entry Content"),
			selenium.getText(
				"//div[@class=' entry-content entry-type-general']/p"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//td[@class='edit-entry']/span/a/span"));
		selenium.clickAt("//td[@class='edit-entry']/span/a/span",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_84_title']",
			RuntimeVariables.replace("Announcements Entry Title Edit"));
		selenium.type("//input[@id='_84_url']",
			RuntimeVariables.replace("http://www.alloyui.com"));
		selenium.waitForElementPresent(
			"//textarea[@id='_84_editor' and @style='display: none;']");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//td[@id='cke_contents__84_editor']/textarea");
		selenium.type("//td[@id='cke_contents__84_editor']/textarea",
			RuntimeVariables.replace("Announcements Entry Content Edit"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForElementPresent(
			"//textarea[@id='_84_editor' and @style='display: none;']");
		selenium.waitForVisible("//td[@id='cke_contents__84_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__84_editor']/iframe");
		selenium.waitForText("//body", "Announcements Entry Content Edit");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Announcements Entry Title Edit"),
			selenium.getText("//h3[@class='entry-title']/a"));
		assertEquals(RuntimeVariables.replace("General"),
			selenium.getText("//span[@class='entry-scope']"));
		assertEquals(RuntimeVariables.replace(
				"Announcements Entry Content Edit"),
			selenium.getText(
				"//div[@class=' entry-content entry-type-general']/p"));
	}
}