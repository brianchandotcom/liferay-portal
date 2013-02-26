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

package com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite.acceptmemberrequestsiteprivaterestrict.AcceptMemberRequestSitePrivateRestrictTests;
import com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite.addsitessitetypeprivaterestricted.AddSitesSiteTypePrivateRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite.deletesitetypeprivaterestricted.DeleteSiteTypePrivateRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite.denymemberrequestsitetypeprivaterestrict.DenyMemberRequestSiteTypePrivateRestrictTests;
import com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite.searchdeletesitetypeprivaterestricted.SearchDeleteSiteTypePrivateRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite.searchdocumentdeletesiteprivaterestrict.SearchDocumentDeleteSitePrivateRestrictTests;
import com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite.searchsitessitetypeprivaterestricted.SearchSitesSiteTypePrivateRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite.searchthreaddeletesiteprivaterestrict.SearchThreadDeleteSitePrivateRestrictTests;
import com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite.sousfavoritesite1typeprivaterestricted.SOUs_FavoriteSite1TypePrivateRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite.soussearchsitessitetypeprivaterestricted.SOUs_SearchSitesSiteTypePrivateRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite.viewsitessitetypeprivaterestricted.ViewSitesSiteTypePrivateRestrictedTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PrivateRestrictedSiteTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AcceptMemberRequestSitePrivateRestrictTests.suite());
		testSuite.addTest(AddSitesSiteTypePrivateRestrictedTests.suite());
		testSuite.addTest(DeleteSiteTypePrivateRestrictedTests.suite());
		testSuite.addTest(
			DenyMemberRequestSiteTypePrivateRestrictTests.suite());
		testSuite.addTest(SearchDeleteSiteTypePrivateRestrictedTests.suite());
		testSuite.addTest(SearchDocumentDeleteSitePrivateRestrictTests.suite());
		testSuite.addTest(SearchSitesSiteTypePrivateRestrictedTests.suite());
		testSuite.addTest(SearchThreadDeleteSitePrivateRestrictTests.suite());
		testSuite.addTest(SOUs_FavoriteSite1TypePrivateRestrictedTests.suite());
		testSuite.addTest(
			SOUs_SearchSitesSiteTypePrivateRestrictedTests.suite());
		testSuite.addTest(ViewSitesSiteTypePrivateRestrictedTests.suite());

		return testSuite;
	}

}