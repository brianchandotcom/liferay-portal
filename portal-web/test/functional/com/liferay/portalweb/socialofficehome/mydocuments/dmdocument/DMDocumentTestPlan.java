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

package com.liferay.portalweb.socialofficehome.mydocuments.dmdocument;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmdocument.AddDMDocumentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderdocument.AddDMFolderDocumentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderdocumentcomment.AddDMFolderDocumentCommentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderdocumentmultiple.AddDMFolderDocumentMultipleTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderdocumenttags.AddDMFolderDocumentTagsTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderimage.AddDMFolderImageTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderimagecomment.AddDMFolderImageCommentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderimagemultiple.AddDMFolderImageMultipleTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.adddmfolderimagetags.AddDMFolderImageTagsTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.cancelcheckoutdmfolderdocument.CancelCheckoutDMFolderDocumentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.checkinmajordmfolderdocument.CheckinMajorDMFolderDocumentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.checkinminordmfolderdocument.CheckinMinorDMFolderDocumentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.checkoutdmfolderdocument.CheckoutDMFolderDocumentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.deletedmfolderdocument.DeleteDMFolderDocumentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.deletedmfolderimage.DeleteDMFolderImageTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.editdmfolderdocument.EditDMFolderDocumentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.editdmfolderimage.EditDMFolderImageTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.movedmfolder1documentfolder2.MoveDMFolder1DocumentFolder2Tests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.publishdraftdmfolderdocument.PublishDraftDMFolderDocumentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.ratedmfolderdocument.RateDMFolderDocumentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.ratedmfolderimage.RateDMFolderImageTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.saveandcheckinmajordmfolderdocument.SaveAndCheckinMajorDMFolderDocumentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.saveandcheckinminordmfolderdocument.SaveAndCheckinMinorDMFolderDocumentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.saveasdraftdmfolderdocument.SaveAsDraftDMFolderDocumentTests;
import com.liferay.portalweb.socialofficehome.mydocuments.dmdocument.saveasdrafteditdmfolderdocument.SaveAsDraftEditDMFolderDocumentTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DMDocumentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDMDocumentTests.suite());
		testSuite.addTest(AddDMFolderDocumentTests.suite());
		testSuite.addTest(AddDMFolderDocumentCommentTests.suite());
		testSuite.addTest(AddDMFolderDocumentMultipleTests.suite());
		testSuite.addTest(AddDMFolderDocumentTagsTests.suite());
		testSuite.addTest(AddDMFolderImageTests.suite());
		testSuite.addTest(AddDMFolderImageCommentTests.suite());
		testSuite.addTest(AddDMFolderImageMultipleTests.suite());
		testSuite.addTest(AddDMFolderImageTagsTests.suite());
		testSuite.addTest(CancelCheckoutDMFolderDocumentTests.suite());
		testSuite.addTest(CheckinMajorDMFolderDocumentTests.suite());
		testSuite.addTest(CheckinMinorDMFolderDocumentTests.suite());
		testSuite.addTest(CheckoutDMFolderDocumentTests.suite());
		testSuite.addTest(DeleteDMFolderDocumentTests.suite());
		testSuite.addTest(DeleteDMFolderImageTests.suite());
		testSuite.addTest(EditDMFolderDocumentTests.suite());
		testSuite.addTest(EditDMFolderImageTests.suite());
		testSuite.addTest(MoveDMFolder1DocumentFolder2Tests.suite());
		testSuite.addTest(PublishDraftDMFolderDocumentTests.suite());
		testSuite.addTest(RateDMFolderDocumentTests.suite());
		testSuite.addTest(RateDMFolderImageTests.suite());
		testSuite.addTest(SaveAndCheckinMajorDMFolderDocumentTests.suite());
		testSuite.addTest(SaveAndCheckinMinorDMFolderDocumentTests.suite());
		testSuite.addTest(SaveAsDraftDMFolderDocumentTests.suite());
		testSuite.addTest(SaveAsDraftEditDMFolderDocumentTests.suite());

		return testSuite;
	}

}