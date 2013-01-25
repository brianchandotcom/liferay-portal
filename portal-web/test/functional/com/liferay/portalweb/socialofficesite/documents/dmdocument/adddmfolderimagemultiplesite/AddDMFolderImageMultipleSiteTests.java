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

package com.liferay.portalweb.socialofficesite.documents.dmdocument.adddmfolderimagemultiplesite;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessite.AddSitesSiteTest;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessite.TearDownSOSitesTest;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.adddmfolderdocumentsite.AddDMFolderSiteTest;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.adddmfolderimagesite.AddDMFolderImage1SiteTest;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.adddmfolderimagesite.AddDMFolderImage2SiteTest;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.adddmfolderimagesite.AddDMFolderImage3SiteTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMFolderImageMultipleSiteTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSitesSiteTest.class);
		testSuite.addTestSuite(AddDMFolderSiteTest.class);
		testSuite.addTestSuite(AddDMFolderImage1SiteTest.class);
		testSuite.addTestSuite(AddDMFolderImage2SiteTest.class);
		testSuite.addTestSuite(AddDMFolderImage3SiteTest.class);
		testSuite.addTestSuite(ViewDMFolderImageMultipleSiteTest.class);
		testSuite.addTestSuite(TearDownSOSitesTest.class);

		return testSuite;
	}
}