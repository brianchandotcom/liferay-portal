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

package com.liferay.portalweb.socialoffice.users.user.viewsiterolesouser;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.roles.role.addregrole.TearDownRoleTest;
import com.liferay.portalweb.portal.controlpanel.roles.role.addsiterole.AddSiteRoleTest;
import com.liferay.portalweb.socialoffice.users.user.addsouser.AddSOUserTest;
import com.liferay.portalweb.socialoffice.users.user.addsouser.TearDownSOUserTest;
import com.liferay.portalweb.socialoffice.users.user.editsouserpassword.EditSOUserPasswordTest;
import com.liferay.portalweb.socialoffice.users.user.selectregularrolessouser.SelectRegularRolesSOUserTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs_SignInSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SOUs_SignOutSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SignInSOTest;
import com.liferay.portalweb.socialoffice.users.user.signinso.SignOutSOTest;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessite.AddSitesSiteTest;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessite.TearDownSOSitesTest;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddadditionalemailaddressprofile.SOUs_AddAdditionalEmailAddressProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddaddressprofile.SOUs_AddAddressProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddexpertiseprofile.SOUs_AddExpertiseProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddinstantmessengerprofile.SOUs_AddInstantMessengerProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddintroductionprofile.SOUs_AddIntroductionProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddphonenumberprofile.SOUs_AddPhoneNumberProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddsmsprofile.SOUs_AddSMSProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddsocialnetworkprofile.SOUs_AddSocialNetworkProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddtagprofile.SOUs_AddTagProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddwebsiteprofile.SOUs_AddWebsiteProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.souseditadditionalemailaddressprofile.SOUs_EditAdditionalEmailAddressProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.souseditaddressprofile.SOUs_EditAddressProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.souseditexpertiseprofile.SOUs_EditExpertiseProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.souseditinstantmessengerprofile.SOUs_EditInstantMessengerProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.souseditintroductionprofile.SOUs_EditIntroductionProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.souseditphonenumberprofile.SOUs_EditPhoneNumberProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.souseditsmsprofile.SOUs_EditSMSProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.souseditsocialnetworkprofile.SOUs_EditSocialNetworkProfileTest;
import com.liferay.portalweb.socialofficeprofile.profile.souseditwebsiteprofile.SOUs_EditWebsiteProfileTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSiteRoleSOUserTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSOUserTest.class);
		testSuite.addTestSuite(SelectRegularRolesSOUserTest.class);
		testSuite.addTestSuite(EditSOUserPasswordTest.class);
		testSuite.addTestSuite(AddSitesSiteTest.class);
		testSuite.addTestSuite(AddSiteRoleTest.class);
		testSuite.addTestSuite(SelectSiteSOUserTest.class);
		testSuite.addTestSuite(SelectSiteRoleSOUserTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SOUs_SignInSOTest.class);
		testSuite.addTestSuite(SOUs_AddAdditionalEmailAddressProfileTest.class);
		testSuite.addTestSuite(SOUs_AddAddressProfileTest.class);
		testSuite.addTestSuite(SOUs_AddExpertiseProfileTest.class);
		testSuite.addTestSuite(SOUs_AddInstantMessengerProfileTest.class);
		testSuite.addTestSuite(SOUs_AddIntroductionProfileTest.class);
		testSuite.addTestSuite(SOUs_AddPhoneNumberProfileTest.class);
		testSuite.addTestSuite(SOUs_AddSMSProfileTest.class);
		testSuite.addTestSuite(SOUs_AddSocialNetworkProfileTest.class);
		testSuite.addTestSuite(SOUs_AddTagProfileTest.class);
		testSuite.addTestSuite(SOUs_AddWebsiteProfileTest.class);
		testSuite.addTestSuite(SOUs_EditIntroductionProfileTest.class);
		testSuite.addTestSuite(SOUs_EditAdditionalEmailAddressProfileTest.class);
		testSuite.addTestSuite(SOUs_EditAddressProfileTest.class);
		testSuite.addTestSuite(SOUs_EditExpertiseProfileTest.class);
		testSuite.addTestSuite(SOUs_EditInstantMessengerProfileTest.class);
		testSuite.addTestSuite(SOUs_EditPhoneNumberProfileTest.class);
		testSuite.addTestSuite(SOUs_EditSMSProfileTest.class);
		testSuite.addTestSuite(SOUs_EditSocialNetworkProfileTest.class);
		testSuite.addTestSuite(SOUs_EditWebsiteProfileTest.class);
		testSuite.addTestSuite(SOUs_SignOutSOTest.class);
		testSuite.addTestSuite(SignInSOTest.class);
		testSuite.addTestSuite(ViewSiteRoleSOUserTest.class);
		testSuite.addTestSuite(TearDownSOUserTest.class);
		testSuite.addTestSuite(TearDownRoleTest.class);
		testSuite.addTestSuite(TearDownSOSitesTest.class);

		return testSuite;
	}
}