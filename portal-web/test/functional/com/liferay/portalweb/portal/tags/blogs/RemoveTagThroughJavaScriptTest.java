/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.tags.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemoveTagThroughJavaScriptTest extends BaseTestCase {
	public void testRemoveTagThroughJavaScript() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Blogs Tags Test Page",
					RuntimeVariables.replace("Blogs Tags Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Tags Blog Entry3 Title"),
					selenium.getText(
						"xPath=(//div[@class='entry-title']/h2/a)[1]"));
				selenium.clickAt("xPath=(//div[@class='entry-title']/h2/a)[1]",
					RuntimeVariables.replace("Tags Blog Entry3 Title"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//a[@class=' taglib-icon']/span[contains(.,'Edit')]"));
				selenium.click(RuntimeVariables.replace(
						"//a[@class=' taglib-icon']/span[contains(.,'Edit')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//a[contains(@class,'cke_button_disabled')]");
				selenium.waitForVisible(
					"//iframe[contains(@title,'Rich Text Editor')]");

				boolean tagsVisible = selenium.isVisible(
						"//input[@class='lfr-tag-selector-input field-input-text']");

				if (tagsVisible) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Categorization"),
					selenium.getText(
						"xPath=(//div[@class='lfr-panel-title'])[2]/span"));
				selenium.clickAt("xPath=(//div[@class='lfr-panel-title'])[2]/span",
					RuntimeVariables.replace("Categorization"));
				selenium.waitForVisible(
					"//input[@class='lfr-tag-selector-input field-input-text']");
				assertTrue(selenium.isVisible(
						"//input[@class='lfr-tag-selector-input field-input-text']"));

			case 2:
				selenium.click(
					"xPath=(//span[@class='icon icon-close textboxlistentry-close'])[2]");
				assertTrue(selenium.isTextPresent("selenium2 liferay2"));
				selenium.waitForTextNotPresent("selenium3 liferay3");
				assertFalse(selenium.isTextPresent("selenium3 liferay3"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertFalse(selenium.isTextPresent("selenium3 liferay3"));

			case 100:
				label = -1;
			}
		}
	}
}