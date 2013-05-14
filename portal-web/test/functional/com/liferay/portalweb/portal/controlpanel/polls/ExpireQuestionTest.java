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

package com.liferay.portalweb.portal.controlpanel.polls;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ExpireQuestionTest extends BaseTestCase {
	public void testExpireQuestion() throws Exception {
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
				selenium.clickAt("link=Polls", RuntimeVariables.replace("Polls"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Edited Test Question 2"),
					selenium.getText(
						"//tr[contains(.,'Edited Test Question 2')]/td[1]/a"));
				selenium.clickAt("//tr[contains(.,'Edited Test Question 2')]/td[1]/a",
					RuntimeVariables.replace("Edited Test Question 2"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible(
						"//span[1]/span/span/input[@type='radio']"));
				assertEquals(RuntimeVariables.replace("a. Test Choice A"),
					selenium.getText("//span[1]/span/label"));
				assertTrue(selenium.isVisible(
						"//span[2]/span/span/input[@type='radio']"));
				assertEquals(RuntimeVariables.replace("b. Test Choice B"),
					selenium.getText("//span[2]/span/label"));
				assertTrue(selenium.isVisible(
						"//span[3]/span/span/input[@type='radio']"));
				assertEquals(RuntimeVariables.replace("c. Test Choice C"),
					selenium.getText("//span[3]/span/label"));
				assertTrue(selenium.isVisible(
						"//span[4]/span/span/input[@type='radio']"));
				assertEquals(RuntimeVariables.replace("d. NEW Test Choice D"),
					selenium.getText("//span[4]/span/label"));
				selenium.clickAt("//input[@value='Cancel']",
					RuntimeVariables.replace("Cancel"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
				selenium.waitForPageToLoad("30000");

				boolean neverExpireCheckbox = selenium.isChecked(
						"//input[@id='_25_neverExpireCheckbox']");

				if (!neverExpireCheckbox) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_25_neverExpireCheckbox']",
					RuntimeVariables.replace("Never Expire"));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='_25_neverExpireCheckbox']"));
				selenium.waitForVisible(
					"//select[@id='_25_expirationdatemonth']");
				selenium.select("//select[@id='_25_expirationdateyear']",
					RuntimeVariables.replace("2008"));
				selenium.select("//select[@id='_25_expirationdatemonth']",
					RuntimeVariables.replace("January"));
				selenium.select("//select[@id='_25_expirationdateday']",
					RuntimeVariables.replace("1"));
				selenium.select("//select[@name='_25_expirationDateHour']",
					RuntimeVariables.replace("12"));
				selenium.select("//select[@name='_25_expirationDateMinute']",
					RuntimeVariables.replace(":00"));
				selenium.select("//select[@name='_25_expirationDateAmPm']",
					RuntimeVariables.replace("AM"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Edited Test Question 2"),
					selenium.getText(
						"//tr[contains(.,'Edited Test Question 2')]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("0"),
					selenium.getText(
						"//tr[contains(.,'Edited Test Question 2')]/td[2]/a"));
				assertEquals(RuntimeVariables.replace("Never"),
					selenium.getText(
						"//tr[contains(.,'Edited Test Question 2')]/td[3]/a"));
				assertNotEquals(RuntimeVariables.replace("Never"),
					selenium.getText(
						"//tr[contains(.,'Edited Test Question 2')]/td[4]/a"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//tr[contains(.,'Edited Test Question 2')]/td[5]/span[@title='Actions']/ul/li/strong/a/span"));

			case 100:
				label = -1;
			}
		}
	}
}