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

package com.liferay.portalweb.portlet.announcements.announcementsentry.viewpriorityorder;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.announcements.announcementsentry.addannouncementsentrygeneral.TearDownAnnouncementsEntryTest;
import com.liferay.portalweb.portlet.announcements.announcementsentry.addannouncementsentrypriorityimportant.AddAnnouncementsEntryPriorityImportantTest;
import com.liferay.portalweb.portlet.announcements.announcementsentry.addannouncementsentryprioritynormal.AddAnnouncementsEntryPriorityNormalTest;
import com.liferay.portalweb.portlet.announcements.portlet.addportletannouncements.AddPageAnnouncementsTest;
import com.liferay.portalweb.portlet.announcements.portlet.addportletannouncements.AddPortletAnnouncementsTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPriorityOrderTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageAnnouncementsTest.class);
		testSuite.addTestSuite(AddPortletAnnouncementsTest.class);
		testSuite.addTestSuite(AddAnnouncementsEntryPriorityImportantTest.class);
		testSuite.addTestSuite(AddAnnouncementsEntryPriorityNormalTest.class);
		testSuite.addTestSuite(ViewPriorityOrderTest.class);
		testSuite.addTestSuite(TearDownAnnouncementsEntryTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}