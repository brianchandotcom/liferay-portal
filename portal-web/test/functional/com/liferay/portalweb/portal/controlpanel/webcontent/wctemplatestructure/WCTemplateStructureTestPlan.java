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

package com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructure.AddWCTemplateStructureTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldboolean.AddWCTemplateStructureFieldBooleanTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefielddate.AddWCTemplateStructureFieldDateTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefielddecimal.AddWCTemplateStructureFieldDecimalTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefielddm.AddWCTemplateStructureFieldDMTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldfileupload.AddWCTemplateStructureFieldFileUploadTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldhtml.AddWCTemplateStructureFieldHTMLTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldimage.AddWCTemplateStructureFieldImageTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldinteger.AddWCTemplateStructureFieldIntegerTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldlink.AddWCTemplateStructureFieldLinkTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldnumber.AddWCTemplateStructureFieldNumberTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldradio.AddWCTemplateStructureFieldRadioTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldselect.AddWCTemplateStructureFieldSelectTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldtext.AddWCTemplateStructureFieldTextTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldtextbox.AddWCTemplateStructureFieldTextBoxTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldtextindex.AddWCTemplateStructureFieldTextIndexTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructurefieldtextrepeat.AddWCTemplateStructureFieldTextRepeatTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructures.AddWCTemplateStructuresTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatesubstructure.AddWCTemplateSubstructureTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatesubstructures.AddWCTemplateSubstructuresTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.advancedsearchwctemplatestructure.AdvancedSearchWCTemplateStructureTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.searchwctemplatestructure.SearchWCTemplateStructureTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WCTemplateStructureTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWCTemplateStructureTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldBooleanTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldDateTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldDecimalTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldDMTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldFileUploadTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldHTMLTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldImageTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldIntegerTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldLinkTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldNumberTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldRadioTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldSelectTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldTextTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldTextBoxTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldTextIndexTests.suite());
		testSuite.addTest(AddWCTemplateStructureFieldTextRepeatTests.suite());
		testSuite.addTest(AddWCTemplateStructuresTests.suite());
		testSuite.addTest(AddWCTemplateSubstructureTests.suite());
		testSuite.addTest(AddWCTemplateSubstructuresTests.suite());
		testSuite.addTest(AdvancedSearchWCTemplateStructureTests.suite());
		testSuite.addTest(SearchWCTemplateStructureTests.suite());

		return testSuite;
	}

}