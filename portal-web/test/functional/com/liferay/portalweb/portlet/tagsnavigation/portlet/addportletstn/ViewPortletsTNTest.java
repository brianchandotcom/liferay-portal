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

package com.liferay.portalweb.portlet.tagsnavigation.portlet.addportletstn;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletsTNTest extends BaseTestCase {
	public void testViewPortletsTN() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Tags Navigation Test Page",
			RuntimeVariables.replace("Tags Navigation Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//div[1]/section"));
		assertEquals(RuntimeVariables.replace("There are no tags."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[1]"));
		assertTrue(selenium.isVisible("//div[2]/section"));
		assertEquals(RuntimeVariables.replace("There are no tags."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[2]"));
		assertTrue(selenium.isVisible("//div[3]/section"));
		assertEquals(RuntimeVariables.replace("There are no tags."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[3]"));
	}
}