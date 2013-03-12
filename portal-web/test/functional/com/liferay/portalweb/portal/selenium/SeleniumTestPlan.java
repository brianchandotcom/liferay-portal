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

package com.liferay.portalweb.portal.selenium;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.selenium.alerts.AlertsTestPlan;
import com.liferay.portalweb.portal.selenium.assertions.AssertionsTestPlan;
import com.liferay.portalweb.portal.selenium.browsercommands.BrowserCommandsTestPlan;
import com.liferay.portalweb.portal.selenium.clicking.ClickingTestPlan;
import com.liferay.portalweb.portal.selenium.javascript.JavascriptTestPlan;
import com.liferay.portalweb.portal.selenium.list.ListTestPlan;
import com.liferay.portalweb.portal.selenium.mouseactions.MouseActionsTestPlan;
import com.liferay.portalweb.portal.selenium.selection.SelectionTestPlan;
import com.liferay.portalweb.portal.selenium.typing.TypingTestPlan;
import com.liferay.portalweb.portal.selenium.waitfor.WaitForTestPlan;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleniumTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AlertsTestPlan.suite());
		testSuite.addTest(AssertionsTestPlan.suite());
		testSuite.addTest(BrowserCommandsTestPlan.suite());
		testSuite.addTest(ClickingTestPlan.suite());
		testSuite.addTest(JavascriptTestPlan.suite());
		testSuite.addTest(ListTestPlan.suite());
		testSuite.addTest(MouseActionsTestPlan.suite());
		testSuite.addTest(SelectionTestPlan.suite());
		testSuite.addTest(TypingTestPlan.suite());
		testSuite.addTest(WaitForTestPlan.suite());

		return testSuite;
	}

}