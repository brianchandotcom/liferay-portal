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

package com.liferay.portalweb.stagingsite.webcontentdisplay.wcwebcontent.asserteditwcwebcontentwcd;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.AddSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.TearDownSiteTest;
import com.liferay.portalweb.stagingsite.sites.site.activatestaging.ActivateStagingTest;
import com.liferay.portalweb.stagingsite.sites.site.deactivatestaging.DeactivateStagingTest;
import com.liferay.portalweb.stagingsite.webcontentdisplay.wcwebcontent.assertcannoteditwcwebcontentwcd.AddPageWCDTest;
import com.liferay.portalweb.stagingsite.webcontentdisplay.wcwebcontent.assertcannoteditwcwebcontentwcd.AddPortletWCDTest;
import com.liferay.portalweb.stagingsite.webcontentdisplay.wcwebcontent.assertcannoteditwcwebcontentwcd.AddSitePublicPageTest;
import com.liferay.portalweb.stagingsite.webcontentdisplay.wcwebcontent.assertcannoteditwcwebcontentwcd.AddWCWebContentWCDTest;
import com.liferay.portalweb.stagingsite.webcontentdisplay.wcwebcontent.assertcannoteditwcwebcontentwcd.AssertCannotEditWCWebContentWCDTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertEditWCWebContentWCDTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSiteTest.class);
		testSuite.addTestSuite(AddPageWCDTest.class);
		testSuite.addTestSuite(AddPortletWCDTest.class);
		testSuite.addTestSuite(AddWCWebContentWCDTest.class);
		testSuite.addTestSuite(AssertEditWCWebContentWCDTest.class);
		testSuite.addTestSuite(ActivateStagingTest.class);
		testSuite.addTestSuite(AssertCannotEditWCWebContentWCDTest.class);
		testSuite.addTestSuite(DeactivateStagingTest.class);
		testSuite.addTestSuite(AssertEditWCWebContentWCDTest.class);
		testSuite.addTestSuite(AddSitePublicPageTest.class);
		testSuite.addTestSuite(TearDownSiteTest.class);

		return testSuite;
	}
}