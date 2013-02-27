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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructure.AddWCStructureTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldboolean.AddWCStructureFieldBooleanTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefielddate.AddWCStructureFieldDateTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefielddecimal.AddWCStructureFieldDecimalTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefielddm.AddWCStructureFieldDMTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldfileupload.AddWCStructureFieldFileUploadTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldhtml.AddWCStructureFieldHTMLTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldimage.AddWCStructureFieldImageTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldinteger.AddWCStructureFieldIntegerTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldlink.AddWCStructureFieldLinkTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldnumber.AddWCStructureFieldNumberTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldradio.AddWCStructureFieldRadioTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldselect.AddWCStructureFieldSelectTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldtext.AddWCStructureFieldTextTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldtextbox.AddWCStructureFieldTextBoxTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldtextindexable.AddWCStructureFieldTextIndexableTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldtextrepeatable.AddWCStructureFieldTextRepeatableTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructures.AddWCStructuresTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcsubstructure.AddWCSubstructureTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcsubstructures.AddWCSubstructuresTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.advancedsearchwcstructure.AdvancedSearchWCStructureTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.editwcsubstructuresdefaultvalues.EditWCSubStructuresDefaultValuesTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.localizewcstructure.LocalizeWCStructureTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.searchwcstructure.SearchWCStructureTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WCStructureTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWCStructureTests.suite());
		testSuite.addTest(AddWCStructureFieldBooleanTests.suite());
		testSuite.addTest(AddWCStructureFieldDateTests.suite());
		testSuite.addTest(AddWCStructureFieldDecimalTests.suite());
		testSuite.addTest(AddWCStructureFieldDMTests.suite());
		testSuite.addTest(AddWCStructureFieldFileUploadTests.suite());
		testSuite.addTest(AddWCStructureFieldHTMLTests.suite());
		testSuite.addTest(AddWCStructureFieldImageTests.suite());
		testSuite.addTest(AddWCStructureFieldIntegerTests.suite());
		testSuite.addTest(AddWCStructureFieldLinkTests.suite());
		testSuite.addTest(AddWCStructureFieldNumberTests.suite());
		testSuite.addTest(AddWCStructureFieldRadioTests.suite());
		testSuite.addTest(AddWCStructureFieldSelectTests.suite());
		testSuite.addTest(AddWCStructureFieldTextTests.suite());
		testSuite.addTest(AddWCStructureFieldTextBoxTests.suite());
		testSuite.addTest(AddWCStructureFieldTextIndexableTests.suite());
		testSuite.addTest(AddWCStructureFieldTextRepeatableTests.suite());
		testSuite.addTest(AddWCStructuresTests.suite());
		testSuite.addTest(AddWCSubstructureTests.suite());
		testSuite.addTest(AddWCSubstructuresTests.suite());
		testSuite.addTest(AdvancedSearchWCStructureTests.suite());
		testSuite.addTest(EditWCSubStructuresDefaultValuesTests.suite());
		testSuite.addTest(LocalizeWCStructureTests.suite());
		testSuite.addTest(SearchWCStructureTests.suite());

		return testSuite;
	}

}