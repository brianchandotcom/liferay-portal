/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '~/common/services/liferay';
import {FORMAT_DATE_TYPES} from '~/common/utils/constants';

export function getFormattedDate(
	date: string | undefined,
	formatType: keyof typeof FORMAT_DATE_TYPES
): string {
	if (!date) {
		return '';
	}

	try {
		const parsedDate = new Date(date);

		if (isNaN(parsedDate.getTime())) {
			return 'Invalid Date';
		}

		const options: Intl.DateTimeFormatOptions =
			FORMAT_DATE_TYPES[formatType];

		return parsedDate.toLocaleDateString(
			Liferay.ThemeDisplay.getBCP47LanguageId(),
			options
		);
	}
	catch (error) {
		console.error('Error parsing date:', error);

		return 'Invalid Date';
	}
}
