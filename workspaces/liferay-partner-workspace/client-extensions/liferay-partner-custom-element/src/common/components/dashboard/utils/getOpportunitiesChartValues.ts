/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {dealsChartStatus} from './constants/dealsChartStatus';
import getChartQuarterCount from './getDealsChartQuarterCount';
import isNotOpportunity from './getIsNotOpportunityDeals';

const INITIAL_OPPORTUNITIES_CHART_VALUES = {
	approved: [0, 0, 0, 0],
	closedWon: [0, 0, 0, 0],
	rejected: [0, 0, 0, 0],
};

export function getOpportunitiesChartValues(
	opportunities: any[]
): typeof INITIAL_OPPORTUNITIES_CHART_VALUES {
	return opportunities?.reduce(
		(accumulatedChartValues, currentOpportunity) => {
			if (!isNotOpportunity(currentOpportunity)) {
				accumulatedChartValues.approved = getChartQuarterCount(
					accumulatedChartValues.approved,
					currentOpportunity.dateCreated
				);
			}

			if (currentOpportunity.stage === dealsChartStatus.STAGE_CLOSEDWON) {
				accumulatedChartValues.closedWon = getChartQuarterCount(
					accumulatedChartValues.closedWon,
					currentOpportunity.dateCreated
				);
			}
			if (currentOpportunity.stage === dealsChartStatus.STAGE_REJECTED) {
				accumulatedChartValues.rejected = getChartQuarterCount(
					accumulatedChartValues.rejected,
					currentOpportunity.dateCreated
				);
			}

			return accumulatedChartValues;
		},
		INITIAL_OPPORTUNITIES_CHART_VALUES
	);
}
