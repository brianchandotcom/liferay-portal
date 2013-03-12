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

package com.liferay.portalweb.portal.ldap;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.StopSeleniumTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class LDAPTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AssertNoLDAPUsersTest.class);
		testSuite.addTestSuite(AssertNoLDAPGroupsTest.class);
		testSuite.addTestSuite(EnableLDAPTest.class);
		testSuite.addTestSuite(AddLDAPServerTest.class);
		testSuite.addTestSuite(AssertLDAPConnectionTest.class);
		testSuite.addTestSuite(AssertLDAPUsersTest.class);
		testSuite.addTestSuite(AssertLDAPGroupsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(LoginJaneLDAPTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(LoginLukeLDAPTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(LoginMartinLDAPTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(LoginAdminTest.class);
		testSuite.addTestSuite(AssertLDAPUsersPresentTest.class);
		testSuite.addTestSuite(AssertLDAPGroupsPresentTest.class);
		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}
}