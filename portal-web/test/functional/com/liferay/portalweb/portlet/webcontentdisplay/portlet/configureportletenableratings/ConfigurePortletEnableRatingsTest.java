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

package com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletenableratings;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletEnableRatingsTest extends BaseTestCase {
	public void testConfigurePortletEnableRatings() throws Exception {
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
					"//input[@id='_86_enableRatingsCheckbox']");

				boolean enableRatingsNotChecked = selenium.isChecked(
						"//input[@id='_86_enableRatingsCheckbox']");

				if (enableRatingsNotChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enableRatingsCheckbox']",
					RuntimeVariables.replace("Enable Ratings"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_86_enableRatingsCheckbox']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isChecked(
						"//input[@id='_86_enableRatingsCheckbox']"));
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Web Content Display Test Page",
					RuntimeVariables.replace("Web Content Display Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("WC WebContent Content"),
					selenium.getText(
						"//div[@class='journal-content-article']/p"));
				assertEquals(RuntimeVariables.replace("Your Rating"),
					selenium.getText(
						"//div[contains(@id,'ratingStarContent')]/div"));
				assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
					selenium.getText(
						"//div[contains(@id,'ratingScoreContent')]/div"));

			case 100:
				label = -1;
			}
		}
	}
}