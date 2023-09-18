/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const KB_TO_MB = 1024;
const MAX_MB = KB_TO_MB * 3;

export const validateDocument = {
	document: {
		message:
			'Unsupported File Format, upload a valid format *jpg *jpeg *png *tif *tiff *pdf *doc *docx',
		types: [
			'image/jpg',
			'image/jpeg',
			'image/png',
			'image/tif',
			'image/tiff',
			'application/pdf',
			'application/msword',
			'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
		],
	},
	fileSize: {
		maxSize: MAX_MB,
		message: 'File Size is too large',
	},
	imageDocument: {
		message:
			'Unsupported File Format, upload a valid format *jpg *jpeg *png *tif *tiff *pdf',
		types: [
			'image/jpg',
			'image/jpeg',
			'image/png',
			'image/tif',
			'image/tiff',
			'application/pdf',
		],
	},
	listOfLeadsDocuments: {
		message:
			'Unsupported File Format, upload a valid format *csv *xlsx *xls',
		types: [
			'text/csv',
			'application/vnd.ms-excel',
			'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
		],
	},
};
