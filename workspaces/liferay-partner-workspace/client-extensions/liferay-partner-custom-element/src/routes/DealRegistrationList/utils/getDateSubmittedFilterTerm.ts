/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {INITIAL_FILTER} from './constants/initialFilter';

export default function getDateSubmittedFilterTerm(
	initialFilter: string,
	dataSubmitted: typeof INITIAL_FILTER.dataSubmitted
) {
	const filterDates = [];

	if (dataSubmitted.dates.startDate) {
		filterDates.push(
			`createdDate ge ${dataSubmitted.dates.startDate}T00:00:00Z`
		);
	}
	if (dataSubmitted.dates.endDate) {
		filterDates.push(
			`createdDate le ${dataSubmitted.dates.endDate}T00:00:00Z`
		);
	}

	return initialFilter
		.concat(' and ')
		.concat(
			filterDates.join(
				dataSubmitted.dates.startDate > dataSubmitted.dates.endDate
					? ' or '
					: ' and '
			)
		);
}
