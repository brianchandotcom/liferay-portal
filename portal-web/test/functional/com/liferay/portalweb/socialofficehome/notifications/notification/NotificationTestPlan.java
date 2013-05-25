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

package com.liferay.portalweb.socialofficehome.notifications.notification;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.notifications.notification.requestccaddasconnection.RequestCCAddConnnectionTests;
import com.liferay.portalweb.socialofficehome.notifications.notification.requestprofileaddasconnection.RequestProfileAddConnnectionTests;
import com.liferay.portalweb.socialofficehome.notifications.notification.sousconfirmnotificationjoinprivatesite.SOUs_ConfirmNotificationJoinPrivateSiteTests;
import com.liferay.portalweb.socialofficehome.notifications.notification.sousconfirmnotificationjoinprivrstrsite.SOUs_ConfirmNotificationJoinPrivRstrSiteTests;
import com.liferay.portalweb.socialofficehome.notifications.notification.sousconfirmnotificationjoinpubrstrsite.SOUs_ConfirmNotificationJoinPubRstrSiteTests;
import com.liferay.portalweb.socialofficehome.notifications.notification.sousconfirmnotificationjoinsite.SOUs_ConfirmNotificationJoinSiteTests;
import com.liferay.portalweb.socialofficehome.notifications.notification.sousviewnotificationannouncementsite.SOUs_ViewNotificationAnnouncementSiteTests;
import com.liferay.portalweb.socialofficehome.notifications.notification.sousviewnotificationtask.SOUs_ViewNotificationTaskTests;
import com.liferay.portalweb.socialofficehome.notifications.notification.viewnotificationspaginated.ViewNotificationsPaginatedTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class NotificationTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(RequestCCAddConnnectionTests.suite());
		testSuite.addTest(RequestProfileAddConnnectionTests.suite());
		testSuite.addTest(SOUs_ConfirmNotificationJoinPrivateSiteTests.suite());
		testSuite.addTest(
			SOUs_ConfirmNotificationJoinPrivRstrSiteTests.suite());
		testSuite.addTest(SOUs_ConfirmNotificationJoinPubRstrSiteTests.suite());
		testSuite.addTest(SOUs_ConfirmNotificationJoinSiteTests.suite());
		testSuite.addTest(SOUs_ViewNotificationAnnouncementSiteTests.suite());
		testSuite.addTest(SOUs_ViewNotificationTaskTests.suite());
		testSuite.addTest(ViewNotificationsPaginatedTests.suite());

		return testSuite;
	}

}