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

package com.liferay.portalweb.socialofficesite.forums.mbthread.postnewmbcategorythreadmultiplemessagesite;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessite.AddSitesSiteTest;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessite.TearDownSOSitesTest;
import com.liferay.portalweb.socialofficesite.forums.mbthread.postnewmbcategorythreadmessagesite.AddMBCategorySiteTest;
import com.liferay.portalweb.socialofficesite.forums.mbthread.postnewmbcategorythreadmessagesite.PostNewMBCategoryThread1MessageSiteTest;
import com.liferay.portalweb.socialofficesite.forums.mbthread.postnewmbcategorythreadmessagesite.PostNewMBCategoryThread2MessageSiteTest;
import com.liferay.portalweb.socialofficesite.forums.mbthread.postnewmbcategorythreadmessagesite.PostNewMBCategoryThread3MessageSiteTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PostNewMBCategoryThreadMultipleMessageSiteTests
	extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSitesSiteTest.class);
		testSuite.addTestSuite(AddMBCategorySiteTest.class);
		testSuite.addTestSuite(PostNewMBCategoryThread1MessageSiteTest.class);
		testSuite.addTestSuite(ViewMBCategoryThread1MessageSiteTest.class);
		testSuite.addTestSuite(PostNewMBCategoryThread2MessageSiteTest.class);
		testSuite.addTestSuite(ViewMBCategoryThread2MessageSiteTest.class);
		testSuite.addTestSuite(PostNewMBCategoryThread3MessageSiteTest.class);
		testSuite.addTestSuite(ViewMBCategoryThread3MessageSiteTest.class);
		testSuite.addTestSuite(NextMBCategoryThreadMessageSiteTest.class);
		testSuite.addTestSuite(PreviousMBCategoryThreadMessageSiteTest.class);
		testSuite.addTestSuite(TearDownSOSitesTest.class);

		return testSuite;
	}
}