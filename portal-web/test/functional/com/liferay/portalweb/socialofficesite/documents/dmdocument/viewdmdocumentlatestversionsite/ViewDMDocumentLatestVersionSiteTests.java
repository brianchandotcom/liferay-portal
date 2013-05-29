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

package com.liferay.portalweb.socialofficesite.documents.dmdocument.viewdmdocumentlatestversionsite;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.roles.role.addregrole.TearDownRoleTest;
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

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMDocumentLatestVersionSiteTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSOUserTest.class);
		testSuite.addTestSuite(SelectRegularRolesSOUserTest.class);
		testSuite.addTestSuite(EditSOUserPasswordTest.class);
		testSuite.addTestSuite(AddSitesSiteTest.class);
		testSuite.addTestSuite(AddSiteRoleDocumentEditorTest.class);
		testSuite.addTestSuite(DefinePermissionsDocumentEditorTest.class);
		testSuite.addTestSuite(SelectSiteSOUserTest.class);
		testSuite.addTestSuite(SelectDocumentEditorRoleSOUserTest.class);
		testSuite.addTestSuite(AddDMDocumentSiteTest.class);
		testSuite.addTestSuite(ViewDMDocumentSiteTest.class);
		testSuite.addTestSuite(AddDMDocumentComment1SiteTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SOUs_SignInSOTest.class);
		testSuite.addTestSuite(SOUs_EditDMDocumentMinorSiteTest.class);
		testSuite.addTestSuite(SOUs_AddDMDocumentComment2SiteTest.class);
		testSuite.addTestSuite(SOUs_EditDMDocumentComment2SiteTest.class);
		testSuite.addTestSuite(SOUs_EditDMDocumentMajorSiteTest.class);
		testSuite.addTestSuite(SOUs_AddDMDocumentComment3SiteTest.class);
		testSuite.addTestSuite(SOUs_EditDMDocumentComment3SiteTest.class);
		testSuite.addTestSuite(SOUs_ViewDMDocumentOriginalSiteTest.class);
		testSuite.addTestSuite(SOUs_RevertDMDocumentMinorEditSiteTest.class);
		testSuite.addTestSuite(SOUs_DeleteDMDocumentMajorEditSiteTest.class);
		testSuite.addTestSuite(SOUs_SignOutSOTest.class);
		testSuite.addTestSuite(SignInSOTest.class);
		testSuite.addTestSuite(ViewDMDocumentLatestVersionSiteTest.class);
		testSuite.addTestSuite(TearDownSOUserTest.class);
		testSuite.addTestSuite(TearDownRoleTest.class);
		testSuite.addTestSuite(TearDownSOSitesTest.class);

		return testSuite;
	}
}