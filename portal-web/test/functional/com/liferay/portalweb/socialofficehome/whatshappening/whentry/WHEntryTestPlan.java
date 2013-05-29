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

package com.liferay.portalweb.socialofficehome.whatshappening.whentry;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontent150character.AddWHEntryContent150CharacterTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontent151character.AddWHEntryContent151CharacterTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontentviewablebyconnections.AddWHEntryContentViewableByConnectionsTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontentviewablebyeveryone.AddWHEntryContentViewableByEveryoneTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontentviewablebyfollowers.AddWHEntryContentViewableByFollowersTests;
import com.liferay.portalweb.socialofficehome.whatshappening.whentry.sousviewwhentrycontentviewablebyeveryone.SOUs_ViewWHEntryContentViewableByEveryoneTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WHEntryTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWHEntryContent150CharacterTests.suite());
		testSuite.addTest(AddWHEntryContent151CharacterTests.suite());
		testSuite.addTest(AddWHEntryContentViewableByConnectionsTests.suite());
		testSuite.addTest(AddWHEntryContentViewableByEveryoneTests.suite());
		testSuite.addTest(AddWHEntryContentViewableByFollowersTests.suite());
		testSuite.addTest(
			SOUs_ViewWHEntryContentViewableByEveryoneTests.suite());

		return testSuite;
	}

}