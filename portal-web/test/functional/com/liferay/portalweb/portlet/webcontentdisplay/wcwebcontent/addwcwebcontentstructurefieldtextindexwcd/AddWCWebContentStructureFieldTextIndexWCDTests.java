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

package com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldtextindexwcd;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructure.TearDownWCStructureTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldtextindexable.AddWCStructureFieldTextIndexableTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructure.TearDownWCTemplateStructureTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldtextindex.AddWCTemplateStructureFieldTextIndexTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontent.TearDownWCWebContentTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.search.portlet.addportletsearch.AddPageSearchTest;
import com.liferay.portalweb.portlet.search.portlet.addportletsearch.AddPortletSearchTest;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.addportletwcd.AddPageWCDTest;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.addportletwcd.AddPortletWCDTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCWebContentStructureFieldTextIndexWCDTests
	extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageWCDTest.class);
		testSuite.addTestSuite(AddPortletWCDTest.class);
		testSuite.addTestSuite(AddPageSearchTest.class);
		testSuite.addTestSuite(AddPortletSearchTest.class);
		testSuite.addTestSuite(AddWCStructureFieldTextIndexableTest.class);
		testSuite.addTestSuite(AddWCTemplateStructureFieldTextIndexTest.class);
		testSuite.addTestSuite(AddWCWebContentStructureFieldTextIndexWCDTest.class);
		testSuite.addTestSuite(ViewWCWebContentStructureFieldTextIndexWCDTest.class);
		testSuite.addTestSuite(SearchWCWebContentStructureFieldTextIndexSearchTest.class);
		testSuite.addTestSuite(TearDownWCWebContentTest.class);
		testSuite.addTestSuite(TearDownWCTemplateStructureTest.class);
		testSuite.addTestSuite(TearDownWCStructureTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}