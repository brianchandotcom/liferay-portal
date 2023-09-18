/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ProofOfPerformanceType} from '../../routes/MDFClaimForm/constants/proofOfPerformanceType';
import MDFClaimActivityDTO from '../interfaces/dto/mdfClaimActivityDTO';
import MDFClaimActivityDocument from '../interfaces/mdfClaimActivityDocument';
import {getPOPDocument} from './getPOPDocument';

export default function getPOPFromMDFActDocs(
	activityItem: MDFClaimActivityDTO
) {
	return activityItem.mdfClmActToMDFActDocs?.reduce(
		(accumulatorDocuments, currentDocument) => {
			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.ALL_CONTENTS.key
			) {
				accumulatorDocuments.allContents?.push(
					getPOPDocument(currentDocument)
				);
			}

			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.EVENT_COLLATERALS.key
			) {
				accumulatorDocuments.eventCollaterals?.push(
					getPOPDocument(currentDocument)
				);
			}
			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.EVENT_INVITATIONS.key
			) {
				accumulatorDocuments.eventInvitations?.push(
					getPOPDocument(currentDocument)
				);
			}
			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.EVENT_PHOTOS.key
			) {
				accumulatorDocuments.eventPhotos?.push(
					getPOPDocument(currentDocument)
				);
			}
			if (
				currentDocument.proofOfPerformanceType?.key ===
				ProofOfPerformanceType.IMAGES.key
			) {
				accumulatorDocuments.images?.push(
					getPOPDocument(currentDocument)
				);
			}

			return accumulatorDocuments;
		},
		{
			allContents: [],
			eventCollaterals: [],
			eventInvitations: [],
			eventPhotos: [],
			images: [],
		} as MDFClaimActivityDocument
	) as MDFClaimActivityDocument;
}
