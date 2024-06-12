/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaim from '../../../common/interfaces/mdfClaim';
import uploadDocument from './uploadDocument';
import uploadDocuments from './uploadDocuments';

const submitDocuments = async (
	mdfClaim: MDFClaim,
	claimParentFolderId: number
) => {
	if (mdfClaim.reimbursementInvoices?.length) {
		mdfClaim.reimbursementInvoices = await uploadDocuments(
			mdfClaim.reimbursementInvoices,
			claimParentFolderId
		);
	}

	if (mdfClaim.activities?.length) {
		for (const activity of mdfClaim.activities) {
			if (
				activity.eventProgramFile &&
				!activity.eventProgramFile.documentId
			) {
				activity.eventProgramFile.documentId = await uploadDocument(
					activity.eventProgramFile,
					claimParentFolderId
				);
			}

			if (
				activity.listOfQualifiedLeadsFile &&
				!activity.listOfQualifiedLeadsFile.documentId
			) {
				activity.listOfQualifiedLeadsFile.documentId =
					await uploadDocument(
						activity.listOfQualifiedLeadsFile,
						claimParentFolderId
					);
			}

			if (
				activity.telemarketingScriptFile &&
				!activity.telemarketingScriptFile.documentId
			) {
				activity.telemarketingScriptFile.documentId =
					await uploadDocument(
						activity.telemarketingScriptFile,
						claimParentFolderId
					);
			}

			if (activity.proofOfPerformance?.allContents?.length) {
				activity.proofOfPerformance.allContents = await uploadDocuments(
					activity.proofOfPerformance.allContents,
					claimParentFolderId
				);
			}

			if (activity.proofOfPerformance?.eventCollaterals?.length) {
				activity.proofOfPerformance.eventCollaterals =
					await uploadDocuments(
						activity.proofOfPerformance.eventCollaterals,
						claimParentFolderId
					);
			}

			if (activity.proofOfPerformance?.eventInvitations?.length) {
				activity.proofOfPerformance.eventInvitations =
					await uploadDocuments(
						activity.proofOfPerformance.eventInvitations,
						claimParentFolderId
					);
			}

			if (activity.proofOfPerformance?.eventPhotos?.length) {
				activity.proofOfPerformance.eventPhotos = await uploadDocuments(
					activity.proofOfPerformance.eventPhotos,
					claimParentFolderId
				);
			}

			if (activity.proofOfPerformance?.images?.length) {
				activity.proofOfPerformance.images = await uploadDocuments(
					activity.proofOfPerformance.images,
					claimParentFolderId
				);
			}

			if (activity.budgets?.length) {
				for (const budget of activity.budgets) {
					if (budget.invoiceFile && !budget.invoiceFile.documentId) {
						budget.invoiceFile.documentId = await uploadDocument(
							budget.invoiceFile,
							claimParentFolderId
						);
					}
				}
			}
		}
	}

	return mdfClaim;
};
export default submitDocuments;
