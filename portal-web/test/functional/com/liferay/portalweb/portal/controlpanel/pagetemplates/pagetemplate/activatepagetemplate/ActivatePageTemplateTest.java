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

package com.liferay.portalweb.portal.controlpanel.pagetemplates.pagetemplate.activatepagetemplate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivatePageTemplateTest extends BaseTestCase {
	public void testActivatePageTemplate() throws Exception {
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
				selenium.clickAt("link=Page Templates",
					RuntimeVariables.replace("Page Templates"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Page Template Name"),
					selenium.getText(
						"//tr[contains(.,'Page Template Name')]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("No"),
					selenium.getText(
						"//tr[contains(.,'Page Template Name')]/td[2]/a"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//tr[contains(.,'Page Template Name')]/td/span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//tr[contains(.,'Page Template Name')]/td/span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
				selenium.waitForPageToLoad("30000");

				boolean activeCheckbox = selenium.isChecked(
						"//input[@id='_146_activeCheckbox']");

				if (activeCheckbox) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_146_activeCheckbox']",
					RuntimeVariables.replace("Active Checkbox"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_146_activeCheckbox']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Page Template Name"),
					selenium.getText(
						"//tr[contains(.,'Page Template Name')]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("Yes"),
					selenium.getText(
						"//tr[contains(.,'Page Template Name')]/td[2]/a"));

			case 100:
				label = -1;
			}
		}
	}
}