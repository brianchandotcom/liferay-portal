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

package com.liferay.portalweb.socialofficesite.wiki.wikipage;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficesite.wiki.wikipage.addsaveasdraftwikifrontpagesite.AddSaveAsDraftWikiFrontPageSiteTests;
import com.liferay.portalweb.socialofficesite.wiki.wikipage.addwikifrontpageattachmentsite.AddWikiFrontPageAttachmentSiteTests;
import com.liferay.portalweb.socialofficesite.wiki.wikipage.addwikifrontpagechildpagesite.AddWikiFrontPageChildPageSiteTests;
import com.liferay.portalweb.socialofficesite.wiki.wikipage.addwikifrontpagecommentsite.AddWikiFrontPageCommentSiteTests;
import com.liferay.portalweb.socialofficesite.wiki.wikipage.addwikifrontpagesite.AddWikiFrontPageSiteTests;
import com.liferay.portalweb.socialofficesite.wiki.wikipage.editpermissionsfrontpagechildpageguestnoview.EditPermissionsFrontPageChildPageGuestNoViewTests;
import com.liferay.portalweb.socialofficesite.wiki.wikipage.ratewikifrontpagesite.RateWikiFrontPageSiteTests;
import com.liferay.portalweb.socialofficesite.wiki.wikipage.saveasdraftwikifrontpagesite.SaveAsDraftWikiFrontPageSiteTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WikiPageTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddSaveAsDraftWikiFrontPageSiteTests.suite());
		testSuite.addTest(AddWikiFrontPageAttachmentSiteTests.suite());
		testSuite.addTest(AddWikiFrontPageChildPageSiteTests.suite());
		testSuite.addTest(AddWikiFrontPageCommentSiteTests.suite());
		testSuite.addTest(AddWikiFrontPageSiteTests.suite());
		testSuite.addTest(
			EditPermissionsFrontPageChildPageGuestNoViewTests.suite());
		testSuite.addTest(RateWikiFrontPageSiteTests.suite());
		testSuite.addTest(SaveAsDraftWikiFrontPageSiteTests.suite());

		return testSuite;
	}

}