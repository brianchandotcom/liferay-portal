/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {INITIAL_FILTER} from './constants/initialFilter';

export default function getActivityPeriodFilterTerm(
	initialFilter: string,
	activityPeriod: typeof INITIAL_FILTER.activityPeriod
) {
	const filterDates = [];

	if (activityPeriod.dates.startDate) {
		filterDates.push(
			`minDateActivity ge ${activityPeriod.dates.startDate}`
		);
	}
	if (activityPeriod.dates.endDate) {
		filterDates.push(`maxDateActivity le ${activityPeriod.dates.endDate}`);
	}

	return initialFilter.concat(
		filterDates.join(
			activityPeriod.dates.startDate > activityPeriod.dates.endDate
				? ' or '
				: ' and '
		)
	);
}
