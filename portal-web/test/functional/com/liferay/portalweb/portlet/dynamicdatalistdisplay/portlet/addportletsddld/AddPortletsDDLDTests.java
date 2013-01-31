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

package com.liferay.portalweb.portlet.dynamicdatalistdisplay.portlet.addportletsddld;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.dynamicdatalistdisplay.portlet.addportletddld.AddPageDDLDTest;
import com.liferay.portalweb.portlet.dynamicdatalistdisplay.portlet.addportletddld.AddPortletDDLD1Test;
import com.liferay.portalweb.portlet.dynamicdatalistdisplay.portlet.addportletddld.AddPortletDDLD2Test;
import com.liferay.portalweb.portlet.dynamicdatalistdisplay.portlet.addportletddld.AddPortletDDLD3Test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPortletsDDLDTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageDDLDTest.class);
		testSuite.addTestSuite(AddPortletDDLD1Test.class);
		testSuite.addTestSuite(AddPortletDDLD2Test.class);
		testSuite.addTestSuite(AddPortletDDLD3Test.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}