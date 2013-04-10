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

package com.liferay.portalweb.socialofficehome.sites.site;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.sites.site.addsitesdefaultpagenone.AddSitesDefaultPageNoneTests;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessite.AddSitesSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.addsiteviewccusertest.AddSiteViewCCUserTests;
import com.liferay.portalweb.socialofficehome.sites.site.deletesite.DeleteSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.searchdeletesite.SearchDeleteSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.searchdmfolderdocumentdeletesite.SearchDMFolderDocumentDeleteSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.searchmbthreaddeletesite.SearchMBThreadDeleteSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.searchsitesdirectory.SearchSitesDirectoryTests;
import com.liferay.portalweb.socialofficehome.sites.site.searchsitessite.SearchSitesSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.sousfavoritesite1.SOUs_FavoriteSite1Tests;
import com.liferay.portalweb.socialofficehome.sites.site.sousjoinsitessite.SOUs_JoinSitesSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.sousleavesite.SOUs_LeaveSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.soussearchsitessite.SOUs_SearchSitesSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.sousviewsiteslinksauserprofile.SOUs_ViewSitesLinkSAUserProfileTests;
import com.liferay.portalweb.socialofficehome.sites.site.viewsitesdirectory.ViewSitesDirectoryTests;
import com.liferay.portalweb.socialofficehome.sites.site.viewsitesdirectorymysites.ViewSitesDirectoryMySitesTests;
import com.liferay.portalweb.socialofficehome.sites.site.viewsitessite.ViewSitesSiteTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddSitesDefaultPageNoneTests.suite());
		testSuite.addTest(AddSitesSiteTests.suite());
		testSuite.addTest(AddSiteViewCCUserTests.suite());
		testSuite.addTest(DeleteSiteTests.suite());
		testSuite.addTest(SearchDeleteSiteTests.suite());
		testSuite.addTest(SearchDMFolderDocumentDeleteSiteTests.suite());
		testSuite.addTest(SearchMBThreadDeleteSiteTests.suite());
		testSuite.addTest(SearchSitesDirectoryTests.suite());
		testSuite.addTest(SearchSitesSiteTests.suite());
		testSuite.addTest(SOUs_FavoriteSite1Tests.suite());
		testSuite.addTest(SOUs_JoinSitesSiteTests.suite());
		testSuite.addTest(SOUs_LeaveSiteTests.suite());
		testSuite.addTest(SOUs_SearchSitesSiteTests.suite());
		testSuite.addTest(SOUs_ViewSitesLinkSAUserProfileTests.suite());
		testSuite.addTest(ViewSitesDirectoryTests.suite());
		testSuite.addTest(ViewSitesDirectoryMySitesTests.suite());
		testSuite.addTest(ViewSitesSiteTests.suite());

		return testSuite;
	}

}