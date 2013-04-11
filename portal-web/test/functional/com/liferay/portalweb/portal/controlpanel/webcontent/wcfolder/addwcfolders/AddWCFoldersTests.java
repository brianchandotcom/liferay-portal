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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.addwcfolders;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.addwcfolder.AddWCFolder1Test;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.addwcfolder.AddWCFolder2Test;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.addwcfolder.AddWCFolder3Test;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontent.TearDownWCWebContentTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCFoldersTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddWCFolder1Test.class);
		testSuite.addTestSuite(AddWCFolder2Test.class);
		testSuite.addTestSuite(AddWCFolder3Test.class);
		testSuite.addTestSuite(ViewWCFoldersTest.class);
		testSuite.addTestSuite(TearDownWCWebContentTest.class);

		return testSuite;
	}
}