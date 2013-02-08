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
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefielddatewcd.AddWCWebContentStructureFieldDateWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldfileuploadwcd.AddWCWebContentStructureFieldFileUploadWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldhtmlwcd.AddWCWebContentStructureFieldHTMLWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldrepeatablewcd.AddWCWebContentStructureFieldRepeatableWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentwcd.AddWCWebContentWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.permissionswcwebcontentguestviewoffwcd.PermissionsWCWebContentGuestViewOffWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.printwcwebcontentwcd.PrintWCWebContentWCDTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WCWebContentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWCWebContentStructureFieldDateWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldFileUploadWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldHTMLWCDTests.suite());
		testSuite.addTest(AddWCWebContentStructureFieldRepeatableWCDTests.suite());
		testSuite.addTest(AddWCWebContentWCDTests.suite());
		testSuite.addTest(PermissionsWCWebContentGuestViewOffWCDTests.suite());
		testSuite.addTest(PrintWCWebContentWCDTests.suite());

		return testSuite;
	}

}