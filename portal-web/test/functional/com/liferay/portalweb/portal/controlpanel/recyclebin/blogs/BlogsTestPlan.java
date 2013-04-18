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

package com.liferay.portalweb.portal.controlpanel.recyclebin.blogs;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.recyclebin.blogs.movetorecyclebinblogentries.MoveToRecycleBinBlogEntriesTests;
import com.liferay.portalweb.portal.controlpanel.recyclebin.blogs.movetorecyclebinblogentry.MoveToRecycleBinBlogEntryTests;
import com.liferay.portalweb.portal.controlpanel.recyclebin.blogs.restoreblogentriesrecyclebin.RestoreBlogEntriesRecycleBinTests;
import com.liferay.portalweb.portal.controlpanel.recyclebin.blogs.restoreblogentryrecyclebin.RestoreBlogEntryRecycleBinTests;
import com.liferay.portalweb.portal.controlpanel.recyclebin.blogs.searchblogentriesno.SearchBlogEntriesNoTests;
import com.liferay.portalweb.portal.controlpanel.recyclebin.blogs.searchblogentryno.SearchBlogEntryNoTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(MoveToRecycleBinBlogEntriesTests.suite());
		testSuite.addTest(MoveToRecycleBinBlogEntryTests.suite());
		testSuite.addTest(RestoreBlogEntriesRecycleBinTests.suite());
		testSuite.addTest(RestoreBlogEntryRecycleBinTests.suite());
		testSuite.addTest(SearchBlogEntriesNoTests.suite());
		testSuite.addTest(SearchBlogEntryNoTests.suite());

		return testSuite;
	}

}