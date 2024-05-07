/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimDTO from '../interfaces/dto/mdfClaimDTO';
import LiferayFile from '../interfaces/liferayFile';
import getNameFromMDFClaimDocument from './getNameFromMDFClaimDocument';

export default function getInvoiceFromMDFClmDocs(mdfClaimDto: MDFClaimDTO) {
	return mdfClaimDto.mdfClmToMDFClmDocs?.reduce<LiferayFile[]>(
		(accumulatorDocuments, currentDocument) => {
			const reimbursementInvoice = {
				documentId: currentDocument.file?.id,
				link: currentDocument.file?.link,
				name:
					currentDocument.file?.name &&
					getNameFromMDFClaimDocument(currentDocument.file.name),
				objectId: currentDocument.id,
			};

			accumulatorDocuments?.push(reimbursementInvoice);

			return accumulatorDocuments;
		},
		[]
	);
}
