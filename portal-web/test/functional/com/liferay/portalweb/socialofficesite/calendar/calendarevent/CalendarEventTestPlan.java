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

package com.liferay.portalweb.socialofficesite.calendar.calendarevent;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficesite.calendar.calendarevent.addcalendareventcommentsite.AddCalendarEventCommentSiteTests;
import com.liferay.portalweb.socialofficesite.calendar.calendarevent.addcalendareventmultiplesite.AddCalendarEventMultipleSiteTests;
import com.liferay.portalweb.socialofficesite.calendar.calendarevent.addcalendareventsite.AddCalendarEventSiteTests;
import com.liferay.portalweb.socialofficesite.calendar.calendarevent.addcalendareventtagssite.AddCalendarEventTagsSiteTests;
import com.liferay.portalweb.socialofficesite.calendar.calendarevent.deletecalendareventsite.DeleteCalendarEventSiteTests;
import com.liferay.portalweb.socialofficesite.calendar.calendarevent.editcalendareventsite.EditCalendarEventSiteTests;
import com.liferay.portalweb.socialofficesite.calendar.calendarevent.editpermissionscalendarevent2guestnoview.EditPermissionsCalendarEvent2GuestNoViewTests;
import com.liferay.portalweb.socialofficesite.calendar.calendarevent.ratecalendareventsite.RateCalendarEventSiteTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class CalendarEventTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddCalendarEventCommentSiteTests.suite());
		testSuite.addTest(AddCalendarEventMultipleSiteTests.suite());
		testSuite.addTest(AddCalendarEventSiteTests.suite());
		testSuite.addTest(AddCalendarEventTagsSiteTests.suite());
		testSuite.addTest(DeleteCalendarEventSiteTests.suite());
		testSuite.addTest(EditCalendarEventSiteTests.suite());
		testSuite.addTest(
			EditPermissionsCalendarEvent2GuestNoViewTests.suite());
		testSuite.addTest(RateCalendarEventSiteTests.suite());

		return testSuite;
	}

}