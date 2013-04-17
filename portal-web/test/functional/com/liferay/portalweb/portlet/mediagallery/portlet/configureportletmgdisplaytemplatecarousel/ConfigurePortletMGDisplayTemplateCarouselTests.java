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

package com.liferay.portalweb.portlet.mediagallery.portlet.configureportletmgdisplaytemplatecarousel;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.mediagallery.portlet.addportletmg.AddPageMGTest;
import com.liferay.portalweb.portlet.mediagallery.portlet.addportletmg.AddPortletMGTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletMGDisplayTemplateCarouselTests
	extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageMGTest.class);
		testSuite.addTestSuite(AddPortletMGTest.class);
		testSuite.addTestSuite(ConfigurePortletMGDisplayTemplateCarouselTest.class);
		testSuite.addTestSuite(ConfigurePortletMGDisplayTemplateDefaultTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}