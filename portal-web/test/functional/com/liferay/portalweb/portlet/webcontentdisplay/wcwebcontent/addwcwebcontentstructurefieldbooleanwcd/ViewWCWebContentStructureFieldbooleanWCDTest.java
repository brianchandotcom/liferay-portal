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

package com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldbooleanwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCWebContentStructureFieldbooleanWCDTest extends BaseTestCase {
	public void testViewWCWebContentStructureFieldbooleanWCD()
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
		assertEquals(RuntimeVariables.replace("true"),
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
	}
}