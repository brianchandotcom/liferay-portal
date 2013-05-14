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

package com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletenablecommentratings;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPortletSetupTest extends BaseTestCase {
	public void testTearDownPortletSetup() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Web Content Display Test Page",
					RuntimeVariables.replace("Web Content Display Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]",
					RuntimeVariables.replace("Configuration"));
				selenium.waitForVisible(
					"//iframe[contains(@id,'configurationIframeDialog')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'configurationIframeDialog')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/navigation_interaction.js')]");
				selenium.waitForVisible(
					"//input[@id='_86_showAvailableLocalesCheckbox']");

				boolean showAvailableLocalesChecked = selenium.isChecked(
						"//input[@id='_86_showAvailableLocalesCheckbox']");

				if (!showAvailableLocalesChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_86_showAvailableLocalesCheckbox']",
					RuntimeVariables.replace("Show Available Locales"));

			case 2:
				selenium.waitForVisible(
					"//input[@id='_86_enablePrintCheckbox']");

				boolean enablePrintChecked = selenium.isChecked(
						"//input[@id='_86_enablePrintCheckbox']");

				if (!enablePrintChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enablePrintCheckbox']",
					RuntimeVariables.replace("Enable Print"));

			case 3:
				selenium.waitForVisible(
					"//input[@id='_86_enableRelatedAssetsCheckbox']");

				boolean enableRelatedAssetsNotChecked = selenium.isChecked(
						"//input[@id='_86_enableRelatedAssetsCheckbox']");

				if (enableRelatedAssetsNotChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enableRelatedAssetsCheckbox']",
					RuntimeVariables.replace("Enable Related Assets"));

			case 4:
				selenium.waitForVisible(
					"//input[@id='_86_enableRatingsCheckbox']");

				boolean enableRatingsChecked = selenium.isChecked(
						"//input[@id='_86_enableRatingsCheckbox']");

				if (!enableRatingsChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enableRatingsCheckbox']",
					RuntimeVariables.replace("Enable Ratings"));

			case 5:
				selenium.waitForVisible(
					"//input[@id='_86_enableCommentsCheckbox']");

				boolean enableCommentsChecked = selenium.isChecked(
						"//input[@id='_86_enableCommentsCheckbox']");

				if (!enableCommentsChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enableCommentsCheckbox']",
					RuntimeVariables.replace("Enable Comments"));

			case 6:
				selenium.waitForVisible(
					"//input[@id='_86_enableCommentRatingsCheckbox']");

				boolean enableCommentRatingsChecked = selenium.isChecked(
						"//input[@id='_86_enableCommentRatingsCheckbox']");

				if (!enableCommentRatingsChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enableCommentRatingsCheckbox']",
					RuntimeVariables.replace("Enable Comment Ratings"));

			case 7:
				selenium.waitForVisible(
					"//input[@id='_86_enableViewCountIncrementCheckbox']");

				boolean enableViewCountIncrementNotChecked = selenium.isChecked(
						"//input[@id='_86_enableViewCountIncrementCheckbox']");

				if (enableViewCountIncrementNotChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enableViewCountIncrementCheckbox']",
					RuntimeVariables.replace("Enable View Count Increment"));

			case 8:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_showAvailableLocalesCheckbox']"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_enablePrintCheckbox']"));
				assertTrue(selenium.isChecked(
						"//input[@id='_86_enableRelatedAssetsCheckbox']"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_enableRatingsCheckbox']"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_enableCommentsCheckbox']"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_enableCommentRatingsCheckbox']"));
				assertTrue(selenium.isChecked(
						"//input[@id='_86_enableViewCountIncrementCheckbox']"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}