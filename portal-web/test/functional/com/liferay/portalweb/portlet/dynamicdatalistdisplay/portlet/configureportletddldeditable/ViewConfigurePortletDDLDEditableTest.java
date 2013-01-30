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

package com.liferay.portalweb.portlet.dynamicdatalistdisplay.portlet.configureportletddldeditable;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewConfigurePortletDDLDEditableTest extends BaseTestCase {
	public void testViewConfigurePortletDDLDEditable()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Dynamic Data List Display Test Page",
			RuntimeVariables.replace("Dynamic Data List Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("List Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isVisible(
				"//input[contains(@id,'dynamic_data_lists_record_searchkeywords')]"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertEquals(RuntimeVariables.replace("Advanced \u00bb"),
			selenium.getText("//a[contains(.,'Advanced \u00bb')]"));
		assertTrue(selenium.isElementNotPresent("//input[@value='Add Record']"));
		assertEquals(RuntimeVariables.replace("No records were found."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertTrue(selenium.isVisible(
				"//div[@class='icon-actions']/span/a/img[@title='Add Form Template']"));
		assertTrue(selenium.isVisible(
				"//div[@class='icon-actions']/span/a/img[@title='Add Display Template']"));
		assertTrue(selenium.isVisible(
				"//div[@class='icon-actions']/span/a/img[@title='Select List']"));
		assertTrue(selenium.isVisible(
				"//div[@class='icon-actions']/span/a/img[@title='Add List']"));
	}
}