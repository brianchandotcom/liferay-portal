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

package com.liferay.portalweb.portlet.mediagallery.dmimage;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.mediagallery.dmimage.adddmfolderimageimageinvalidmg.AddDMFolderImageImageInvalidMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmimage.adddmfolderimageimagenullmg.AddDMFolderImageImageNullMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmimage.adddmfolderimagemg.AddDMFolderImageMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmimage.adddmfolderimagemultiplemg.AddDMFolderImageMultipleMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmimage.adddmfolderimagenameduplicatemg.AddDMFolderImageNameDuplicateMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmimage.adddmfolderimagenamenullmg.AddDMFolderImageNameNullMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmimage.adddmimagemg.AddDMImageMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmimage.adddmsubfolderimagemg.AddDMSubfolderImageMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmimage.deletedmfolderimagemg.DeleteDMFolderImageMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmimage.deletedmsubfolderimagemg.DeleteDMSubfolderImageMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmimage.editdmfolderimagemg.EditDMFolderImageMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmimage.editdmsubfolderimagemg.EditDMSubfolderImageMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmimage.movedmfolderimagetofoldermg.MoveDMFolderImageToFolderMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmimage.searchdmfolderimagemg.SearchDMFolderImageMGTests;
import com.liferay.portalweb.portlet.mediagallery.dmimage.searchdmfolderimagemgfolderdetails.SearchDMFolderImageFolderDetailsMGTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DMImageTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDMFolderImageMGTests.suite());
		testSuite.addTest(AddDMFolderImageImageInvalidMGTests.suite());
		testSuite.addTest(AddDMFolderImageImageNullMGTests.suite());
		testSuite.addTest(AddDMFolderImageNameDuplicateMGTests.suite());
		testSuite.addTest(AddDMFolderImageNameNullMGTests.suite());
		testSuite.addTest(AddDMFolderImageMultipleMGTests.suite());
		testSuite.addTest(AddDMImageMGTests.suite());
		testSuite.addTest(AddDMSubfolderImageMGTests.suite());
		testSuite.addTest(DeleteDMFolderImageMGTests.suite());
		testSuite.addTest(DeleteDMSubfolderImageMGTests.suite());
		testSuite.addTest(EditDMFolderImageMGTests.suite());
		testSuite.addTest(EditDMSubfolderImageMGTests.suite());
		testSuite.addTest(MoveDMFolderImageToFolderMGTests.suite());
		testSuite.addTest(SearchDMFolderImageMGTests.suite());
		testSuite.addTest(SearchDMFolderImageFolderDetailsMGTests.suite());

		return testSuite;
	}

}