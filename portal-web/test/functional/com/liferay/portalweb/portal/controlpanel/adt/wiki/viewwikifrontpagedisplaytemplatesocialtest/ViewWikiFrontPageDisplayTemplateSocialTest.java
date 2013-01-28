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

package com.liferay.portalweb.portal.controlpanel.adt.wiki.viewwikifrontpagedisplaytemplatesocialtest;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWikiFrontPageDisplayTemplateSocialTest extends BaseTestCase {
	public void testViewWikiFrontPageDisplayTemplateSocial()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//div[@class='wiki-info']/span"));
		assertTrue(selenium.isVisible("//div[@class='wiki-info']/span[2]"));
		assertEquals(RuntimeVariables.replace("By Joe Bloggs"),
			selenium.getText("//div[@class='wiki-info']/span[3]"));
		assertTrue(selenium.isVisible("//div[@id='widget']/div/a/span"));
		assertTrue(selenium.isVisible(
				"//div[@class='pluginConnectButton']/div/div/button"));
		assertTrue(selenium.isVisible(
				"//span[@id='widget_bounds']/table/tbody/tr/td/div/span"));
		assertFalse(selenium.isTextPresent(
				"//div[@class='page-actions top-actions']/span/a/span"));
		assertFalse(selenium.isTextPresent(
				"//div[@class='page-actions top-actions']/span[2]/a/span"));
		assertFalse(selenium.isTextPresent(
				"//div[@class='page-actions top-actions']/span[3]/a/span"));
		assertFalse(selenium.isTextPresent(
				"//div[@class='lfr-panel-title']/span"));
	}
}