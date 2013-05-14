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

package com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontentcomment.ratewcwebcontentcommentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RateWCWebContentCommentWCDTest extends BaseTestCase {
	public void testRateWCWebContentCommentWCD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//div[@class='journal-content-article']/p"));
		assertEquals(RuntimeVariables.replace("WC WebContent Comment"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//div[@class='rating-label-element']"));
		assertTrue(selenium.isVisible(
				"//div[contains(@id, 'ratingThumbContent')]/a[contains(@class,'rating-thumb-up')]"));
		assertTrue(selenium.isVisible(
				"//div[contains(@id, 'ratingThumbContent')]/a[contains(@class,'rating-thumb-down')]"));
		selenium.clickAt("//div[contains(@id, 'ratingThumbContent')]/a[contains(@class,'rating-thumb-up')]",
			RuntimeVariables.replace("Rate this as good"));
		Thread.sleep(1000);
		selenium.waitForVisible("//a[contains(@class,'aui-rating-element-on')]");
		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("//div[@class='rating-label-element']"));
		selenium.clickAt("//div[contains(@id, 'ratingThumbContent')]/a[contains(@class,'rating-thumb-up')]",
			RuntimeVariables.replace("Rate this as good"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//a[contains(@class,'aui-rating-element-off')]");
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//div[@class='rating-label-element']"));
		selenium.clickAt("//div[contains(@id, 'ratingThumbContent')]/a[contains(@class,'rating-thumb-down')]",
			RuntimeVariables.replace("Rate this as bad"));
		Thread.sleep(1000);
		selenium.waitForVisible("//a[contains(@class,'aui-rating-element-on')]");
		assertEquals(RuntimeVariables.replace("-1 (1 Vote)"),
			selenium.getText("//div[@class='rating-label-element']"));
		selenium.clickAt("//div[contains(@id, 'ratingThumbContent')]/a[contains(@class,'rating-thumb-down')]",
			RuntimeVariables.replace("Rate this as bad"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//a[contains(@class,'aui-rating-element-off')]");
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//div[@class='rating-label-element']"));
	}
}