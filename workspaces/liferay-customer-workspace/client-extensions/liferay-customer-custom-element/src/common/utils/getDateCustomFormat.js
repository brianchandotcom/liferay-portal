/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {Liferay} from '../services/liferay';

export default function getDateCustomFormat(rawDate, format) {
	if (rawDate) {
		const date =
			typeof rawDate === 'string'
				? new Date(rawDate.substring(0, rawDate.length - 1))
				: rawDate;

		return date.toLocaleDateString(
			Liferay.ThemeDisplay.getBCP47LanguageId(),
			format
		);
	}
}
