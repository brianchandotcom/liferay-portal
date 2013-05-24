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

package com.liferay.portalweb.socialofficeprofile.profile;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficeprofile.profile.addasconnectionccuserprofile.AddAsConnectionCCUserProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersoadditionalemailaddress.AddUserSOAdditionalEmailAddressTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersoaddress.AddUserSOAddressTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersoexpertise.AddUserSOExpertiseTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersoinstantmessenger.AddUserSOInstantMessengerTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersophonenumber.AddUserSOPhoneNumberTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersosms.AddUserSOSMSTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersosocialnetwork.AddUserSOSocialNetworkTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersotag.AddUserSOTagTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersowebsite.AddUserSOWebsiteTests;
import com.liferay.portalweb.socialofficeprofile.profile.blockccuserprofile.BlockCCUserProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.followccuserprofile.FollowCCUserProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.removeconnectionccuserprofile.RemoveConnectionCCUserProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddadditionalemailaddressprofile.SOUs_AddAdditionalEmailAddressProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddaddressprofile.SOUs_AddAddressProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddasconnectionccuserprofile.SOUs_AddAsConnectionCCUserProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddexpertiseprofile.SOUs_AddExpertiseProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddinstantmessengerprofile.SOUs_AddInstantMessengerProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddintroductionprofile.SOUs_AddIntroductionProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddphonenumberprofile.SOUs_AddPhoneNumberProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddsmsprofile.SOUs_AddSMSProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddsocialnetworkprofile.SOUs_AddSocialNetworkProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddtagprofile.SOUs_AddTagProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousaddwebsiteprofile.SOUs_AddWebsiteProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousblockccuserprofile.SOUs_BlockCCUserProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditadditionalemailaddressnullprofile.SOUs_EditAdditionalEmailAddressNullProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditadditionalemailaddressprofile.SOUs_EditAdditionalEmailAddressProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditaddressnullprofile.SOUs_EditAddressNullProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditaddressprofile.SOUs_EditAddressProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditexpertisenullprofile.SOUs_EditExpertiseNullProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditexpertiseprofile.SOUs_EditExpertiseProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditinstantmessengernullprofile.SOUs_EditInstantMessengerNullProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditinstantmessengerprofile.SOUs_EditInstantMessengerProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditintroductionprofile.SOUs_EditIntroductionProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditphonenumbernullprofile.SOUs_EditPhoneNumberNullProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditphonenumberprofile.SOUs_EditPhoneNumberProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditsmsnullprofile.SOUs_EditSMSNullProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditsmsprofile.SOUs_EditSMSProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditsocialnetworknullprofile.SOUs_EditSocialNetworkNullProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditsocialnetworkprofile.SOUs_EditSocialNetworkProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousedittagnullprofile.SOUs_EditTagNullProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousedittagprofile.SOUs_EditTagProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditwebsitenullprofile.SOUs_EditWebsiteNullProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.souseditwebsiteprofile.SOUs_EditWebsiteProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousfollowccuserprofile.SOUs_FollowCCUserProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousremoveconnectionccuserprofile.SOUs_RemoveConnectionCCUserProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousunblockccuserprofile.SOUs_UnblockCCUserProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousunfollowccuserprofile.SOUs_UnfollowCCUserProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousviewactivitiessitetypeprivateprofile.SOUs_ViewActivitiesSiteTypePrivateProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousviewactivitiessitetypeprivrstrprofile.SOUs_ViewActivitiesSiteTypePrivRstrProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.sousviewaddtagmyaccountprofile.SOUs_ViewAddTagMyAccountProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.unblockccuserprofile.UnblockCCUserProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.unfollowccuserprofile.UnfollowCCUserProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.viewactivitiesdashboardactivitiesprofile.ViewActivitiesDashboardActivitiesProfileTests;
import com.liferay.portalweb.socialofficeprofile.profile.viewactivitiessitesactivitiesprofile.ViewActivitiesSitesActivitiesProfileTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ProfileTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddAsConnectionCCUserProfileTests.suite());
		testSuite.addTest(AddUserSOAdditionalEmailAddressTests.suite());
		testSuite.addTest(AddUserSOAddressTests.suite());
		testSuite.addTest(AddUserSOExpertiseTests.suite());
		testSuite.addTest(AddUserSOInstantMessengerTests.suite());
		testSuite.addTest(AddUserSOPhoneNumberTests.suite());
		testSuite.addTest(AddUserSOSMSTests.suite());
		testSuite.addTest(AddUserSOSocialNetworkTests.suite());
		testSuite.addTest(AddUserSOTagTests.suite());
		testSuite.addTest(AddUserSOWebsiteTests.suite());
		testSuite.addTest(BlockCCUserProfileTests.suite());
		testSuite.addTest(FollowCCUserProfileTests.suite());
		testSuite.addTest(RemoveConnectionCCUserProfileTests.suite());
		testSuite.addTest(SOUs_AddAdditionalEmailAddressProfileTests.suite());
		testSuite.addTest(SOUs_AddAddressProfileTests.suite());
		testSuite.addTest(SOUs_AddAsConnectionCCUserProfileTests.suite());
		testSuite.addTest(SOUs_AddExpertiseProfileTests.suite());
		testSuite.addTest(SOUs_AddInstantMessengerProfileTests.suite());
		testSuite.addTest(SOUs_AddIntroductionProfileTests.suite());
		testSuite.addTest(SOUs_AddPhoneNumberProfileTests.suite());
		testSuite.addTest(SOUs_AddSMSProfileTests.suite());
		testSuite.addTest(SOUs_AddSocialNetworkProfileTests.suite());
		testSuite.addTest(SOUs_AddTagProfileTests.suite());
		testSuite.addTest(SOUs_AddWebsiteProfileTests.suite());
		testSuite.addTest(SOUs_BlockCCUserProfileTests.suite());
		testSuite.addTest(
			SOUs_EditAdditionalEmailAddressNullProfileTests.suite());
		testSuite.addTest(SOUs_EditAdditionalEmailAddressProfileTests.suite());
		testSuite.addTest(SOUs_EditAddressNullProfileTests.suite());
		testSuite.addTest(SOUs_EditAddressProfileTests.suite());
		testSuite.addTest(SOUs_EditExpertiseNullProfileTests.suite());
		testSuite.addTest(SOUs_EditExpertiseProfileTests.suite());
		testSuite.addTest(SOUs_EditInstantMessengerNullProfileTests.suite());
		testSuite.addTest(SOUs_EditInstantMessengerProfileTests.suite());
		testSuite.addTest(SOUs_EditIntroductionProfileTests.suite());
		testSuite.addTest(SOUs_EditPhoneNumberNullProfileTests.suite());
		testSuite.addTest(SOUs_EditPhoneNumberProfileTests.suite());
		testSuite.addTest(SOUs_EditSMSNullProfileTests.suite());
		testSuite.addTest(SOUs_EditSMSProfileTests.suite());
		testSuite.addTest(SOUs_EditSocialNetworkNullProfileTests.suite());
		testSuite.addTest(SOUs_EditSocialNetworkProfileTests.suite());
		testSuite.addTest(SOUs_EditTagNullProfileTests.suite());
		testSuite.addTest(SOUs_EditTagProfileTests.suite());
		testSuite.addTest(SOUs_EditWebsiteNullProfileTests.suite());
		testSuite.addTest(SOUs_EditWebsiteProfileTests.suite());
		testSuite.addTest(SOUs_FollowCCUserProfileTests.suite());
		testSuite.addTest(SOUs_RemoveConnectionCCUserProfileTests.suite());
		testSuite.addTest(SOUs_UnblockCCUserProfileTests.suite());
		testSuite.addTest(SOUs_UnfollowCCUserProfileTests.suite());
		testSuite.addTest(
			SOUs_ViewActivitiesSiteTypePrivateProfileTests.suite());
		testSuite.addTest(
			SOUs_ViewActivitiesSiteTypePrivRstrProfileTests.suite());
		testSuite.addTest(SOUs_ViewAddTagMyAccountProfileTests.suite());
		testSuite.addTest(UnblockCCUserProfileTests.suite());
		testSuite.addTest(UnfollowCCUserProfileTests.suite());
		testSuite.addTest(
			ViewActivitiesDashboardActivitiesProfileTests.suite());
		testSuite.addTest(ViewActivitiesSitesActivitiesProfileTests.suite());

		return testSuite;
	}

}