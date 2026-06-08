/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClassName, FILE_MIME_MAP, STRUCTURES_MAP} from './constants';

export const getMimeType = function getMimeType({
	assetType,
	mimeType,
}: {
	assetType?: string;
	mimeType?: string;
}): {className: ClassName; icon: string} {
	if (!mimeType) {
		if (STRUCTURES_MAP[assetType || ClassName.DocumentDefault]) {
			return STRUCTURES_MAP[assetType || ClassName.DocumentDefault];
		}

		return STRUCTURES_MAP['CMSDocumentDefault'];
	}

	if (FILE_MIME_MAP[mimeType]) {
		return FILE_MIME_MAP[mimeType];
	}

	const prefix = mimeType.split('/')[0];

	if (FILE_MIME_MAP[prefix]) {
		return FILE_MIME_MAP[prefix];
	}

	return FILE_MIME_MAP['default'];
};
