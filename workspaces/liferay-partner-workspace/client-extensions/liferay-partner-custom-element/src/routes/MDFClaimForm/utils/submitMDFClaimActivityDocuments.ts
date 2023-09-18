/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimActivityDocumentDTO from '../../../common/interfaces/dto/mdfClaimActivityDocumentDTO';
import MDFClaimActivityDocument from '../../../common/interfaces/mdfClaimActivityDocument';
import createMDFClaimActivityDocument from '../../../common/services/liferay/object/mdf-claim-activity-documents/createMDFClaimActivityDocument';
import updateMDFClaimActivityDocument from '../../../common/services/liferay/object/mdf-claim-activity-documents/updateMDFClaimActivityDocument.';
import getDocumentDTOFromLiferayFile from '../../../common/utils/dto/mdf-claim-activity-document/getDocumentDTOFromLiferayFile';
import {ProofOfPerformanceType} from '../constants/proofOfPerformanceType';

const submitMDFClaimActivityDocuments = async (
	proofOfPerformance: MDFClaimActivityDocument,
	companyId: number,
	dtoMDFClaimActivityId: number
) => {
	const dtoMDFClaimActivityDocumentsCreate: MDFClaimActivityDocumentDTO[] = [];
	const dtoMDFClaimActivityDocumentsUpdate: MDFClaimActivityDocumentDTO[] = [];

	if (proofOfPerformance.allContents?.length) {
		proofOfPerformance.allContents.map(async (allContentDocument) => {
			if (allContentDocument.documentId) {
				const dtoMDFClaimActivityDocument = getDocumentDTOFromLiferayFile(
					allContentDocument,
					ProofOfPerformanceType.ALL_CONTENTS,
					companyId,
					dtoMDFClaimActivityId
				);
				dtoMDFClaimActivityDocument.id
					? dtoMDFClaimActivityDocumentsUpdate.push(
							dtoMDFClaimActivityDocument
					  )
					: dtoMDFClaimActivityDocumentsCreate.push(
							dtoMDFClaimActivityDocument
					  );
			}
		});
	}

	if (proofOfPerformance.eventCollaterals?.length) {
		proofOfPerformance.eventCollaterals.map(
			async (eventCollateralsDocument) => {
				if (eventCollateralsDocument.documentId) {
					const dtoMDFClaimActivityDocument = getDocumentDTOFromLiferayFile(
						eventCollateralsDocument,
						ProofOfPerformanceType.EVENT_COLLATERALS,
						companyId,
						dtoMDFClaimActivityId
					);
					dtoMDFClaimActivityDocument.id
						? dtoMDFClaimActivityDocumentsUpdate.push(
								dtoMDFClaimActivityDocument
						  )
						: dtoMDFClaimActivityDocumentsCreate.push(
								dtoMDFClaimActivityDocument
						  );
				}
			}
		);
	}
	if (proofOfPerformance.eventInvitations?.length) {
		proofOfPerformance.eventInvitations.map(
			async (eventInvitationsDocument) => {
				if (eventInvitationsDocument.documentId) {
					const dtoMDFClaimActivityDocument = getDocumentDTOFromLiferayFile(
						eventInvitationsDocument,
						ProofOfPerformanceType.EVENT_INVITATIONS,
						companyId,
						dtoMDFClaimActivityId
					);
					dtoMDFClaimActivityDocument.id
						? dtoMDFClaimActivityDocumentsUpdate.push(
								dtoMDFClaimActivityDocument
						  )
						: dtoMDFClaimActivityDocumentsCreate.push(
								dtoMDFClaimActivityDocument
						  );
				}
			}
		);
	}
	if (proofOfPerformance.eventPhotos?.length) {
		proofOfPerformance.eventPhotos.map(async (eventPhotosDocument) => {
			if (eventPhotosDocument.documentId) {
				const dtoMDFClaimActivityDocument = getDocumentDTOFromLiferayFile(
					eventPhotosDocument,
					ProofOfPerformanceType.EVENT_PHOTOS,
					companyId,
					dtoMDFClaimActivityId
				);
				dtoMDFClaimActivityDocument.id
					? dtoMDFClaimActivityDocumentsUpdate.push(
							dtoMDFClaimActivityDocument
					  )
					: dtoMDFClaimActivityDocumentsCreate.push(
							dtoMDFClaimActivityDocument
					  );
			}
		});
	}
	if (proofOfPerformance.images?.length) {
		proofOfPerformance.images.map(async (imagesDocument) => {
			if (imagesDocument.documentId) {
				const dtoMDFClaimActivityDocument = getDocumentDTOFromLiferayFile(
					imagesDocument,
					ProofOfPerformanceType.IMAGES,
					companyId,
					dtoMDFClaimActivityId
				);
				dtoMDFClaimActivityDocument.id
					? dtoMDFClaimActivityDocumentsUpdate.push(
							dtoMDFClaimActivityDocument
					  )
					: dtoMDFClaimActivityDocumentsCreate.push(
							dtoMDFClaimActivityDocument
					  );
			}
		});
	}

	if (dtoMDFClaimActivityDocumentsCreate.length) {
		await createMDFClaimActivityDocument(
			dtoMDFClaimActivityDocumentsCreate
		);
	}

	if (dtoMDFClaimActivityDocumentsUpdate.length) {
		await updateMDFClaimActivityDocument(
			dtoMDFClaimActivityDocumentsUpdate
		);
	}
};

export default submitMDFClaimActivityDocuments;
