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

package com.liferay.portalweb.portal.tags.wiki.addwikipagetag;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWikiPageTagTest extends BaseTestCase {
	public void testAddWikiPageTag() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Wiki Test Page",
					RuntimeVariables.replace("Wiki Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("All Pages"),
					selenium.getText(
						"//ul[@class='top-links-navigation']/li/span/a/span[contains(.,'All Pages')]"));
				selenium.clickAt("//ul[@class='top-links-navigation']/li/span/a/span[contains(.,'All Pages')]",
					RuntimeVariables.replace("All Pages"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Wiki Page Title"),
					selenium.getText(
						"//tr[contains(.,'Wiki Page Title')]/td[1]/a"));
				selenium.clickAt("//tr[contains(.,'Wiki Page Title')]/td[1]/a",
					RuntimeVariables.replace("Wiki Page Title"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='page-actions top-actions']/span/a[contains(.,'Edit')]"));
				selenium.clickAt("//div[@class='page-actions top-actions']/span/a[contains(.,'Edit')]",
					RuntimeVariables.replace("Edit"));
				selenium.waitForPageToLoad("30000");

				boolean tagsVisible = selenium.isVisible(
						"//input[@class='lfr-tag-selector-input field-input-text']");

				if (tagsVisible) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Categorization"),
					selenium.getText(
						"//div[@id='wikiPageCategorizationPanel']/div/div/span"));
				selenium.clickAt("//div[@id='wikiPageCategorizationPanel']/div/div/span",
					RuntimeVariables.replace("Categorization"));
				selenium.waitForVisible(
					"//input[@class='lfr-tag-selector-input field-input-text']");
				assertTrue(selenium.isVisible(
						"//input[@class='lfr-tag-selector-input field-input-text']"));

			case 2:
				selenium.type("//input[@class='lfr-tag-selector-input field-input-text']",
					RuntimeVariables.replace("wiki tag1"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("wiki tag1"),
					selenium.getText("//div[@class='page-tags']/span/a"));

			case 100:
				label = -1;
			}
		}
	}
}