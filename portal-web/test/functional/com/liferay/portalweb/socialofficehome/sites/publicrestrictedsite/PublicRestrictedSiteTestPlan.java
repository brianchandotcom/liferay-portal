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

package com.liferay.portalweb.socialofficehome.sites.publicrestrictedsite;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.sites.publicrestrictedsite.acceptmemberrequestsitepublicrestrict.AcceptMemberRequestSitePublicRestrictTests;
import com.liferay.portalweb.socialofficehome.sites.publicrestrictedsite.addsitessitetypepublicrestricted.AddSitesSiteTypePublicRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.publicrestrictedsite.deletesitetypepublicrestricted.DeleteSiteTypePublicRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.publicrestrictedsite.denymemberrequestsitetypepublicrestrict.DenyMemberRequestSiteTypePublicRestrictTests;
import com.liferay.portalweb.socialofficehome.sites.publicrestrictedsite.searchdeletesitetypepublicrestricted.SearchDeleteSiteTypePublicRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.publicrestrictedsite.searchdocumentdeletesitepublicrestrict.SearchDocumentDeleteSitePublicRestrictTests;
import com.liferay.portalweb.socialofficehome.sites.publicrestrictedsite.searchsitessitetypepublicrestricted.SearchSitesSiteTypePublicRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.publicrestrictedsite.searchthreaddeletesitepublicrestrict.SearchThreadDeleteSitePublicRestrictTests;
import com.liferay.portalweb.socialofficehome.sites.publicrestrictedsite.sousfavoritesite1typepublicrestricted.SOUs_FavoriteSite1TypePublicRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.publicrestrictedsite.soussearchsitessitetypepublicrestricted.SOUs_SearchSitesSiteTypePublicRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.publicrestrictedsite.viewsitessitetypepublicrestricted.ViewSitesSiteTypePublicRestrictedTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PublicRestrictedSiteTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AcceptMemberRequestSitePublicRestrictTests.suite());
		testSuite.addTest(AddSitesSiteTypePublicRestrictedTests.suite());
		testSuite.addTest(DeleteSiteTypePublicRestrictedTests.suite());
		testSuite.addTest(DenyMemberRequestSiteTypePublicRestrictTests.suite());
		testSuite.addTest(SearchDeleteSiteTypePublicRestrictedTests.suite());
		testSuite.addTest(SearchDocumentDeleteSitePublicRestrictTests.suite());
		testSuite.addTest(SearchSitesSiteTypePublicRestrictedTests.suite());
		testSuite.addTest(SearchThreadDeleteSitePublicRestrictTests.suite());
		testSuite.addTest(SOUs_FavoriteSite1TypePublicRestrictedTests.suite());
		testSuite.addTest(
			SOUs_SearchSitesSiteTypePublicRestrictedTests.suite());
		testSuite.addTest(ViewSitesSiteTypePublicRestrictedTests.suite());

		return testSuite;
	}

}