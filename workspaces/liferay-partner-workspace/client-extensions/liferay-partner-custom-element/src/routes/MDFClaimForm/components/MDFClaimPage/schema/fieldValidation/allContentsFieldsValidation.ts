/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {array, mixed} from 'yup';

import {validateDocument} from '../constants/validateDocument';

export const allContentsFieldsValidation = {
	allContents: array()
		.of(
			mixed()
				.test(
					'fileSize',
					validateDocument.fileSize.message,
					(allContentFile) => {
						if (allContentFile && !allContentFile.documentId) {
							return (
								Math.ceil(allContentFile.size / 1000) <=
								validateDocument.fileSize.maxSize
							);
						}

						return true;
					}
				)
				.test(
					'fileType',
					validateDocument.document.message,
					(allContentFile) => {
						if (allContentFile && !allContentFile.documentId) {
							return validateDocument.document.types.includes(
								allContentFile.type
							);
						}

						return true;
					}
				)
		)
		.min(1, 'All Contents must have at least 1 file')
		.required('Required'),
};
