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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontentexpirationdate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCWebContentExpirationDateTest extends BaseTestCase {
	public void testAddWCWebContentExpirationDate() throws Exception {
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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]");
		assertEquals(RuntimeVariables.replace("Basic Web Content"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/html/js/editor/ckeditor/plugins/restore/plugin.js')]");
		selenium.type("//input[@id='_15_title_en_US']",
			RuntimeVariables.replace("WC WebContent Title"));
		selenium.waitForText("//span[@class='cke_toolbar']/span[2]/a/span",
			"Normal");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible(
			"//a[@class='cke_button cke_button__source cke_button_on']");
		selenium.waitForVisible("//div[@id='cke_1_contents']/textarea");
		selenium.type("//div[@id='cke_1_contents']/textarea",
			RuntimeVariables.replace("WC WebContent Content"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible(
			"//a[@class='cke_button cke_button__source cke_button_off']");
		selenium.waitForVisible("//div[@id='cke_1_contents']/iframe");
		selenium.selectFrame("//div[@id='cke_1_contents']/iframe");
		selenium.waitForText("//body", "WC WebContent Content");
		selenium.selectFrame("relative=top");
		assertTrue(selenium.isPartialText("//a[@id='_15_scheduleLink']",
				"Schedule"));
		selenium.clickAt("//a[@id='_15_scheduleLink']",
			RuntimeVariables.replace("Schedule"));
		selenium.waitForVisible(
			"//select[@id='_15_expirationDateMonth' and @disabled='disabled']");
		assertTrue(selenium.isVisible(
				"//select[@id='_15_expirationDateMonth' and @disabled='disabled']"));
		assertTrue(selenium.isVisible(
				"//select[@id='_15_expirationDateDay' and @disabled='disabled']"));
		assertTrue(selenium.isVisible(
				"//select[@id='_15_expirationDateYear' and @disabled='disabled']"));
		assertTrue(selenium.isVisible(
				"//select[@name='_15_expirationDateHour' and @disabled='disabled']"));
		assertTrue(selenium.isVisible(
				"//select[@name='_15_expirationDateMinute' and @disabled='disabled']"));
		assertTrue(selenium.isVisible(
				"//select[@name='_15_expirationDateAmPm' and @disabled='disabled']"));
		assertTrue(selenium.isChecked("//input[@id='_15_neverExpireCheckbox']"));
		selenium.clickAt("//input[@id='_15_neverExpireCheckbox']",
			RuntimeVariables.replace("Never Auto Expire"));
		assertFalse(selenium.isChecked("//input[@id='_15_neverExpireCheckbox']"));
		selenium.waitForElementNotPresent(
			"//select[@id='_15_expirationDateMonth' and @disabled='disabled']");
		assertTrue(selenium.isElementNotPresent(
				"//select[@id='_15_expirationDateMonth' and @disabled='disabled']"));
		assertTrue(selenium.isElementNotPresent(
				"//select[@id='_15_expirationDateDay' and @disabled='disabled']"));
		assertTrue(selenium.isElementNotPresent(
				"//select[@id='_15_expirationDateYear' and @disabled='disabled']"));
		assertTrue(selenium.isElementNotPresent(
				"//select[@name='_15_expirationDateHour' and @disabled='disabled']"));
		assertTrue(selenium.isElementNotPresent(
				"//select[@name='_15_expirationDateMinute' and @disabled='disabled']"));
		assertTrue(selenium.isElementNotPresent(
				"//select[@name='_15_expirationDateAmPm' and @disabled='disabled']"));
		selenium.select("//select[@id='_15_expirationDateMonth']",
			RuntimeVariables.replace("label=December"));
		selenium.select("//select[@id='_15_expirationDateDay']",
			RuntimeVariables.replace("label=31"));
		selenium.select("//select[@id='_15_expirationDateYear']",
			RuntimeVariables.replace("label=2015"));
		selenium.select("//select[@name='_15_expirationDateHour']",
			RuntimeVariables.replace("label=12"));
		selenium.select("//select[@name='_15_expirationDateMinute']",
			RuntimeVariables.replace("label=:00"));
		selenium.select("//select[@name='_15_expirationDateAmPm']",
			RuntimeVariables.replace("label=AM"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible(
				"//a[contains(@title,'WC WebContent Title')]/div/img"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText(
				"//a[@class='entry-link']/span[contains(.,'WC WebContent Title')]"));
		selenium.clickAt("//a[@class='entry-link']/span[contains(.,'WC WebContent Title')]",
			RuntimeVariables.replace("WC WebContent Title"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/html/js/editor/ckeditor/plugins/restore/plugin.js')]");
		assertEquals(RuntimeVariables.replace("Schedule (Modified)"),
			selenium.getText("//a[@id='_15_scheduleLink']"));
		selenium.clickAt("//a[@id='_15_scheduleLink']",
			RuntimeVariables.replace("Schedule (Modified)"));
		selenium.waitForVisible("//select[@id='_15_expirationDateMonth']");
		assertEquals("December",
			selenium.getSelectedLabel("//select[@id='_15_expirationDateMonth']"));
		assertEquals("31",
			selenium.getSelectedLabel("//select[@id='_15_expirationDateDay']"));
		assertEquals("2015",
			selenium.getSelectedLabel("//select[@id='_15_expirationDateYear']"));
		assertEquals("12",
			selenium.getSelectedLabel(
				"//select[@name='_15_expirationDateHour']"));
		assertEquals(":00",
			selenium.getSelectedLabel(
				"//select[@name='_15_expirationDateMinute']"));
		assertEquals("AM",
			selenium.getSelectedLabel(
				"//select[@name='_15_expirationDateAmPm']"));
		assertFalse(selenium.isChecked("//input[@id='_15_neverExpireCheckbox']"));
	}
}