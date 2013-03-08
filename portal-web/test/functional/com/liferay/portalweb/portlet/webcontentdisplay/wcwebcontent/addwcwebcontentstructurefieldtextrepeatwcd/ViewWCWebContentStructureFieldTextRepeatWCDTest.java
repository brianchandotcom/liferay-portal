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

package com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldtextrepeatwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCWebContentStructureFieldTextRepeatWCDTest
	extends BaseTestCase {
	public void testViewWCWebContentStructureFieldTextRepeatWCD()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		assertTrue(selenium.isVisible(
				"//span[@class='portlet-minimize portlet-minimize-icon']/a"));
		assertTrue(selenium.isVisible(
				"//span[@class='portlet-maximize portlet-maximize-icon']/a"));
		assertTrue(selenium.isVisible(
				"//span[@class='portlet-close portlet-close-icon']/a"));
		assertEquals(RuntimeVariables.replace("WC Structure Text1 Repeatable"),
			selenium.getText("//div[@class='journal-content-article']/p"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//span[@class='icon-action icon-action-edit']/a"));
		assertEquals(RuntimeVariables.replace("Edit Template"),
			selenium.getText(
				"//span[@class='icon-action icon-action-edit-template']"));
		assertEquals(RuntimeVariables.replace("Select Web Content"),
			selenium.getText(
				"//span[@class='icon-action icon-action-configuration']/a"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@class='icon-action icon-action-add']/a"));
		selenium.clickAt("//span[@class='icon-action icon-action-edit']/a",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals("WC WebContent Structure Text Repeatable Title",
			selenium.getValue("//input[@id='_15_title_en_US']"));
		assertEquals("WC Structure Text1 Repeatable",
			selenium.getValue("xpath=(//input[contains(@id,'_15_text')])[1]"));
		assertTrue(selenium.isVisible(
				"xpath=(//a[@class='lfr-ddm-repeatable-add-button'])[1]"));
		assertEquals("WC Structure Text2 Repeatable",
			selenium.getValue("xpath=(//input[contains(@id,'_15_text')])[2]"));
		assertTrue(selenium.isVisible(
				"xpath=(//a[@class='lfr-ddm-repeatable-add-button'])[2]"));
		assertEquals("WC Structure Text3 Repeatable",
			selenium.getValue("xpath=(//input[contains(@id,'_15_text')])[3]"));
		assertTrue(selenium.isVisible(
				"xpath=(//a[@class='lfr-ddm-repeatable-add-button'])[3]"));
	}
}