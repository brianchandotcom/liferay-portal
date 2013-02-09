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

package com.liferay.portalweb.portal.selenium.assertions;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.selenium.assertions.assertchecked.AssertCheckedTests;
import com.liferay.portalweb.portal.selenium.assertions.assertelementpresent.AssertElementPresentTests;
import com.liferay.portalweb.portal.selenium.assertions.assertselectedlabel.AssertSelectedLabelTests;
import com.liferay.portalweb.portal.selenium.assertions.asserttext.AssertTextTests;
import com.liferay.portalweb.portal.selenium.assertions.asserttextpresent.AssertTextPresentTests;
import com.liferay.portalweb.portal.selenium.assertions.assertvalue.AssertValueTests;
import com.liferay.portalweb.portal.selenium.assertions.assertvisible.AssertVisibleTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertionsTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AssertCheckedTests.suite());
		testSuite.addTest(AssertElementPresentTests.suite());
		testSuite.addTest(AssertSelectedLabelTests.suite());
		testSuite.addTest(AssertTextTests.suite());
		testSuite.addTest(AssertTextPresentTests.suite());
		testSuite.addTest(AssertValueTests.suite());
		testSuite.addTest(AssertVisibleTests.suite());

		return testSuite;
	}

}