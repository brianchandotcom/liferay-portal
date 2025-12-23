/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FORMAT_DATE_TYPES} from '~/utils/constants';
import getDateCustomFormat from '~/utils/getDateCustomFormat';

export default function getSLACard(endDate, startDate, title, label) {
	return {
		endDate: getDateCustomFormat(
			FORMAT_DATE_TYPES.day2DMonth2DYearN,
			endDate
		),
		label,
		startDate: getDateCustomFormat(
			FORMAT_DATE_TYPES.day2DMonth2DYearN,
			startDate
		),
		title: title.includes('/')
			? `${title.split(' ')[0]} ${title.split(' ')[1]}`
			: title.split(' ')[0],
	};
}
