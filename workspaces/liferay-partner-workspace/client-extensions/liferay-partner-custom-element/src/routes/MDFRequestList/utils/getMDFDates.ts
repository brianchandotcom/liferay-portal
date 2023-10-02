/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MDFColumnKey} from '../../../common/enums/mdfColumnKey';
import {customFormatDateOptions} from '../../../common/utils/constants/customFormatDateOptions';
import getDateCustomFormat from '../../../common/utils/getDateCustomFormat';

export default function getMDFDates(
	dateCreated?: string,
	dateModified?: string
) {
	if (dateCreated && dateModified) {
		const dateSubmitted = getDateCustomFormat(
			dateCreated,
			customFormatDateOptions.SHORT_MONTH
		);

		const lastModified = getDateCustomFormat(
			dateModified,
			customFormatDateOptions.SHORT_MONTH
		);

		return {
			[MDFColumnKey.DATE_SUBMITTTED]: dateSubmitted,
			[MDFColumnKey.LAST_MODIFIED]: lastModified,
		};
	}
}
