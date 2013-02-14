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

package com.liferay.portalweb.portlet.wiki.wikipage.compareversioneditfrontpageminorchange;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditFrontPageMinorChangeTest extends BaseTestCase {
	public void testEditFrontPageMinorChange() throws Exception {
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
				assertEquals(RuntimeVariables.replace("Wiki FrontPage Content"),
					selenium.getText("//div[@class='wiki-body']/p"));
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='page-actions top-actions']/span[contains(.,'Edit')]/a/span"));
				selenium.clickAt("//div[@class='page-actions top-actions']/span[contains(.,'Edit')]/a/span",
					RuntimeVariables.replace("Edit"));
				selenium.waitForVisible(
					"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]");
				selenium.waitForVisible(
					"//iframe[contains(@title,'Rich Text Editor')]");
				selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
					RuntimeVariables.replace("Wiki FrontPage Content Edit"));

				boolean minorEditChecked = selenium.isChecked(
						"//input[@id='_36_minorEditCheckbox']");

				if (minorEditChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_36_minorEditCheckbox']",
					RuntimeVariables.replace("This is a minor edit."));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_36_minorEditCheckbox']"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace(
						"Wiki FrontPage Content Edit"),
					selenium.getText("//div[@class='wiki-body']/p"));
				assertNotEquals(RuntimeVariables.replace(
						"Wiki FrontPage Content"),
					selenium.getText("//div[@class='wiki-body']/p"));
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Wiki Test Page",
					RuntimeVariables.replace("Wiki Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Details"),
					selenium.getText(
						"//div[@class='page-actions top-actions']/span[contains(.,'Details')]/a/span"));
				selenium.clickAt("//div[@class='page-actions top-actions']/span[contains(.,'Details')]/a/span",
					RuntimeVariables.replace("Details"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=History",
					RuntimeVariables.replace("History"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("1.2 (Minor Edit)"),
					selenium.getText(
						"//tr[contains(.,'1.2 (Minor Edit)')]/td[4]/a"));
				assertEquals(RuntimeVariables.replace("1.1"),
					selenium.getText("//tr[contains(.,'1.1')]/td[4]/a"));
				assertEquals(RuntimeVariables.replace("1.0 (Minor Edit)"),
					selenium.getText(
						"//tr[contains(.,'1.0 (Minor Edit)')]/td[4]/a"));

			case 100:
				label = -1;
			}
		}
	}
}