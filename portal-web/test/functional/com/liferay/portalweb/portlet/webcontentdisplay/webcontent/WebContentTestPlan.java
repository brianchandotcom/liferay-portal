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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.addportletscopecurrentpagewcwebcontentwcd.AddPortletScopeCurrentPageWCWebContentWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.addportletscopedefaultwcwebcontentwcd.AddPortletScopeDefaultWCWebContentWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.deletethisversionwcwebcontentwcdactions.DeleteThisVersionWCWebContentWCDActionsTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.editwcwebcontentwcddetails.EditWCWebContentWCDDetailsTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.expirethisversionwcwebcontentwcddetails.ExpireThisVersionWCWebContentWCDDetailsTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.localizewcwebcontenttemplatewcd.LocalizeWCWebContentTemplateWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.localizewcwebcontentwcd.LocalizeWCWebContentWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.ratewcwebcontentwcd.RateWCWebContentWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.selectportletscopepage2wcwebcontentwcd.SelectPortletScopePage2WCWebContentWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.selectwcwebcontentwcd.SelectWCWebContentWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewlocalizewebcontentlanguagewcd.ViewLocalizeWebContentLanguageWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewportletshowlocaleswebcontenttemplatewcd.ViewPortletShowLocalesWebContentTemplateWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewportletshowlocaleswebcontentwcd.ViewPortletShowLocalesWebContentWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewwcwebcontentscopeglobalwcd.ViewWCWebContentScopeGlobalWCDTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WebContentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(
			AddPortletScopeCurrentPageWCWebContentWCDTests.suite());
		testSuite.addTest(AddPortletScopeDefaultWCWebContentWCDTests.suite());
		testSuite.addTest(DeleteThisVersionWCWebContentWCDActionsTests.suite());
		testSuite.addTest(EditWCWebContentWCDDetailsTests.suite());
		testSuite.addTest(ExpireThisVersionWCWebContentWCDDetailsTests.suite());
		testSuite.addTest(LocalizeWCWebContentTemplateWCDTests.suite());
		testSuite.addTest(LocalizeWCWebContentWCDTests.suite());
		testSuite.addTest(RateWCWebContentWCDTests.suite());
		testSuite.addTest(SelectPortletScopePage2WCWebContentWCDTests.suite());
		testSuite.addTest(SelectWCWebContentWCDTests.suite());
		testSuite.addTest(ViewLocalizeWebContentLanguageWCDTests.suite());
		testSuite.addTest(
			ViewPortletShowLocalesWebContentTemplateWCDTests.suite());
		testSuite.addTest(ViewPortletShowLocalesWebContentWCDTests.suite());
		testSuite.addTest(ViewWCWebContentScopeGlobalWCDTests.suite());

		return testSuite;
	}

}