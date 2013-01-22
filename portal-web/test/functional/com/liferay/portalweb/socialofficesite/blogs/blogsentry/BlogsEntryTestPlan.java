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

package com.liferay.portalweb.socialofficesite.blogs.blogsentry;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficesite.blogs.blogsentry.addblogsentrycategorysite.AddBlogsEntryCategorySiteTests;
import com.liferay.portalweb.socialofficesite.blogs.blogsentry.addblogsentrycommentsite.AddBlogsEntryCommentSiteTests;
import com.liferay.portalweb.socialofficesite.blogs.blogsentry.addblogsentrymultiplesite.AddBlogsEntryMultipleSiteTests;
import com.liferay.portalweb.socialofficesite.blogs.blogsentry.addblogsentrysite.AddBlogsEntrySiteTests;
import com.liferay.portalweb.socialofficesite.blogs.blogsentry.addblogsentrytagssite.AddBlogsEntryTagsSiteTests;
import com.liferay.portalweb.socialofficesite.blogs.blogsentry.deleteblogsentrysite.DeleteBlogsEntrySiteTests;
import com.liferay.portalweb.socialofficesite.blogs.blogsentry.editblogsentrysite.EditBlogsEntrySiteTests;
import com.liferay.portalweb.socialofficesite.blogs.blogsentry.editpermissionsblogsentry2guestnoview.EditPermissionsBlogsEntry2GuestNoViewTests;
import com.liferay.portalweb.socialofficesite.blogs.blogsentry.rateblogsentrysite.RateBlogsEntrySiteTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsEntryTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddBlogsEntryCategorySiteTests.suite());
		testSuite.addTest(AddBlogsEntryCommentSiteTests.suite());
		testSuite.addTest(AddBlogsEntryMultipleSiteTests.suite());
		testSuite.addTest(AddBlogsEntrySiteTests.suite());
		testSuite.addTest(AddBlogsEntryTagsSiteTests.suite());
		testSuite.addTest(DeleteBlogsEntrySiteTests.suite());
		testSuite.addTest(EditBlogsEntrySiteTests.suite());
		testSuite.addTest(EditPermissionsBlogsEntry2GuestNoViewTests.suite());
		testSuite.addTest(RateBlogsEntrySiteTests.suite());

		return testSuite;
	}

}