/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {dealsChartStatus} from './constants/dealsChartStatus';
import getChartQuarterCount from './getDealsChartQuarterCount';

export default function getLeadsChartValues(leads: any[]) {
	const INITIAL_LEADS_CHART_VALUES = {
		rejected: [0, 0, 0, 0],
		submitted: [0, 0, 0, 0],
	};

	return leads?.reduce((accumulatedChartValues, item) => {
		if (item.leadStatus === dealsChartStatus.STATUS_CAMREJECTED) {
			accumulatedChartValues.rejected = getChartQuarterCount(
				accumulatedChartValues.rejected,
				item.dateCreated
			);
		}
		if (
			item.leadType === dealsChartStatus.TYPE_PARTNER_QUALIFIED_LEAD &&
			(item.leadStatus !==
				dealsChartStatus.STATUS_SALES_QUALIFIED_OPPORTUNITY ||
				item.leadStatus !== dealsChartStatus.STATUS_CAMREJECTED)
		) {
			accumulatedChartValues.submitted = getChartQuarterCount(
				accumulatedChartValues.submitted,
				item.dateCreated
			);
		}

		return accumulatedChartValues;
	}, INITIAL_LEADS_CHART_VALUES);
}
