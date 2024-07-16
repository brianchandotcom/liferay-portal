/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FORMAT_DATE_TYPES} from './constants';
import getDateCustomFormat from './getDateCustomFormat';

export default function getActivationStatusDateRange(orderItems) {
	const dates = orderItems.reduce(
		(dateAccumulator, orderItem) => ({
			endDates: [...dateAccumulator.endDates, orderItem.options.endDate],
			startDates: [
				...dateAccumulator.startDates,
				orderItem.options.startDate,
			],
		}),
		{endDates: [], startDates: []}
	);
	const earliestStartDate = new Date(
		Math.min(...dates.startDates.map((date) => new Date(date)))
	).toString();
	const farthestEndDate = new Date(
		Math.max(...dates.endDates.map((date) => new Date(date)))
	).toString();
	const activationStatusDateRange = `${getDateCustomFormat(
		earliestStartDate,
		FORMAT_DATE_TYPES.day2DMonthSYearN
	)} - ${getDateCustomFormat(
		farthestEndDate,
		FORMAT_DATE_TYPES.day2DMonthSYearN
	)}`;

	return activationStatusDateRange;
}
