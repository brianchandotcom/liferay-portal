/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayFile from '../liferayFile';
import MDFClaimDocument from '../mdfClaimDocument';

export default interface MDFClaimDocumentDTO
	extends Omit<MDFClaimDocument, 'reimbursementInvoices'> {
	file?: LiferayFile & number;
}
