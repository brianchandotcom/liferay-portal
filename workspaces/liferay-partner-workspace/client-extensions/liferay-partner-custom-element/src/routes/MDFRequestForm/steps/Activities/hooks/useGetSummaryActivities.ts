/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestActivity from '../../../../../common/interfaces/mdfRequestActivity';
import getMaxDateActivity from '../../../../../common/utils/getMaxDateActivity';
import getMinDateActivity from '../../../../../common/utils/getMinDateActivity';
import getTotalBudget from '../../../../../common/utils/getTotalBudget';
import getTotalMDFRequest from '../../../../../common/utils/getTotalMDFRequest';

interface DateActivities {
	endDates: string[];
	startDates: string[];
}

export default function useGetSummaryActivities(
	mdfRequestActivities: MDFRequestActivity[]
) {
	const datesActivities = mdfRequestActivities.reduce<DateActivities>(
		(previousValue, currentValue) => {
			const endDateAccumulator = previousValue.endDates;
			const startDateAccumulator = previousValue.startDates;

			if (currentValue.endDate) {
				endDateAccumulator.push(currentValue.endDate);
			}

			if (currentValue.startDate) {
				startDateAccumulator.push(currentValue.startDate);
			}

			return {
				endDates: endDateAccumulator,
				startDates: startDateAccumulator,
			};
		},
		{endDates: [], startDates: []}
	);

	return {
		maxDateActivity: getMaxDateActivity(datesActivities.endDates),
		minDateActivity: getMinDateActivity(datesActivities.startDates),
		totalCostOfExpense: getTotalBudget(mdfRequestActivities),
		totalMDFRequestAmount: getTotalMDFRequest(mdfRequestActivities),
	};
}
