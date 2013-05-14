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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewRatePage2DLFolder2Document2Test extends BaseTestCase {
	public void testViewRatePage2DLFolder2Document2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/document-library-page-scope-community/");
		selenium.waitForVisible("link=DL Page2 Name");
		selenium.clickAt("link=DL Page2 Name",
			RuntimeVariables.replace("DL Page2 Name"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Documents and Media (DL Page2 Name)");
		assertEquals(RuntimeVariables.replace(
				"Documents and Media (DL Page2 Name)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("DL Folder2 Name"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DL Folder2 Name"));
		selenium.waitForText("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			"DL Folder2 Document2 Title.xls");
		assertEquals(RuntimeVariables.replace("DL Folder2 Document2 Title.xls"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DL Folder2 Document2 Title.xls"));
		selenium.waitForVisible("//h1[@class='header-title']/span");
		assertEquals(RuntimeVariables.replace("DL Folder2 Document2 Title.xls"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[2]"));
		assertTrue(selenium.isElementPresent(
				"//img[@alt='The average rating is 4.0 stars out of 5.']"));
		assertTrue(selenium.isTextPresent(
				"You have rated this 4 stars out of 5."));
		selenium.open("/web/document-library-page-scope-community/");
		selenium.waitForVisible("link=DL Page3 Name");
		selenium.clickAt("link=DL Page3 Name",
			RuntimeVariables.replace("DL Page3 Name"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Documents and Media (DL Page2 Name)");
		assertEquals(RuntimeVariables.replace(
				"Documents and Media (DL Page2 Name)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("DL Folder2 Name"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DL Folder2 Name"));
		selenium.waitForText("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			"DL Folder2 Document2 Title.xls");
		assertEquals(RuntimeVariables.replace("DL Folder2 Document2 Title.xls"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DL Folder2 Document2 Title.xls"));
		selenium.waitForVisible("//h1[@class='header-title']/span");
		assertEquals(RuntimeVariables.replace("DL Folder2 Document2 Title.xls"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[2]"));
		assertTrue(selenium.isElementPresent(
				"//img[@alt='The average rating is 4.0 stars out of 5.']"));
		assertTrue(selenium.isTextPresent(
				"You have rated this 4 stars out of 5."));
	}
}