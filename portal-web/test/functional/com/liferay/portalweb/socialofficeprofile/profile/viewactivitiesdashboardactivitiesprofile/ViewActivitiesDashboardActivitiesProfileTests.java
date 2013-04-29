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

package com.liferay.portalweb.socialofficeprofile.profile.viewactivitiesdashboardactivitiesprofile;

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
import com.liferay.portalweb.socialofficehome.microblogs.mbentry.addmicroblogscontentviewablebyeveryone.TearDownWHEntryContentTest;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderdocument.AddDMFolderDocument1Test;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderdocument.AddDMFolderTest;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderdocument.TearDownDMFolderTest;
import com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtome.TearDownTasksTaskAssignedToMeTest;
import com.liferay.portalweb.socialofficehome.tasks.task.addtaskstaskassignedtome.TearDownTasksTaskTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewActivitiesDashboardActivitiesProfileTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSOUserTest.class);
		testSuite.addTestSuite(SelectRegularRolesSOUserTest.class);
		testSuite.addTestSuite(EditSOUserPasswordTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SOUs_SignInSOTest.class);
		testSuite.addTestSuite(SOUs_AddAsConnectionCCUserTest.class);
		testSuite.addTestSuite(SOUs_SignOutSOTest.class);
		testSuite.addTestSuite(SignInSOTest.class);
		testSuite.addTestSuite(ConfirmNotificationsAddConnectionTest.class);
		testSuite.addTestSuite(AddMicroblogsContent1ViewableByEveryoneTest.class);
		testSuite.addTestSuite(AddMicroblogsContent2TagViewableByEveryoneTest.class);
		testSuite.addTestSuite(AddDMFolderTest.class);
		testSuite.addTestSuite(AddDMFolderDocument1Test.class);
		testSuite.addTestSuite(AddTasksTask1AssignedToMeTest.class);
		testSuite.addTestSuite(AddTasksTask2AssignedToConnectionTest.class);
		testSuite.addTestSuite(SignOutSOTest.class);
		testSuite.addTestSuite(SOUs_SignInSOTest.class);
		testSuite.addTestSuite(SOUs_ViewActivitiesProfileTest.class);
		testSuite.addTestSuite(SOUs_AddMicroblogsContent3ViewableByEveryoneTest.class);
		testSuite.addTestSuite(SOUs_AddMicroblogsContent4TagViewableByEveryoneTest.class);
		testSuite.addTestSuite(SOUs_AddDMFolderTest.class);
		testSuite.addTestSuite(SOUs_AddDMFolderDocument2Test.class);
		testSuite.addTestSuite(SOUs_AddTasksTask3AssignedToMeTest.class);
		testSuite.addTestSuite(SOUs_AddTasksTask4AssignedToConnectionTest.class);
		testSuite.addTestSuite(SOUs_ViewActivitiesDashboardActivitesProfileTest.class);
		testSuite.addTestSuite(SOUs_SignOutSOTest.class);
		testSuite.addTestSuite(SignInSOTest.class);
		testSuite.addTestSuite(ViewActivitiesDashboardActivitiesProfileTest.class);
		testSuite.addTestSuite(TearDownWHEntryContentTest.class);
		testSuite.addTestSuite(TearDownDMFolderTest.class);
		testSuite.addTestSuite(TearDownTasksTaskTest.class);
		testSuite.addTestSuite(TearDownTasksTaskAssignedToMeTest.class);
		testSuite.addTestSuite(TearDownNotificationsTest.class);
		testSuite.addTestSuite(TearDownSOUserTest.class);

		return testSuite;
	}
}