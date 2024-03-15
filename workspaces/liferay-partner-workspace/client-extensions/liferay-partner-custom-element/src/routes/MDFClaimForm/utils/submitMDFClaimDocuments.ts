/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayFile from '../../../common/interfaces/liferayFile';
import createMDFClaimDocuments from '../../../common/services/liferay/object/mdf-claim-documents/createMDFClaimDocuments';
import updateMDFClaimDocuments from '../../../common/services/liferay/object/mdf-claim-documents/updateMDFClaimDocuments';

const submitMDFClaimDocuments = async (
	companyId: number,
	dtoMDFClaimId: number,
	reimbursementInvoices?: LiferayFile[]
) => {
	const dtoMDFClaimDocumentsCreate: LiferayFile[] = [];
	const dtoMDFClaimDocumentsUpdate: LiferayFile[] = [];

	if (reimbursementInvoices?.length) {
		reimbursementInvoices.map(async (reimbursementInvoice) => {
			if (reimbursementInvoice.documentId) {
				const dtoMDFClaimDocument = {
					file: reimbursementInvoice.documentId,
					id: reimbursementInvoice.objectId,
					r_accToMDFClmDocs_accountEntryId: companyId,
					r_mdfClmToMDFClmDocs_c_mdfClaimId: dtoMDFClaimId,
				};

				dtoMDFClaimDocument.id
					? dtoMDFClaimDocumentsUpdate.push(dtoMDFClaimDocument)
					: dtoMDFClaimDocumentsCreate.push(dtoMDFClaimDocument);
			}
		});
	}

	if (dtoMDFClaimDocumentsCreate.length) {
		await createMDFClaimDocuments(dtoMDFClaimDocumentsCreate);
	}

	if (dtoMDFClaimDocumentsUpdate.length) {
		await updateMDFClaimDocuments(dtoMDFClaimDocumentsUpdate);
	}
};

export default submitMDFClaimDocuments;
