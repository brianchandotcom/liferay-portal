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

package com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabulary;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownVocabularyTest extends BaseTestCase {
	public void testTearDownVocabulary() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
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
				selenium.clickAt("link=Categories",
					RuntimeVariables.replace("Categories"));
				selenium.waitForPageToLoad("30000");

				boolean vocabularyPresent = selenium.isElementPresent(
						"//input[@name='vocabulary-item-check']");

				if (!vocabularyPresent) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_147_checkAllVocabulariesCheckbox']"));
				selenium.clickAt("//input[@id='_147_checkAllVocabulariesCheckbox']",
					RuntimeVariables.replace("Select All"));
				assertTrue(selenium.isChecked(
						"//input[@id='_147_checkAllVocabulariesCheckbox']"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForText("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]",
					"Delete");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]",
					RuntimeVariables.replace("Delete"));
				selenium.waitForConfirmation(
					"Are you sure you want to delete the selected vocabularies?");
				selenium.waitForElementNotPresent(
					"//input[@name='vocabulary-item-check']");
				assertTrue(selenium.isElementNotPresent(
						"//input[@name='vocabulary-item-check']"));

			case 2:
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace(
						"There are no vocabularies."),
					selenium.getText(
						"xPath=(//div[@class='lfr-message-response portlet-msg-info'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"There are no categories."),
					selenium.getText(
						"xPath=(//div[@class='lfr-message-response portlet-msg-info'])[2]"));

			case 100:
				label = -1;
			}
		}
	}
}