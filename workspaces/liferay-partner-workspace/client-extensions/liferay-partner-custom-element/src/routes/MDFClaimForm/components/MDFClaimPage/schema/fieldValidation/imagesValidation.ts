/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {array, mixed} from 'yup';

import {validateDocument} from '../constants/validateDocument';

export const imagesValidation = {
	images: array().of(
		mixed()
			.test(
				'fileSize',
				validateDocument.fileSize.message,
				(imageFile) => {
					if (imageFile && !imageFile.documentId) {
						return (
							Math.ceil(imageFile.size / 1000) <=
							validateDocument.fileSize.maxSize
						);
					}

					return true;
				}
			)
			.test(
				'fileType',
				validateDocument.imageDocument.message,
				(imageFile) => {
					if (imageFile && !imageFile.documentId) {
						return validateDocument.imageDocument.types.includes(
							imageFile.type
						);
					}

					return true;
				}
			)
	),
};
