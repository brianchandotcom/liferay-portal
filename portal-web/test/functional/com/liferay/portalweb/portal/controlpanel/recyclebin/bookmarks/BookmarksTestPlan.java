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

package com.liferay.portalweb.portal.controlpanel.recyclebin.bookmarks;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.recyclebin.bookmarks.movetorecyclebinbookmarks.MoveToRecycleBinBookmarksTests;
import com.liferay.portalweb.portal.controlpanel.recyclebin.bookmarks.movetorecyclebinbookmarksfolder.MoveToRecycleBinBookmarksFolderTests;
import com.liferay.portalweb.portal.controlpanel.recyclebin.bookmarks.movetorecyclebinbookmarkssubfolder.MoveToRecycleBinBookmarksSubFolderTests;
import com.liferay.portalweb.portal.controlpanel.recyclebin.bookmarks.restorebookmarksfolderrecyclebin.RestoreBookmarksFolderRecycleBinTests;
import com.liferay.portalweb.portal.controlpanel.recyclebin.bookmarks.restorebookmarksrecyclebin.RestoreBookmarksRecycleBinTests;
import com.liferay.portalweb.portal.controlpanel.recyclebin.bookmarks.restorebookmarkssubfolderrecyclebin.RestoreBookmarksSubFolderRecycleBinTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(MoveToRecycleBinBookmarksTests.suite());
		testSuite.addTest(MoveToRecycleBinBookmarksFolderTests.suite());
		testSuite.addTest(MoveToRecycleBinBookmarksSubFolderTests.suite());
		testSuite.addTest(RestoreBookmarksFolderRecycleBinTests.suite());
		testSuite.addTest(RestoreBookmarksRecycleBinTests.suite());
		testSuite.addTest(RestoreBookmarksSubFolderRecycleBinTests.suite());

		return testSuite;
	}

}