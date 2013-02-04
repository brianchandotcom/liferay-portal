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

package com.liferay.portalweb.portal.permissions.blogs.portlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Portlet_AssertCannotAddCommentTest extends BaseTestCase {
	public void testPortlet_AssertCannotAddComment() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Blogs Test Page",
					RuntimeVariables.replace("Blogs Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='entry-title']/h2/a");
				assertEquals(RuntimeVariables.replace(
						"Blogs Entry Title Temporary"),
					selenium.getText("//div[@class='entry-title']/h2/a"));
				selenium.clickAt("//div[@class='entry-title']/h2/a",
					RuntimeVariables.replace("Blogs Entry Title Temporary"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='lfr-panel-title']/span");
				assertEquals(RuntimeVariables.replace("Comments"),
					selenium.getText("//div[@class='lfr-panel-title']/span"));
				Thread.sleep(1000);

				boolean blogCommentsExpanded = selenium.isVisible(
						"//input[@class='form-text lfr-input-resource ']");

				if (blogCommentsExpanded) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-panel-title']/span[contains(.,'Comments')]",
					RuntimeVariables.replace("Comments"));
				Thread.sleep(1000);

			case 2:
				assertTrue(selenium.isElementNotPresent("link=Add Comment"));
				assertTrue(selenium.isElementNotPresent("link=Be the first."));

			case 100:
				label = -1;
			}
		}
	}
}