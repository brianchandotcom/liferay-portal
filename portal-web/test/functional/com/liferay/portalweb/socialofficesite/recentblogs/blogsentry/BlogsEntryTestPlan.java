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

package com.liferay.portalweb.socialofficesite.recentblogs.blogsentry;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficesite.recentblogs.blogsentry.configurerbselectionmethodusers.ConfigureRBSelectionMethodUsersTests;
import com.liferay.portalweb.socialofficesite.recentblogs.blogsentry.sousviewblogsentryguestnoviewrbsite.SOUs_ViewBlogsEntryGuestNoViewRBSiteTests;
import com.liferay.portalweb.socialofficesite.recentblogs.blogsentry.viewblogsentrymultiplerbsite.ViewBlogsEntryMultipleRBSiteTests;
import com.liferay.portalweb.socialofficesite.recentblogs.blogsentry.viewblogsentryrbsite.ViewBlogsEntryRBSiteTests;
import com.liferay.portalweb.socialofficesite.recentblogs.blogsentry.viewdeleteblogsentryrbsite.ViewDeleteBlogsEntryRBSiteTests;
import com.liferay.portalweb.socialofficesite.recentblogs.blogsentry.vieweditblogsentryrbsite.ViewEditBlogsEntryRBSiteTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsEntryTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ConfigureRBSelectionMethodUsersTests.suite());
		testSuite.addTest(SOUs_ViewBlogsEntryGuestNoViewRBSiteTests.suite());
		testSuite.addTest(ViewBlogsEntryMultipleRBSiteTests.suite());
		testSuite.addTest(ViewBlogsEntryRBSiteTests.suite());
		testSuite.addTest(ViewDeleteBlogsEntryRBSiteTests.suite());
		testSuite.addTest(ViewEditBlogsEntryRBSiteTests.suite());

		return testSuite;
	}

}