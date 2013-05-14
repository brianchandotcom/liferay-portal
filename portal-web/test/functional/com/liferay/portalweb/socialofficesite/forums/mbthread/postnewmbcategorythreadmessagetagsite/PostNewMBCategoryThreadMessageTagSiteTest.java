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

package com.liferay.portalweb.socialofficesite.forums.mbthread.postnewmbcategorythreadmessagetagsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PostNewMBCategoryThreadMessageTagSiteTest extends BaseTestCase {
	public void testPostNewMBCategoryThreadMessageTagSite()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard");
				assertEquals(RuntimeVariables.replace("Sites"),
					selenium.getText("//div[@id='so-sidebar']/h3"));
				assertTrue(selenium.isVisible("//input[@class='search-input']"));
				selenium.type("//input[@class='search-input']",
					RuntimeVariables.replace("Open"));
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Open Site Name"),
					selenium.getText(
						"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
				selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
					RuntimeVariables.replace("Open Site Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Forums"),
					selenium.getText("//nav/ul/li[contains(.,'Forums')]/a/span"));
				selenium.clickAt("//nav/ul/li[contains(.,'Forums')]/a/span",
					RuntimeVariables.replace("Forums"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("MB Category Name"),
					selenium.getText("//td[1]/a/strong"));
				selenium.clickAt("//td[1]/a/strong",
					RuntimeVariables.replace("MB Category Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("MB Category Name"),
					selenium.getText("//h1[@class='header-title']/span"));
				selenium.clickAt("//input[@value='Post New Thread']",
					RuntimeVariables.replace("Post New Thread"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_19_subject']",
					RuntimeVariables.replace(
						"MB Category Thread Message Subject"));
				selenium.waitForElementPresent(
					"//textarea[@id='_19_editor' and @style='display: none;']");
				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText("//span[.='Source']"));
				selenium.clickAt("//span[.='Source']",
					RuntimeVariables.replace("Source"));
				selenium.waitForVisible(
					"//a[@class='cke_button_source cke_on']");
				selenium.waitForVisible(
					"//td[@id='cke_contents__19_editor']/textarea");
				selenium.type("//td[@id='cke_contents__19_editor']/textarea",
					RuntimeVariables.replace("MB Category Thread Message Body"));
				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText("//span[.='Source']"));
				selenium.clickAt("//span[.='Source']",
					RuntimeVariables.replace("Source"));
				selenium.waitForElementPresent(
					"//textarea[@id='_19_editor' and @style='display: none;']");
				selenium.waitForVisible(
					"//td[@id='cke_contents__19_editor']/iframe");
				selenium.selectFrame(
					"//td[@id='cke_contents__19_editor']/iframe");
				selenium.waitForText("//body", "MB Category Thread Message Body");
				selenium.selectFrame("relative=top");

				boolean addTagsVisible = selenium.isVisible(
						"//input[@title='Add Tags']");

				if (addTagsVisible) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Categorization"),
					selenium.getText(
						"//div[@id='mbMessageCategorizationPanel']/div/div/span"));
				selenium.clickAt("//div[@id='mbMessageCategorizationPanel']/div/div/span",
					RuntimeVariables.replace("Categorization"));
				selenium.waitForVisible("//input[@title='Add Tags']");

			case 2:
				selenium.type("//input[@title='Add Tags']",
					RuntimeVariables.replace("tag1"));
				selenium.clickAt("//button[@id='add']",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible(
					"xPath=(//span[@class='textboxlistentry-text'])[1]");
				assertEquals(RuntimeVariables.replace("tag1"),
					selenium.getText(
						"xPath=(//span[@class='textboxlistentry-text'])[1]"));
				selenium.type("//input[@title='Add Tags']",
					RuntimeVariables.replace("tag2"));
				selenium.clickAt("//button[@id='add']",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible(
					"xPath=(//span[@class='textboxlistentry-text'])[2]");
				assertEquals(RuntimeVariables.replace("tag2"),
					selenium.getText(
						"xPath=(//span[@class='textboxlistentry-text'])[2]"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message Subject"),
					selenium.getText("//div[@class='subject']/a/strong"));
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message Body"),
					selenium.getText("//div[@class='thread-body']"));

			case 100:
				label = -1;
			}
		}
	}
}