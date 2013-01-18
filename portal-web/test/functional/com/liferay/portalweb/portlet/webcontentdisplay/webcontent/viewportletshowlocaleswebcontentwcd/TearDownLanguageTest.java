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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewportletshowlocaleswebcontentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownLanguageTest extends BaseTestCase {
	public void testTearDownLanguage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");

				String currentURL = selenium.getLocation();
				RuntimeVariables.setValue("currentURL", currentURL);
				selenium.open(RuntimeVariables.getValue("currentURL"));
				selenium.clickAt("link=Web Content Display Test Page",
					RuntimeVariables.replace("Web Content Display Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean englishLocaleVisible = selenium.isElementPresent(
						"//div[@class='locale-actions']/span/a/img[@title='English (United States)']");

				if (!englishLocaleVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@class='locale-actions']/span/a/img[@title='English (United States)']",
					RuntimeVariables.replace("English (United States)"));
				selenium.waitForPageToLoad("30000");

			case 2:
				assertEquals(RuntimeVariables.replace("Web Content Display"),
					selenium.getText("//span[@class='portlet-title-text']"));

			case 100:
				label = -1;
			}
		}
	}
}