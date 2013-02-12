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

package com.liferay.portalweb.portal.controlpanel.recyclebin.blogs.searchblogentriesno;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.recyclebin.blogs.movetorecyclebinblogentries.MoveToRecycleBinBlogEntry1Test;
import com.liferay.portalweb.portal.controlpanel.recyclebin.blogs.movetorecyclebinblogentries.MoveToRecycleBinBlogEntry2Test;
import com.liferay.portalweb.portal.controlpanel.recyclebin.blogs.movetorecyclebinblogentries.MoveToRecycleBinBlogEntry3Test;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentry.AddBlogsEntry1Test;
import com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentry.AddBlogsEntry2Test;
import com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentry.AddBlogsEntry3Test;
import com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentry.TearDownBlogsEntryTest;
import com.liferay.portalweb.portlet.blogs.portlet.addportletblogs.AddPageBlogsTest;
import com.liferay.portalweb.portlet.blogs.portlet.addportletblogs.AddPortletBlogsTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchBlogEntriesNoTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageBlogsTest.class);
		testSuite.addTestSuite(AddPortletBlogsTest.class);
		testSuite.addTestSuite(AddBlogsEntry1Test.class);
		testSuite.addTestSuite(AddBlogsEntry2Test.class);
		testSuite.addTestSuite(AddBlogsEntry3Test.class);
		testSuite.addTestSuite(MoveToRecycleBinBlogEntry1Test.class);
		testSuite.addTestSuite(MoveToRecycleBinBlogEntry2Test.class);
		testSuite.addTestSuite(MoveToRecycleBinBlogEntry3Test.class);
		testSuite.addTestSuite(SearchBlogEntriesNoTest.class);
		testSuite.addTestSuite(SearchBlogsEntriesQuotesNoTest.class);
		testSuite.addTestSuite(TearDownBlogsEntryTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}