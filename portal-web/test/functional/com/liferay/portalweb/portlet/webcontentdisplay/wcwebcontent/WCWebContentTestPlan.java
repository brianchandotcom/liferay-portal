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

package com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldbooleanwcd.AddWCWebContentStructureFieldBooleanWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefielddatewcd.AddWCWebContentStructureFieldDateWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefielddecimalwcd.AddWCWebContentStructureFieldDecimalWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefielddmwcd.AddWCWebContentStructureFieldDMWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldfileuploadwcd.AddWCWebContentStructureFieldFileUploadWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldhtmlwcd.AddWCWebContentStructureFieldHTMLWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldimagewcd.AddWCWebContentStructureFieldImageWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldintegerwcd.AddWCWebContentStructureFieldIntegerWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldlinkwcd.AddWCWebContentStructureFieldLinkWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldnumberwcd.AddWCWebContentStructureFieldNumberWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldradiowcd.AddWCWebContentStructureFieldRadioWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldselectwcd.AddWCWebContentStructureFieldSelectWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldtextboxwcd.AddWCWebContentStructureFieldTextBoxWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldtextindexwcd.AddWCWebContentStructureFieldTextIndexWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldtextrepeatwcd.AddWCWebContentStructureFieldTextRepeatWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldtextwcd.AddWCWebContentStructureFieldTextWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentwcd.AddWCWebContentWCDTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WCWebContentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWCWebContentStructureFieldBooleanWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldDateWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldDecimalWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldDMWCDTests.suite());
		testSuite.addTest(
			AddWCWebContentStructureFieldFileUploadWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldHTMLWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldImageWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldIntegerWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldLinkWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldNumberWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldRadioWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldSelectWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldTextBoxWCDTests.suite());
		testSuite.addTest(
			AddWCWebContentStructureFieldTextIndexWCDTests.suite());
		testSuite.addTest(
			AddWCWebContentStructureFieldTextRepeatWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldTextWCDTests.suite());
		testSuite.addTest(AddWCWebContentWCDTests.suite());

		return testSuite;
	}

}