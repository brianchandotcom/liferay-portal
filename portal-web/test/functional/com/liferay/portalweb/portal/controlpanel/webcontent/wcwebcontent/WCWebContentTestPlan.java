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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontent.AddWCWebContentTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontents.AddWCWebContentsTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontentscopeglobal.AddWCWebContentScopeGlobalTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontentscopeliferay.AddWCWebContentScopeLiferayTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontentscopemysite.AddWCWebContentScopeMySiteTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontentsearchable.AddWCWebContentSearchableTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontentsearchableno.AddWCWebContentSearchableNoTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontentstructure.AddWCWebContentStructureTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.editwcwebcontent.EditWCWebContentTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.localizewcwebcontent.LocalizeWCWebContentTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.movewcwebcontenttofolder.MoveWCWebContentToFolderTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.viewwcwebcontentfiltermine.ViewWCWebContentFilterMineTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.viewwcwebcontentfilterrecent.ViewWCWebContentFilterRecentTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.viewwcwebcontentfilterstructure.ViewWCWebContentFilterStructureTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WCWebContentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWCWebContentTests.suite());
		testSuite.addTest(AddWCWebContentsTests.suite());
		testSuite.addTest(AddWCWebContentScopeGlobalTests.suite());
		testSuite.addTest(AddWCWebContentScopeLiferayTests.suite());
		testSuite.addTest(AddWCWebContentScopeMySiteTests.suite());
		testSuite.addTest(AddWCWebContentSearchableTests.suite());
		testSuite.addTest(AddWCWebContentSearchableNoTests.suite());
		testSuite.addTest(AddWCWebContentStructureTests.suite());
		testSuite.addTest(EditWCWebContentTests.suite());
		testSuite.addTest(LocalizeWCWebContentTests.suite());
		testSuite.addTest(MoveWCWebContentToFolderTests.suite());
		testSuite.addTest(ViewWCWebContentFilterMineTests.suite());
		testSuite.addTest(ViewWCWebContentFilterRecentTests.suite());
		testSuite.addTest(ViewWCWebContentFilterStructureTests.suite());

		return testSuite;
	}

}