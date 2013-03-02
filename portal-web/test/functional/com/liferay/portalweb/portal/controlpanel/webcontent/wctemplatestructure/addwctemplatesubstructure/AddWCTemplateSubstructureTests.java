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

package com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatesubstructure;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructure.AddWCStructureTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructure.TearDownWCStructureTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcsubstructure.AddWCSubstructureTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructure.AddWCTemplateStructureTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructure.TearDownWCTemplateStructureTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCTemplateSubstructureTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddWCStructureTest.class);
		testSuite.addTestSuite(AddWCTemplateStructureTest.class);
		testSuite.addTestSuite(AddWCSubstructureTest.class);
		testSuite.addTestSuite(AddWCTemplateSubstructureTest.class);
		testSuite.addTestSuite(ViewWCTemplateSubstructureTest.class);
		testSuite.addTestSuite(TearDownWCTemplateStructureTest.class);
		testSuite.addTestSuite(TearDownWCStructureTest.class);

		return testSuite;
	}
}