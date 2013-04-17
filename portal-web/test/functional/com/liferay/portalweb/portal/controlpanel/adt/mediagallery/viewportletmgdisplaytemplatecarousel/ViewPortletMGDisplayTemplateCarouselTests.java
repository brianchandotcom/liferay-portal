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

package com.liferay.portalweb.portal.controlpanel.adt.mediagallery.viewportletmgdisplaytemplatecarousel;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.mediagallery.dmfolder.adddmfoldermg.AddDMFolderMGTest;
import com.liferay.portalweb.portlet.mediagallery.dmfolder.adddmfoldermg.TearDownDMFolderMGTest;
import com.liferay.portalweb.portlet.mediagallery.dmimage.adddmfolderimagemg.AddDMFolderImageMGTest;
import com.liferay.portalweb.portlet.mediagallery.portlet.addportletmg.AddPageMGTest;
import com.liferay.portalweb.portlet.mediagallery.portlet.addportletmg.AddPortletMGTest;
import com.liferay.portalweb.portlet.mediagallery.portlet.configureportletmgdisplaytemplatecarousel.ConfigurePortletMGDisplayTemplateCarouselTest;
import com.liferay.portalweb.portlet.mediagallery.portlet.configureportletmgdisplaytemplatecarousel.ConfigurePortletMGDisplayTemplateDefaultTest;
import com.liferay.portalweb.portlet.mediagallery.portlet.configureportletmgshowactions.ConfigurePortletMGShowActionsTest;
import com.liferay.portalweb.portlet.mediagallery.portlet.configureportletmgshowfoldermenu.ConfigurePortletMGShowFolderMenuTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletMGDisplayTemplateCarouselTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageMGTest.class);
		testSuite.addTestSuite(AddPortletMGTest.class);
		testSuite.addTestSuite(ConfigurePortletMGShowActionsTest.class);
		testSuite.addTestSuite(ConfigurePortletMGShowFolderMenuTest.class);
		testSuite.addTestSuite(AddDMFolderMGTest.class);
		testSuite.addTestSuite(AddDMFolderImageMGTest.class);
		testSuite.addTestSuite(ConfigurePortletMGDisplayTemplateCarouselTest.class);
		testSuite.addTestSuite(ViewPortletMGDisplayTemplateCarouselTest.class);
		testSuite.addTestSuite(ConfigurePortletMGDisplayTemplateDefaultTest.class);
		testSuite.addTestSuite(TearDownDMFolderMGTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}