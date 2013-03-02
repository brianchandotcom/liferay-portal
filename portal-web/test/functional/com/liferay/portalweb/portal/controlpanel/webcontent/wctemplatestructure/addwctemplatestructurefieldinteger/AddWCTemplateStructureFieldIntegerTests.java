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

package com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldinteger;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructure.TearDownWCStructureTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldinteger.AddWCStructureFieldIntegerTest;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructure.TearDownWCTemplateStructureTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCTemplateStructureFieldIntegerTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddWCStructureFieldIntegerTest.class);
		testSuite.addTestSuite(AddWCTemplateStructureFieldIntegerTest.class);
		testSuite.addTestSuite(ViewWCTemplateStructureFieldIntegerTest.class);
		testSuite.addTestSuite(TearDownWCTemplateStructureTest.class);
		testSuite.addTestSuite(TearDownWCStructureTest.class);

		return testSuite;
	}
}