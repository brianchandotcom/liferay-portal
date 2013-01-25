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

package com.liferay.portalweb.portlet.language;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class InternationalizationJapaneseTest extends BaseTestCase {
	public void testInternationalizationJapanese() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Language Test Page",
			RuntimeVariables.replace("Language Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//img[@alt='\u65e5\u672c\u8a9e (\u65e5\u672c) [Beta]']",
			RuntimeVariables.replace("\u65e5\u672c\u8a9e (\u65e5\u672c) [Beta]"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//nav/ul/li[2]/a/span",
			RuntimeVariables.replace("Edit Page Name"));
		selenium.waitForVisible("//input[@type='text']");
		selenium.type("//input[@type='text']",
			RuntimeVariables.replace(
				"\u8a00\u8a9e\u30c6\u30b9\u30c8\u30da\u30fc\u30b8"));
		selenium.clickAt("//button[contains(@id,'Save')]",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible(
			"link=\u8a00\u8a9e\u30c6\u30b9\u30c8\u30da\u30fc\u30b8");
		assertTrue(selenium.isVisible(
				"link=\u8a00\u8a9e\u30c6\u30b9\u30c8\u30da\u30fc\u30b8"));
		selenium.clickAt("//img[@alt='English (United States)']",
			RuntimeVariables.replace("English (United States)"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("link=Language Test Page"));
	}
}