/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {INITIAL_FILTER} from './initialFilter';

export default function getCloseDateFilterTerm(
	initialFilter: string,
	activityPeriod: typeof INITIAL_FILTER.closeDate
) {
	const filterDates = [];

	if (activityPeriod.dates.startDate) {
		filterDates.push(`closeDate ge ${activityPeriod.dates.startDate}`);
	}

	if (activityPeriod.dates.endDate) {
		filterDates.push(`closeDate le ${activityPeriod.dates.endDate}`);
	}

	return initialFilter
		.concat(' and ')
		.concat(
			filterDates.join(
				activityPeriod.dates.startDate > activityPeriod.dates.endDate
					? ' or '
					: ' and '
			)
		);
}
