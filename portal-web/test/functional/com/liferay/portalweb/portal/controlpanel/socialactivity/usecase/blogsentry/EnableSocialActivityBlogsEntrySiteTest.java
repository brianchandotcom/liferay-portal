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

package com.liferay.portalweb.portal.controlpanel.socialactivity.usecase.blogsentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EnableSocialActivityBlogsEntrySiteTest extends BaseTestCase {
	public void testEnableSocialActivityBlogsEntrySite()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/site-name/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Social Activity",
					RuntimeVariables.replace("Social Activity"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//input[@id='_179_com.liferay.portlet.blogs.model.BlogsEntry.enabledCheckbox']");

				boolean blogsEntryCheckbox = selenium.isChecked(
						"//input[@id='_179_com.liferay.portlet.blogs.model.BlogsEntry.enabledCheckbox']");

				if (blogsEntryCheckbox) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_179_com.liferay.portlet.blogs.model.BlogsEntry.enabledCheckbox']",
					RuntimeVariables.replace("Blogs Entry Checkbox"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_179_com.liferay.portlet.blogs.model.BlogsEntry.enabledCheckbox']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isChecked(
						"//input[@id='_179_com.liferay.portlet.blogs.model.BlogsEntry.enabledCheckbox']"));

			case 100:
				label = -1;
			}
		}
	}
}