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

package com.liferay.portalweb.portal.controlpanel.organizations.suborganization;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.organizations.suborganization.addsuborganization.AddSubOrganizationTests;
import com.liferay.portalweb.portal.controlpanel.organizations.suborganization.addsuborganizations.AddSubOrganizationsTests;
import com.liferay.portalweb.portal.controlpanel.organizations.suborganization.assignmemberssuborganizationuser.AssignMembersSubOrganizationUserTests;
import com.liferay.portalweb.portal.controlpanel.organizations.suborganization.deletesuborganization.DeleteSubOrganizationTests;
import com.liferay.portalweb.portal.controlpanel.organizations.suborganization.deletesuborganizationassignmembers.DeleteSubOrganizationAssignMembersTests;
import com.liferay.portalweb.portal.controlpanel.organizations.suborganization.editsuborganizationsite.EditSubOrganizationSiteTests;
import com.liferay.portalweb.portal.controlpanel.organizations.suborganization.removememberssuborganization.RemoveMembersSubOrganizationTests;
import com.liferay.portalweb.portal.controlpanel.organizations.suborganization.searchsuborganization.SearchSubOrganizationTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SubOrganizationTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddSubOrganizationTests.suite());
		testSuite.addTest(AddSubOrganizationsTests.suite());
		testSuite.addTest(AssignMembersSubOrganizationUserTests.suite());
		testSuite.addTest(DeleteSubOrganizationTests.suite());
		testSuite.addTest(DeleteSubOrganizationAssignMembersTests.suite());
		testSuite.addTest(EditSubOrganizationSiteTests.suite());
		testSuite.addTest(RemoveMembersSubOrganizationTests.suite());
		testSuite.addTest(SearchSubOrganizationTests.suite());

		return testSuite;
	}

}