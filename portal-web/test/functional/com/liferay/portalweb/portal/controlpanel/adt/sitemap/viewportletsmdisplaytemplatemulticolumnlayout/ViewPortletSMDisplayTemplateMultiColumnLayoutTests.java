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

package com.liferay.portalweb.portal.controlpanel.adt.sitemap.viewportletsmdisplaytemplatemulticolumnlayout;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.sitemap.portlet.addportletsm.AddPageSMTest;
import com.liferay.portalweb.portlet.sitemap.portlet.addportletsm.AddPortletSMTest;
import com.liferay.portalweb.portlet.sitemap.portlet.configureportletsmdisplaytemplatemulticolumn.ConfigurePortletSMDisplayTemplateDefaultTest;
import com.liferay.portalweb.portlet.sitemap.portlet.configureportletsmdisplaytemplatemulticolumn.ConfigurePortletSMDisplayTemplateMultiColumnLayoutTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletSMDisplayTemplateMultiColumnLayoutTests
	extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageSMTest.class);
		testSuite.addTestSuite(AddPortletSMTest.class);
		testSuite.addTestSuite(ConfigurePortletSMDisplayTemplateMultiColumnLayoutTest.class);
		testSuite.addTestSuite(ViewPortletSMDisplayTemplateMultiColumnLayoutTest.class);
		testSuite.addTestSuite(ConfigurePortletSMDisplayTemplateDefaultTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}