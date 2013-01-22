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

package com.liferay.portalweb.portal.permissions.blogs.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_DefineBARoleTest extends BaseTestCase {
	public void testSA_DefineBARole() throws Exception {
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
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_128_keywords']",
					RuntimeVariables.replace("Blogs"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Blogs Administrator"),
					selenium.getText("//tr[3]/td/a"));
				selenium.clickAt("//tr[3]/td/a",
					RuntimeVariables.replace("Blogs Administrator"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Define Permissions",
					RuntimeVariables.replace("Define Permissions"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("label=Blogs"));
				selenium.waitForPageToLoad("30000");

				boolean blogsSelectAllChecked1 = selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])[1]");

				if (blogsSelectAllChecked1) {
					label = 2;

					continue;
				}

				selenium.clickAt("xPath=(//input[@name='_128_allRowIds'])[1]",
					RuntimeVariables.replace("Select All"));

			case 2:
				assertTrue(selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])[1]"));

				boolean blogsEntrySelectAllChecked2 = selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])[2]");

				if (blogsEntrySelectAllChecked2) {
					label = 3;

					continue;
				}

				selenium.clickAt("xPath=(//input[@name='_128_allRowIds'])[2]",
					RuntimeVariables.replace("Select All"));

			case 3:
				assertTrue(selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])[2]"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("label=Blogs"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogsADD_PORTLET_DISPLAY_TEMPLATE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogsADD_ENTRY']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogsPERMISSIONS']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogsSUBSCRIBE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogs.model.BlogsEntryADD_DISCUSSION']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogs.model.BlogsEntryDELETE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogs.model.BlogsEntryDELETE_DISCUSSION']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogs.model.BlogsEntryPERMISSIONS']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogs.model.BlogsEntryUPDATE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogs.model.BlogsEntryUPDATE_DISCUSSION']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogs.model.BlogsEntryVIEW']"));
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("index=36"));
				selenium.waitForPageToLoad("30000");

				boolean blogsSelectAllChecked3 = selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])");

				if (blogsSelectAllChecked3) {
					label = 4;

					continue;
				}

				selenium.clickAt("xPath=(//input[@name='_128_allRowIds'])",
					RuntimeVariables.replace("Select All"));

			case 4:
				assertTrue(selenium.isChecked(
						"xPath=(//input[@name='_128_allRowIds'])"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("index=36"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isChecked("//input[@value='33ADD_TO_PAGE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='33CONFIGURATION']"));
				assertTrue(selenium.isChecked("//input[@value='33PERMISSIONS']"));
				assertTrue(selenium.isChecked("//input[@value='33VIEW']"));

			case 100:
				label = -1;
			}
		}
	}
}