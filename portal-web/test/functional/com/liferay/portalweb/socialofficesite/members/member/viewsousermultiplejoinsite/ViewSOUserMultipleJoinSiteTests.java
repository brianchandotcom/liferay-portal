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

package com.liferay.portalweb.socialofficesite.members.member.viewsousermultiplejoinsite;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialoffice.users.user.addsouser.AddSOUser1Test;
import com.liferay.portalweb.socialoffice.users.user.addsouser.AddSOUser2Test;
import com.liferay.portalweb.socialoffice.users.user.addsouser.AddSOUser3Test;
import com.liferay.portalweb.socialoffice.users.user.addsouser.AddSOUser4Test;
import com.liferay.portalweb.socialoffice.users.user.addsouser.TearDownSOUserTest;
import com.liferay.portalweb.socialoffice.users.user.editsouserpassword.EditSOUser1PasswordTest;
import com.liferay.portalweb.socialoffice.users.user.editsouserpassword.EditSOUser2PasswordTest;
import com.liferay.portalweb.socialoffice.users.user.editsouserpassword.EditSOUser3PasswordTest;
import com.liferay.portalweb.socialoffice.users.user.editsouserpassword.EditSOUser4PasswordTest;
import com.liferay.portalweb.socialoffice.users.user.selectregularrolessouser.SelectRegularRolesSOUser1Test;
import com.liferay.portalweb.socialoffice.users.user.selectregularrolessouser.SelectRegularRolesSOUser2Test;
import com.liferay.portalweb.socialoffice.users.user.selectregularrolessouser.SelectRegularRolesSOUser3Test;
import com.liferay.portalweb.socialoffice.users.user.selectregularrolessouser.SelectRegularRolesSOUser4Test;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs1_SignInSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs1_SignOutSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs2_SignInSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs2_SignOutSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs3_SignInSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs3_SignOutSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs4_SignInSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs4_SignOutSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SignInSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SignOutSOTest;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessite.AddSitesSiteTest;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessite.TearDownSOSitesTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSOUserMultipleJoinSiteTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSOUser1Test.class);
		testSuite.addTestSuite(SelectRegularRolesSOUser1Test.class);
		testSuite.addTestSuite(EditSOUser1PasswordTest.class);
		testSuite.addTestSuite(AddSOUser2Test.class);
		testSuite.addTestSuite(SelectRegularRolesSOUser2Test.class);
		testSuite.addTestSuite(EditSOUser2PasswordTest.class);
		testSuite.addTestSuite(AddSOUser3Test.class);
		testSuite.addTestSuite(SelectRegularRolesSOUser3Test.class);
		testSuite.addTestSuite(EditSOUser3PasswordTest.class);
		testSuite.addTestSuite(AddSOUser4Test.class);
		testSuite.addTestSuite(SelectRegularRolesSOUser4Test.class);
		testSuite.addTestSuite(EditSOUser4PasswordTest.class);
		testSuite.addTestSuite(AddAsConnectionCCUser1Test.class);
		testSuite.addTestSuite(FollowCCUser2Test.class);
		testSuite.addTestSuite(AddAsConnectionCCUser3Test.class);
		testSuite.addTestSuite(FollowCCUser4Test.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SOUs1_SignInSOTest.class);
		testSuite.addTestSuite(SOUs1_ConfirmNotificationsAddConnectionTest.class);
		testSuite.addTestSuite(SOUs1_SignOutSOTest.class);
		testSuite.addTestSuite(SOUs3_SignInSOTest.class);
		testSuite.addTestSuite(SOUs3_ConfirmNotificationsAddConnectionTest.class);
		testSuite.addTestSuite(SOUs3_SignOutSOTest.class);
		testSuite.addTestSuite(SignInSOTest.class);
		testSuite.addTestSuite(AddSitesSiteTest.class);
		testSuite.addTestSuite(SendMemberInviteSOUserMultipleSiteTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SOUs1_SignInSOTest.class);
		testSuite.addTestSuite(SOUs1_ConfirmMemberInviteSiteTest.class);
		testSuite.addTestSuite(SOUs1_SignOutSOTest.class);
		testSuite.addTestSuite(SOUs2_SignInSOTest.class);
		testSuite.addTestSuite(SOUs2_ConfirmMemberInviteSiteTest.class);
		testSuite.addTestSuite(SOUs2_SignOutSOTest.class);
		testSuite.addTestSuite(SOUs3_SignInSOTest.class);
		testSuite.addTestSuite(SOUs3_IgnoreMemberInviteSiteTest.class);
		testSuite.addTestSuite(SOUs3_SignOutSOTest.class);
		testSuite.addTestSuite(SOUs4_SignInSOTest.class);
		testSuite.addTestSuite(SOUs4_IgnoreMemberInviteSiteTest.class);
		testSuite.addTestSuite(SOUs4_SignOutSOTest.class);
		testSuite.addTestSuite(SignInSOTest.class);
		testSuite.addTestSuite(ViewSOUser1SOUser2JoinSiteTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SOUs3_SignInSOTest.class);
		testSuite.addTestSuite(SOUs3_JoinSiteSiteDirectoryTest.class);
		testSuite.addTestSuite(SOUs3_SignOutSOTest.class);
		testSuite.addTestSuite(SOUs4_SignInSOTest.class);
		testSuite.addTestSuite(SOUs4_JoinSiteTest.class);
		testSuite.addTestSuite(SOUs4_SignOutSOTest.class);
		testSuite.addTestSuite(SignInSOTest.class);
		testSuite.addTestSuite(ViewSOUserAllJoinSiteTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SOUs1_SignInSOTest.class);
		testSuite.addTestSuite(SOUs1_LeaveSiteTest.class);
		testSuite.addTestSuite(SOUs1_SignOutSOTest.class);
		testSuite.addTestSuite(SOUs2_SignInSOTest.class);
		testSuite.addTestSuite(SOUs2_LeaveSiteTest.class);
		testSuite.addTestSuite(SOUs2_SignOutSOTest.class);
		testSuite.addTestSuite(SignInSOTest.class);
		testSuite.addTestSuite(ViewSOUser1SOUser2LeaveSiteTest.class);
		testSuite.addTestSuite(TearDownSOUserTest.class);
		testSuite.addTestSuite(TearDownSOSitesTest.class);

		return testSuite;
	}
}