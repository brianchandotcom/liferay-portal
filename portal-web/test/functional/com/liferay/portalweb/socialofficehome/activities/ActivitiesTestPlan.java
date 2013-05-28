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

package com.liferay.portalweb.socialofficehome.activities;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.activities.activitiesblockedsouser.ActivitiesBlockedSOUserTestPlan;
import com.liferay.portalweb.socialofficehome.activities.activitiesprofileimage.ActivitiesProfileImageTestPlan;
import com.liferay.portalweb.socialofficehome.activities.activitiessites.ActivitiesSitesTestPlan;
import com.liferay.portalweb.socialofficehome.activities.dashboardactivity.DashboardActivityTestPlan;
import com.liferay.portalweb.socialofficehome.activities.mbentryactivity.MBEntryActivityTestPlan;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivitiesTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ActivitiesBlockedSOUserTestPlan.suite());
		testSuite.addTest(ActivitiesProfileImageTestPlan.suite());
		testSuite.addTest(ActivitiesSitesTestPlan.suite());
		testSuite.addTest(DashboardActivityTestPlan.suite());
		testSuite.addTest(MBEntryActivityTestPlan.suite());

		return testSuite;
	}

}