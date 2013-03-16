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

package com.liferay.portalweb.socialofficesite.documents.dmdocument;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.adddmdocumentsite.AddDMDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.adddmfolderdocumentcommentsite.AddDMFolderDocumentCommentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.adddmfolderdocumentmultiplesite.AddDMFolderDocumentMultipleSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.adddmfolderdocumentsite.AddDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.adddmfolderdocumenttagssite.AddDMFolderDocumentTagsSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.adddmfolderimagecommentsite.AddDMFolderImageCommentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.adddmfolderimagemultiplesite.AddDMFolderImageMultipleSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.adddmfolderimagesite.AddDMFolderImageSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.adddmfolderimagetagssite.AddDMFolderImageTagsSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.cancelcheckoutdmfolderdocumentsite.CancelCheckoutDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.checkinmajordmfolderdocumentsite.CheckinMajorDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.checkinminordmfolderdocumentsite.CheckinMinorDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.checkoutdmfolderdocumentsite.CheckoutDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.deletedmfolderdocumentsite.DeleteDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.deletedmfolderimagesite.DeleteDMFolderImageSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.editdmfolderdocumentsite.EditDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.editdmfolderimagesite.EditDMFolderImageSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.editpermissionsfolder2guestnoview.EditPermissionsFolder2GuestNoViewTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.editpermissionsfolderdocument2guestnoview.EditPermissionsFolderDocument2GuestNoViewTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.movedmfolder1documentfolder2site.MoveDMFolder1DocumentFolder2SiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.publishdraftdmfolderdocumentsite.PublishDraftDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.ratedmfolderdocumentsite.RateDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.ratedmfolderimagesite.RateDMFolderImageSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.saveandcheckinmajordmfolderdocumentsite.SaveAndCheckinMajorDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.saveandcheckinminordmfolderdocumentsite.SaveAndCheckinMinorDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.saveasdraftdmfolderdocumentsite.SaveAsDraftDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.saveasdrafteditdmfolderdocumentsite.SaveAsDraftEditDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.sousviewcheckoutdmfolderdocumentsite.SOUs_ViewCheckoutDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.sousviewdraftdmfolderdocumentsite.SOUs_ViewDraftDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.sousviewdrafteditdmfolderdocumentsite.SOUs_ViewDraftEditDMFolderDocumentSiteTests;
import com.liferay.portalweb.socialofficesite.documents.dmdocument.viewdmdocumentlatestversionsite.ViewDMDocumentLatestVersionSiteTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DMDocumentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDMDocumentSiteTests.suite());
		testSuite.addTest(AddDMFolderDocumentCommentSiteTests.suite());
		testSuite.addTest(AddDMFolderDocumentMultipleSiteTests.suite());
		testSuite.addTest(AddDMFolderDocumentSiteTests.suite());
		testSuite.addTest(AddDMFolderDocumentTagsSiteTests.suite());
		testSuite.addTest(AddDMFolderImageCommentSiteTests.suite());
		testSuite.addTest(AddDMFolderImageMultipleSiteTests.suite());
		testSuite.addTest(AddDMFolderImageSiteTests.suite());
		testSuite.addTest(AddDMFolderImageTagsSiteTests.suite());
		testSuite.addTest(CancelCheckoutDMFolderDocumentSiteTests.suite());
		testSuite.addTest(CheckinMajorDMFolderDocumentSiteTests.suite());
		testSuite.addTest(CheckinMinorDMFolderDocumentSiteTests.suite());
		testSuite.addTest(CheckoutDMFolderDocumentSiteTests.suite());
		testSuite.addTest(DeleteDMFolderDocumentSiteTests.suite());
		testSuite.addTest(DeleteDMFolderImageSiteTests.suite());
		testSuite.addTest(EditDMFolderDocumentSiteTests.suite());
		testSuite.addTest(EditDMFolderImageSiteTests.suite());
		testSuite.addTest(EditPermissionsFolder2GuestNoViewTests.suite());
		testSuite.addTest(
			EditPermissionsFolderDocument2GuestNoViewTests.suite());
		testSuite.addTest(MoveDMFolder1DocumentFolder2SiteTests.suite());
		testSuite.addTest(PublishDraftDMFolderDocumentSiteTests.suite());
		testSuite.addTest(RateDMFolderDocumentSiteTests.suite());
		testSuite.addTest(RateDMFolderImageSiteTests.suite());
		testSuite.addTest(SaveAndCheckinMajorDMFolderDocumentSiteTests.suite());
		testSuite.addTest(SaveAndCheckinMinorDMFolderDocumentSiteTests.suite());
		testSuite.addTest(SaveAsDraftDMFolderDocumentSiteTests.suite());
		testSuite.addTest(SaveAsDraftEditDMFolderDocumentSiteTests.suite());
		testSuite.addTest(SOUs_ViewCheckoutDMFolderDocumentSiteTests.suite());
		testSuite.addTest(SOUs_ViewDraftDMFolderDocumentSiteTests.suite());
		testSuite.addTest(SOUs_ViewDraftEditDMFolderDocumentSiteTests.suite());
		testSuite.addTest(ViewDMDocumentLatestVersionSiteTests.suite());

		return testSuite;
	}

}