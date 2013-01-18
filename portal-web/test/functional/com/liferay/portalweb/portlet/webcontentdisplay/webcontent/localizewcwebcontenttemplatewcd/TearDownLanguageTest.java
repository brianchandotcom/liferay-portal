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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.localizewcwebcontenttemplatewcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownLanguageTest extends BaseTestCase {
	public void testTearDownLanguage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");

		String currentURL = selenium.getLocation();
		RuntimeVariables.setValue("currentURL", currentURL);
		selenium.open(RuntimeVariables.getValue("currentURL"));
		selenium.clickAt("link=Language Test Page",
			RuntimeVariables.replace("Language Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//img[@title='English (United States)']",
			RuntimeVariables.replace("English (United States)"));
		selenium.waitForText("//span[@class='portlet-title-text']", "Language");
		assertEquals(RuntimeVariables.replace("Language"),
			selenium.getText("//span[@class='portlet-title-text']"));
	}
}