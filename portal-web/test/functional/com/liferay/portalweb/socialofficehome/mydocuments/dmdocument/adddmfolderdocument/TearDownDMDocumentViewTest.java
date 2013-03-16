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

package com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderdocument;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownDMDocumentViewTest extends BaseTestCase {
	public void testTearDownDMDocumentView() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard/");
				assertEquals(RuntimeVariables.replace("My Documents"),
					selenium.getText(
						"//nav/ul/li[contains(.,'My Documents')]/a/span"));
				selenium.clickAt("//nav/ul/li[contains(.,'My Documents')]/a/span",
					RuntimeVariables.replace("My Documents"));
				selenium.waitForPageToLoad("30000");

				boolean iconViewSelected = selenium.isElementPresent(
						"//button[contains(@class,'active') and contains(@title,'Icon')]");

				if (iconViewSelected) {
					label = 2;

					continue;
				}

				Thread.sleep(1000);
				assertTrue(selenium.isVisible("//button[@title='Icon View']"));
				selenium.clickAt("//button[@title='Icon View']",
					RuntimeVariables.replace("Icon View"));
				selenium.waitForElementPresent(
					"//button[contains(@class,'active') and contains(@title,'Icon')]");

			case 2:
				assertTrue(selenium.isElementPresent(
						"//button[contains(@class,'active') and contains(@title,'Icon')]"));

			case 100:
				label = -1;
			}
		}
	}
}