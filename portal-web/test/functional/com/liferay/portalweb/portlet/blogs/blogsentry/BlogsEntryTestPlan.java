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

package com.liferay.portalweb.portlet.blogs.blogsentry;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentries.AddBlogsEntriesTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentry.AddBlogsEntryTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentryautodraft.AddBlogsEntryAutoDraftTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentrycontentnull.AddBlogsEntryContentNullTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentrytitle150characters.AddBlogsEntryTitle150CharactersTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentrytitle151characters.AddBlogsEntryTitle151CharactersTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentrytitleescapecharacters.AddBlogsEntryTitleEscapeCharactersTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentrytitlenull.AddBlogsEntryTitleNullTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentrytrackback.AddBlogsEntryTrackbackTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.addportletscopelayoutblogsentry.AddPortletScopeLayoutBlogsEntryTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.deleteblogsentry.DeleteBlogsEntryTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.deleteblogsentrytitle150characters.DeleteBlogsEntryTitle150CharactersTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.deleteblogsentrytitleescapecharacters.DeleteBlogsEntryTitleEscapeCharactersTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.deletesaveasdraftblogsentrydetails.DeleteSaveAsDraftBlogsEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.editblogsentrycontent.EditBlogsEntryContentTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.editblogsentrycontentdetails.EditBlogsEntryContentDetailsTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.editblogsentrytitle.EditBlogsEntryTitleTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.editblogsentrytitledetails.EditBlogsEntryTitleDetailsTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.guestviewblogsentry2.Guest_ViewBlogsEntry2Tests;
import com.liferay.portalweb.portlet.blogs.blogsentry.previewblogsentrydetails.PreviewBlogsEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.publishsaveasdraftblogsentrydetails.PublishSaveAsDraftBlogsEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.rateblogsentry.RateBlogsEntryTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.rateblogsentrydetails.RateBlogsEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.saveasdraftblogsentrydetails.SaveAsDraftBlogsEntryDetailsTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.searchblogsentry.SearchBlogsEntryTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.searchblogsentryscopecurrentpage.SearchBlogsEntryScopeCurrentPageTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.userviewsaveasdraftblogsentry.User_ViewSaveAsDraftBlogsEntryTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.viewblogsentryscopecurrentpage.ViewBlogsEntryScopeCurrentPageTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.viewportletdisplaystyleabstractblogsentry.ViewPortletDisplayStyleAbstractBlogsEntryTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.viewportletdisplaystylefullcontentblogsentry.ViewPortletDisplayStyleFullContentBlogsEntryTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.viewportletdisplaystyletitleblogsentry.ViewPortletDisplayStyleTitleBlogsEntryTests;
import com.liferay.portalweb.portlet.blogs.blogsentry.viewportletscopelayoutblogsentry.ViewPortletScopeLayoutBlogsEntryTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsEntryTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddBlogsEntriesTests.suite());
		testSuite.addTest(AddBlogsEntryTests.suite());
		testSuite.addTest(AddBlogsEntryAutoDraftTests.suite());
		testSuite.addTest(AddBlogsEntryContentNullTests.suite());
		testSuite.addTest(AddBlogsEntryTitle150CharactersTests.suite());
		testSuite.addTest(AddBlogsEntryTitle151CharactersTests.suite());
		testSuite.addTest(AddBlogsEntryTitleEscapeCharactersTests.suite());
		testSuite.addTest(AddBlogsEntryTitleNullTests.suite());
		testSuite.addTest(AddBlogsEntryTrackbackTests.suite());
		testSuite.addTest(AddPortletScopeLayoutBlogsEntryTests.suite());
		testSuite.addTest(DeleteBlogsEntryTests.suite());
		testSuite.addTest(DeleteBlogsEntryTitle150CharactersTests.suite());
		testSuite.addTest(DeleteBlogsEntryTitleEscapeCharactersTests.suite());
		testSuite.addTest(DeleteSaveAsDraftBlogsEntryDetailsTests.suite());
		testSuite.addTest(EditBlogsEntryContentTests.suite());
		testSuite.addTest(EditBlogsEntryContentDetailsTests.suite());
		testSuite.addTest(EditBlogsEntryTitleTests.suite());
		testSuite.addTest(EditBlogsEntryTitleDetailsTests.suite());
		testSuite.addTest(Guest_ViewBlogsEntry2Tests.suite());
		testSuite.addTest(PreviewBlogsEntryDetailsTests.suite());
		testSuite.addTest(PublishSaveAsDraftBlogsEntryDetailsTests.suite());
		testSuite.addTest(RateBlogsEntryTests.suite());
		testSuite.addTest(RateBlogsEntryDetailsTests.suite());
		testSuite.addTest(SaveAsDraftBlogsEntryDetailsTests.suite());
		testSuite.addTest(SearchBlogsEntryTests.suite());
		testSuite.addTest(SearchBlogsEntryScopeCurrentPageTests.suite());
		testSuite.addTest(User_ViewSaveAsDraftBlogsEntryTests.suite());
		testSuite.addTest(ViewBlogsEntryScopeCurrentPageTests.suite());
		testSuite.addTest(
			ViewPortletDisplayStyleAbstractBlogsEntryTests.suite());
		testSuite.addTest(
			ViewPortletDisplayStyleFullContentBlogsEntryTests.suite());
		testSuite.addTest(ViewPortletDisplayStyleTitleBlogsEntryTests.suite());
		testSuite.addTest(ViewPortletScopeLayoutBlogsEntryTests.suite());

		return testSuite;
	}

}