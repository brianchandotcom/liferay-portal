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

package com.liferay.portalweb.portlet.mediagallery.portlet;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.mediagallery.portlet.addportletmg.AddPortletMGTests;
import com.liferay.portalweb.portlet.mediagallery.portlet.configureportletmgdisplaytemplatecarousel.ConfigurePortletMGDisplayTemplateCarouselTests;
import com.liferay.portalweb.portlet.mediagallery.portlet.configureportletmgshowactions.ConfigurePortletMGShowActionsTests;
import com.liferay.portalweb.portlet.mediagallery.portlet.configureportletmgshowfoldermenu.ConfigurePortletMGShowFolderMenuTests;
import com.liferay.portalweb.portlet.mediagallery.portlet.configureportletmgshownavigationlinks.ConfigurePortletMGShowNavigationLinksTests;
import com.liferay.portalweb.portlet.mediagallery.portlet.configureportletmgshowsearch.ConfigurePortletMGShowSearchTests;
import com.liferay.portalweb.portlet.mediagallery.portlet.removeportletmg.RemovePortletMGTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletMGTests.suite());
		testSuite.addTest(ConfigurePortletMGShowActionsTests.suite());
		testSuite.addTest(ConfigurePortletMGDisplayTemplateCarouselTests.suite());
		testSuite.addTest(ConfigurePortletMGShowFolderMenuTests.suite());
		testSuite.addTest(ConfigurePortletMGShowNavigationLinksTests.suite());
		testSuite.addTest(ConfigurePortletMGShowSearchTests.suite());
		testSuite.addTest(RemovePortletMGTests.suite());

		return testSuite;
	}

}