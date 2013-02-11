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

package com.liferay.portalweb.properties.webcontent.mailintegration.editwcwebcontentemail;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.site.addmemberssiteuser.AddMembersSiteUserTest;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.AddSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.TearDownSiteTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUserGmailTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.TearDownUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.edituserpassword.EditUserPasswordGmailTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail.AddRoleWebContentEditorCPTest;
import com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail.AddWCWebContentCPTest;
import com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail.AssignMembersRoleWebContentEditorUserCPActionsTest;
import com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail.ConfigureServerAdministrationMailTest;
import com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail.ConfigureWCPortletEmailFromGmailServerCPTest;
import com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail.ConfigureWCPortletWebContentAddedEmailCPTest;
import com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail.DefineRoleWebContentEditorCPTest;
import com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail.EditPortalInstanceMailDomainTest;
import com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail.Gmail_SignInTest;
import com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail.Gmail_SubscribeWCPortletCPTest;
import com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail.TearDownEmailConfigurationTest;
import com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail.TearDownRoleWebContentEditorTest;
import com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail.TearDownServerTest;
import com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail.TearDownWCWebContentCPTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EditWCWebContentEmailTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(ConfigureServerAdministrationMailTest.class);
		testSuite.addTestSuite(EditPortalInstanceMailDomainTest.class);
		testSuite.addTestSuite(AddUserGmailTest.class);
		testSuite.addTestSuite(EditUserPasswordGmailTest.class);
		testSuite.addTestSuite(AddSiteTest.class);
		testSuite.addTestSuite(AddMembersSiteUserTest.class);
		testSuite.addTestSuite(ConfigureWCPortletEmailFromGmailServerCPTest.class);
		testSuite.addTestSuite(ConfigureWCPortletWebContentAddedEmailCPTest.class);
		testSuite.addTestSuite(ConfigureWCPortletWebContentUpdatedEmailCPTest.class);
		testSuite.addTestSuite(AddRoleWebContentEditorCPTest.class);
		testSuite.addTestSuite(DefineRoleWebContentEditorCPTest.class);
		testSuite.addTestSuite(AssignMembersRoleWebContentEditorUserCPActionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Gmail_SignInTest.class);
		testSuite.addTestSuite(Gmail_SubscribeWCPortletCPTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(AddWCWebContentCPTest.class);
		testSuite.addTestSuite(EditWCContentCPActionsTest.class);
		testSuite.addTestSuite(Gmail_ViewEditWCWebContentUpdatedEmailTest.class);
		testSuite.addTestSuite(Gmail_TearDownEmailTest.class);
		testSuite.addTestSuite(TearDownWCWebContentCPTest.class);
		testSuite.addTestSuite(TearDownEmailConfigurationTest.class);
		testSuite.addTestSuite(TearDownRoleWebContentEditorTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownSiteTest.class);
		testSuite.addTestSuite(TearDownServerTest.class);

		return testSuite;
	}
}