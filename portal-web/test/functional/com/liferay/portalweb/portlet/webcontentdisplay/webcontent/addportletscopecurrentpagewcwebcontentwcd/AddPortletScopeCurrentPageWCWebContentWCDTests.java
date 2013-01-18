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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.addportletscopecurrentpagewcwebcontentwcd;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.addportletwcd.AddPageWCD1Test;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.addportletwcd.AddPageWCD2Test;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.addportletwcd.AddPageWCD3Test;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.addportletwcd.AddPortletWCD1Test;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.addportletwcd.AddPortletWCD2Test;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.addportletwcd.AddPortletWCD3Test;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletscopecurrentpage.ConfigurePortletWCD2ScopeCurrentPageTest;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletscopedefault.ConfigurePortletWCD1ScopeDefaultTest;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletscopedefault.TearDownPortletWCD1ScopeTest;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletscopepage2.ConfigurePortletWCD3ScopePageWCD2Test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPortletScopeCurrentPageWCWebContentWCDTests
	extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageWCD1Test.class);
		testSuite.addTestSuite(AddPortletWCD1Test.class);
		testSuite.addTestSuite(AddPageWCD2Test.class);
		testSuite.addTestSuite(AddPortletWCD2Test.class);
		testSuite.addTestSuite(AddPageWCD3Test.class);
		testSuite.addTestSuite(AddPortletWCD3Test.class);
		testSuite.addTestSuite(ConfigurePortletWCD1ScopeDefaultTest.class);
		testSuite.addTestSuite(ConfigurePortletWCD2ScopeCurrentPageTest.class);
		testSuite.addTestSuite(ConfigurePortletWCD3ScopePageWCD2Test.class);
		testSuite.addTestSuite(AddPortletScopeCurrentPageWCWebContentWCDTest.class);
		testSuite.addTestSuite(ViewScopeCurrentPageWCWebContentNullDefaultTest.class);
		testSuite.addTestSuite(ViewScopeCurrentPageWCWebContentTest.class);
		testSuite.addTestSuite(ViewScopeCurrentPageWCWebContentNullPage2Test.class);
		testSuite.addTestSuite(ViewScopeCurrentPageWebContentListDefaultTest.class);
		testSuite.addTestSuite(ViewScopeCurrentPageWebContentListCurrentPageTest.class);
		testSuite.addTestSuite(ViewScopeCurrentPageWebContentListPage2Test.class);
		testSuite.addTestSuite(ViewScopeCurrentPageWCWebContentListDefaultCPTest.class);
		testSuite.addTestSuite(ViewScopeCurrentPageWCWebContentListCurrentPageCPTest.class);
		testSuite.addTestSuite(TearDownPortletWCD1ScopeTest.class);
		testSuite.addTestSuite(TearDownScopeWebContentTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}