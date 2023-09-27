/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayFile from '../../../interfaces/liferayFile';
import getNameFromMDFClaimDocument from '../../getNameFromMDFClaimDocument';

export function getLiferayFileFromAttachment(
	liferayFile: LiferayFile
): LiferayFile & number {
	return {
		documentId: liferayFile.id,
		link: liferayFile.link,
		name: liferayFile.name && getNameFromMDFClaimDocument(liferayFile.name),
	} as LiferayFile & number;
}
