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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.addwcfolder.AddWCFolderTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.addwcfolders.AddWCFoldersTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.addwcsubfolder.AddWCSubfolderTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.addwcsubfolders.AddWCSubfoldersTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.deletewcfolder.DeleteWCFolderTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.deletewcsubfolder.DeleteWCSubfolderTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.editwcfolder.EditWCFolderTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.editwcsubfolder.EditWCSubfolderTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.movewcsubfoldertofolder2.MoveWCSubfolderToFolder2Tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WCFolderTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWCFolderTests.suite());
		testSuite.addTest(AddWCFoldersTests.suite());
		testSuite.addTest(AddWCSubfolderTests.suite());
		testSuite.addTest(AddWCSubfoldersTests.suite());
		testSuite.addTest(DeleteWCFolderTests.suite());
		testSuite.addTest(DeleteWCSubfolderTests.suite());
		testSuite.addTest(EditWCFolderTests.suite());
		testSuite.addTest(EditWCSubfolderTests.suite());
		testSuite.addTest(MoveWCSubfolderToFolder2Tests.suite());

		return testSuite;
	}

}