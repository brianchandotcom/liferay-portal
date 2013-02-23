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

package com.liferay.portalweb.socialofficesite.forums.mbthread;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficesite.forums.mbthread.deletembcategorythreadmessagesite.DeleteMBCategoryThreadMessageSiteTests;
import com.liferay.portalweb.socialofficesite.forums.mbthread.editmbcategorythreadmessagesite.EditMBCategoryThreadMessageSiteTests;
import com.liferay.portalweb.socialofficesite.forums.mbthread.editpermissionsmbcategory2guestnoview.EditPermissionsMBCategory2GuestNoViewTests;
import com.liferay.portalweb.socialofficesite.forums.mbthread.markasanswermbcategorythreadreplysite.MarkAsAnswerMBCategoryThreadReplySiteTests;
import com.liferay.portalweb.socialofficesite.forums.mbthread.postnewmbcategorymultiplethreadmessagesite.PostNewMBCategoryMultipleThreadMessageSiteTests;
import com.liferay.portalweb.socialofficesite.forums.mbthread.postnewmbcategorythreadmessagesite.PostNewMBCategoryThreadMessageSiteTests;
import com.liferay.portalweb.socialofficesite.forums.mbthread.postnewmbcategorythreadmessagetagsite.PostNewMBCategoryThreadMessageTagSiteTests;
import com.liferay.portalweb.socialofficesite.forums.mbthread.postnewmbcategorythreadmultiplemessagesite.PostNewMBCategoryThreadMultipleMessageSiteTests;
import com.liferay.portalweb.socialofficesite.forums.mbthread.replymbcategorythreadmessagereplymultiplesite.ReplyMBCategoryThreadMessageReplyMultipleSiteTests;
import com.liferay.portalweb.socialofficesite.forums.mbthread.replymbcategorythreadmessagereplysite.ReplyMBCategoryThreadMessageReplySiteTests;
import com.liferay.portalweb.socialofficesite.forums.mbthread.votembcategorythreadmessagesite.VoteMBCategoryThreadMessageSiteTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MBThreadTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(DeleteMBCategoryThreadMessageSiteTests.suite());
		testSuite.addTest(EditMBCategoryThreadMessageSiteTests.suite());
		testSuite.addTest(EditPermissionsMBCategory2GuestNoViewTests.suite());
		testSuite.addTest(MarkAsAnswerMBCategoryThreadReplySiteTests.suite());
		testSuite.addTest(
			PostNewMBCategoryMultipleThreadMessageSiteTests.suite());
		testSuite.addTest(PostNewMBCategoryThreadMessageSiteTests.suite());
		testSuite.addTest(PostNewMBCategoryThreadMessageTagSiteTests.suite());
		testSuite.addTest(
			PostNewMBCategoryThreadMultipleMessageSiteTests.suite());
		testSuite.addTest(
			ReplyMBCategoryThreadMessageReplyMultipleSiteTests.suite());
		testSuite.addTest(ReplyMBCategoryThreadMessageReplySiteTests.suite());
		testSuite.addTest(VoteMBCategoryThreadMessageSiteTests.suite());

		return testSuite;
	}

}