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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.addwcsubfolders;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.addwcfolder.AddWCFolderTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.addwcsubfolder.AddWCSubfolder1Test;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.addwcsubfolder.AddWCSubfolder2Test;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.addwcsubfolder.AddWCSubfolder3Test;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontent.TearDownWCWebContentTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCSubfoldersTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddWCFolderTest.class);
		testSuite.addTestSuite(AddWCSubfolder1Test.class);
		testSuite.addTestSuite(AddWCSubfolder2Test.class);
		testSuite.addTestSuite(AddWCSubfolder3Test.class);
		testSuite.addTestSuite(ViewWCSubfoldersTest.class);
		testSuite.addTestSuite(TearDownWCWebContentTest.class);

		return testSuite;
	}
}