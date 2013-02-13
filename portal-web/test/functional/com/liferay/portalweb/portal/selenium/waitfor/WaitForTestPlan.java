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

package com.liferay.portalweb.portal.selenium.waitfor;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.selenium.waitfor.confirmation.WaitForConfirmationTests;
import com.liferay.portalweb.portal.selenium.waitfor.elementpresent.WaitForElementPresentTests;
import com.liferay.portalweb.portal.selenium.waitfor.partialtext.WaitForPartialTextTests;
import com.liferay.portalweb.portal.selenium.waitfor.popup.WaitForPopupTests;
import com.liferay.portalweb.portal.selenium.waitfor.selectedlabel.WaitForSelectedLabelTests;
import com.liferay.portalweb.portal.selenium.waitfor.text.WaitForTextTests;
import com.liferay.portalweb.portal.selenium.waitfor.textpresent.WaitForTextPresentTests;
import com.liferay.portalweb.portal.selenium.waitfor.value.WaitForValueTests;
import com.liferay.portalweb.portal.selenium.waitfor.visible.WaitForVisibleTests;


import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WaitForTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(WaitForConfirmationTests.suite());
		testSuite.addTest(WaitForElementPresentTests.suite());
		testSuite.addTest(WaitForPartialTextTests.suite());
		testSuite.addTest(WaitForPopupTests.suite());
		testSuite.addTest(WaitForSelectedLabelTests.suite());
		testSuite.addTest(WaitForTextTests.suite());
		testSuite.addTest(WaitForTextPresentTests.suite());
		testSuite.addTest(WaitForValueTests.suite());
		testSuite.addTest(WaitForVisibleTests.suite());


		return testSuite;
	}

}