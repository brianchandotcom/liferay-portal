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

package com.liferay.portalweb.socialofficehome.activities.activitiesblockedsouser.sousviewactivitiesblockedsouser3;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs1_ViewActivitiesSOUser3Test extends BaseTestCase {
	public void testSOUs1_ViewActivitiesSOUser3() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard");
		assertEquals(RuntimeVariables.replace("Activities"),
			selenium.getText("xPath=(//h1[@class='portlet-title']/span)[2]"));
		assertEquals(RuntimeVariables.replace("Connections"),
			selenium.getText("link=Connections"));
		selenium.clickAt("link=Connections",
			RuntimeVariables.replace("Connections"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Social03 Office03 User03 wrote a new message board post, Forums Thread1 Message Subject, in Open Site Name."),
			selenium.getText("xPath=(//div[@class='activity-title'])[1]"));
		assertEquals(RuntimeVariables.replace("Social03 Office03 User03"),
			selenium.getText("xPath=(//a[@class='user'])[1]"));
		assertEquals(RuntimeVariables.replace("Microblogs Post1"),
			selenium.getText("xPath=(//div[@class='activity-title'])[2]"));
		assertEquals(RuntimeVariables.replace("Social03 Office03 User03"),
			selenium.getText("xPath=(//a[@class='user'])[2]"));
	}
}