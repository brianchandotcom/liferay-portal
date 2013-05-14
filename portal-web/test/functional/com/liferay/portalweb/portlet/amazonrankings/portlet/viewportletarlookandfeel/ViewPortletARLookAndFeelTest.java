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

package com.liferay.portalweb.portlet.amazonrankings.portlet.viewportletarlookandfeel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletARLookAndFeelTest extends BaseTestCase {
	public void testViewPortletARLookAndFeel() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Amazon Rankings Test Page",
			RuntimeVariables.replace("Amazon Rankings Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForVisible("//span[@title='Options']/ul/li/strong/a");
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Look and Feel')]");
		assertEquals(RuntimeVariables.replace("Look and Feel"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Look and Feel')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Look and Feel')]",
			RuntimeVariables.replace("Look and Feel"));
		selenium.waitForElementPresent("//style[@class='lfr-custom-css-block']");
		selenium.waitForVisible("//input[@id='_113_custom-title']");
		assertEquals("Amazon Rankings",
			selenium.getValue("//input[@id='_113_custom-title']"));
		assertEquals("English (United States)",
			selenium.getSelectedLabel(
				"//select[@id='_113_lfr-portlet-language']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_113_use-custom-titleCheckbox']"));
		assertEquals("Current Page",
			selenium.getSelectedLabel("//select[@id='_113_lfr-point-links']"));
		assertEquals("",
			selenium.getSelectedLabel("//select[@id='_113_show-borders']"));
		selenium.clickAt("link=Text Styles",
			RuntimeVariables.replace("Text Styles"));
		selenium.waitForVisible("//select[@id='_113_lfr-font-family']");
		assertEquals(RuntimeVariables.replace(
				"Arial Georgia Times New Roman Tahoma Trebuchet MS Verdana"),
			selenium.getText("//select[@id='_113_lfr-font-family']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_113_lfr-font-boldCheckbox']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_113_lfr-font-italicCheckbox']"));
		assertEquals(RuntimeVariables.replace(
				"0.1em 0.2em 0.3em 0.4em 0.5em 0.6em 0.7em 0.8em 0.9em 1em 1.1em 1.2em 1.3em 1.4em 1.5em 1.6em 1.7em 1.8em 1.9em 2em 2.1em 2.2em 2.3em 2.4em 2.5em 2.6em 2.7em 2.8em 2.9em 3em 3.1em 3.2em 3.3em 3.4em 3.5em 3.6em 3.7em 3.8em 3.9em 4em 4.1em 4.2em 4.3em 4.4em 4.5em 4.6em 4.7em 4.8em 4.9em 5em 5.1em 5.2em 5.3em 5.4em 5.5em 5.6em 5.7em 5.8em 5.9em 6em 6.1em 6.2em 6.3em 6.4em 6.5em 6.6em 6.7em 6.8em 6.9em 7em 7.1em 7.2em 7.3em 7.4em 7.5em 7.6em 7.7em 7.8em 7.9em 8em 8.1em 8.2em 8.3em 8.4em 8.5em 8.6em 8.7em 8.8em 8.9em 9em 9.1em 9.2em 9.3em 9.4em 9.5em 9.6em 9.7em 9.8em 9.9em 10em 10.1em 10.2em 10.3em 10.4em 10.5em 10.6em 10.7em 10.8em 10.9em 11em 11.1em 11.2em 11.3em 11.4em 11.5em 11.6em 11.7em 11.8em 11.9em 12em"),
			selenium.getText("//select[@id='_113_lfr-font-leading']"));
		assertEquals(RuntimeVariables.replace(
				"-1em -0.95em -0.9em -0.85em -0.8em -0.75em -0.7em -0.65em -0.6em -0.55em -0.5em -0.45em -0.4em -0.35em -0.3em -0.25em -0.2em -0.15em -0.1em -0.05em Normal 0.05em 0.1em 0.15em 0.2em 0.25em 0.3em 0.35em 0.4em 0.45em 0.5em 0.55em 0.6em 0.65em 0.7em 0.75em 0.8em 0.85em 0.9em 0.95em"),
			selenium.getText("//select[@id='_113_lfr-font-space']"));
		assertEquals(RuntimeVariables.replace(
				"0.1em 0.2em 0.3em 0.4em 0.5em 0.6em 0.7em 0.8em 0.9em 1em 1.1em 1.2em 1.3em 1.4em 1.5em 1.6em 1.7em 1.8em 1.9em 2em 2.1em 2.2em 2.3em 2.4em 2.5em 2.6em 2.7em 2.8em 2.9em 3em 3.1em 3.2em 3.3em 3.4em 3.5em 3.6em 3.7em 3.8em 3.9em 4em 4.1em 4.2em 4.3em 4.4em 4.5em 4.6em 4.7em 4.8em 4.9em 5em 5.1em 5.2em 5.3em 5.4em 5.5em 5.6em 5.7em 5.8em 5.9em 6em 6.1em 6.2em 6.3em 6.4em 6.5em 6.6em 6.7em 6.8em 6.9em 7em 7.1em 7.2em 7.3em 7.4em 7.5em 7.6em 7.7em 7.8em 7.9em 8em 8.1em 8.2em 8.3em 8.4em 8.5em 8.6em 8.7em 8.8em 8.9em 9em 9.1em 9.2em 9.3em 9.4em 9.5em 9.6em 9.7em 9.8em 9.9em 10em 10.1em 10.2em 10.3em 10.4em 10.5em 10.6em 10.7em 10.8em 10.9em 11em 11.1em 11.2em 11.3em 11.4em 11.5em 11.6em 11.7em 11.8em 11.9em 12em"),
			selenium.getText("//select[@id='_113_lfr-font-size']"));
		assertEquals(RuntimeVariables.replace(
				"-10px -9px -8px -7px -6px -5px -4px -3px -2px -1px 0 1px 2px 3px 4px 5px 6px 7px 8px 9px 10px 11px 12px 13px 14px 15px 16px 17px 18px 19px 20px 21px 22px 23px 24px 25px 26px 27px 28px 29px 30px 31px 32px 33px 34px 35px 36px 37px 38px 39px 40px 41px 42px 43px 44px 45px 46px 47px 48px 49px 50px"),
			selenium.getText("//select[@id='_113_lfr-font-tracking']"));
		assertEquals("", selenium.getValue("//input[@id='_113_lfr-font-color']"));
		assertEquals(RuntimeVariables.replace("Justify Left Right Center"),
			selenium.getText("//select[@id='_113_lfr-font-align']"));
		assertEquals(RuntimeVariables.replace(
				"None Underline Overline Strikethrough"),
			selenium.getText("//select[@id='_113_lfr-font-decoration']"));
		selenium.clickAt("link=Background Styles",
			RuntimeVariables.replace("Background Styles"));
		selenium.waitForVisible("//input[@id='_113_lfr-bg-color']");
		assertEquals("", selenium.getValue("//input[@id='_113_lfr-bg-color']"));
		selenium.clickAt("link=Border Styles",
			RuntimeVariables.replace("Border Styles"));
		selenium.waitForVisible("//legend/span");
		assertEquals(RuntimeVariables.replace("Border Width"),
			selenium.getText("//legend/span"));
		assertEquals(RuntimeVariables.replace("Border Style"),
			selenium.getText("//div[2]/div/fieldset/legend/span"));
		assertEquals(RuntimeVariables.replace("Border Color"),
			selenium.getText("//div[3]/div/fieldset/legend/span"));
		assertTrue(selenium.isChecked(
				"//input[@id='_113_lfr-use-for-all-widthCheckbox']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_113_lfr-use-for-all-styleCheckbox']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_113_lfr-use-for-all-colorCheckbox']"));
		assertEquals("",
			selenium.getValue("//input[@id='_113_lfr-border-width-top']"));
		assertEquals(RuntimeVariables.replace("% px em"),
			selenium.getText("//select[@id='_113_lfr-border-width-top-unit']"));
		assertEquals(RuntimeVariables.replace(
				"Dashed Double Dotted Groove Hidden Inset Outset Ridge Solid"),
			selenium.getText("//select[@id='_113_lfr-border-style-top']"));
		assertEquals("",
			selenium.getValue("//input[@id='_113_lfr-border-color-top']"));
		selenium.clickAt("link=Margin and Padding",
			RuntimeVariables.replace("Margin and Padding"));
		selenium.waitForVisible(
			"//fieldset[5]/div/div/div/div[1]/div/fieldset/legend/span");
		assertEquals(RuntimeVariables.replace("Padding"),
			selenium.getText(
				"//fieldset[5]/div/div/div/div[1]/div/fieldset/legend/span"));
		assertEquals(RuntimeVariables.replace("Margin"),
			selenium.getText(
				"//fieldset[5]/div/div/div/div[2]/div/fieldset/legend/span"));
		assertTrue(selenium.isChecked(
				"//input[@id='_113_lfr-use-for-all-paddingCheckbox']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_113_lfr-use-for-all-marginCheckbox']"));
		assertEquals("",
			selenium.getValue("//input[@id='_113_lfr-padding-top']"));
		assertEquals(RuntimeVariables.replace("% px em"),
			selenium.getText("//select[@id='_113_lfr-padding-top-unit']"));
		assertEquals("", selenium.getValue("//input[@id='_113_lfr-margin-top']"));
		assertEquals(RuntimeVariables.replace("% px em"),
			selenium.getText("//select[@id='_113_lfr-margin-top-unit']"));
		selenium.clickAt("link=Advanced Styling",
			RuntimeVariables.replace("Advanced Styling"));
		selenium.waitForVisible("//p[@id='lfr-portlet-info']");
		selenium.waitForVisible("//input[@id='_113_lfr-custom-css-class-name']");
		assertTrue(selenium.isPartialText("//p[@id='lfr-portlet-info']",
				"Your current portlet information is as follows:\nPortlet ID: #portlet_67\nPortlet Classes: .portlet"));
		assertTrue(selenium.isPartialText("//p[@id='lfr-portlet-info']",
				"Portlet ID: #portlet_67"));
		assertTrue(selenium.isPartialText("//p[@id='lfr-portlet-info']",
				"Portlet Classes: .portlet"));
		assertEquals("",
			selenium.getValue("//input[@id='_113_lfr-custom-css-class-name']"));
		assertEquals("",
			selenium.getValue("//textarea[@id='_113_lfr-custom-css']"));
		assertEquals(RuntimeVariables.replace(
				"Add a CSS rule for just this portlet."),
			selenium.getText("//a[@id='lfr-add-id']"));
		assertEquals(RuntimeVariables.replace(
				"Add a CSS rule for all portlets like this one."),
			selenium.getText("//a[@id='lfr-add-class']"));
		assertFalse(selenium.isChecked("//input[@id='lfr-update-on-type']"));
		selenium.clickAt("link=WAP Styling",
			RuntimeVariables.replace("WAP Styling"));
		selenium.waitForVisible("//input[@id='_113_lfr-wap-title']");
		assertEquals("", selenium.getValue("//input[@id='_113_lfr-wap-title']"));
		assertEquals(RuntimeVariables.replace("Minimized Normal"),
			selenium.getText(
				"//select[@id='_113_lfr-wap-initial-window-state']"));
	}
}