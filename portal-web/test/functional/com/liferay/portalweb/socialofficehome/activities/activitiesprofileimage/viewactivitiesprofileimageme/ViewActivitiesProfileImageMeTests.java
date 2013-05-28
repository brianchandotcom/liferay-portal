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

package com.liferay.portalweb.socialofficehome.activities.activitiesprofileimage.viewactivitiesprofileimageme;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialoffice.users.user.addsouser.AddSOUserTest;
import com.liferay.portalweb.socialoffice.users.user.addsouser.TearDownSOUserTest;
import com.liferay.portalweb.socialoffice.users.user.editsouserpassword.EditSOUserPasswordTest;
import com.liferay.portalweb.socialoffice.users.user.selectregularrolessouser.SelectRegularRolesSOUserTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs_SignInSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs_SignOutSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SignInSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SignOutSOTest;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.sousaddasconnectionccuser.ConfirmNotificationsAddConnectionTest;
import com.liferay.portalweb.socialofficehome.contactscenter.contacts.sousaddasconnectionccuser.SOUs_AddAsConnectionCCUserTest;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.addmicroblogscontentviewablebyeveryone.AddMicroblogsContentViewableByEveryoneTest;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.addmicroblogscontentviewablebyeveryone.TearDownWHEntryContentTest;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousreplymbcontentviewablebyconnectioncomment.TearDownNotificationsTest;
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousreplymicroblogscontentprofile.SOUs_ReplyMicroblogsContentProfileTest;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessite.AddSitesSiteTest;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessite.TearDownSOSitesTest;
import com.liferay.portalweb.socialofficehome.sites.site.sousjoinsitessite.SOUs_JoinSitesSiteTest;
import com.liferay.portalweb.socialofficesite.blogs.blogsentry.addblogsentrysite.AddBlogsEntrySiteTest;
import com.liferay.portalweb.socialofficesite.forums.mbthread.postnewmbcategorythreadmessagesite.AddMBCategorySiteTest;
import com.liferay.portalweb.socialofficesite.forums.mbthread.postnewmbcategorythreadmessagesite.PostNewMBCategoryThreadMessageSiteTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewActivitiesProfileImageMeTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSOUserTest.class);
		testSuite.addTestSuite(SelectRegularRolesSOUserTest.class);
		testSuite.addTestSuite(EditSOUserPasswordTest.class);
		testSuite.addTestSuite(AddSitesSiteTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SOUs_SignInSOTest.class);
		testSuite.addTestSuite(SOUs_AddAsConnectionCCUserTest.class);
		testSuite.addTestSuite(SOUs_SignOutSOTest.class);
		testSuite.addTestSuite(SignInSOTest.class);
		testSuite.addTestSuite(ConfirmNotificationsAddConnectionTest.class);
		testSuite.addTestSuite(AddMicroblogsContentViewableByEveryoneTest.class);
		testSuite.addTestSuite(AddMBCategorySiteTest.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadMessageSiteTest.class);
		testSuite.addTestSuite(AddBlogsEntrySiteTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SOUs_SignInSOTest.class);
		testSuite.addTestSuite(SOUs_JoinSitesSiteTest.class);
		testSuite.addTestSuite(SOUs_ReplyMicroblogsContentProfileTest.class);
		testSuite.addTestSuite(SOUs_ReplyMBThreadMessageReplySiteTest.class);
		testSuite.addTestSuite(SOUs_AddBlogsEntryCommentSiteTest.class);
		testSuite.addTestSuite(SOUs_ViewActivitiesProfileImageMeTest.class);
		testSuite.addTestSuite(SOUs_SignOutSOTest.class);
		testSuite.addTestSuite(SignInSOTest.class);
		testSuite.addTestSuite(ViewActivitiesProfileImageMeTest.class);
		testSuite.addTestSuite(TearDownWHEntryContentTest.class);
		testSuite.addTestSuite(TearDownSOUserTest.class);
		testSuite.addTestSuite(TearDownSOSitesTest.class);
		testSuite.addTestSuite(TearDownNotificationsTest.class);

		return testSuite;
	}
}