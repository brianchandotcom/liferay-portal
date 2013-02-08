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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcsubstructures;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructure.AddWCStructureTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructure.TearDownWCStructureTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcsubstructure.AddWCSubstructure1Test;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcsubstructure.AddWCSubstructure2Test;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcsubstructure.AddWCSubstructure3Test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCSubstructuresTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddWCStructureTest.class);
		testSuite.addTestSuite(AddWCSubstructure1Test.class);
		testSuite.addTestSuite(AddWCSubstructure2Test.class);
		testSuite.addTestSuite(AddWCSubstructure3Test.class);
		testSuite.addTestSuite(ViewWCSubstructuresTest.class);
		testSuite.addTestSuite(TearDownWCStructureTest.class);

		return testSuite;
	}
}