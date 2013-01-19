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

package com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderimagemultiple;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderdocument.AddDMFolderTest;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderdocument.TearDownDMFolderTest;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderimage.AddDMFolderImage1Test;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderimage.AddDMFolderImage2Test;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderimage.AddDMFolderImage3Test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMFolderImageMultipleTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddDMFolderTest.class);
		testSuite.addTestSuite(AddDMFolderImage1Test.class);
		testSuite.addTestSuite(AddDMFolderImage2Test.class);
		testSuite.addTestSuite(AddDMFolderImage3Test.class);
		testSuite.addTestSuite(ViewDMFolderImageMultipleTest.class);
		testSuite.addTestSuite(TearDownDMFolderTest.class);

		return testSuite;
	}
}