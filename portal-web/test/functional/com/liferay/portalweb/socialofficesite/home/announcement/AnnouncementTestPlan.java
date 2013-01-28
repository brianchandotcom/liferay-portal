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

package com.liferay.portalweb.socialofficesite.home.announcement;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficesite.home.announcement.addannouncementsentrygeneralsite.AddAnnouncementsEntryGeneralSiteTests;
import com.liferay.portalweb.socialofficesite.home.announcement.deleteannouncementsentrygeneralsite.DeleteAnnouncementsEntryGeneralSiteTests;
import com.liferay.portalweb.socialofficesite.home.announcement.editannouncementsentrygeneralsite.EditAnnouncementsEntryGeneralSiteTests;
import com.liferay.portalweb.socialofficesite.home.announcement.markasreadannouncementsentrygeneralsite.MarkAsReadAnnouncementsEntryGeneralSiteTests;
import com.liferay.portalweb.socialofficesite.home.announcement.showannouncementsentrygeneralsite.ShowAnnouncementsEntryGeneralSiteTests;
import com.liferay.portalweb.socialofficesite.home.announcement.sousaddannouncementsentrysitesite.SOUs_AddAnnouncementsEntrySiteSiteTests;
import com.liferay.portalweb.socialofficesite.home.announcement.sousdeleteannouncementsentrysitesite.SOUs_DeleteAnnouncementsEntrySiteSiteTests;
import com.liferay.portalweb.socialofficesite.home.announcement.souseditannouncementsentrysitesite.SOUs_EditAnnouncementsEntrySiteSiteTests;
import com.liferay.portalweb.socialofficesite.home.announcement.sousmarkasreadannouncementsentrysitesite.SOUs_MarkAsReadAnnouncementsEntrySiteSiteTests;
import com.liferay.portalweb.socialofficesite.home.announcement.sousshowannouncementsentrysitesite.SOUs_ShowAnnouncementsEntrySiteSiteTests;
import com.liferay.portalweb.socialofficesite.home.announcement.sousviewannouncementsentrysitesite.SOUs_ViewAnnouncementsEntrySiteSiteTests;
import com.liferay.portalweb.socialofficesite.home.announcement.viewannouncementsentrygeneralsite.ViewAnnouncementsEntryGeneralSiteTests;
import com.liferay.portalweb.socialofficesite.home.announcement.viewannouncementsentrynotifications.ViewAnnouncementsEntryNotificationsTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnouncementTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddAnnouncementsEntryGeneralSiteTests.suite());
		testSuite.addTest(DeleteAnnouncementsEntryGeneralSiteTests.suite());
		testSuite.addTest(EditAnnouncementsEntryGeneralSiteTests.suite());
		testSuite.addTest(MarkAsReadAnnouncementsEntryGeneralSiteTests.suite());
		testSuite.addTest(ShowAnnouncementsEntryGeneralSiteTests.suite());
		testSuite.addTest(SOUs_AddAnnouncementsEntrySiteSiteTests.suite());
		testSuite.addTest(SOUs_DeleteAnnouncementsEntrySiteSiteTests.suite());
		testSuite.addTest(SOUs_EditAnnouncementsEntrySiteSiteTests.suite());
		testSuite.addTest(
			SOUs_MarkAsReadAnnouncementsEntrySiteSiteTests.suite());
		testSuite.addTest(SOUs_ShowAnnouncementsEntrySiteSiteTests.suite());
		testSuite.addTest(SOUs_ViewAnnouncementsEntrySiteSiteTests.suite());
		testSuite.addTest(ViewAnnouncementsEntryGeneralSiteTests.suite());
		testSuite.addTest(ViewAnnouncementsEntryNotificationsTests.suite());

		return testSuite;
	}

}