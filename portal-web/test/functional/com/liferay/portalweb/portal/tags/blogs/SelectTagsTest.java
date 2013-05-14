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
public class SelectTagsTest extends BaseTestCase {
	public void testSelectTags() throws Exception {
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
				selenium.clickAt("//button[@id='select']",
					RuntimeVariables.replace("Select"));
				Thread.sleep(5000);
				selenium.waitForVisible(
					"//label[@title='selenium1 liferay1']/input");
				selenium.clickAt("//label[@title='selenium1 liferay1']",
					RuntimeVariables.replace("selenium1 liferay1"));
				selenium.clickAt("//label[@title='selenium2 liferay2']",
					RuntimeVariables.replace("selenium2 liferay2"));
				selenium.clickAt("//button[@title='Close dialog']",
					RuntimeVariables.replace("Close"));
				assertEquals(RuntimeVariables.replace("selenium1 liferay1"),
					selenium.getText(
						"xPath=(//span[@class='textboxlistentry-text'])[1]"));
				assertEquals(RuntimeVariables.replace("selenium2 liferay2"),
					selenium.getText(
						"xPath=(//span[@class='textboxlistentry-text'])[2]"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"Your request completed successfully."));
				assertEquals(RuntimeVariables.replace("selenium1 liferay1"),
					selenium.getText(
						"xPath=(//span[@class='taglib-asset-tags-summary']/a)[1]"));
				assertEquals(RuntimeVariables.replace("selenium2 liferay2"),
					selenium.getText(
						"xPath=(//span[@class='taglib-asset-tags-summary']/a)[2]"));

			case 100:
				label = -1;
			}
		}
	}
}