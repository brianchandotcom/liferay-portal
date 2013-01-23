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

package com.liferay.portalweb.portlet.categoriesnavigation.portlet.addportletcn;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletCNTest extends BaseTestCase {
	public void testViewPortletCN() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Categories Navigation Test Page",
			RuntimeVariables.replace("Categories Navigation Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//section"));
		assertEquals(RuntimeVariables.replace("Categories Navigation"),
			selenium.getText("//h1[@class='portlet-title']/span[2]"));
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		assertTrue(selenium.isVisible("//img[@title='Minimize']"));
		assertTrue(selenium.isVisible("//img[@title='Minimize']"));
		assertTrue(selenium.isVisible("//img[@title='Remove']"));
		assertEquals(RuntimeVariables.replace("There are no categories."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}